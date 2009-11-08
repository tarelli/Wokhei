/**
 * 
 */
package com.brainz.wokhei.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brainz.wokhei.resources.PayPalStrings;
import com.brainz.wokhei.server.InvoiceSender;
import com.brainz.wokhei.server.OrderServiceImpl;
import com.brainz.wokhei.shared.InvoiceDTO;
import com.brainz.wokhei.shared.Status;
import com.brainz.wokhei.shared.TransactionType;


/**
 * @author matteocantarelli
 *
 */
public class PaymentDoneServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6003934788688102743L;

	private static final Logger log = Logger.getLogger(PaymentDoneServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		log.log(Level.INFO,"-->PaymentDone - IPN Handler Start<--");

		// read post from PayPal system and add 'cmd'
		Enumeration en = req.getParameterNames();
		String str = PayPalStrings.PAYPAL_CMD_NOTIFY_VALIDATE.getString();
		while(en.hasMoreElements()){
			String paramName = (String)en.nextElement();
			String paramValue = req.getParameter(paramName);
			str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue);
		}

		// post back to PayPal system to validate
		// NOTE: change http: to https: in the following URL to verify using SSL (for increased security).
		// using HTTPS requires either Java 1.4 or greater, or Java Secure Socket Extension (JSSE)
		// and configured for older versions.
		URL u = new URL(PayPalStrings.PAYPAL_ACTION.getString());
		URLConnection uc = u.openConnection();
		uc.setDoOutput(true);
		uc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		PrintWriter pw = new PrintWriter(uc.getOutputStream());
		pw.println(str);
		pw.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		String resX = in.readLine();
		in.close();

		// assign posted variables to local variables
		// we run checks on some of these before processing the transaction
		String itemName = req.getParameter("item_name");
		String itemNumber = req.getParameter("item_number");
		String paymentStatus = req.getParameter("payment_status");
		Double paymentAmount = Double.parseDouble(req.getParameter("mc_gross"));
		String paymentCurrency = req.getParameter("mc_currency");
		String txnId = req.getParameter("txn_id");
		String receiverEmail = req.getParameter("receiver_email");
		String payerEmail = req.getParameter("payer_email");
		//get our custom pass-through variable containing orderID
		Long orderId = Long.parseLong(req.getParameter(PayPalStrings.PAYPAL_CUSTOM_NAME.getString()));
		TransactionType transactionType =TransactionType.valueOf(req.getParameter(PayPalStrings.PAYPAL_TRANSACTIONTYPE_NAME.getString()));

		//check notification validation
		if(resX.equals("VERIFIED")) {
			// check that paymentStatus = Completed
			//TODO: check that txnId has not been previously processed
			// check that receiverEmail is your Primary PayPal email
			// check that paymentAmount/paymentCurrency are correct


			if((paymentStatus.equalsIgnoreCase("pending") || paymentStatus.equalsIgnoreCase("completed")) 
					&& receiverEmail.equalsIgnoreCase(PayPalStrings.PAYPAL_BUSINESS_VALUE.getString())
					//&& paymentAmount==transactionType.getTotal() FIXTHIS!!
					&& paymentCurrency.equalsIgnoreCase(PayPalStrings.PAYPAL_CURRENCY_VALUE.getString()))
			{
				switch(transactionType)
				{
				case BUYING_LOGO:
				{
					// process payment
					// here we do our shit (which is hot SHITE)
					OrderServiceImpl orderService=new OrderServiceImpl();
					InvoiceDTO invoiceDTO = orderService.attachInvoice(orderId);
					orderService.setOrderStatus(orderId, Status.BOUGHT);

					if(invoiceDTO!=null)
					{
						InvoiceSender.sendInvoice(invoiceDTO);
						log.log(Level.INFO,"Payment completed successfully for order: " + orderId + " | invoiceDetails: #" + invoiceDTO.getInvoiceNumber() + " - " + invoiceDTO.getEmail());
					}
					else
					{
						log.log(Level.SEVERE,"Payment completed request but there was an error generating the invoice (most likely no user logged)");
					}

					break;
				}
				case MICROPAYMENT:
				{
					OrderServiceImpl orderService=new OrderServiceImpl();
					orderService.setOrderStatus(orderId, Status.INCOMING);
					log.log(Level.INFO,"MicroPayment completed successfully for order: " + orderId);
					break;
				}
				}
			}
			else
			{
				logError(paymentStatus, paymentAmount, paymentCurrency,
						receiverEmail, transactionType);
			}


		}
		else if(resX.equals("INVALID")) {
			// log for investigation
			log.log(Level.SEVERE, "Payal IPN returned INVALID transaction - needs investigation!");
		}
		else {
			// error
			log.log(Level.SEVERE, "Payal IPN returned unexpected value ["+ resX +"] - WTF!");
		}

		log.log(Level.INFO,"-->PaymentDone - IPN Handler End<--");
	}


	/**
	 * @param paymentStatus
	 * @param paymentAmount
	 * @param paymentCurrency
	 * @param receiverEmail
	 */
	private void logError(String paymentStatus, Double paymentAmount,
			String paymentCurrency, String receiverEmail, TransactionType transactionType) 
	{
		// some errors in the parameters checked
		String errorMsg = "Checked failed on Paypal parameters sent with IPN, actual values follow: ";
		errorMsg += "paymentStatus = " + paymentStatus + "[" + (paymentStatus.equalsIgnoreCase("pending") || paymentStatus.equalsIgnoreCase("completed")) + "]";
		errorMsg += " - receiverEmail = " + receiverEmail + "[" + receiverEmail.equalsIgnoreCase(PayPalStrings.PAYPAL_BUSINESS_VALUE.getString()) + "]";
		errorMsg += " - paymentAmount = " + paymentAmount + "[" + /*paymentAmount==transactionType.getTotal() + FIX THIS!!*/ "]";
		errorMsg += " - paymentCurrency = " + paymentCurrency + "[" + paymentCurrency.equalsIgnoreCase(PayPalStrings.PAYPAL_CURRENCY_VALUE.getString()) + "]";
		// log the error
		log.log(Level.SEVERE, errorMsg);
	}
}

/**
 * 
 */
package com.brainz.wokhei.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brainz.wokhei.server.OrderServiceImpl;
import com.brainz.wokhei.shared.InvoiceDTO;


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
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		Long orderId = Long.parseLong(req.getParameter("orderid"));

		OrderServiceImpl orderService=new OrderServiceImpl();
		InvoiceDTO invoiceDTO = orderService.attachInvoice(orderId);

		if(invoiceDTO!=null)
		{
			res.sendRedirect("getInvoice?invoiceNumber="+invoiceDTO.getInvoiceNumber()+"&nick="+invoiceDTO.getNick()+"&mail="+invoiceDTO.getEmail());
		}
		else
		{
			log.log(Level.SEVERE,"Payment completed request but there was an error generating the invoice (most likely no user logged)");
		}
	}
}

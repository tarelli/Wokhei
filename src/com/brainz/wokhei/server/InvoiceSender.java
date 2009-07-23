/*
 *
 *  Sean C. Sullivan 
 *  June 2003
 * 
 *  URL:  http://www.seansullivan.com/
 *  
 */

package com.brainz.wokhei.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Mails;
import com.brainz.wokhei.shared.InvoiceDTO;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.lowagie.text.pdf.draw.VerticalPositionMark;


/**
 * @author matteocantarelli
 *
 */
public class InvoiceSender extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7831595424625538546L;

	private static final Logger log = Logger.getLogger(InvoiceSender.class.getName());




	/**
	 * @param invoiceNumber
	 * @param nickName
	 * @param email
	 * @return
	 * @throws DocumentException
	 */
	private static ByteArrayOutputStream getInvoicePDF(InvoiceDTO invoiceDetails) throws DocumentException
	{
		Document doc = new Document();

		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		PdfWriter docWriter = null;

		try
		{
			docWriter = PdfWriter.getInstance(doc, baosPDF);

			doc.addAuthor("Wokhei");
			doc.addCreationDate();
			doc.addCreator("Wokhei");
			doc.addTitle("Wokhei invoice");

			doc.setPageSize(PageSize.A4);

			HeaderFooter footer = new HeaderFooter(
					new Phrase("*EU customers: prices exclude VAT"),
					false);

			doc.setFooter(footer);

			doc.open();

			doc.add(new Paragraph(""));
			try {
				Image image = Image.getInstance(Images.LOGO.getImageURL());
				image.scalePercent(30);
				doc.add(image);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);
			Phrase myPhrase =
				new Phrase("INVOICE",
						new Font(Font.HELVETICA, 20, Font.BOLD));

			Paragraph invoice=new Paragraph();
			invoice.setAlignment(Paragraph.ALIGN_CENTER);
			invoice.add(myPhrase);
			doc.add(invoice);

			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);


			doc.add(new Paragraph(
					"Invoice number: W0000"
					+ invoiceDetails.getInvoiceNumber()));

			doc.add(new Paragraph(
					"Issued on: "
					+ new java.util.Date()));

			doc.add(Chunk.NEWLINE);

			Paragraph user=new Paragraph(
					"User billed: "
					+  invoiceDetails.getNick()+"("+ invoiceDetails.getEmail()+")");
			user.setAlignment(Paragraph.ALIGN_RIGHT);
			doc.add(user);

			Phrase paypal =
				new Phrase("For further details see the PayPal receipt.",
						new Font(Font.HELVETICA, 8, Font.BOLD));

			Paragraph payPalP=new Paragraph();
			payPalP.setAlignment(Paragraph.ALIGN_RIGHT);
			payPalP.add(paypal);
			doc.add(payPalP);

			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);

			VerticalPositionMark separator = new LineSeparator(); 
			doc.add(separator);
			Phrase logoType =
				new Phrase("Logo Type: ",
						new Font(Font.HELVETICA, 11, Font.BOLD));

			Phrase logoTypeName =
				new Phrase("Stir Fried Logo 24h",
						new Font(Font.HELVETICA, 11, Font.NORMAL));

			Paragraph logoTypeP=new Paragraph();
			logoTypeP.setAlignment(Paragraph.ALIGN_LEFT);
			logoTypeP.add(logoType);
			logoTypeP.add(logoTypeName);
			doc.add(logoTypeP);

			Phrase price =
				new Phrase("Price: ",
						new Font(Font.HELVETICA, 11, Font.BOLD));

			Phrase priceName =
				new Phrase("EUR Û49*",
						new Font(Font.HELVETICA, 11, Font.NORMAL));

			Paragraph priceP=new Paragraph();
			priceP.setAlignment(Paragraph.ALIGN_LEFT);
			priceP.add(price);
			priceP.add(priceName);
			doc.add(priceP);

			doc.add(Chunk.NEWLINE);

			VerticalPositionMark separator2 = new LineSeparator(); 
			doc.add(separator2);
			doc.add(Chunk.NEWLINE);
			doc.add(new Paragraph("Printhouse S.r.l."));
			doc.add(new Paragraph("Via della Pineta 88/a"));
			doc.add(new Paragraph("09126 Cagliari"));
			doc.add(new Paragraph("Italy"));
			doc.add(new Paragraph("VAT number: 01510980921"));

			try {
				Image image = Image.getInstance(Images.PAID.getImageURL());
				image.scalePercent(40);
				image.setAlignment(Image.ALIGN_CENTER);
				doc.add(image);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		catch (DocumentException dex)
		{
			baosPDF.reset();
			throw dex; 
		}
		finally
		{
			if (doc != null)
			{
				doc.close();
			}
			if (docWriter != null)
			{
				docWriter.close();
			}
		}

		if (baosPDF.size() < 1)
		{
			throw new DocumentException(
					"document has "
					+ baosPDF.size()
					+ " bytes");		
		}
		return baosPDF;
	}


	public static void sendInvoice(InvoiceDTO invoiceDTO) {


		log.log(Level.INFO, "Request to send an invoce Received");

		ByteArrayOutputStream baosPDF=null;
		try {
			baosPDF = getInvoicePDF(invoiceDTO);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		String sbFilename = "wokheiInvoice"+invoiceDTO.getInvoiceNumber()+".pdf";
		String contentType="application/pdf";

		StringBuffer sbContentDispValue = new StringBuffer();
		sbContentDispValue.append("inline");
		sbContentDispValue.append("; filename=");
		sbContentDispValue.append(sbFilename);


		String[] recipientsAccountancy={Mails.INVOICES.getMailAddress()};
		String titleCustomer="Wokhei payment receipt";
		String bodyCustomer="In attachment you can find the invoice for the purchase of your Stir Fried Logo.\nWokhei\nwww.wokhei.com";
		String titleAccountancy="Wokhei Invoice number "+ invoiceDTO.getInvoiceNumber()+" issued";
		String bodyAccountancy=invoiceDTO.getEmail()+" has completed the payment for his logo. Invoice "+invoiceDTO.getInvoiceNumber()+" has been automatically issued and sent to him.";

		String[] recipientsCustomer={invoiceDTO.getEmail()};

		Attachment[] attachments={new Attachment(baosPDF.toByteArray(),sbFilename,contentType)};

		log.log(Level.INFO, "About to send emails");

		EmailSender.sendEmailWithAttachments(Mails.YOURLOGO.getMailAddress(), Arrays.asList(recipientsAccountancy), titleAccountancy, bodyAccountancy, Arrays.asList(attachments));
		EmailSender.sendEmailWithAttachments(Mails.YOURLOGO.getMailAddress(), Arrays.asList(recipientsCustomer), titleCustomer, bodyCustomer, Arrays.asList(attachments));

		log.log(Level.INFO, "Emails sent, now redirecting to home");

		if (baosPDF != null)
		{
			baosPDF.reset();
		}

	}






}

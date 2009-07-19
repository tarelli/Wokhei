/*
 *
 *  Sean C. Sullivan 
 *  June 2003
 * 
 *  URL:  http://www.seansullivan.com/
 *  
 */

package com.brainz.wokhei.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Mails;
import com.brainz.wokhei.server.Attachment;
import com.brainz.wokhei.server.EmailSender;
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
public class SendInvoiceServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7831595424625538546L;

	/** 
	 * 
	 * 
	 */
	public SendInvoiceServlet()
	{
		super();
	}

	/**
	 *  
	 * 
	 * we implement doGet so that this servlet will process all 
	 * HTTP GET requests
	 * 
	 * @param req HTTP request object 
	 * @param resp HTTP response object
	 * 
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws javax.servlet.ServletException, java.io.IOException
	{
		DocumentException ex = null;

		ByteArrayOutputStream baosPDF = null;

		try
		{
			baosPDF = generatePDFDocumentBytes(req, this.getServletContext());

			String sbFilename = "wokheiInvoice"+req.getParameter("invoiceNumber")+"Wokhei.pdf";
			String contentType="application/pdf";

			resp.setHeader("Cache-Control", "max-age=30");
			resp.setContentType(contentType);

			StringBuffer sbContentDispValue = new StringBuffer();
			sbContentDispValue.append("inline");
			sbContentDispValue.append("; filename=");
			sbContentDispValue.append(sbFilename);

			resp.setHeader(
					"Content-disposition",
					sbContentDispValue.toString());

			resp.setContentLength(baosPDF.size());

			//			ServletOutputStream sos;
			//
			//			sos = resp.getOutputStream();
			//
			//			baosPDF.writeTo(sos);

			String[] recipientsAccountancy={Mails.INVOICES.getMailAddress()};
			String titleCustomer="Wokhei payment receipt";
			String bodyCustomer="In attachment you can find the invoice for the purchase of your Stir Fried Logo.\nWokhei\nwww.wokhei.com";
			String titleAccountancy="Wokhei Invoice number "+ req.getParameter("invoiceNumber")+" issued";
			String bodyAccountancy=req.getParameter("mail")+" has completed the payment for his logo. Invoice "+req.getParameter("invoiceNumber")+" has been automatically issued and sent to him.";

			String[] recipientsCustomer={req.getParameter("mail")};

			Attachment[] attachments={new Attachment(baosPDF.toByteArray(),sbFilename,contentType)};

			EmailSender.sendEmailWithAttachments(Mails.YOURLOGO.getMailAddress(), Arrays.asList(recipientsAccountancy), titleAccountancy, bodyAccountancy, Arrays.asList(attachments));
			EmailSender.sendEmailWithAttachments(Mails.YOURLOGO.getMailAddress(), Arrays.asList(recipientsCustomer), titleCustomer, bodyCustomer, Arrays.asList(attachments));

			resp.sendRedirect("/home.html");

			//			sos.flush();
		}
		catch (DocumentException dex)
		{
			resp.setContentType("text/html");
			PrintWriter writer = resp.getWriter();
			writer.println(
					this.getClass().getName() 
					+ " caught an exception: " 
					+ dex.getClass().getName()
					+ "<br>");
			writer.println("<pre>");
			dex.printStackTrace(writer);
			writer.println("</pre>");
		}
		finally
		{
			if (baosPDF != null)
			{
				baosPDF.reset();
			}
		}

	}

	/**
	 *  
	 * @param req must be non-null
	 * 
	 * @return a non-null output stream. The output stream contains
	 *         the bytes for the PDF document
	 * 
	 * @throws DocumentException
	 * 
	 */
	protected ByteArrayOutputStream generatePDFDocumentBytes(
			final HttpServletRequest req,
			final ServletContext ctx)
	throws DocumentException

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

			String invoiceNumber = req.getParameter("invoiceNumber");
			doc.add(new Paragraph(
					"Invoice number: W0000"
					+ invoiceNumber));

			doc.add(new Paragraph(
					"Issued on: "
					+ new java.util.Date()));

			doc.add(Chunk.NEWLINE);

			String nickName=req.getParameter("nick");
			String email=req.getParameter("mail");

			Paragraph user=new Paragraph(
					"User billed: "
					+ nickName+"("+email+")");
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



}

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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.server.EmailSender;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.lowagie.text.pdf.draw.VerticalPositionMark;


/**
 * @author matteocantarelli
 *
 */
public class PDFInvoiceServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7831595424625538546L;

	/** 
	 * 
	 * 
	 */
	public PDFInvoiceServlet()
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

			String sbFilename = "invoiceWokhei.pdf";

			////////////////////////////////////////////////////////
			// Note: 
			//
			// It is important to set the HTTP response headers 
			// before writing data to the servlet's OutputStream 
			//
			////////////////////////////////////////////////////////
			//
			//
			// Read the HTTP 1.1 specification for full details
			// about the Cache-Control header
			//
			resp.setHeader("Cache-Control", "max-age=30");

			resp.setContentType("application/pdf");

			//
			//
			// The Content-disposition header is explained
			// in RFC 2183
			//
			//    http://www.ietf.org/rfc/rfc2183.txt
			//
			// The Content-disposition value will be in one of 
			// two forms:
			//
			//   1)  inline; filename=foobar.pdf
			//   2)  attachment; filename=foobar.pdf
			//
			// In this servlet, we use "inline"
			//
			StringBuffer sbContentDispValue = new StringBuffer();
			sbContentDispValue.append("inline");
			sbContentDispValue.append("; filename=");
			sbContentDispValue.append(sbFilename);

			resp.setHeader(
					"Content-disposition",
					sbContentDispValue.toString());

			resp.setContentLength(baosPDF.size());

			ServletOutputStream sos;

			sos = resp.getOutputStream();

			baosPDF.writeTo(sos);

			EmailSender.sendEmailWithInvoice("yourLogo@wokhei.com", "matteo.cantarelli@gmail.com", "Invoice", "In attachment your invoice", baosPDF.toByteArray());

			sos.flush();
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

			doc.add( makeGeneralRequestDetailsElement(req) );

			doc.add(Chunk.NEWLINE);


			VerticalPositionMark separator = new LineSeparator(); 
			doc.add(separator);
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

	/**
	 * 
	 * @param req HTTP request object
	 * @return an iText Element object
	 * 
	 */
	protected Element makeHTTPHeaderInfoElement(final HttpServletRequest req)
	{
		Map mapHeaders = new java.util.TreeMap();

		Enumeration enumHeaderNames = req.getHeaderNames();
		while (enumHeaderNames.hasMoreElements())
		{
			String strHeaderName = (String) enumHeaderNames.nextElement();
			String strHeaderValue = req.getHeader(strHeaderName);

			if (strHeaderValue == null)
			{
				strHeaderValue = "";
			}
			mapHeaders.put(strHeaderName, strHeaderValue);
		}

		Table tab = makeTableFromMap(
				"HTTP header name",
				"HTTP header value",
				mapHeaders);

		return tab;
	}

	/**
	 *  
	 * @param req HTTP request object 
	 * @return an iText Element object
	 * 
	 */
	protected Element makeGeneralRequestDetailsElement(
			final HttpServletRequest req)
	{
		Map<String,String> mapRequestDetails = new TreeMap<String,String>();

		mapRequestDetails.put("Stir Fried Logo 24h", "EUR Û49*");

		Table tab = null;

		tab = makeTableFromMap(
				"Logo Type", 
				"Price",
				mapRequestDetails);

		return tab;
	}

	/**
	 * 
	 * 
	 * @param req HTTP request object
	 * @return an iText Element object
	 * 
	 */
	protected Element makeHTTPParameterInfoElement(
			final HttpServletRequest req)
	{
		Map mapParameters = null;

		mapParameters = new java.util.TreeMap(req.getParameterMap());

		Table tab = null;

		tab = makeTableFromMap(
				"HTTP parameter name",
				"HTTP parameter value",
				mapParameters);

		return tab;
	}

	/**
	 *
	 * @param firstColumnTitle
	 * @param secondColumnTitle
	 * @param m map containing the data for column 1 and column 2
	 * 
	 * @return an iText Table
	 * 
	 */
	private static Table makeTableFromMap(
			final String firstColumnTitle,
			final String secondColumnTitle,
			final java.util.Map m)
	{
		Table tab = null;

		try
		{
			tab = new Table(2 /* columns */);
		}
		catch (BadElementException ex)
		{
			throw new RuntimeException(ex);
		}

		tab.setBorderWidth(2.0f);
		tab.setPadding(4);
		//		tab.setSpacing(5);

		tab.addCell(new Cell(firstColumnTitle));
		tab.addCell(new Cell(secondColumnTitle));

		tab.endHeaders();

		if (m.keySet().size() == 0)
		{
			Cell c = new Cell("none");
			c.setColspan(tab.getColumns());
			tab.addCell(c);
		}
		else
		{
			Iterator iter = m.keySet().iterator();
			while (iter.hasNext())
			{
				String strName = (String) iter.next();
				Object value = m.get(strName);
				String strValue = null;
				if (value == null)
				{
					strValue = "";
				}
				else if (value instanceof String[])
				{
					String[] aValues = (String[]) value;   
					strValue = aValues[0];
				}
				else
				{
					strValue = value.toString();
				}

				tab.addCell(new Cell(strName));
				tab.addCell(new Cell(strValue));
			}
		}

		return tab;
	}

}

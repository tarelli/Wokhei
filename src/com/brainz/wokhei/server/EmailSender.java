package com.brainz.wokhei.server;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EmailSender {

	private static final Logger log = Logger.getLogger(EmailSender.class.getName());

	/**
	 * @param sender
	 * @param recipients
	 * @param subject
	 * @param msgBody
	 */
	public static void sendEmail(String sender, List<String> recipients, String subject, String msgBody)
	{
		//-----------------------------------------------------------------------------
		// send email
		Properties props = new Properties();
		Session sessionX = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(sessionX);
			msg.setFrom(new InternetAddress(sender));
			for(String recipient : recipients)
			{
				msg.addRecipient(Message.RecipientType.TO,
						new InternetAddress(recipient));
			}
			msg.setSubject(subject);
			msg.setText(msgBody);
			Transport.send(msg);

		} catch (AddressException e) {
			log.log(Level.SEVERE, "setOrderStatus failed to send email to user: " + e.getMessage());
		} catch (MessagingException e) {
			log.log(Level.SEVERE, "setOrderStatus failed to send email to user: " + e.getMessage());
		}
		//-----------------------------------------------------------------------------
	}

	/**
	 * @param sender
	 * @param recipients
	 * @param subject
	 * @param msgBody
	 * @param attachmentData
	 */
	public static void sendEmailWithInvoice(String sender, String recipient, String subject, String msgBody, byte[] attachmentData)
	{
		//-----------------------------------------------------------------------------
		// send email
		Properties props = new Properties();
		Session sessionX = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(sessionX);
			msg.setFrom(new InternetAddress(sender));

			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(recipient));
			msg.setSubject(subject);
			msg.setText(msgBody);

			Multipart mp = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(msgBody, "text/html");

			MimeBodyPart attachment = new MimeBodyPart();
			DataSource src = new ByteArrayDataSource (attachmentData, "application/pdf"); 
			attachment.setFileName("invoiceWokhei.pdf"); 
			attachment.setDataHandler(new DataHandler (src)); 

			//put the parts together into a multipart 
			mp.addBodyPart(htmlPart); 
			mp.addBodyPart(attachment); 
			msg.setContent(mp);

			Transport.send(msg);

		} catch (AddressException e) {
			log.log(Level.SEVERE, "setOrderStatus failed to send email to user: " + e.getMessage());
		} catch (MessagingException e) {
			log.log(Level.SEVERE, "setOrderStatus failed to send email to user: " + e.getMessage());
		}
		//-----------------------------------------------------------------------------
	}
}

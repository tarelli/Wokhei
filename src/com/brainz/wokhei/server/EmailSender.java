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

import com.brainz.wokhei.resources.Messages;

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

			UtilityServiceImpl utilityService=new UtilityServiceImpl();
			String webSite=utilityService.isSandBox()?Messages.WEBSITE_SANDBOX.getString():Messages.WEBSITE.getString();

			Message msg = new MimeMessage(sessionX);
			msg.setFrom(new InternetAddress(sender));
			for(String recipient : recipients)
			{
				msg.addRecipient(Message.RecipientType.TO,
						new InternetAddress(recipient));
			}
			if(utilityService.isSandBox())
			{
				msg.setSubject(Messages.SANDBOX.getString()+ " "+subject);	
			}
			else
			{
				msg.setSubject(subject);
			}
			msg.setText(msgBody.replace("$w", webSite));
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
	public static void sendEmailWithAttachments(String sender, List<String> recipients, String subject, String msgBody, List<Attachment> attachments)
	{
		//-----------------------------------------------------------------------------
		// send email
		Properties props = new Properties();
		Session sessionX = Session.getDefaultInstance(props, null);

		try {
			UtilityServiceImpl utilityService=new UtilityServiceImpl();
			String webSite=utilityService.isSandBox()?Messages.WEBSITE.getString():Messages.WEBSITE_SANDBOX.getString();

			Message msg = new MimeMessage(sessionX);
			msg.setFrom(new InternetAddress(sender));

			for(String recipient : recipients)
			{
				msg.addRecipient(Message.RecipientType.TO,
						new InternetAddress(recipient));
			}
			if(utilityService.isSandBox())
			{
				msg.setSubject(Messages.SANDBOX.getString()+ " "+subject);	
			}
			else
			{
				msg.setSubject(subject);
			}
			msg.setText(msgBody.replace("$w", webSite));

			Multipart mp = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(msgBody.replace("$w", webSite), "text/html");
			mp.addBodyPart(htmlPart); 

			for(Attachment attachment : attachments)
			{
				MimeBodyPart attachmentPart = new MimeBodyPart();
				DataSource src = new ByteArrayDataSource (attachment.getData(), attachment.getContentType()); 
				attachmentPart.setFileName(attachment.getFilename()); 
				attachmentPart.setDataHandler(new DataHandler (src)); 
				mp.addBodyPart(attachmentPart); 
			}

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

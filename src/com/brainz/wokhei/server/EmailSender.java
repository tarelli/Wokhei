package com.brainz.wokhei.server;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	private static final Logger log = Logger.getLogger(EmailSender.class.getName());

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

}

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.logging.Level"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.brainz.wokhei.shared.Status"%>
<%@ page import="com.brainz.wokhei.shared.DateDifferenceCalculator"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="javax.jdo.*"%>
<%@ page import="javax.mail.internet.AddressException"%>
<%@ page import="javax.mail.internet.InternetAddress"%>
<%@ page import="javax.mail.MessagingException"%>
<%@ page import="javax.mail.internet.MimeMessage"%>
<%@ page import="javax.mail.Message"%>
<%@ page import="javax.mail.Session"%>
<%@ page import="javax.mail.Transport"%>
<%@ page import="com.brainz.wokhei.Order"%>
<%@ page import="com.brainz.wokhei.PMF"%>
<%@ page import="com.brainz.wokhei.server.EmailSender"%>
<%@ page import="com.brainz.wokhei.server.OrderServiceImpl" %>
<%@ page import="com.brainz.wokhei.shared.FileType" %>
<%@ page import="com.brainz.wokhei.resources.Messages" %>
<%@ page import="com.brainz.wokhei.resources.Mails" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>updateOrderStatus</title>
</head>
<%
	// declare logger --> class name needs changed
	Logger log = Logger.getLogger(com.brainz.wokhei.Order.class.getName());
	// log start of job
	log.info(" --> updateOrderStatus Job START <--");
	
	//declare stuff for sending emails
	Properties props = new Properties();
    Session sessionX = Session.getDefaultInstance(props, null);
	
	// declare list of updated orders (to be included in the email to admin)
	List<Order> updatedOrders = new ArrayList<Order>();

	//1.get server date time (create a date object and load it in a calendar)
	Date serverDate = new Date();

	// --> 2.get everything not incoming or rejected (might need to do some sort of staging)
	//prepare query
	PersistenceManager pm = PMF.get().getPersistenceManager();
	String select_query = "select from " + Order.class.getName();
	Query query = pm.newQuery(select_query);
	
	//prepare the filters and params - we could use the queryBuilder class to do this once refactored
	query.setFilter("status == paramStatus1");
	
	query.declareParameters("com.brainz.wokhei.shared.Status paramStatus1");
	
	try {
		//execute
		List<Order> acceptedOrders = (List<Order>) query.executeWithArray(Status.ACCEPTED);
		List<Order> inProgressOrders = (List<Order>) query.executeWithArray(Status.IN_PROGRESS);
		List<Order> qualityGateOrders = (List<Order>) query.executeWithArray(Status.QUALITY_GATE);
		List<Order> viewedOrders = (List<Order>) query.executeWithArray(Status.VIEWED);
		
		List<Order> orders = new ArrayList<Order>();
		
		//process normal order lifecycle (evevrything except viewed)
		orders.addAll(acceptedOrders);
		orders.addAll(inProgressOrders);
		orders.addAll(qualityGateOrders);
		
		//3.foreach order get diff between items and server timestamp
		for(Order order : orders)
		{
			//3.1.depending on the diff update statuses	
			float diffHours = DateDifferenceCalculator.getDifferenceInHours(order.getAcceptedDate(), serverDate);
			
			// we need to retrieve only accepted - in progress - quality gate
			if(diffHours > 4 && diffHours < 16)
			{
				if(order.getStatus() != Status.IN_PROGRESS)
				{
					order.setStatus(Status.IN_PROGRESS);
					updatedOrders.add(order);
					
					//-----------------------------------------------------------------------------
					//send an email to the user
				    String msgBody = Messages.EMAIL_ORDER_IN_PROGRESS.getString() + "\n\n";
				    
			    	//add updated orders to email
			    	msgBody+= 	"Order details: \n" + 
			    				"Text: " + order.getText() + "\n" + 
			    				"Tags: " + order.getTags().toString() + "\n" + 
			    				"Colour: " + order.getColour().toString() + "\n" + 
			    				"\n" +
			    				Messages.EMAIL_ORDER_IN_PROGRESS_FOOTER.getString() + "\n\n" +
			    				Messages.EMAIL_ORDER_GOODBYE.getString();
				
				    List<String> recipients = new ArrayList<String>();
				    recipients.add(order.getCustomer().getEmail());
			    
				    EmailSender.sendEmail(Mails.YOURLOGO.getMailAddress(), recipients, Messages.EMAIL_ORDER_SUBJ.getString(), msgBody);
				    //-----------------------------------------------------------------------------
				}
			}
			else if(diffHours > 16 && diffHours < 24)
			{
				if(order.getStatus() != Status.QUALITY_GATE)
				{
					order.setStatus(Status.QUALITY_GATE);
					updatedOrders.add(order);
					
					//-----------------------------------------------------------------------------
					//send an email to the user
				    String msgBody = Messages.EMAIL_ORDER_QUALITY_GATE.getString() + "\n\n";
				    
			    	//add updated orders to email
			    	msgBody+= 	"Order details: \n" + 
			    				"Text: " + order.getText() + "\n" + 
			    				"Tags: " + order.getTags().toString() + "\n" + 
			    				"Colour: " + order.getColour().toString() + "\n" + 
			    				"\n" +
			    				Messages.EMAIL_ORDER_QUALITY_GATE_FOOTER.getString() + "\n\n" +
			    				Messages.EMAIL_ORDER_GOODBYE.getString();
				
				    List<String> recipients = new ArrayList<String>();
				    recipients.add(order.getCustomer().getEmail());
			    
				    EmailSender.sendEmail(Mails.YOURLOGO.getMailAddress(), recipients, Messages.EMAIL_ORDER_SUBJ.getString(), msgBody);
				    //-----------------------------------------------------------------------------
				}
			}
			else if(diffHours > 24)
			{
				OrderServiceImpl orderService = new OrderServiceImpl();
			
				// check here to see if it's really ready
				if(orderService.hasFileUploaded(order.getId(), FileType.PDF_VECTORIAL_LOGO) && 
				   orderService.hasFileUploaded(order.getId(), FileType.PNG_LOGO_PRESENTATION) &&
				   orderService.hasFileUploaded(order.getId(), FileType.PNG_LOGO))
				{
				// update status
				order.setStatus(Status.READY);
				updatedOrders.add(order);
		
				//-----------------------------------------------------------------------------
				//send an email to the user
			    String msgBody = Messages.EMAIL_ORDER_READY.getString() + "\n\n";
			    
		    	//add updated orders to email
		    	msgBody+= 	"Order details: \n" +
		    				"Text: " + order.getText() + "\n" + 
		    				"Tags: " + order.getTags().toString() + "\n" + 
		    				"Colour: " + order.getColour().toString() + "\n" + 
		    				"\n" +
		    				Messages.EMAIL_ORDER_READY_FOOTER.getString() + "\n\n" +
		    				Messages.EMAIL_ORDER_GOODBYE.getString();
			
			    List<String> recipients = new ArrayList<String>();
			    recipients.add(order.getCustomer().getEmail());
		    
			    EmailSender.sendEmail(Mails.YOURLOGO.getMailAddress(), recipients, Messages.EMAIL_ORDER_READY_SUBJ.getString(), msgBody);
			    //-----------------------------------------------------------------------------
				}
			}	
		}
		
		// 5.if any loop through VIEWED orders and set to ARCHIVED if more than 24hrs from viewed timestamp
		for(Order order : viewedOrders)
		{
			if(order.getViewedDate() != null)
			{
				float diffHours = DateDifferenceCalculator.getDifferenceInHours(order.getViewedDate(), serverDate);
				if(diffHours > 24)
				order.setStatus(Status.ARCHIVED);
			}	
		}
		
	} catch (Exception ex) {
		log.log(Level.SEVERE, "update order Status Job failed to retrieve Orders: " + ex.getMessage());
	} finally {
		//any changes will be persisted here
		pm.close();
	}
	
	//-----------------------------------------------------------------------------
	// send email to admin with list of updated orders (maybe put it in a function)

    String msgBody = "updateOrderStatus job updated [" + ((Integer)updatedOrders.size()).toString() + "] Orders \n";
    for(Order updatedOrder :updatedOrders)
    {
    	//add updated orders to email
    	msgBody+= 	updatedOrder.getId() + " | " + 
    				updatedOrder.getCustomer().getEmail() + " | " + 
    				updatedOrder.getText() + " | " + 
    				updatedOrder.getTags().toString() + " | " + 
    				updatedOrder.getStatus() + " | " + 
    				updatedOrder.getDate().toString() + "\n";
    }

    List<String> recipients = new ArrayList<String>();
    // quasi tutti gli apostoli ed evangelisti mali
    recipients.add(Mails.GIOVANNI.getMailAddress());
    recipients.add(Mails.MATTEO.getMailAddress());
    if((Integer)updatedOrders.size() > 0)
    recipients.add(Mails.SIMONE.getMailAddress());
    
    EmailSender.sendEmail(Mails.YOURLOGO.getMailAddress(), recipients, "updateOrderStatus Job - " + serverDate.toString(), msgBody);
    //-----------------------------------------------------------------------------
    
    // log end of job
	log.info(" --> updateOrderStatus Job END <--");
%>
<body>
UPDATE ORDER STATUS JOB - Updated [<%= updatedOrders.size() %>] Orders - <%= serverDate.toString() %>
</body>
</html>
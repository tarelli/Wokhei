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
	
	//TODO --> 0. setup a timer for the timeout
	//TODO --> 0.1. before timeout reload the page so that the job keeps going
	
	// declare list of updated orders (to be included in the email to admin)
	List<Order> updatedOrders = new ArrayList<Order>();

	//1.get server date time (create a date object and load it in a calendar)
	Date date = new Date();
	Calendar calend = Calendar.getInstance();
	calend.setTime(date);

	//TODO --> 2.get everything not incoming or rejected (might need to do some sort of staging)
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
		
		List<Order> orders = new ArrayList<Order>();
		
		orders.addAll(acceptedOrders);
		orders.addAll(inProgressOrders);
		orders.addAll(qualityGateOrders);
		
		//3.foreach order get diff between items and server timestamp
		for(Order order : orders)
		{
			//4.depending on the diff update statuses	
			Calendar orderCalend = Calendar.getInstance();
			orderCalend.setTime(order.getDate());
			
			//Get the represented dates in milliseconds
			long servertimeNowMilis = calend.getTimeInMillis();
			long orderDateMillis = orderCalend.getTimeInMillis();
			//calculate diff in millisecs
			long diff = servertimeNowMilis - orderDateMillis;
			
			//difference in hours
			float diffHours = (float)diff / (60f * 60f * 1000f);
			float diffMinutes = (float)diff / (60f * 1000f);
			
			// we need to retrieve only accepted - in progress - quality gate
			if(diffHours > 4 && diffHours < 16)
			{
				order.setStatus(Status.IN_PROGRESS);
				updatedOrders.add(order);
			}
			else if(diffHours > 16 && diffHours < 24)
			{
				order.setStatus(Status.QUALITY_GATE);
				updatedOrders.add(order);
			}
			else if(diffHours > 24)
			{
				// need to put some check here to see if it's really ready 
				order.setStatus(Status.READY);
				updatedOrders.add(order);
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
	Properties props = new Properties();
    Session sessionX = Session.getDefaultInstance(props, null);

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

    try {
        Message msg = new MimeMessage(sessionX);
        msg.setFrom(new InternetAddress("yourlogo@wokhei.com"));
        msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress("matteo.cantarelli@wokhei.com"));
		msg.addRecipient(Message.RecipientType.TO,
       		 	new InternetAddress("giovanni.idili@wokhei.com"));
        msg.setSubject("updateOrderStatus job");
        msg.setText(msgBody);
        Transport.send(msg);

    } catch (AddressException e) {
    	log.log(Level.SEVERE, "update order Status Job failed to send email: " + e.getMessage());
    } catch (MessagingException e) {
    	log.log(Level.SEVERE, "update order Status Job failed to send email: " + e.getMessage());
    }
	//-----------------------------------------------------------------------------
	
    // log end of job
	log.info(" --> updateOrderStatus Job END <--");
%>
<body>
UPDATE ORDER STATUS JOB
</body>
</html>
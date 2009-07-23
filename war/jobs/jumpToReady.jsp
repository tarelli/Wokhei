<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
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
<%@ page import="com.brainz.wokhei.server.OrderServiceImpl"%>
<%@ page import="com.brainz.wokhei.shared.FileType"%>
<%@ page import="com.brainz.wokhei.resources.Messages"%>
<%@ page import="com.brainz.wokhei.resources.Mails"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>updateOrderStatus</title>
</head>
<%
	// declare logger --> class name needs changed
	Logger log = Logger.getLogger(com.brainz.wokhei.Order.class
			.getName());
	// log start of job
	log.info(" --> updateOrderStatus Job START <--");

	// --> 0. setup a timer for the timeout
	// --> 0.1. before timeout reload the page so that the job keeps going

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

	query
			.declareParameters("com.brainz.wokhei.shared.Status paramStatus1");

	try {
		//execute
		List<Order> acceptedOrders = (List<Order>) query
				.executeWithArray(Status.ACCEPTED);
		List<Order> inProgressOrders = (List<Order>) query
				.executeWithArray(Status.IN_PROGRESS);
		List<Order> qualityGateOrders = (List<Order>) query
				.executeWithArray(Status.QUALITY_GATE);

		List<Order> orders = new ArrayList<Order>();

		orders.addAll(acceptedOrders);
		orders.addAll(inProgressOrders);
		orders.addAll(qualityGateOrders);

		//3.foreach order get diff between items and server timestamp
		for (Order order : orders) {
			//4.depending on the diff update statuses	
			float diffHours = DateDifferenceCalculator
					.getDifferenceInHours(order.getAcceptedDate(),
							serverDate);

			OrderServiceImpl orderService = new OrderServiceImpl();

			// check here to see if it's really ready
			if (orderService.hasFileUploaded(order.getId(),
					FileType.PDF_VECTORIAL_LOGO)
					|| orderService.hasFileUploaded(order.getId(),
							FileType.PNG_LOGO_PRESENTATION)
					|| orderService.hasFileUploaded(order.getId(),
							FileType.PNG_LOGO)) 
			{
				// update status
				order.setStatus(Status.READY);
				updatedOrders.add(order);

				//-----------------------------------------------------------------------------
				//send an email with attachment (or link) to the user
				Properties props = new Properties();
				Session sessionX = Session.getDefaultInstance(props,
						null);

				String msgBody = Messages.EMAIL_ORDER_READY.getString()
						+ "\n\n";

				for (Order updatedOrder : updatedOrders) {
					//add updated orders to email
					msgBody += "Logo Details: \n"
							+ "ID: "
							+ updatedOrder.getId()
							+ "\n"
							+ "Text: "
							+ updatedOrder.getText()
							+ "\n"
							+ "Tags: "
							+ updatedOrder.getTags().toString()
							+ "\n"
							+ "Colour: "
							+ updatedOrder.getColour().toString()
							+ "\n"
							+ updatedOrder.getTags().toString()
							+ "\n"
							+ "\n\n"
							+ "Visit [ http://www.wokhei.com ] to view and download your stir fried logo!";
				}

				List<String> recipients = new ArrayList<String>();
				recipients.add(order.getCustomer().getEmail());

				EmailSender.sendEmail(Mails.YOURLOGO.getMailAddress(),
						recipients, "Your Stir Fried Logo is Ready!",
						msgBody);
				//-----------------------------------------------------------------------------
			}
		}

	} catch (Exception ex) {
		log.log(Level.SEVERE,
				"update order Status Job failed to retrieve Orders: "
						+ ex.getMessage());
	} finally {
		//any changes will be persisted here
		pm.close();
	}

	//-----------------------------------------------------------------------------
	// send email to admin with list of updated orders (maybe put it in a function)
	Properties props = new Properties();
	Session sessionX = Session.getDefaultInstance(props, null);

	String msgBody = "updateOrderStatus job updated ["
			+ ((Integer) updatedOrders.size()).toString()
			+ "] Orders \n";
	for (Order updatedOrder : updatedOrders) {
		//add updated orders to email
		msgBody += updatedOrder.getId() + " | "
				+ updatedOrder.getCustomer().getEmail() + " | "
				+ updatedOrder.getText() + " | "
				+ updatedOrder.getTags().toString() + " | "
				+ updatedOrder.getStatus() + " | "
				+ updatedOrder.getDate().toString() + "\n";
	}

	List<String> recipients = new ArrayList<String>();
	EmailSender
			.sendEmail(Mails.YOURLOGO.getMailAddress(), recipients,
					"updateOrderStatus Job - " + serverDate.toString(),
					msgBody);
	//-----------------------------------------------------------------------------

	// log end of job
	log.info(" --> updateOrderStatus Job END <--");
%>
<body>
UPDATE ORDER STATUS JOB - Updated [<%=updatedOrders.size()%>] Orders -
<%=serverDate.toString()%>
</body>
</html>
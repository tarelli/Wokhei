<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.logging.Level"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.brainz.wokhei.shared.Status"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="javax.jdo.*"%>
<%@ page import="com.brainz.wokhei.Order"%>
<%@ page import="com.brainz.wokhei.PMF"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>updateOrderStatus</title>
</head>
<%
	Logger log = Logger.getLogger(com.brainz.wokhei.Order.class.getName());
	log.info(" --> updateOrderStatus Job START <--");
	//TODO --> 0. setup a timer for the timeout
	//TODO --> 0.1. before timeout reload the page so that the job keeps going

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
			}
			else if(diffHours > 16 && diffHours < 24)
			{
				order.setStatus(Status.QUALITY_GATE);
			}
			else if(diffHours > 24)
			{
				// need to put some check here to see if it's really ready 
				order.setStatus(Status.READY);
			}	
		}
		
	} catch (Exception ex) {
		log.log(Level.SEVERE, "update order Status Job failed to retrieve Orders: " + ex.getMessage());
	} finally {
		//any changes will be persisted here
		pm.close();
	}
	
	log.info(" --> updateOrderStatus Job END <--");
%>
<body>

</body>
</html>
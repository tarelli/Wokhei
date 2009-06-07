<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
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
	Logger log = Logger.getLogger("JOBS_updateOrderStatus");
	log.info(" --> updateOrderStatus Job START <--");
	//TODO --> 0. setup a timer for the timeout
	//TODO --> 0.1. before timeout reload the page so that the job keeps going

	//1.get server date time (just create a date object - this will be executed on the server)
	/*Date date = new Date();

	//TODO --> 2.get everything not incoming or rejected (might need to do some sort of staging)
	//prepare query
	PersistenceManager pm = PMF.get().getPersistenceManager();
	String select_query = "select from " + Order.class.getName();
	Query query = pm.newQuery(select_query);
	query.setFilter("status != paramStatus1 && status != paramStatus2");
	query.declareParameters("com.brainz.wokhei.shared.Status paramStatus1, com.brainz.wokhei.shared.Status paramStatus2");
	try {
		//execute
		//List<Order> orders = (List<Order>) query.execute(Status.ACCEPTED, Status.REJECTED);
		
		//TODO --> 3.foreach order get diff between items and server timestamp
		//TODO --> 4.depending on the diff update statuses
		// changes will be persisted when the pm is closed
		
	} catch (Exception ex) {
		log.log(Level.SEVERE, "update order Status Job failed to retrieve Orders");
	} finally {
		//any changes will be persisted here
		pm.close();
	}*/
	
	log.info(" --> updateOrderStatus Job END <--");
%>
<body>

</body>
</html>
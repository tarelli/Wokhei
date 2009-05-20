<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="javax.jdo.*"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.brainz.wokhei.Admin"%>
<%@ page import="com.brainz.wokhei.AdminAuthenticator"%>
<%@ page import="com.brainz.wokhei.Order"%>
<%@ page import="com.brainz.wokhei.shared.Status"%>
<%@ page import="com.brainz.wokhei.PMF"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Wokhei - Admin</title>
<link href="/stylesheets/style.css" rel="stylesheet" type="text/css" />
<!--                                           -->
<!-- This script loads your compiled module.   -->
<!-- If you add any GWT meta tags, they must   -->
<!-- be added before this line.                -->
<!--                                           -->
<script type="text/javascript" language="javascript" src="wokhei/wokhei.nocache.js"></script>
</head>

<body>

<div id="page-wrap">
<div class="header">
<%
	// redirection block
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	if (user == null) {
		// if you're not legged in go back to index
		response.sendRedirect("index.jsp");
	} else if (!(user.getEmail().equals("matteo.cantarelli@wokhei.com") || AdminAuthenticator.isAdmin(user))) {
		// if you're not admin go back to home - you son of a bitch
		response.sendRedirect("home.jsp");
	}
%>

<div class="signin"><a href="<%=userService.createLogoutURL(request.getRequestURI())%>"><a2>Logout</a2></a></div>
<div class="home"><a href="home.jsp"><a2>Home</a2></a></div>

</div>
<div class="body">

<div id="content">
<%
	if (user != null) {
		if (user.getEmail().equals("matteo.cantarelli@wokhei.com") || AdminAuthenticator.isAdmin(user)) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			String select_query = "select from "
					+ Order.class.getName();

			List<Order> orders = (List<Order>) pm.newQuery(select_query).execute();

			if (orders.isEmpty()) {
%>
<p>No logo orders</p>
<%
	} else {
%>
<p>All the orders:</p>
<%
	for (Order g : orders) {
					if (g.getCustomer() == null) {
%>
<p>An anonymous person ordered:</p>
<%
	} else {
%>
<p><b><%=g.getCustomer().getNickname()%></b> wrote:</p>
<%
	}
%>
<blockquote>
	
	<b>Order: <%=g.getText()%></b> - 
	<%=g.getTags()%> - 
	Status: <%=g.getStatus()%> - 
	<%=g.getDate().toString()%>
	<%
		if (g.getStatus() != null
								&& (g.getStatus().equals(Status.INCOMING))) {
	%>
	<a href="HELL">SEND LOGO</a> <a href="./rejectOrder?id=<%=g.getId()%>" >REJECT</a>
	<%
		}
	%>
</blockquote>
<%
	}
			}
			pm.close();
%> 
<br>
<!-- Submit Order Form -->
<div style="border: medium solid purple;"> 
<form action="/addadmin" method="post">
Email
<div><textarea name="email" rows="1" cols="30"></textarea></div>
<div><input type="submit" value="Add Admin!" /></div>
</form><br>
<%
	pm = PMF.get().getPersistenceManager();
	select_query = "select from " + Admin.class.getName();

	List<Admin> admins = (List<Admin>) pm.newQuery(select_query).execute();

		if (admins.isEmpty()) 
		{
%>
<p>No Admins</p>
<%
		} 
		else 
		{
%>
<p>All the admins:</p>
<%
			for (Admin a : admins) 
				{
%>
<p><b><%=a.getAdministrator().getNickname()%></b></p>
<%
				}
			}
			pm.close();
%> 
</div>
<%
		} 
		else 
		{
			response.sendRedirect("home.jsp");
		}
	} 
	else 
	{
		response.sendRedirect("index.jsp");
	}
%>
</div>

<!-- will put the gwt control here -->
<div class="adminConsole"></div>

</div>
<div class="bodytile">
</div>
<div class="footer">
</div>
</div>

</body>
</html>
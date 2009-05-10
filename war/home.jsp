<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="javax.jdo.*"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.brainz.wokhei.OrderUtils"%>
<%@ page import="com.brainz.wokhei.PMF"%>
<%@ page import="com.brainz.wokhei.Messages"%>
<%@ page import="com.brainz.wokhei.AdminAuthenticator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" language="javascript"
	src="wokhei/wokhei.nocache.js"></script>
<link href="/stylesheets/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Wokhei</title>

</head>

<body>
<div id="page-wrap">
<div class="header">
<div class="headerDoor"></div>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user == null) 
    {
    	response.sendRedirect("index.jsp");
    }
    else
    {
  		if(user.getEmail().equals(Messages.getString("Admin.0")) || AdminAuthenticator.isAdmin(user))
		{
%>
<div class="admin"><a href="/admin.jsp"><a2>Admin<a2></a></div>
<%
		}
    }
%>

<div class="signin"><a
	href="<%= userService.createLogoutURL(request.getRequestURI()) %>"><a2>Logout</a2></a></div>



</div>

<div class="body">



<div class="bodyDoor">
<div id="orderSubmitterAlternateBody"></div>
</div>

	<div id="ordersBrowser" class="orders">
	</div>
	
	<div id="ordersBrowserButtons" class="ordersButtons">
	</div>
	
	<div id="ordersBrowserImage" class="ordersImage">
	</div>
	
	
	<div id="orderSubmitter" class="submitOrder">
	<h3>Request your logo here</h3>
	</div>

</div>

<div class="bodytile">
<div class="bodytileDoor">
<div id="orderSubmitterAlternateBodytile"></div>
</div>

</div>

<div class="footer">
<div class="footerDoor">
<div id="orderSubmitterAlternateFooter"></div>
</div>

</div>

</div>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="javax.jdo.*"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.brainz.wokhei.Order"%>
<%@ page import="com.brainz.wokhei.shared.Status"%>
<%@ page import="com.brainz.wokhei.PMF"%>
<%@ page import="com.brainz.wokhei.Messages"%>
<%@ page import="com.brainz.wokhei.Admin"%>
<%@ page import="com.brainz.wokhei.AdminAuthenticator"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
<!--                                           -->
<!-- This script loads your compiled module.   -->
<!-- If you add any GWT meta tags, they must   -->
<!-- be added before this line.                -->
<!--                                           -->
<script type="text/javascript" language="javascript" src="wokhei/wokhei.nocache.js"></script>
</head>
<body>
<div class="wrapper">
<div class="left">
<a href="/home.jsp">
<img src="/images/logo.png" alt="It's a Win-Win!" />
</a>
</div>
<div class="right">
<%
	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) 
    {
%>
<p>Hello, <%=user.getNickname()%>! (You can <a
	href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
out</a>.)</p>
<%
	} 
    else 
    {
%>
<p>Hello! <a
	href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
in</a> to browse your orders!</p>
<%
	}
%>
</div>
</div>
<br>
<!-- Start of Content -->
<div class="wrapper">
<div id="content">
<%
	if(user!= null)
{
	if (user.getEmail().equals("tarelli") || AdminAuthenticator.isAdmin(user))
	{
    	PersistenceManager pm = PMF.get().getPersistenceManager();
    	String select_query = "select from " + Order.class.getName();  
 
    	List<Order> orders = (List<Order>) pm.newQuery(select_query).execute();
    
    	if (orders.isEmpty()) 
    	{
%>
<p>No logo orders</p>
<%
	} 
    	else 
    	{
%>
<p>All the orders:</p>
<%
	for (Order g : orders) 
        	{
            	if (g.getCustomer() == null) 
            	{
%>
<p>An anonymous person ordered:</p>
<%
	}
            	else 
            	{
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
		if(g.getStatus()!=null && (g.getStatus().equals(Status.INCOMING))) {
	%>
	<a href="HELL">SEND LOGO</a> <a href="./rejectOrder?id=<%= g.getId() %>" >REJECT</a>
	<%}%>
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
<p><b><%= a.getAdministrator().getNickname() %></b></p>
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
%>
<p>You are not enough of a motherfucking admin to see this fucking admin page. Harden the fuck up.</p>
<%
	}
}
else
{
%>
<p>First thing you need to fucking login to see this fucking page. Harden teh fuck up.</p>
<%	
}
%>
</div>
</div>

<!-- OPTIONAL: include this if you want history support -->
<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
</body>
</html>
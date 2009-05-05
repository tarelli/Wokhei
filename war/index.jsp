<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>WOKHEI - Stir Fried Logos</title>
<link href="/stylesheets/style.css" rel="stylesheet" type="text/css" />
</head>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) 
    {
    	response.sendRedirect("home.jsp");
    }
%>

<body>


<div id="page-wrap">
<div class="header">

<div class="takeTour">Take a tour!</div>
<div class="signin"><a href="<%= userService.createLoginURL(request.getRequestURI()) %>"><a1>Login</a1></a></div>

</div>
<div class="body">

<div class="join">Join Wokhei now!</div>
<div class="screen">
	<div class="logos">
	</div>
</div>

<div class="hat"></div>

<div class="clock"></div>

<div class="ready"></div>

</div>
<div class="bodytile">
</div>
<div class="footer">
</div>
</div>

</body>
</html>

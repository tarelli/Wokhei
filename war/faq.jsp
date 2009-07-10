<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script src="/js/cufon-yui.js" type="text/javascript"></script>
<script src="/js/Arial_Rounded_MT_Bold_400.font.js"
	type="text/javascript"></script>
<script type="text/javascript" language="javascript"
	src="wokhei/wokhei.nocache.js"></script>
<script type="text/javascript">
function applyCufon()
{
Cufon.replace('.fontAR');
}
</script>
<noscript>Sorry, Javascript is disable in your browser,
enable Javascript to use Wokhei.com</noscript>

<link href="/stylesheets/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Wokhei</title>

</head>

<body>
<div id="faq"></div>
<div id="page-wrap">

<div class="header"><div id="headerPanel" class="headerPanel"></div></div>
<div class="body">
<div id="faqBodyPart" class="pageBodyPart"></div>
</div>
<div class="bodytile"></div>
<div class="footer"><div id="footerPanel" class="footerPanel"></div>

</div>

<!-- OPTIONAL: include this if you want history support -->
<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
	style="position: absolute; width: 0; height: 0; border: 0"></iframe>
</body>
</html>

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
Cufon.replace('.oddRow');
Cufon.replace('.evenRow');
Cufon.replace('.headerRow');
}
</script>
<noscript>Sorry, Javascript is disable in your browser,
enable Javascript to use Wokhei.com</noscript>
</head>

<body>

<div id="admin"></div>

<div id="page-wrap" style="overflow: visible;">

<div class="header">
<div id="headerPanel" class="headerPanel"></div>
</div>

<div class="body">
<div class="adminModule" id="adminConsole"></div>
</div>

<div class="bodytileAdmin"></div>

<div class="footer">

<div id="footerPanel" class="footerPanel"></div>

</div>
</div>

</body>
</html>
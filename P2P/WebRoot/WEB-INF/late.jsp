<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'late.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/preview.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/templet.css">
  </head>
  
  <body>
    <div class="container">
    	<div class="content-box" style="text-align: center;">
    		<h2>阿偶，该问卷过期了</h2><a href="<%=basePath %>templet.jsp?type=publish">点我查看更多问卷吧！</a>
    	</div>
    </div>
  </body>
</html>

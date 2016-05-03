<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'error.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" href="<%=basePath %>css/preview.css">
	<link rel="stylesheet" href="<%=basePath %>css/templet.css">
	<style>
		.header a{float:left;display:block;height: 100%;line-height:71px;color:#fff;font-size:18px;margin-right:30px;}
	</style>
  </head>
  
  <body>
    <div class="container">
    	<div class="header">
    		<div class="h2" style="width:auto;">
				<a href="<%=basePath %>index.jsp" style="font-size:26px;">问卷网</a>
			</div>
			<a href="<%=basePath %>create.jsp" style="margin-left: 250px;">设计模板</a>
			<a href="<%=basePath %>templet.jsp">模板库</a>
    	</div>
    	<script>var time = 3;</script>
    	<div class="content-box">
    		<div class="content" style="text-align:center;">
    			<h2 id="message">阿偶出错了,3秒钟后返回主页</h2><a href="<%=basePath%>index.jsp">立即点我返回主页</a>
    			<script>
    			oMsg = document.getElementById('message');
    				setInterval(function(){time--;oMsg.innerHTML='阿偶出错了,'+time+'秒钟后返回主页';},1000);
    				setTimeout(function(){location.href='<%=basePath%>index.jsp'},3000);
    			</script>
    		</div>
    	</div>
    </div>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 发布创建模板名的页面 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'before.jsp' starting page</title>
<style>
	body {
	background-color: #EEEFF1;
}

a {
	text-decoration: none;
}

* {
	margin: 0;
	padding: 0;
	font-family: Microsoft Yahei;
}

.container {
	position: relative;
	width: 100%;
	height: 100%;
}

.header {
	height: 71px;
	background: #484848;
}

.h2 {
	display: block;
	float: left;
	height: 100%;
	width: 100px;
	font-size: 32px;
	line-height: 71px;
	color: #fff;
	margin-left: 24px;
}

.h2 a {
	color: #fff;
}

.head-area {
	float: left;
	height: 100%;
	line-height: 71px;
	margin-left: 300px;
}

.head-area a {
	color: #fff;
	height: 100%;
	width: 100px;
	display: block;
	float: left;
	margin-left: 10px;
	font-size: 16px;
	text-align: center;
}

.user-area a {
	color: #fff;
}

.user-area {
	float: right;
	display: block;
	height: 100%;
	text-align: center;
	line-height: 71px;
}
.content-box{
	margin: 20px auto;
    display: block;
    width: 400px;
    background: #fff;
}

.publish-title{
	text-align: center;
    padding-top: 12px;
}

.publish-title input{

}

.publish-duration{
	margin-left: 95px;
    margin-top: 10px;
}

.submit{
	text-align: center;
    margin: 10px;
}

.submit input{
	border: 0px;
    width: 100px;
    height: 35px;
    background: rgb(55,123,217);
    color: #fff;
}
</style>
  </head>
  <%
  	String flag = request.getParameter("tid");
  	Integer tid = null;
  	if(flag==null)response.sendRedirect("user.jsp");
  	else{
  		tid = Integer.parseInt(flag);
  	}
   %>
  <body>
    <div class="container">
    	<div class="header">
			<div class="h2">
				<a href="index.jsp">问卷网</a>
			</div>
			<div class="head-area">
				<a href="user.jsp">我设计的模板</a>
				<a href="user.jsp?type=publish">我发布的问卷</a> 
				<a href="user.jsp?type=collection">我收藏的模板</a> 
				<a href="templet.jsp">模板库</a>
			</div>
			<div class="user-area">
				<a href="member.jsp" style="margin-right:30px;"><%=session.getAttribute("username")%></a><a
					href="<%=path%>/ActionServlet?action=logout"
					style="margin-right:20px;">退出登录</a>
			</div>
		</div>
		<!-- END header -->
		<div class="content-box" style="border-radius: 3px;
    border: 1px #dedede solid;">
			<form action="<%=path %>/ActionServlet?action=publish&tid=<%=tid%>" method="post" style="padding:10px 0px;">
				<div class="publish-title">
					问卷名:<input type="text" name = "pname" id="pname" style="height:30px;width:180px;border:1px rgb(139,181,192) solid;">
				</div>
				<div class="publish-duration">
					结束时间:
					<select name="select" style="height:30px;width:120px;border:1px rgb(139,181,192) solid;">
						<option>1天后</option>
						<option>3天后</option>
						<option>5天后</option>
						<option>1周后</option>
						<option>2周后</option>
						<option>一月后</option>
					</select>
				</div>
				<div class="submit"> <input type="submit" value="提交" id="submit" style="border-radius:3px;"> </div>
				<script>
					var oSub = document.getElementById('submit');
					var oPname = document.getElementById('pname');
					oSub.onclick = function(){
						if(oPname.value.length<=0){
							alert('问卷名不能为空');
							return false;
						}
					}
				</script>
			</form>
		</div>
    </div>
  </body>
</html>

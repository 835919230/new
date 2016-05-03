<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 创建模板前的页面 -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>创建问卷</title>
	<link rel="stylesheet" href="css/create.css">
	<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
</head>
<body>

	<div class="container">
		<div class="header">
			<a href="templet.jsp?type=publish" class="back">返回</a>
			<div class="nav">
				<a href="create.jsp" class="active">创建全新问卷</a>
				<a href="templet.jsp">引用问卷模板</a>
				<a href="reference.jsp">复制已有问卷</a>
			</div>
		</div>
		<div class="main">
			<div class="box step_one tabs_content">
				<form action="createTemplate" method="post" id="MyForm">
					<h2>问卷标题</h2>
					<input type="textarea" style="border: 1px solid rgb(210, 210, 210);" name="title" id="title">
					<h2>编写您的寄语</h2>
					<input type="textarea" style="border: 1px solid rgb(210, 210, 210);" name="motal" id="motal" value="欢迎参加本次答题（在这里编写寄语）">
					<label style="margin-top:10px;display:block;font-size:16px;color:darkred;"><input type="checkbox" name="isshared" checked="checked" value="true" style="width:20px;height:16px;">同意分享该模板</label>
					<div class="btn-create">
						<button type="submit" id="btn-create">创建</button>
					</div>
				</form>
				<div class="message" id="message" style="display:none;">问卷标题不能为空</div>
				<div class="message" id="message2">寄语不能为空</div>
			</div>
			<script type="text/javascript">
				$(".main").find("#title").blur(function(){
					if($("#title").val().length<=0){
						$("#message").fadeIn();
						$("#btn-create").disabled = "disabled";
					}else{
						$("#btn-create").disabled = "";
					}
				}).click(function(event){
					$("#message").fadeOut();
					$("#btn-create").disabled = "";
				});
				
				$("#motal").blur(function(){
					if($("#motal").val().length<=0){
						$("#message2").fadeIn();
						$("#btn-create").disabled = "disabled";
					}else{
						$("#btn-create").disabled = "";
					}
				}).click(function(event){
					$("#message2").fadeOut();
					$("#btn-create").disabled = "";
				});
				$('#btn-create').click(function(e){
					if($("#title").val().length<=0||$("#motal").val().length<=0){
						$("#message").fadeIn();
						return false;
					}
				});
			</script>
		</div>
	</div>
</body>
</html>
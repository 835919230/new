<%@ page language="java" import="java.util.*,entity.*,DAO.*,util.*" contentType="text/html; charset=utf-8"  isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 更改用户信息的页面 -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>我的账户</title>
	<link rel="stylesheet" href="css/member.css">
	<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
</head>
<body>
	<input name="account" id="account" type="hidden" value="${sessionScope.account}">
	<div class="container">
		<div class="header">
			<a href="user.jsp" class="back">后退</a>
			<div class="h2">我的账户</div>
		</div>
		<div class="userinfo-box">
			<div class="userinfo-area">
				<div class="h2">账号信息</div>
				<div class="info">
					<span>用户名:</span>
					<span id="oldUsername">${username }</span>
					<a href="javascript:;" class="change" id="changeUsername">修改</a>
				</div>
				<div class="info">
					<span>密&nbsp;&nbsp;&nbsp;&nbsp;码:</span>
					<span>******</span>
					<a href="javascript:;" class="change" id="changePassword">修改</a>
				</div>
			</div>
		</div>
		<div class="data-message">更改成功</div>
		<div class="change-user" id="userarea">
			<form id="editUserForm" action="<%=path%>/memberServlet" method="post">
				<div class="h1">修改用户名
					<a href="javascript:;" id="close" class="close">X</a></div>
				<div class="edit-area">
					<span>新的用户名：</span>
					<input type="text" name="username" id="username">
				</div>
				<div class="edit-footer">
					<a href="javascript:;" class="save">保存</a>
				</div>
			</form>
		</div>
		<script>
			$("#userarea").find(".save").click(function(event){
				var length = $("#username").val().length;
				if(length>2&&length<17){
					$.ajax({
					type:"post",
					url:"http://localhost:8080/P2P/ActionServlet",
					data:{action:"username",account:$("#account").val(),oldUsername:$("#oldUsername").html(),newUsername:$("#username").val()},
					dataType:"json",
					success:function(data){
						if(data.success){
							$(".data-message").html(data.msg);
							$(".data-message").fadeIn(2000);
							setTimeout(function(){
							$(".data-message").fadeOut(2000);
							$(".change-user").fadeOut();
							$(".overhide").fadeOut();
							$("#oldUsername").html($("#username").val());
							},2000);
						}else{
							$(".data-message").html(data.msg);
							$(".data-message").fadeIn(2000);
							setTimeout(function(){
								$(".data-message").fadeOut(2000);
								$(".change-user").fadeOut();
								$(".overhide").fadeOut();
							},2000);
						}
					},
					error:function(jqXHR){
							if(jqXHR.status===500){
								$(".data-message").html("服务器正忙");
								$(".data-message").fadeIn(2000);
								setTimeout(function(){
								$(".data-message").fadeOut(2000);
								$(".change-user").fadeOut();
								$(".overhide").fadeOut();
							},2000);
							}
						}
				});
				}else{
					$(".data-message").html("用户名长度应该在3-16位之间");
					$(".data-message").fadeIn(300);
					setTimeout(function(){$(".data-message").fadeOut(1000);},1000)
				}
			})	
		</script>
		
		<div class="change-psw" id="pswarea">
			<div class="h1">修改密码<a href="javascript:;" id="close" class="close">X</a></div>
			<div class="edit-area">
				<span style="margin-right: 32px;">旧的密码：</span>
				<input type="password" name="oldpassword" id="password" class="oldPassword">
			</div>
			<div class="edit-area">
				<span style="margin-right: 32px;">新的密码：</span>
				<input type="password" name="newPassword" id="password" class="newPassword">
			</div>
			<div class="edit-area">
				<span>确认新的密码：</span>
				<input type="password" id="password" class="confirm">
			</div>
			<div class="edit-footer">
				<a href="javascript:;" class="save">保存</a>
			</div>
		</div>
		
		<script>
			$(".change-psw").find(".save").click(function(){
				if($(".confirm").val()!=$(".newPassword").val()){
					$(".data-message").html("新密码不一致，请检查");
					$(".data-message").fadeIn(300);
					setTimeout(function(){$(".data-message").fadeOut(1000);},1000);
					return false;
				}
				
				
				var length = $(".newPassword").val().length;
				if(length>=6&&length<=16){
					$.ajax({
						type:"post",
						url:"http://localhost:8080/P2P/ActionServlet",
						data:{action:"password",newPassword:$(".newPassword").val(),oldPassword:$(".oldPassword").val(),account:$("#account").val()},
						dataType:"json",
						success:function(data){
							if(data.success){
								$(".data-message").html(data.msg);
								$(".data-message").fadeIn(2000);
								setTimeout(function(){
								$(".data-message").fadeOut(2000);
								$(".change-psw").fadeOut();
								$(".overhide").fadeOut();
								},2000);
							}else{
								$(".data-message").html(data.msg);
								$(".data-message").fadeIn(2000);
								setTimeout(function(){
								$(".data-message").fadeOut(2000);
								},2000);
							}
						},
						error:function(jqXHR){
							if(jqXHR.status===500){
								$(".data-message").html("服务器正忙");
								$(".data-message").fadeIn(2000);
								setTimeout(function(){
								$(".data-message").fadeOut(2000);
							},2000);
							}
						}
					})
				}else{
					$(".data-message").html("密码长度应该在6-16位之间");
					$(".data-message").fadeIn(300);
				}
			});
		</script>
		
	</div>
	<div class="overhide" id="overhide"></div>
	<script>
		$('#changeUsername').click(function(event) {
			$('.overhide').fadeIn();
			$('.change-user').show();
		});
		$('.change-user').find('#close').click(function(event) {
			$('.change-user').hide();
			$('.overhide').fadeOut();
		});
		$('#changePassword').click(function(event) {
			$('.overhide').fadeIn();
			$('.change-psw').show();
		});
		$('.change-psw').find('#close').click(function(event) {
			$('.change-psw').hide();
			$('.overhide').fadeOut();
		});
	</script>
</body>
</html>
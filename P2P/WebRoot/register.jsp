<%@ page language="java" import="java.util.*,java.net.*"
	pageEncoding="utf-8"  isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 注册页面 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>开始注册吧！</title>
<link rel="stylesheet" href="css/regcss.css">
<script src="js/newlg.js"></script>
<script src="js/fn.js"></script>
<script src="js/jquery-2.1.4.js"></script>
</head>
<body>
	<div class="overhide" id="overhide"></div>
	<div class="container">
		<%
			if(request.getSession().getAttribute("status")!=null){
				out.println("<div id=\"exsits\" class=\"error\" style=\"position: absolute;left:50%;width:130px;height: 30px;line-height: 30px;margin-left:-72px;top:0;margin-top:133px;background:#e68278;font-size:14px;font-family:Microsoft yahei;z-index:3;\">该账户名已存在</div>");
			}
		%>
		<div class="reg-logo">
			<a href="index.jsp"> <img src="css/img/reg_logo.png" alt="">
			</a>
		</div>
		<!-- 注册部分 -->
		<div class="reg-box">
			<form action="<%=path%>/ActionServlet?action=register" id="RegForm"
				method="post">
				<h1>加入我们吧！</h1>
				<div class="username-box" id="username-box">
					<ul>
						<li><input type="text" name="account" id="regAcc"
							placeholder="用户名"></li>
						<li><div class="error">请填写用户名</div></li>
						<li><div class="ajax-error">该用户名已存在</div></li>
					</ul>
				</div>

				<div class="password-box" id="password-box">
					<ul>
						<li><input type="password" name="password" id="regPsw"
							placeholder="密码"></li>
						<li><div class="error">请填写密码</div></li>
					</ul>
				</div>

				<div class="accept-box">
					<label> <input type="checkbox" name="accept" id="accept"
						value="true"> 我接受
					</label> <a href="javascript:;">问卷网服务协议</a>
				</div>
				<div class="regbutton-box">
					<button class="reg-submit" type="submit" id="regSubmit">立即注册</button>
				</div>
				<div class="reg-footer">
					<span>已有账号?</span>&nbsp;<span><a href="#" id="loginButton">立即登入</a></span>
				</div>
			</form>
		</div>
		<!-- END注册部分 -->
		<script>
			$("#regSubmit")
					.click(
							function(event) {
								//console.log(document.getElementById('accept').checked);
								if ($("#regAcc").val().length < 2
										|| $("#regAcc").val().length > 16) {
									event.preventDefault();
									$("#username-box").find(".error").html(
											"用户名长度为2-16位");
									$("#username-box").find(".error").fadeIn();
								}
								if ($("#regPsw").val().length < 6
										|| $("#regPsw").val().length > 16) {
									$("#password-box").find(".error").html(
											"密码长度为6-16位");
									$("#password-box").find(".error").fadeIn();
									event.preventDefault();
								}
								if (document.getElementById('accept').checked === false) {
									$("#password-box").find(".error").html(
											"要接受协议喔");
									$("#password-box").find(".error").fadeIn();
									event.preventDefault();
								}
							})

			var time = 0;
			$("#regAcc").click(
					function(event) {
						$("#exsits").fadeOut();
						if (time <= 1) {
							time++;
						}
						$("#username-box").find(".error").fadeOut();
						if (time === 1) {
							$("#regAcc").blur(
									function() {
										if ($(this).val().length < 2) {
											$("#username-box").find(".error")
													.html("用户名应大于2位");
											$("#username-box").find(".error")
													.fadeIn();
										}
										if ($(this).val().length > 16) {
											$("#username-box").find(".error")
													.html("用户名应小于16位");
											$("#username-box").find(".error")
													.css({
														"width" : "130"
													});
											$("#username-box").find(".error")
													.fadeIn();
										}
									})
						}
					});

			$("#regAcc")
					.blur(
							function() {
								if ($("#regAcc").val().length >= 2
										&& $("#regAcc").val().length <= 16) {
									$
											.ajax({
												type : "post",
												url : "http://localhost:8080/P2P/ActionServlet",
												data : {
													account : $("#regAcc")
															.val(),
													action : "query"
												},
												dataType : "json",
												success : function(data) {
													if (data.success) {
														$(".ajax-error").html(
																data.msg);
														$(".ajax-error")
																.css(
																		{
																			"background" : "green"
																		});
														$(".ajax-error")
																.fadeIn();
													} else {
														$(".ajax-error").html(
																data.msg);
														$(".ajax-error")
																.css(
																		{
																			"background" : "#e68278"
																		});

														$(".ajax-error")
																.fadeIn();
													}
												},
												error : function(jqXHR) {
													alert("发生错误："
															+ jqXHR.status);
												}
											});
								}
							});

			var time1 = 0;
			$("#regPsw").click(function(event) {
				time1++;
				$("#password-box").find(".error").fadeOut();
				if (time1 === 1) {
					$("#regPsw").blur(function() {
						if ($(this).val().length < 6) {
							$("#password-box").find(".error").html("密码应大于6位");
							$("#password-box").find(".error").fadeIn();
						}
						if ($(this).val().length > 16) {
							$("#password-box").find(".error").html("密码应小于16位");
							$("#password-box").find(".error").css({
								"width" : "130"
							});
							$("#password-box").find(".error").fadeIn();
						}
					})
				}
			});

			$("#regPsw").click(function(event) {
				$(".ajax-error").fadeOut();
			})
		</script>

		<%
			//查看是否存在cookie
			String account = "";
			String password = "";
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie c : cookies) {
					if (c.getName().equals("accountCookie")) {
						account = URLDecoder.decode(c.getValue(), "utf-8");
					}
					if (c.getName().equals("passwordCookie")) {
						password = URLDecoder.decode(c.getValue(), "utf-8");
					}
				}
			}
		%>

		<!-- 登录框部分 -->
		<div class="log-box" id="loginArea">
			<form action="<%=path%>/ActionServlet?action=login" method="post">
				<img src="css/img/close2.png" alt="" style="float:right;margin:5px;"
					id="closeX"><br>
				<h1>
					<img src="css/img/title.png" alt="">
				</h1>
				<div class="log-username-box" style="text-align: center;">
					<i></i> <input type="text" name="account" placeholder="用户名"
						value="<%=account%>">
				</div>
				<div class="log-password-box" style="text-align: center;">
					<i></i> <input type="password" name="password" placeholder="密码"
						value="<%=password%>">
				</div>
				<div class="log-accept-box"
					style="text-align: center; margin-left:-115px">
					<label> <input type="checkbox" name="saveme" checked="checked"> 记住密码
					</label>
				</div>
				<div class="logbutton-box" style="text-align: center;">
					<button class="log-submit" type="submit">开始登陆</button>
				</div>
				<div class="log-footer">
					<span>没有账号?</span>&nbsp;<span><a href="register.jsp"
						id="loginButton">立即注册</a></span>
				</div>
			</form>
		</div>
		<!-- END登录框部分 -->

		<div class="footer">
			<a href="index.jsp"><img src="css/img/reg_footer.png" alt=""></a>
		</div>
	</div>
</body>
</html>
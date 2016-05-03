<%@ page language="java" import="java.util.*,java.net.*"
	pageEncoding="utf-8"  isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	//查看是否存在cookie
	String account="";
	String password="";
	Cookie[] cookies = request.getCookies();
	if(cookies!=null&&cookies.length>0){
		for(Cookie c:cookies){
			if(c.getName().equals("accountCookie")){
				account = URLDecoder.decode(c.getValue(),"utf-8");
			}
			if(c.getName().equals("passwordCookie")){
				password = URLDecoder.decode(c.getValue(),"utf-8");
			}
		}
	}
%>
<!-- 主页 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>问卷调查主页</title>
<link rel="stylesheet" href="css/css.css">
<link rel="stylesheet" href="css/loginAreaCss.css">
<script type="text/javascript" src="js/indexjs.js"></script>
<script type="text/javascript" src="js/fn.js"></script>
</head>
<body>
	<div class="container">
		<div class="header">
			<div class="h1">
				<a href="index.jsp"><img src="css/img/templet_logo.png"
					alt="问卷调查By HC&Chen"></a>
			</div>
			<div class="nav">
				<a href="templet.jsp">问卷</a> <a href="templet.jsp">表单</a> <a href="templet.jsp">测评</a> <a
					id="drop" href="templet.jsp"
					onmouseover="document.getElementById('dropMenu').style.display='block'"
					onmouseout="document.getElementById('dropMenu').style.display='none'">模板库</a>
				<!-- 下拉菜单部分-->
				<div class="drop-menu" id="dropMenu"
					onmouseover="document.getElementById('dropMenu').style.display='block'"
					onmouseout="document.getElementById('dropMenu').style.display='none'">
					<div id="dl1" class="dl-nav">
						<dl>
							<dt>问卷模板</dt>
							<dd>
								<a href="templet.jsp">品牌营销</a>
							</dd>
							<dd>
								<a href="templet.jsp">产品测试</a>
							</dd>
							<dd>
								<a href="templet.jsp">消费者分析</a>
							</dd>
							<dd>
								<a href="templet.jsp">学术教育</a>
							</dd>
							<dd>
								<a href="templet.jsp">社会民意</a>
							</dd>
							<dd>
								<a href="templet.jsp">其他</a>
							</dd>
						</dl>
						<dl>
							<dt>表单模板</dt>
							<dd>
								<a href="templet.jsp">报名</a>
							</dd>
							<dd>
								<a href="templet.jsp">登记</a>
							</dd>
							<dd>
								<a href="templet.jsp">邀请</a>
							</dd>
							<dd>
								<a href="templet.jsp">反馈</a>
							</dd>
							<dd>
								<a href="templet.jsp">订单</a>
							</dd>
							<dd>
								<a href="templet.jsp">其他</a>
							</dd>
						</dl>
						<dl style="border-right: 0px;">
							<dt>测评模板</dt>
							<dd>
								<a href="templet.jsp">培训考评</a>
							</dd>
							<dd>
								<a href="templet.jsp">教学测验</a>
							</dd>
							<dd>
								<a href="templet.jsp">员工考核</a>
							</dd>
							<dd>
								<a href="templet.jsp">趣味测试</a>
							</dd>
							<dd>
								<a href="templet.jsp">心理测试</a>
							</dd>
						</dl>
					</div>
				</div>
				<!--END 下拉菜单部分-->
			</div>
			<!-- 导航条按钮组件部分-->
			<!-- 如果没登录，执行这个 -->
			<%
				if(session.getAttribute("account")==null){
			%>
			<div class="btn-member">
				<button class="btn-primary" id="loginButton">登录</button>
				<button class="btn-green" id="regButton"
					onclick="location.href='register.jsp'">
					<a href="register.jsp">注册</a>
				</button>
			</div>
			<%
				}else{
			%>
			<div class="btn-member">
				<button class="btn-primary" id="enter"
					onclick="location.href='user.jsp'">进入后台</button>
			</div>
			<%
				}
			%>
			<!-- END导航条按钮组件部分-->
		</div>
		<!-- 页面内容部分-->
		<div class="content-box">
			<!-- 显示内容部分-->
			<div class="content">
				<span class="show-font">倾听你在乎的</span>
			</div>
			<div class="content">
				<span class="show-font">Listen to what you care</span>
			</div>
			<%
				if(session.getAttribute("account")==null){
			%>
			<div class="reg">
				<button class="reg-button" onClick="location.href='register.jsp'">免费注册</button>
			</div>
			<!-- END显示内容部分-->
			<%
				}
			%>

			<!-- 点击登录按钮下拉登录部分-->
			<div class="login-area" id="loginArea"
				style="display: none; opacity: 0; background-color: white;">
				<form action="<%=path%>/ActionServlet?action=login" method="post">
					<a href="javascript:;" class="login-close"><img
						src="css/img/close2.png" alt="" id="closeX"></a>
					<center>
						<a href="index.jsp"> <img src="css/img/title.png" alt="问卷网">
						</a>
					</center>
					<div class="username">
						<i></i> <input type="text" name="account" id="account"
							placeholder="请输入您的用户名" value="<%=account%>">
					</div>
					<div class="password">
						<i></i> <input type="password" name="password" id="password"
							placeholder="请输入您的密码" value="<%=password%>">
					</div>
					<div class="login-forget">
						<label> <input type="checkbox" name="saveme" value="true" checked="checked">
							记住我
						</label> 
					</div>
					<div class="login-but">
						<button class="login-button" type="submit">登&nbsp;录</button>
					</div>
				</form>
			</div>
			<!-- END点击登录按钮下拉登录部分-->

		</div>
		<!-- END页面内容部分-->
	</div>

	<div class="overhide" id="overhide"></div>
	<script>
		var loginButton = document.getElementById('loginButton');
		var overhide = document.getElementById('overhide');

		loginButton.onclick = function() {
			var loginArea = document.getElementById('loginArea');
			loginArea.style.display = 'block';
			overhide.style.display = 'block';
			startMove(loginArea, {
				opacity : 100
			}, startMove(overhide, {
				opacity : 50
			}));
		}
		/*END显示登陆小窗口*/

		/*关闭登录小窗口*/
		var closeX = document.getElementById('closeX');

		closeX.onclick = function() {
			var loginArea = document.getElementById('loginArea');
			startMove(loginArea, {
				opacity : 0
			}, startMove(overhide, {
				opacity : 0
			}));
			return false;
		}
		/*END关闭登录小窗口*/
	</script>
</body>
</html>
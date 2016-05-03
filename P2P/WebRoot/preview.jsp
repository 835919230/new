<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.*,entity.*,DAO.*"  isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- 观看和预览模板的页面 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>问卷网</title>
<link rel="stylesheet" href="css/preview.css">
<link rel="stylesheet" href="css/templet.css">
</head>
<%
	String account  = (String) session.getAttribute("account");
	String username = (String) session.getAttribute("username");
	String action = request.getParameter("action");
	Integer tId = null;
	String flag = request.getParameter("tid");
	if(flag!=null){
		tId = Integer.parseInt(request.getParameter("tid"));
	}else{
		response.sendRedirect("templet.jsp");
	}
	Templet temp = null;
	User user = null;
	if (tId != null) {
		temp = TempletDAO.findById(tId);
	}
%>
<body>
	<div class="container">
	<div class="overhide" id="overhide"></div>
	<div class="login-area-box" style="top:100px;">
		<div class="login-area">
			<div class="login-header">
				<div class="h3">
					<a href="index.jsp" style="color:rgb(75,170,250); font-size:30px;">问卷网</a>
					<a href="javascript:;" id="closeX">X</a>
				</div>
			</div>
			<form action="<%=path%>/ActionServlet?action=login" method="post" id="loginForm">
			<div class="login-content">
				<div class="account-area">
					用户名:<input type="text" name="account" id="account">
				</div>
				<div class="password-area">
					密&nbsp;&nbsp;&nbsp;码:<input type="password" name="password" id="password">
				</div>
				<div class="saveme-area">
					<label><input type="checkbox" name="saveme" checked="checked">记住密码</label>
				</div>
				<div class="login-button">
					<input type="submit" id="loginButton" value="登录">
				</div>
			</div>
			<div class="login-footer">
				<span>还没账号？</span><a href="register.jsp">立即注册</a>
			</div>
			</form>
		</div>
	</div>
	
	
		<div class="header">
			<div class="h2">
				<a href="index.jsp">问卷网</a>
			</div>
			<%
				if (account != null) {
			%>
			<div class="head-area">
				<a href="user.jsp">我设计的模板</a> <a href="user.jsp?tyoe=collection">我收藏的模板</a> <a href="templet.jsp">模板库</a>
			</div>
			<%
				}else{
			%>
			<div class="head-area">
				<a href="templet.jsp">模板库</a>
			</div>
			<%
				}
				if (account != null&&username != null) {
			%>
			<div class="user-area">
				<a href="user.jsp"><%=username %></a>
			</div>
			<%
				}else{
			%>
			<div class="user-area">
				<a href="javascript:;" id="loginButton">登录</a>
				<a href="register.jsp">注册</a>
			</div>
			<%
				}
			%>
		</div>
		<!-- END header -->
		<div class="content-box">
			<div class="content">
				<%
					if (temp != null) {
				%>
				<div class="h3"><%=temp.gettName()%></div>
				<div class="reference">
					该模板被引用的次数：<%=temp.getRefTimes()%>次</div>
				<div class="motal"><%=temp.getMotal()%></div>
				<div class="question-box">
					<%
						List<Item> items = ItemDAO.listByTemplet(temp);
							if (items != null) {
								for (int i = 0; i < items.size(); i++) {
									Item item = items.get(i);
					%>
					<div class="item">
						<div class="item-title"><%=item.getqName()%></div>
						<%
							List<Choice> choices = ChoiceDAO.listByItem(item);
										if (choices != null) {
											int number = choices.size();
						%>
						<div class="item-choice">
							<%
								if (item.getqType().indexOf("单选题") != -1
														|| item.getqType().indexOf("性别") != -1
														|| item.getqType().indexOf("工作年限") != -1
														|| item.getqType().indexOf("教育程度") != -1
														|| item.getqType().indexOf("个人收入") != -1
														|| item.getqType().indexOf("工作单位") != -1
														|| item.getqType().indexOf("婚姻状况") != -1) {
							%>
							<ul>
								<%
									for (int j = 0; j < number; j++) {
								%>
								<li><label><input type="radio"
										name="radio<%=(i + 1)%>" value="<%=choices.get(j).getcId()%>"><%=choices.get(j).getcName()%></label>
								</li>
								<%
									}
								%>
								<%
									} else if (item.getqType().indexOf("多选题") != -1) {
								%>
								<%
									for (int j = 0; j < number; j++) {
								%>
								<li><label><input type="checkbox"
										name="<%=item.getqName()%>checkbox"><%=choices.get(j).getcName()%></label>
								</li>
								<%
									}
								%>
								<%
									} else if (item.getqType().indexOf("单行填空题") != -1
															|| item.getqType().indexOf("手机") != -1
															|| item.getqType().indexOf("邮箱") != -1
															|| item.getqType().indexOf("多项填空题") != -1) {
								%>
								<%
									for (int j = 0; j < number; j++) {
								%>
								<li><input type="text" name="<%=choices.get(j).getcId()%>">
								</li>
								<%
									}
								%>
								<%
									} else if (item.getqType().indexOf("多行填空题") != -1) {
								%>
								<li><textarea rows="10" cols="30"></textarea></li>
								<%
									}
								%>
							</ul>
						</div>
					</div>
					<%
						}
								}
							}
						}
					%>
				</div>
			</div>
		</div>
		<!-- END content-box -->
		<%
			user = UserDAO.findByAccount(account);
			if (temp != null && user != null) {
				User user1 = UserDAO.findById(temp.getuId());
				if (account != null && !user1.getAccount().equals(account)&&action==null) {
					entity.Collection collection = CollectionDAO.findBy2(user,temp);
					//System.out.println(user.getUid()+","+temp.gettId());
					String name = null;
					//System.out.println(collection==null);
					if(collection==null){name="收藏该模板";
		%>
		<div class="content-footer">
			<a href="<%=path%>/ActionServlet?action=collect&tmpId=<%=temp.gettId()%>" class="btn"><%=name %></a>
		</div>
		<%
					}else{name="取消收藏";
		%>
		<div class="content-footer">
			<a href="<%=path%>/ActionServlet?action=cancelCollect&coid=<%=collection.getCoId()%>" class="btn"><%=name %></a>
		</div>
		<%
					}
				} else if(account!=null && user1.getAccount().equals(account)&&action==null) {
		%>
		<div class="content-footer">
			<a href="design.jsp?tid=<%=tId %>" class="btn">修改该模板</a>
		</div>
		<%
				}else if("preview".equals(action)){
				
		%>
		<div class="content-footer">
			<a href="design.jsp?tid=<%=tId %>" class="btn">返回继续设计模板</a>
		</div>
		<%	
				
				}
			}else if(user == null){
		%>
		<div class="content-footer">
			<a href="register.jsp" class="btn">立即注册</a>
		</div>
		<%
			}
			
			String from = request.getParameter("from");
			if("reference".equals(from)&&"reference".equals(action)){
		%>
		<div class="content-footer">
			<a href="before.jsp?tid=<%=tId %>" class="btn">引用该模板并发布</a>
		</div>
		<%
			}
		%>
	</div>
</body>
<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
	<script>
		$(".user-area").find("#loginButton").click(function(event){
			$(".overhide").fadeIn();
			$(".login-area-box").fadeIn();
		});
		$(".login-area-box").find("#closeX").click(function(event){
			$(".overhide").fadeOut();
			$(".login-area-box").fadeOut();
		});
		$(".login-area-box").find("#loginButton").click(function(event){
			if($("#account").val().length<=0||$("#password").val().length<=0){
				event.preventDefault();
			}
		})
	</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="entity.*,java.util.*,DAO.*,util.*,java.net.*"  isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<!-- 模板库 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/preview.css">
<link rel="stylesheet" href="css/templet.css">
<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
<style>
	.pagination-area a{
		width: auto;
		padding:0 3px;
	    display: inline-block;
	    margin-left: 5px;
	    background: #53a4f4;
	    color: #fff;
	    height: 30px;
	    line-height: 30px;
	    border-radius: 3px;
	}
	.pagination-area {
		color: #666666;
	}
</style>
</head>
<%
	String username 	= (String)session.getAttribute("username");
	String account  	= (String)session.getAttribute("account");
	List<Templet> temps = null;
	List<Publish> publishes = null;
	final int pageSize = 10;
	int pageNum = 1;
	int pageCount = 1;
	int recordCount = 1;
	String search = request.getParameter("search");
	String type = request.getParameter("type");
	String pagenum = request.getParameter("pageNum");
	if(pagenum!=null){pageNum = Integer.parseInt(pagenum);}
	//System.out.println(search);
	if((type==null&&search==null)){
		temps = TempletDAO.listSharedTemp(pageNum,pageSize);
		recordCount = DbManager.getCount("select count(tid) from templet");
		pageCount = (recordCount + pageSize -1) / pageSize;
	}else if(type!=null&&"publish".equals(type)){
		publishes = PublishDAO.listPublish(pageNum,pageSize);
		recordCount = DbManager.getCount("select count(pid) from publish");
		pageCount = (recordCount + pageSize -1) / pageSize;
	}else if(type==null&&search!=null){
		temps = (List<Templet>)session.getAttribute("SearchTemps");
		recordCount = temps.size();
		pageCount = (recordCount + pageSize -1) / pageSize;
	}
	
	
	//查看是否存在cookie
	String account1="";
	String password="";
	Cookie[] cookies = request.getCookies();
	if(cookies!=null&&cookies.length>0){
		for(Cookie c:cookies){
			if(c.getName().equals("accountCookie")){
				account1 = URLDecoder.decode(c.getValue(),"utf-8");
			}
			if(c.getName().equals("passwordCookie")){
				password = URLDecoder.decode(c.getValue(),"utf-8");
			}
		}
	}
%>
<body>
	<div class="overhide" id="overhide"></div>
	<div class="container">
		<div class="login-area-box">
			<div class="login-area">
				<div class="login-header">
					<div class="h3">
						<a href="index.jsp" style="color:rgb(75,170,250); font-size:30px;">问卷网</a>
						<a href="javascript:;" id="closeX">X</a>
					</div>
				</div>
				<form action="<%=path%>/ActionServlet?action=login" method="post"
					id="loginForm">
					<div class="login-content">
						<div class="account-area">
							用户名:<input type="text" name="account" id="account" value="<%=account1%>">
						</div>
						<div class="password-area">
							密&nbsp;&nbsp;&nbsp;码:<input type="password" name="password"
								id="password" value="<%=password%>">
						</div>
						<div class="saveme-area">
							<label><input type="checkbox" name="saveme"
								checked="checked" value="true">记住密码</label>
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
			<div class="head-area">
				<a href="templet.jsp?type=publish" <%if(type!=null){ %>style="background:rgb(80,137,193);width:120px;"<%}else{ %>style="width:120px;"<%} %>>正在调查的问卷</a>
				<%
					if(username!=null&&account!=null){
				%>
				<a href="create.jsp">设计模板</a>
				<%
					}
				%>
				<a href="templet.jsp" <%if(type==null) {%>style="background:rgb(80,137,193);"<%} %>>模板库</a>
			</div>
			<%
				if(username!=null&&account!=null){
			%>
			<div class="user-area">
				<a href="user.jsp"><%=username%></a>
			</div>
			<%
				}else{
			%>
			<div class="user-area">
				<a href="javascript:;" id="loginButton">登录</a> <a
					href="register.jsp">注册</a>
			</div>
			<%
				}
			%>
		</div>
		<!-- END header -->
		<%
				if(type==null){
		%>
		<div class="search-box">
			<form action="Search" method="post" id="search-form">
				<input type="text" name="search" id="search" placeholder="搜索您想要的模板吧">
				<input type="submit" value="提交" id="submit">
				<input type="hidden" name="hidden" value="templet">
			</form>
		</div>
		<script>
			$('#submit').click(function(e){
				if($('#search').val().length<=0){
					alert("搜索内容不能为空");
					return false;
				}
			});
		</script>
		<%
			}
		%>
		<div class="content-box">
			<div class="content">
				<%if(search==null){ %>
					<%
				if(type==null){
					if(temps!=null){
							int length = temps.size();
							for(int i=0;i<length;i++){
								Templet temp = temps.get(i);
								int number = ItemDAO.getCount(temp);
								String username1 = UserDAO.findById(temp.getuId()).getUsername();
								User user = null;
								entity.Collection collection = null;
								if(account!=null){user = UserDAO.findByAccount(account);collection = CollectionDAO.findBy2(user, temp);}
				%>
				<div class="item" <%if(i!=0){%> style="margin-top:30px;" <%}%>>
					<div class="item-title">
						<a href="preview.jsp?tid=<%=temp.gettId()%>"><%=temp.gettName()%>
							<%
								if(user!=null&&collection!=null){
							%><span style="color:red;font-size:12px;position:relative;">已收藏</span>
							<%
								}
							%></a> <span>共1页<%=number%>个问题
						</span>
					</div>
					<div class="item-content">
						<span>作者： <%=username1%>|
						</span> <span>被引用次数：<%=temp.getRefTimes()%>
						</span>
					</div>
					<%
						String motal = temp.getMotal();if(motal.length()>17){motal = motal.substring(0, 17)+"...";}else{}
					%>
					<div class="item-footer"><%=motal%></div>
				</div>
				<%
					}
							}else{
				%>
				<div class="item">没有您要的模板哦！</div>
				<%
					}
						}else{
							if(publishes!=null){
								int length = publishes.size();
								for(int i = 0;i < length;i++){
									Publish publish = publishes.get(i);
									if(publish!=null){
										Templet temp = PublishDAO.findTempById(publish.gettId());
										String username1 = UserDAO.findById(temp.getuId()).getUsername();
										int number = ItemDAO.getCount(temp);
				%>
				<div class="item" <%if(i!=0){%> style="margin-top:30px;" <%}%>>
					<div class="item-title">
						<a href="vote.jsp?pid=<%=publish.getpId()%>"><%=publish.getpName()%>
							</a> <span>共1页<%=number%>个问题
						</span>
					</div>
					<div class="item-content">
						<span>作者： <%=username1%>|</span> 
						<span>被引用次数：<%=temp.getRefTimes()%>|</span>
						<span>发布时间：<%=publish.getpTime() %>|</span>
						<span>结束时间：<%=publish.getlTime() %></span>
					</div>
					<%
						String motal = temp.getMotal();if(motal.length()>=17){motal = motal.substring(0, 17)+"...";}else{}
					%>
					<div class="item-footer"><%=motal%></div>
				</div>	
				<%
									}
								}
							}
						}
				%>
				<%}else if(type==null&&search!=null){ 
					if(temps!=null){
						if(temps.size()-pageSize<=10){
							for(int i = (pageNum-1)*pageSize;i < temps.size();i++){
								Templet temp = temps.get(i);
								int number = ItemDAO.getCount(temp);
								String username1 = UserDAO.findById(temp.getuId()).getUsername();
								User user = null;
								entity.Collection collection = null;
								if(account!=null){user = UserDAO.findByAccount(account);collection = CollectionDAO.findBy2(user, temp);}
				%>
					<div class="item" <%if(i%pageSize==0){%> style="margin-top:30px;" <%}%>>
					<div class="item-title">
						<a href="preview.jsp?tid=<%=temp.gettId()%>"><%=temp.gettName()%>
							<%
								if(user!=null&&collection!=null){
							%><span style="color:red;font-size:12px;position:relative;">已收藏</span>
							<%
								}
							%></a> <span>共1页<%=number%>个问题
						</span>
					</div>
					<div class="item-content">
						<span>作者： <%=username1%>|
						</span> <span>被引用次数：<%=temp.getRefTimes()%>
						</span>
					</div>
					<%
						String motal = temp.getMotal();if(motal.length()>=17){motal = motal.substring(0, 17)+"...";}else{}
					%>
					<div class="item-footer"><%=motal%></div>
				</div>
				<%
							}
						}else{
							for(int i = (pageNum-1)*pageSize;i < temps.size();i++){
								Templet temp = temps.get(i);
								int number = ItemDAO.getCount(temp);
								String username1 = UserDAO.findById(temp.getuId()).getUsername();
								User user = null;
								entity.Collection collection = null;
								if(account!=null){user = UserDAO.findByAccount(account);collection = CollectionDAO.findBy2(user, temp);}
				%>
				<div class="item" <%if(i%pageSize==0){%> style="margin-top:30px;" <%}%>>
					<div class="item-title">
						<a href="preview.jsp?tid=<%=temp.gettId()%>"><%=temp.gettName()%>
							<%
								if(user!=null&&collection!=null){
							%><span style="color:red;font-size:12px;position:relative;">已收藏</span>
							<%
								}
							%></a> <span>共1页<%=number%>个问题
						</span>
					</div>
					<div class="item-content">
						<span>作者： <%=username1%>|
						</span> <span>被引用次数：<%=temp.getRefTimes()%>
						</span>
					</div>
					<%
						String motal = temp.getMotal();if(motal.length()>=17){motal = motal.substring(0, 17)+"...";}else{}
					%>
					<div class="item-footer"><%=motal%></div>
				</div>
				<%		
						} 
					}
				}}
				%>
			</div>
			<%
				StringBuffer url = new StringBuffer(request.getRequestURI());
				if(type!=null&&"publish".equals(type)){url.append("?type=publish");}
				else if(type==null&&search!=null){url.append("?search=search");} 
			%>
			<div class="pagination-area" style="text-align:center;"><%=Pagination.getPagination(pageNum, pageCount, recordCount, url.toString()) %></div>
			<!-- END content -->
		</div>
		<!-- END content-box -->
	</div>
	<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
	<script>
		$(".user-area").find("#loginButton").click(function(event) {
			$(".overhide").fadeIn();
			$(".login-area-box").fadeIn();
		});
		$(".login-area-box").find("#closeX").click(function(event) {
			$(".overhide").fadeOut();
			$(".login-area-box").fadeOut();
		});
		$(".login-area-box").find("#loginButton").click(
				function(event) {
					if ($("#account").val().length <= 0
							|| $("#password").val().length <= 0) {
						event.preventDefault();
					}
				});
		$('.overhide').height($('body').height());
	</script>
</body>
</html>
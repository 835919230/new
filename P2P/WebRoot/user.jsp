<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="DAO.*,entity.*,filter.*,util.*,java.util.*"  isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<!-- 用户后台页面 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户后台</title>
<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
						$('table td #end').click(function(event) {
													if(confirm("确认结束吗？")){
														var This = $(this);
															$.ajax({
																	type : "post",
																	url : "http://localhost:8080/P2P/EndPublish",
																	data : {
																		pid: $(this).parent().find('input[type="hidden"]')
																				.val()
																	},
																	dataType : "json",
																	success : function(data) {
																		if (data.success) {
																			This.remove();
																		}
																	},
																	error : function() {
																		alert("出现错误")
																	}
																});
													}
											return false;
										});
						$('table td #cancel').click(function(event) {
											var tr = $(this).parent().parent();
											if (confirm("确认取消吗？")) {
												$.ajax({
															type : "post",
															url : "http://localhost:8080/P2P/DeletePublish",
															data : {pid : $(this).parent().find('input[type="hidden"]').val()},
															dataType : "json",
															success : function(
																	data) {
																if (data.success) {
																	tr.remove();
																}
															},
															error : function() {
																alert("出现错误")
															}
														});
											}
											return false;
										});
					
					$('table td #priority-admit').click(function(e){
						var This = $(this);
						var parent = $(this).parent();
						if(confirm("确认分享数据吗？")){
							$.ajax({
								type:"post",
								url:"http://localhost:8080/P2P/AddPriority",
								data : {pid : parent.find('input[type="hidden"]').val()},
								dataType:"json",
								success:function(data){
											if(data.success){
												This.hide();
												parent.find('#priority-cancel').show();
											}
										}
							})
						}
						return false;
					});
					$('table td #priority-cancel').click(function(e){
						var This = $(this);
						var parent = $(this).parent();
						if(confirm("确认取消分享数据吗？")){
							$.ajax({
								type:"post",
								url:"http://localhost:8080/P2P/DeletePriority",
								data : {pid : parent.find('input[type="hidden"]').val()},
								dataType:"json",
								success:function(data){
											if(data.success){
												This.hide();
												parent.find('#priority-admit').show();
											}
										}
							})
						}
						return false;
					});
					
					});
					
</script>
<style type="text/css">

#priority-admit,#priority-cancel{display:none;padding: 0 3px;}

tr.pagination td a{width:auto;padding:0 5px;float:none;display:inline-block;}
.pagination td{text-align:center;}
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
	margin-left: 200px;
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

.function-area {
	padding: 10px 20px;
}

.function-area a {
	display: block;
	width: 135px;
	background: #53a4f4;
	color: #fff;
	text-align: center;
	height: 35px;
	border-radius: 4px;
	line-height: 35px;
}

.content {
	margin: 15px 25px;
	background: #fff;
	border-radius: 3px;
	box-shadow: 0 0 15px gray;
}

.table {
	padding: 10px 25px;
	width: 100%;
	color: #666666;
	border-spacing: 0;
}

.table tr {
	border-bottom: 1px #ebebeb solid;
	height: 60px;
}

.table td {
	border-bottom: 1px #dedede solid;
}


.table a {
	width: 60px;
	display: block;
	float: left;
	margin-left: 5px;
	background: #53a4f4;
	color: #fff;
	height: 30px;
	text-align: center;
	line-height: 30px;
	border-radius: 3px;
}

a.title {
	font-size: 17px;
	background: transparent;
	color: #747474;
	width: 90%;
	text-align: left;
}

</style>
</head>
<%
	final int pageSize = 10;
	int pageNum = 1;
	int pageCount = 1;
	int recordCount = 1;
	String pagenum = request.getParameter("pageNum");
	if(pagenum!=null){pageNum = Integer.parseInt(pagenum);}
	
	String type = request.getParameter("type");
	String title = null;
	if (type == null) {
		title = "我设计的模板";
	} else if (type.equals("publish")) {
		title = "我发布的问卷";
	} else if (type.equals("collection")) {
		title = "我收藏的模板";
	}else if(type.equalsIgnoreCase("participate")){
		title = "我参与的问卷";
	}
	String account = (String) session.getAttribute("account");
	User user = null;
	List<Templet> temps = null;
	List<entity.Collection> collections = null;
	List<Publish> publishes = null;
	List<Publish> participates = null;
	if (account != null) {
		user = UserDAO.findByAccount(account);
		if (type == null) {
			temps = TempletDAO.listByUser(user,pageNum,pageSize);
			recordCount = DbManager.getCount("select count(tid) from templet where uid = "+user.getUid());
			pageCount = (recordCount + pageSize -1) / pageSize;
		} else if (type.equals("collection")) {
			collections = CollectionDAO.listByUser(user,pageNum,pageSize);
			recordCount = DbManager.getCount("select count(coid) from collection where userID = "+user.getUid());
			pageCount = (recordCount + pageSize -1) / pageSize;
		} else if (type.equals("publish")) {
			publishes = PublishDAO.listByUser(user,pageNum,pageSize);
			recordCount = DbManager.getCount("select count(pid) from publish where uid = "+user.getUid());
			pageCount = (recordCount + pageSize -1) / pageSize;
		}else if(type.equals("participate")){
			participates = PaticipateDAO.listPaticipates(user.getUid(),pageNum,pageSize);
			recordCount = DbManager.getCount("select count(prid) from participate where uid = "+user.getUid());
			pageCount = (recordCount + pageSize -1) / pageSize;
		}
	} else {
		response.sendRedirect("index.jsp");
	}
%>
<body>
	<div class="container">
		<div class="header">
			<div class="h2">
				<a href="index.jsp">问卷网</a>
			</div>
			<div class="head-area">
				<a href="user.jsp" <%if (type == null) {%>
					style="background:rgb(80,137,193);" <%}%>>我设计的模板</a> <a
					href="user.jsp?type=publish" <%if ("publish".equals(type)) {%>
					style="background:rgb(80,137,193);" <%}%>>我发布的问卷</a> <a
					href="user.jsp?type=participate"
					<%if ("participate".equals(type)) {%>
					style="background:rgb(80,137,193);" <%}%>>我参与的问卷</a> <a
					href="user.jsp?type=collection"
					<%if ("collection".equals(type)) {%>
					style="background:rgb(80,137,193);" <%}%>>我收藏的模板</a> <a
					href="templet.jsp">模板库</a>
			</div>
			<div class="user-area">
				<a href="member.jsp" style="margin-right:30px;"><%=session.getAttribute("username")%></a><a
					href="<%=path%>/ActionServlet?action=logout"
					style="margin-right:20px;">退出登录</a>
			</div>
		</div>
		<div class="function-area">
			<a href="create.jsp" class="btn">+新建</a>
		</div>
		<div class="content">
			<table class="table">
				<caption style="margin-top: 10px;color:black;"><%=title%></caption>
				<tbody>
				<%if(participates==null){ %>
					<tr class="thead">
						<td style="width:40%;">模板名</td>
						<td>发布时间</td>
						<td style="padding-left:20px;">操作</td>
					</tr>
					<%}
						if (temps != null) {
							int length = temps.size();
							for (int i = 0; i < length; i++) {
								Templet temp = temps.get(i);
					%>
					<tr>
						<td style="width:40%;"><a
							href="preview.jsp?tid=<%=temp.gettId()%>" class="title"><%=temp.gettName()%></a></td>
						<td><%=temp.getdTime()%></td>
						<td><a
							href="<%=path%>/ActionServlet?action=delete&tid=<%=temp.gettId()%>"
							id="delete" onclick="if(!confirm('确认删除吗？')){return false;}">删除</a>
							<a href="design.jsp?tid=<%=temp.gettId()%>" id="update"
							onclick="if(!confirm('确认更新吗')){return false;}">更新</a> <a
							href="before.jsp?tid=<%=temp.gettId()%>" id="publish">发布</a></td>
					</tr>
					<%
						}
						} else if (collections != null) {
							int length = collections.size();
								for (int i = 0; i < length; i++) {
									Templet temp = TempletDAO.findById(collections.get(i).getTmpId());
									if(temp!=null){
					%>
					<tr>
						<td style="width:40%;"><a
							href="preview.jsp?tid=<%=temp.gettId()%>" class="title"><%=temp.gettName()%></a></td>
						<td><%=temp.getdTime()%></td>
						<td><a
							href="<%=path%>/ActionServlet?action=deleteCollection&tid=<%=temp.gettId()%>"
							id="delete" onclick="if(!confirm('确认删除吗？')){return false;}">删除</a>
							<a href="before.jsp?tid=<%=temp.gettId()%>" id="publish"
							onclick="if(!confirm('确认发布吗')){return false;}">发布</a></td>
					</tr>
					<%
									}
								}
						} else if (publishes != null) {
							int length = publishes.size();
							for (int i = 0; i < length; i++) {
								Publish publish = publishes.get(i);
								Boolean bool = PublishDAO.queryIsEnd(publish.getpId());
					%>
					<tr>
						<td style="width:40%;"><a
							href="vote.jsp?pid=<%=publish.getpId()%>" class="title"><%=publish.getpName()%></a></td>
						<td><%=publish.getpTime()%></td>
						<td><a href="publish.jsp?pid=<%=publish.getpId()%>"
							style="width:100px;">查看数据</a> <%
						 	if (!bool) {
						 %><a href="#" style="width:100px;" id="end">结束该问卷</a> <%
						 	}
						 %> <a href="#" id="cancel" style="width:100px;">取消发布</a> <input
													type="hidden" value="<%=publish.getpId()%>">
							<a href="#" id="priority-admit" style="width:auto;<%if(!PriorityDAO.queryIsShared(publish.getpId())){%> display:block<% }%>">允许分享该问卷数据</a>
							<a href="#" id="priority-cancel" style="width:auto;<%if(PriorityDAO.queryIsShared(publish.getpId())){%> display:block<% }%>">取消分享该问卷数据</a>
							
							</td>
					</tr>
					<%
						}
						}else if(participates!=null&&participates.size()>0){
							int length = participates.size();
							for(int i = 0;i < length;i++){
								if(participates.get(i)!=null){
					%>
								<tr>
									<td colspan='3' style='text-align:center;'>
										<a href='vote.jsp?pid=<%=participates.get(i).getpId() %>' style='text-align:center;text-indent:80px;' class='title'><%=participates.get(i).getpName() %></a>
									</td>
								</tr>
					<%			
								}
							}
						}else if(publishes==null||participates==null||collections==null||temps==null){
							out.println("<tr>");
							out.println("<td colspan='3' style='text-align:center;'>");
							out.println("空空如也哦");
							out.println("</td>");
							out.println("</tr>");
						}
						StringBuffer pageUrl = new StringBuffer(request.getRequestURI());
						if("publish".equals(type)){pageUrl.append("?type=publish");}
						else if("participate".equals(type)){pageUrl.append("?type=participate");}
						else if("collection".equals(type)){pageUrl.append("?type=collection");}
					%>
					<tr class="pagination">
						<td colspan="3"><%=Pagination.getPagination(pageNum, pageCount, recordCount, pageUrl.toString()) %></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<script> 
	var aTr = document.getElementsByTagName('tr');
	console.log(aTr[0].children[0]);
		for(j = 0;j < aTr.length;j++){
		aTr[j].style='background:white;';
		aTr[j].children[0].style = 'border-left: 2px solid #fff;width:40%;';
	}
		for(i=1;i<aTr.length-1;i++){
		//var ofTd = aTr[i].children[0];
		aTr[i].onmouseover=function(){
			this.style.background = 'rgb(236,250,255)';
			this.children[0].style = 'border-left: 2px solid #53a4f4;width:40%;';
		}
		aTr[i].onmouseout=function(){
			this.style.background = 'white';
			this.children[0].style = 'border-left: 2px solid #fff; width:40%;';
		}
	}

	</script>
</body>
</html>
<%@ page language="java" import="java.util.*,DAO.*,entity.*,util.*" pageEncoding="utf-8"  isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 填写问卷的页面 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'vote.jsp' starting page</title>
	<link rel="stylesheet" type="text/css" href="css/preview.css">
	<link rel="stylesheet" type="text/css" href="css/templet.css">
	<link rel="stylesheet" href="css/report.css">
	<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
	<style>
		.share-area{position: fixed;width: 30px;text-align: center;left: 50%;margin-left: 404px;top: 50%;margin-top: -100px;}
		.share-area a:hover{color:rgb(0,153,255);}
		.share-area a{color:#747474;display:block;}
		a.h2:hover{color:rgb(0,153,255);}
	</style>
  </head>
  <%
	String flag = request.getParameter("pid");
	String action = request.getParameter("action");
	Integer pId = null;
	Publish publish = null;
	List<PItem> pitems = null;
	List<PChoice> pchoices = null;
	if(flag==null){
		response.sendRedirect("templet.jsp?type=publish");
	}else{
		pId = Integer.parseInt(flag);
		publish = PublishDAO.findById(pId);
		publish.getlTime().setDate(publish.getlTime().getDate()+1);
		Date now = new Date();
		Boolean bool = PublishDAO.queryIsEnd(publish.getpId());
		if(now.after(publish.getlTime())||bool){
			if(!bool){PublishDAO.setEnd(publish.getpId());}
			//response.sendRedirect("late.jsp?pid="+publish.getpId());
			request.getRequestDispatcher("WEB-INF/late.jsp?pid="+publish.getpId()).forward(request, response);
		}
	}  
  %>
  <body>
    <div class="container">
    	<div class="header">
    		<a href="templet.jsp?type=publish" class="back" style="float:left;width:auto;">返回查看更多问卷</a>
    		<%if(PriorityDAO.queryIsShared(pId)){ %>
    			<a href="publish.jsp?pid=<%=pId%>" class="h2" style="width:auto;">查看该问卷的数据</a>
    			<a href="<%=path%>/DownData?pid=<%=pId %>&filename=<%=publish.getpName() %>" class="h2" style="width:auto;">下载该问卷的数据</a>
    		<% }%>
    	</div>
    	<div class="content-box" style="border-radius: 3px;">
    	<form action="<%=path%>/ActionServlet?action=vote&pid=<%=publish.getpId()%>" method="post">
    		<div class="content">
				<%
					if (publish != null) {
						Templet temp = TempletDAO.findById(publish.gettId());
						User author = UserDAO.findById(temp.getuId()); 
				%>
				<div class="h3"><%=publish.getpName()%></div>
				<div class="reference">
					作者：<%=author.getUsername()%></div>
				<div class="motal"><%=temp.getMotal()%></div>
				<div class="question-box">
					<%
						pitems = PItemDAO.listByPublish(publish);
							if (pitems != null) {
								int length = pitems.size();
								for (int i = 0; i < length; i++) {
									PItem pitem = pitems.get(i);
					%>
					<div class="item">
						<input type="hidden" class="itemType" value="<%=pitem.getType()%>">
						<input type="hidden" id="status" value="false">
						<div class="item-title"><%=pitem.getName()%></div>
						<%
							pchoices = PChoiceDAO.listByPItem(pitem);
										if (pchoices != null) {
											int number = pchoices.size();
						%>
						<div class="item-choice">
							<%
								if (pitem.getType().indexOf("单选题") != -1
														|| pitem.getType().indexOf("性别") != -1
														|| pitem.getType().indexOf("工作年限") != -1
														|| pitem.getType().indexOf("教育程度") != -1
														|| pitem.getType().indexOf("个人收入") != -1
														|| pitem.getType().indexOf("工作单位") != -1
														|| pitem.getType().indexOf("婚姻状况") != -1) {
							%>
							<ul>
								<%
									for (int j = 0; j < number; j++) {
								%>
								<li><label><input type="radio"
										name="radio<%=(i + 1)%>" value="<%=pchoices.get(j).getPcId()%>"><%=pchoices.get(j).getPcName()%></label>
								</li>
								<%
									}
								%>
								<%
									} else if (pitem.getType().indexOf("多选题") != -1) {
								%>
								<%
									for (int j = 0; j < number; j++) {
								%>
								<li><label><input type="checkbox"
										name="checkbox<%=(i + 1)%>" value="<%=pchoices.get(j).getPcId()%>"><%=pchoices.get(j).getPcName()%></label>
								</li>
								<%
									}
								%>
								<%
									} else if (pitem.getType().indexOf("单行填空题") != -1
															|| pitem.getType().indexOf("手机") != -1
															|| pitem.getType().indexOf("邮箱") != -1
															|| pitem.getType().indexOf("多项填空题") != -1) {
								%>
								<%
									for (int j = 0; j < number; j++) {
								%>
								<li><input type="text" name="write<%=(i+1)%>">
									<input type="hidden" name="hidden<%=(i+1) %>" value="<%=pchoices.get(j).getPcId()%>">
								</li>
								<%
									}
									} else if (pitem.getType().indexOf("多行填空题") != -1) {
								%>
								<li><textarea rows="10" cols="30" name="write<%=(i+1)%>"></textarea>
									<input type="hidden" name="hidden<%=(i+1) %>" value="<%=pchoices.get(0).getPcId()%>">
								</li>
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
			<%if(!"preview".equals(action)){ %>
				<div class="submit" style="display:block;height:40px;margin-left:46px;"><input type="submit" value="提交" id="submit" style="width:100px;"></div>
			<%} %>
			</div>
		</form>
    	</div>
    </div>
    <script type="text/javascript">
    
    $('.item li input[type="radio"]').click(function(e){
    	var ThisItem = $(this).parent().parent().parent().parent().parent();
    	ThisItem.find('#status').val('true');
    })
    $('.item li input[type="checkbox"]').click(function(e){
    	var ThisItem = $(this).parent().parent().parent().parent();
    	var checkboxes = ThisItem.find('input[type="checkbox"]');
    	
    	var bool = false;
    	
    	for(var i = 0;i < checkboxes.length;i++){
    		console.log(checkboxes[i].checked===true);
    		bool = bool||checkboxes[i].checked;
    	}
    	
    	if(bool){
    		ThisItem.find('#status').val('true');
    	}else {ThisItem.find('#status').val('false');}
    		
    	//alert(ThisItem.find('#status').val()==='true')
    });
    
    $('.item li input[type="text"]').blur(function(e){
    	var ThisItem = $(this).parent().parent().parent();
    	var This = $(this);
    	var bool = true;
    	var texts = ThisItem.find('li input[type="text"]');
    	for(var i = 0;i < texts.length;i++){
    		if(texts[i].value.length>0){
    			continue;
    		}else{
    			bool = false;
    		}
    	}
    	if(bool){
    		ThisItem.find('#status').val('true');
    	}else{
    		ThisItem.find('#status').val('false');
    	}
    	console.log(bool)
    });
    $('.item textarea').blur(function(e){
    	var ThisItem = $(this).parent().parent().parent();
    	var This = $(this);
    	if(This.val().length>0){
    		ThisItem.find('#status').val('true');
    	}else{
    		ThisItem.find('#status').val('false');
    	}
    });
    
    	function checkComplete(){
    		var bool = true;
    		var status = $('.item #status');
    		for(var i = 0;i < status.length;i++){
    			if(status[i].value==='false')
    				bool = false;
    		}
    		console.log(bool)
    		return bool;
    	}
    	
    	
    
    	$('.submit #submit').click(function(e){
    		if(!checkComplete()){
    			alert("还有题目没填");
    			return false;
    		}
    	})
    </script>
  </body>
</html>

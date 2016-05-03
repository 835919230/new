<%@ page language="java" import="java.util.*,entity.*,DAO.*,util.*" contentType="text/html; charset=utf-8" isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 设计模板的页面 -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>模板设计</title>
	<link rel="stylesheet" href="css/design.css">
	<script src="js/Sortable.js"></script>
	<script src="js/jquery-2.1.4.js"></script>
	<style>
		#update-status{
			display: none;
		    position: fixed;
		    left: 50%;
		    top: 95px;
		    background: rgb(250,255,189);
		    width: 100px;
		    height: 30px;
		    line-height: 30px;
		    text-align: center;
		    font-size: 15px;
		    z-index:3;
		}
	</style>
</head>
<body>
<%
	String action = null;
	String flag = null;
	Integer tid = null;
	Templet temp = null;
	List<Item> items = null;
	List<Choice> choices = null;
	
	action = request.getParameter("action");
	flag = request.getParameter("tid");
	
	if(flag==null){response.sendRedirect("create.jsp");}
	else{tid = Integer.parseInt(flag);}
%>
<form action="EnsureDesign" method="post" id="DesignForm">
<input type="hidden" id="tempId" value="<%=tid%>" name="tempId">
	<div class="container">
		<div class="sidebar">
			<a href="user.jsp" class="back">返回</a>
			<!-- 
			<ul>
				<li><a href="#">编辑问卷</a></li>
				<li><a href="#">收集数据</a></li>
				<li><a href="#">分析结果</a></li>
			</ul>
			 -->
		</div>
		<!-- END sidebar -->
		<div class="content">
			<div class="header">
				<div class="h2">点击左边的题型，开始设计你的问卷吧！</div>
				<div class="btn-area">
					<ul>
						<li>
							<a href="before.jsp?tid=<%=tid%>" class="published">发布问卷</a>
						</li>
						<li>
							<a href="preview.jsp?tid=<%=tid %>&action=preview" class="preview">预览问卷</a>
						</li>
						<li>
							<a href="<%=path%>/ActionServlet?action=delete&tid=<%=tid%>"
							id="delete" onclick="if(!confirm('确认删除吗？')){return false;}" class="preview">删除该模板</a>
						</li>
					</ul>
				</div>
			</div>
			<!-- END header -->
			<div class="wjcontent">
				<div class="choice-bar-box">
					<div class="choice-bar">
						<div class="overhide" id="overhide"></div>
						<div class="h2">常用题型</div>
						<ul>
							<li><a href="javascript:;" id="sg_tp">单选题</a></li>
							<li><a href="javascript:;" id="ml_tp">多选题</a></li>
							<li><a href="javascript:;" id="sl_wr_tp">单行填空题</a></li>
							<li><a href="javascript:;" id="ml_wr_tp">多行填空题</a></li>
							<li><a href="javascript:;" id="mc_wr_tp">多项填空题</a></li>
						</ul>

						<div class="h2">更多题型</div>
						<ul>
							<li><a href="javascript:;" id="sex_tp">性别</a></li>
							<li><a href="javascript:;" id="pho_tp">手机</a></li>
							<li><a href="javascript:;" id="mail_tp">邮箱</a></li>
							<li><a href="javascript:;" id="work_tp">工作年限</a></li>
							<li><a href="javascript:;" id="edu_tp">教育程度</a></li>
							<li><a href="javascript:;" id="sal_tp">个人收入</a></li>
							<li><a href="javascript:;" id="com_tp">工作单位</a></li>
							<li><a href="javascript:;" id="mar_tp">婚姻状况</a></li>
						</ul>
					</div>
				</div>
				<!-- 提交内容在这里 -->
			<!-- <form action="/ActionServlet?action=design" method="post" id="questionForm"> -->
			<div id="update-status"></div>
				<div class="edit-area-box">
				<div class="edit-area">
					<%temp = TempletDAO.findById(tid); %>
					<div class="edit-title"><input type="text" value="<%=temp.gettName() %>" class="h2" name="templetName"></div>
					<div class="edit-motal"><input type="text" value="<%=temp.getMotal() %>" class="h2" name="motal"></div>
				
				<%
					items = ItemDAO.listByTemplet(temp);
				%>
					<div class="edit-content" id="editContent">
					<%
						if(items!=null){
							for(int i = 0;i < items.size();i++){
								Item item = items.get(i);
								choices = ChoiceDAO.listByItem(item);
								if(item.getqType().indexOf("单选题") != -1
														|| item.getqType().indexOf("性别") != -1
														|| item.getqType().indexOf("工作年限") != -1
														|| item.getqType().indexOf("教育程度") != -1
														|| item.getqType().indexOf("个人收入") != -1
														|| item.getqType().indexOf("工作单位") != -1
														|| item.getqType().indexOf("婚姻状况") != -1){
									int number = choices.size();
					%>
						<div class="item" id="single">
							<input type="hidden" name="itemId" value="<%=item.getqId() %>" id="itemId">
							<div class="item-setup">
								<a href="javascript:;" id="moveUp">上移</a>
								<a href="javascript:;" id="moveDown">下移</a>
								<a href="javascript:;" id="copy">复制</a>
								<a href="javascript:;" id="delete">删除</a>
							</div>
							<div class="item-content">
								<div class="drag">
									<div class="item-title"><input type="text" value="<%=item.getqName() %>" class="h2" name="itemTitle"></div>
								</div>
								<div class="item-choice">
									<ul>
									<%for(int j = 0;j < number;j++){
										Choice choice = choices.get(j);
									%>
										<li>
											<input type="radio" name="<%=item.getqId() %>" value="<%=choice.getcId()%>">
											<input type="text" value="<%=choice.getcName() %>" class="label" name="cName">
											<a href="javascript:;" class="del">删除</a>
											<input type="hidden" name="choiceId" id="choiceId" value="<%=choice.getcId()%>">
										</li>
									<%} %>
									</ul>
								</div>
							</div>
							<div class="item-add"><a href="javascript:;">+添加选项</a></div>
						</div>
					<%
								}else if(item.getqType().indexOf("多选题")!=-1){
								int number = choices.size();
					%>
					<div class="item" id="single">
							<input type="hidden" name="itemId" value="<%=item.getqId() %>" id="itemId">
							<div class="item-setup">
								<a href="javascript:;" id="moveUp">上移</a>
								<a href="javascript:;" id="moveDown">下移</a>
								<a href="javascript:;" id="copy">复制</a>
								<a href="javascript:;" id="delete">删除</a>
							</div>
							<div class="item-content">
								<div class="drag">
									<div class="item-title"><input type="text" value="<%=item.getqName() %>" class="h2" name="itemTitle"></div>
								</div>
								<div class="item-choice">
									<ul>
									<%for(int j = 0;j < number;j++){
										Choice choice = choices.get(j);
									%>
										<li>
											<input type="checkbox" name="<%=item.getqId() %>" value="<%=choice.getcId()%>">
											<input type="text" value="<%=choice.getcName() %>" class="label" name="cName">
											<a href="javascript:;" class="del">删除</a>
											<input type="hidden" name="choiceId" id="choiceId" value="<%=choice.getcId()%>">
										</li>
									<%} %>
									</ul>
								</div>
							</div>
							<div class="item-add"><a href="javascript:;">+添加选项</a></div>
						</div>
					<%				
								}else if(item.getqType().indexOf("单行填空题") != -1
															|| item.getqType().indexOf("手机") != -1
															|| item.getqType().indexOf("邮箱") != -1){
						int number = choices.size();
					%>
					<div class="item" id="single-write">
							<input type="hidden" name="itemId" value="<%=item.getqId() %>" id="itemId">
							<div class="item-setup">
								<a href="javascript:;" id="moveUp">上移</a>
								<a href="javascript:;" id="moveDown">下移</a>
								<a href="javascript:;" id="copy">复制</a>
								<a href="javascript:;" id="delete">删除</a>
							</div>
							<div class="item-content">
								<div class="drag">
									<div class="item-title"><input type="text" value="<%=item.getqName() %>" class="h2" name="itemTitle"></div>
								</div>
								<div class="item-choice">
									<ul>
									<%for(int j = 0;j < number;j++){
										Choice choice = choices.get(j);
									%>
										<li>
											<input type="text" value="<%=choice.getcName() %>" class="label" name="cName">
											<input type="hidden" name="choiceId" id="choiceId" value="<%=choice.getcId()%>">
										</li>
									<%} %>
									</ul>
								</div>
							</div>
						</div>
					<%							
								}else if(item.getqType().indexOf("多项填空题")!=-1){
								int number = choices.size();
					%>
						<div class="item" id="multipleLine">
							<input type="hidden" name="itemId" value="<%=item.getqId() %>" id="itemId">
							<div class="item-setup">
								<a href="javascript:;" id="moveUp">上移</a>
								<a href="javascript:;" id="moveDown">下移</a>
								<a href="javascript:;" id="copy">复制</a>
								<a href="javascript:;" id="delete">删除</a>
							</div>
							<div class="item-content">
								<div class="drag">
									<div class="item-title"><input type="text" value="<%=item.getqName() %>" class="h2" name="itemTitle"></div>
								</div>
								<div class="item-choice">
									<ul>
									<%for(int j = 0;j < number;j++){
										Choice choice = choices.get(j);
									%>
										<li>
											<input type="text" value="<%=choice.getcName() %>" class="label"  name="cName">
											<a href="javascript:;" class="del">删除</a>
											<input type="hidden" name="choiceId" id="choiceId" value="<%=choice.getcId()%>">
										</li>
									<%} %>
									</ul>
								</div>
							</div>
							<div class="item-add"><a href="javascript:;">+添加选项</a></div>
						</div>
					<%			
								}else if(item.getqType().indexOf("多行填空题")!=-1){
								int number = choices.size();
					%>
						<div class="item" id="multiple-write">
							<input type="hidden" name="itemId" value="<%=item.getqId() %>" id="itemId">
							<div class="item-setup">
								<a href="javascript:;" id="moveUp">上移</a>
								<a href="javascript:;" id="moveDown">下移</a>
								<a href="javascript:;" id="copy">复制</a>
								<a href="javascript:;" id="delete">删除</a>
							</div>
							<div class="item-content">
								<div class="drag">
									<div class="item-title"><input type="text" value="<%=item.getqName() %>" class="h2" name="itemTitle"></div>
								</div>
								<div class="item-choice">
									<ul>
									<%for(int j = 0;j < number;j++){
										Choice choice = choices.get(j);
									%>
										<li>
											<textarea cols="30" rows="10" name="cName" ></textarea>
											<input type="hidden" name="choiceId" id="choiceId" value="<%=choice.getcId()%>">
										</li>
									<%} %>
									</ul>
								</div>
							</div>
						</div>
					<%
								}
							}
						}
					%>
					</div>
					<div class="edit-footer"><button type="submit" onclick="setTimeout(function(){location.href='user.jsp'},1000);">保存模板</button></div>
			
				</div>
				</div>
			</div>
			<!-- END wjcontent -->
		</div>
		<!-- END content -->
	</div>
	</form>
	<!-- END container -->
	<script src="js/design.js"></script>
	<script src="js/design1.js"></script>
	<script src="js/design2.js"></script>
	<script src="js/design5.js"></script>
	<script src="js/design6.js"></script>
	<script src="js/design7.js"></script>
	<script src="js/design8.js"></script>
	<script src="js/design9.js"></script>
	<script src="js/design10.js"></script>
	<script src="js/design14.js"></script>
	<script src="js/design15.js"></script>
	<script src="js/design16.js"></script>
	<script src="js/design17.js"></script>
	<script src="js/design18.js"></script>
</body>
</html>
<%@ page language="java" import="java.util.*,DAO.*,entity.*,util.*" pageEncoding="utf-8"  isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Publish publish = null;
	PItem pitem = null;
	PChoice pchoice = null;
	Integer pId = null;
	List<PItem> pitems = null;
	List<PChoice> pchoices = null;
	Templet temp = null;
	Date now = new Date();
	User user = null;
	User author = null;
	String flag = request.getParameter("pid");
	String type = request.getParameter("type");
	if(flag!=null){
		pId = Integer.parseInt(flag);
		publish = PublishDAO.findById(pId);
		if(publish==null){
			response.sendRedirect("user.jsp?type=publish");
			return;
		}
		temp = PublishDAO.findTempById(publish.gettId());
		author = UserDAO.findById(publish.getuId());
	}else{
		response.sendRedirect("user.jsp?type=publish");
	}
	String account = (String)session.getAttribute("account");
	if(account!=null){user = UserDAO.findByAccount(account);}
	System.out.println(type);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>report</title>
	<link rel="stylesheet" href="css/report.css">
	<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
</head>
<body>
	<div class="container">
		<div class="header">
		<%if((user!=null&&author!=null&&author.getUid()==user.getUid())){ %>
			<a href="user.jsp?type=publish" class="back" style="float:left;">返回</a><%}else{ %>
			<a href="vote.jsp?pid=<%=pId %>" class="back" style="float:left;">返回</a>
			<%} %>
			<div class="h2"><%=publish.getpName() %></div>
			<div class="time">
				<span>创建于<%=publish.getpTime() %></span>
				<span>过期时间：<%=publish.getlTime() %></span>
			</div>
		</div>
		<div class="setup-box">
			<div class="setup-area">
				<div class="status">
					<div class="triangle"></div>
					<%
					String status = null;
					Boolean bool = PublishDAO.queryIsEnd(pId);
						if(bool) status = "已结束";
						else status = "收集中";
					%>
					<span><%=status %></span>
				</div>
				<div class="setup">
					<div class="link">
						<span>问卷访问链接</span>
						<input type="text" id="link" name="link" value="<%=basePath%>vote.jsp?pid=<%=pId%>">
						<a href="javascript:copy();">复制</a>
						<script>
							function copy(){
								var t = document.getElementById('link');
								t.select();
								document.execCommand("copy");
								confirm("已复制");
							}
						</script>
					</div>
				<%
					if((user!=null&&author!=null&&author.getUid()==user.getUid())){
				%>
					<div class="button-area">
						<a href="vote.jsp?action=preview&pid=<%=pId %>" class="btn preview">预览问卷</a>
						<a href="before.jsp?tid=<%=temp.gettId() %>" class="btn copy">复制问卷</a>
						<%if(!bool){ %>
							<a href="#" class="btn end" id="EndPublish">结束问卷</a>
						<%} %>
						<a href="#" class="btn delete" id="DeletePublish">删除问卷</a>
						<%if(!"draw".equals(type)){ %>
							<a href="publish.jsp?type=draw&pid=<%=pId %>" class="btn draw">查看数据图</a><small style="line-height: 70px;margin-left: -16px;color: #74747">仅支持选择题</small>
						<%}else{ %>
							<a class="btn" href="publish.jsp?pid=<%=pId %>">查看数据表</a>
						<%} %>
					</div>
				<%
					}else{
				%>
					<div class="button-area">
						<a href="publish.jsp?type=draw" class="btn draw">查看图片信息</a><small>仅支持选择题</small>
					</div>
				<%} %>
				</div>
			</div>
		</div>
		<script>
			$('#EndPublish').click(function(event){
				var This = $(this);
				$.ajax({
					type:"post",
					url:"http://localhost:8080/P2P/EndPublish",
					data:{pid:<%=pId%>},
					dataType:"json",
					success:function(data){
								if(data.success){
									$('.status span').html('已结束');
									This.remove();
								}else{
									
								}
							},
					error:function(){alert("出现错误");}
				});
				return false;
			});
			$('#DeletePublish').click(function(event){
				$.ajax({
					type:"post",
					url:"http://localhost:8080/P2P/DeletePublish",
					data:{pid:<%=pId%>},
					dataType:"json",
					success:function(data){
								if(data.success){
									location.href="user.jsp?type=publish";
								}else{
									
								}
							},
					error:function(){alert("出现错误");}
				})
			})
		</script>
		<%
			pitems = PItemDAO.listByPublish(publish);
		%>
		<div class="analysis">
			<div class="table-box">
			<%
				int size = pitems.size();
				if(type==null){
				for(int i = 0;i < size;i++){
					pitem = pitems.get(i);
					if(pitem.getType().indexOf("单行填空题")==-1&&
						pitem.getType().indexOf("手机")==-1&&
						pitem.getType().indexOf("邮箱")==-1&&
						pitem.getType().indexOf("多行填空题")==-1&&
						pitem.getType().indexOf("多项填空题")==-1){
			%>
				<div class="table">
					<div class="h2" style="float:none;margin-left:0px;height: auto;
					line-height: 1.2;color:#000; text-align: center;"><%=pitem.getName() %></div>
					<table id="choiceTable">
					<tr class="th">
							<td>选&nbsp;项&nbsp;名</td>
							<td>选&nbsp;择&nbsp;的&nbsp;数&nbsp;量</td>
						</tr>
				<%
				pchoices = PChoiceDAO.listByPItem(pitem);
				int length = pchoices.size();
					for(int j = 0;j < length;j++){
						pchoice = pchoices.get(j);		
				%>
						<tr>
							<td><%=pchoice.getPcName() %></td>
							<td><%=pchoice.getNumbers() %></td>
						</tr>
				<%
					}
				%>
					</table>
				</div>
			<%
					}else{
			%>
			<div class="table">
					<div class="h2" style="float:none;margin-left:0px;height: auto;
					line-height: 1.2;color:#000; text-align: center;"><%=pitem.getName() %></div>
					<table id="choiceTable">
					<tr class="th">
							<td colspan="2" style="text-indent:50px;"><b>填写内容</b></td>
						</tr>
				<%
				pchoices = PChoiceDAO.listByWrite(pitem);
				int length = pchoices.size();
					for(int j = 0;j < length;j++){
						pchoice = pchoices.get(j);		
				%>
						<tr>
							<td  colspan="2"  style="text-align:center;text-indent:0px;"><%=pchoice.getPcName() %></td>
						</tr>
				<%
					}
				%>
					</table>
				</div>
			<%		
					}
					}
				}else if("draw".equals(type)){
			%>
				<img alt="图片" src="<%=path%>/BarChart?pid=<%=pId%>">
			<%
				}
			%>	
			</div>
		</div>
	</div>
</body>
</html>
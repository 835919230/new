<%@ page language="java" import="java.util.*,DAO.*,util.*,entity.*" pageEncoding="utf-8"  isErrorPage="false" errorPage="WEB-INF/error.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>引用问卷模板</title>
	<link rel="stylesheet" href="css/create.css">
	<style>
		.pages a:not(.next){padding:0px 3px;margin:0px;float:none;color:#fff;}
		.pages a{background:rgb(0,152,254);}
	</style>
</head>
<%
	final int pageSize = 10;
	int pageNum = 1;
	int pageCount = 1;
	int recordCount = 1;
	String search = request.getParameter("search");
	String pagenum = request.getParameter("pageNum");
	if(pagenum!=null){pageNum = Integer.parseInt(pagenum);}
	List<Publish> publishes = PublishDAO.listPublish(pageNum,pageSize);
	recordCount = DbManager.getCount("select count(pid) from publish");
	pageCount = (recordCount / pageSize) + 1;
%>
<body>
	<div class="container">
		<div class="header">
			<a href="create.jsp" class="back">返回</a>
			<div class="nav">
				<a href="create.jsp">创建全新问卷</a>
				<a href="templet.jsp?type=publish">引用问卷模板</a>
				<a href="reference.jsp"  class="active">复制已有问卷</a>
			</div>
		</div>

		<div class="main-auto">
			<div class="left-bg"></div>
			<div class="right-cont">
				<div class="main">
					<div class="box quote-list" style="min-width:1050px;">
						<form action="Search" method="post">
							<div class="search">
								<input type="text" class="s" id="keywords" name="keywords" placeholder="请输入关键词搜索问卷模板">
								<input type="submit" class="b" value="搜索" id="search_btn">
								<input type="hidden" name="hidden" value="reference">
							</div>
						</form>
						<div class="sear-result"></div>
						<div class="tab-list" style="min-width:1041px;">
							<table>
								<tbody>
							<%if(search==null){ 
								if(publishes!=null&&search==null){
									for(int i = 0;i < publishes.size();i++){
									Templet temp = PublishDAO.findTempById(publishes.get(i).gettId());
									int length = ItemDAO.getCount(temp);			
								%>
									<tr>
										<td class="b"><a href="preview.jsp?tid=<%=publishes.get(i).gettId()%>&action=reference&from=reference"><%=publishes.get(i).getpName() %></a></td>
										<td width="110" class="trd"><%=length %>个题目1页</td>
										<td width="110" class="tld">引用次数：<%=temp.getRefTimes() %></td>
									</tr>
								<%
									}
								} %>
							<%}else if(search!=null&&"search".equals(search)){
								publishes = (List<Publish>) session.getAttribute("SearchPublishes");
								recordCount = publishes.size();
								pageCount = (recordCount / pageSize) + 1;
								if(publishes!=null){
									if(publishes.size()-pageSize<=10){
										for(int i = (pageNum-1)*pageSize;i < publishes.size();i++){
										Templet temp = PublishDAO.findTempById(publishes.get(i).gettId());
										int length = ItemDAO.getCount(temp);			
								%>
										<tr>
											<td class="b"><a href="preview.jsp?tid=<%=publishes.get(i).gettId()%>&action=reference&from=reference"><%=publishes.get(i).getpName() %></a></td>
											<td width="110" class="trd"><%=length %>个题目1页</td>
											<td width="110" class="tld">引用次数：<%=temp.getRefTimes() %></td>
										</tr>
								<%
									}
									}else{
										for(int i = (pageNum-1)*pageSize;i < pageSize;i++){
											Templet temp = PublishDAO.findTempById(publishes.get(i).gettId());
											int length = ItemDAO.getCount(temp);
								%>
										<tr>
											<td class="b"><a href="preview.jsp?tid=<%=publishes.get(i).gettId()%>&action=reference&from=reference"><%=publishes.get(i).getpName() %></a></td>
											<td width="110" class="trd"><%=length %>个题目1页</td>
											<td width="110" class="tld">引用次数：<%=temp.getRefTimes() %></td>
										</tr>
							<%			}
									} 
								}
							}
							%>
								</tbody>
							</table>
						</div>
						<%
							StringBuffer pageUrl = new StringBuffer(request.getRequestURI()); 
							if(search!=null){ 
								pageUrl.append("?search=search");}
						%>
						<div class="pages pagenum">
							<%=Pagination.getPagination(pageNum, pageCount, recordCount, pageUrl.toString()) %>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		<script> 
	var aTr = document.getElementsByTagName('tr');
	console.log(aTr[0].children[0]);
	
		for(i=0;i<aTr.length-1;i++){
		//var ofTd = aTr[i].children[0];
		aTr[i].onmouseover=function(){
			this.style.background = 'rgb(236,250,255)';
			this.children[0].style = 'border-left: 2px solid #53a4f4;';
		}
		aTr[i].onmouseout=function(){
			this.style.background = 'white';
			this.children[0].style = 'border-left: 2px solid #fff;';
		}
	}

	</script>
</body>
</html>
package util;

public class Pagination {
	/**
	 * 
	 * @param pageNum 当前页数
	 * @param pageCount 总页数
	 * @param recordCount 总记录数
	 * @param pageUrl 页面URL
	 * @return 上一页,下一页等分页字符串
	 * @throws Exception
	 */
	public static String getPagination(int pageNum,int pageCount,int recordCount,String pageUrl) throws Exception{
		String url = pageUrl.contains("?")?pageUrl:pageUrl+"?";
		if(!url.endsWith("?")&&!url.endsWith("&"))
			url += "&";
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("第 ").append(pageNum).append("/").append(pageCount).append(" 页  共 ").append(recordCount).append(" 条记录 ");
		buffer.append(pageNum==1?"第一页 ":"<a href='"+url+"pageNum=1'>第一页</a> &nbsp;");
		buffer.append(pageNum==1?"上一页 ":"<a href='"+url+"pageNum="+(pageNum-1)+"'>上一页</a> &nbsp;");
		buffer.append(pageNum==pageCount?"下一页 ":"<a href='"+url+"pageNum="+(pageNum+1)+"'>下一页</a> &nbsp;");
		buffer.append(pageNum==pageCount?"最后一页 ":"<a href='"+url+"pageNum="+pageCount+"'>最后一页</a> &nbsp;");
		return buffer.toString();
	}
}

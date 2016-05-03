package util;

public class Pagination {
	/**
	 * 
	 * @param pageNum ��ǰҳ��
	 * @param pageCount ��ҳ��
	 * @param recordCount �ܼ�¼��
	 * @param pageUrl ҳ��URL
	 * @return ��һҳ,��һҳ�ȷ�ҳ�ַ���
	 * @throws Exception
	 */
	public static String getPagination(int pageNum,int pageCount,int recordCount,String pageUrl) throws Exception{
		String url = pageUrl.contains("?")?pageUrl:pageUrl+"?";
		if(!url.endsWith("?")&&!url.endsWith("&"))
			url += "&";
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("�� ").append(pageNum).append("/").append(pageCount).append(" ҳ  �� ").append(recordCount).append(" ����¼ ");
		buffer.append(pageNum==1?"��һҳ ":"<a href='"+url+"pageNum=1'>��һҳ</a> &nbsp;");
		buffer.append(pageNum==1?"��һҳ ":"<a href='"+url+"pageNum="+(pageNum-1)+"'>��һҳ</a> &nbsp;");
		buffer.append(pageNum==pageCount?"��һҳ ":"<a href='"+url+"pageNum="+(pageNum+1)+"'>��һҳ</a> &nbsp;");
		buffer.append(pageNum==pageCount?"���һҳ ":"<a href='"+url+"pageNum="+pageCount+"'>���һҳ</a> &nbsp;");
		return buffer.toString();
	}
}

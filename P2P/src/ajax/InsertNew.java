package ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ChoiceDAO;
import DAO.ItemDAO;
import DAO.UserDAO;
import entity.Choice;
import entity.Item;
import entity.User;


@WebServlet("/InsertNew")
public class InsertNew extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public InsertNew() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		String type = request.getParameter("type");
		PrintWriter out = response.getWriter();
		User user = null;
		String account = (String)request.getSession().getAttribute("account");
		String flag = request.getParameter("tid");
		Integer tid = null;
		StringBuffer sb = new StringBuffer();
		synchronized (this) {
			if(flag!=null){
				tid = Integer.parseInt(flag);
			}
			try {
				user = UserDAO.findByAccount(account);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(user==null){
				out.println("\"success\":false,\"msg\":\"您没有登录\"}");
				return;
			}
			if(type!=null){
				System.out.println(type);
				
				if("单选题".equalsIgnoreCase(type)){
					Item item = new Item("单选题","单选题",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("选项一",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("选项二",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;
						choice2 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("多选题".equalsIgnoreCase(type)){
					Item item = new Item("多选题","多选题",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("选项一",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("选项二",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;
						choice2 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("单行填空题".equalsIgnoreCase(type)){
					Item item = new Item("单行填空题","单行填空题",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("多行填空题".equalsIgnoreCase(type)){
					Item item = new Item("多行填空题","多行填空题",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("多项填空题".equalsIgnoreCase(type)){
					Item item = new Item("多项填空题","多项填空题",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;
						choice2 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("性别".equalsIgnoreCase(type)){
					Item item = new Item("性别","性别",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("男",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("女",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;
						choice2 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("手机".equalsIgnoreCase(type)){
					Item item = new Item("手机号码","手机",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("邮箱".equalsIgnoreCase(type)){
					Item item = new Item("邮箱","邮箱",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("工作年限".equalsIgnoreCase(type)){
					Item item = new Item("工作年限","工作年限",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("1年及以下",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("2-3年",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						Choice choice3 = new Choice("4-5年",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice3);
						Choice choice4 = new Choice("6-9年",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice4);
						Choice choice5 = new Choice("10年及以上",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice5);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"cid3\":").append(choice3.getcId()).
							append(",\"cid4\":").append(choice4.getcId()).
							append(",\"cid5\":").append(choice5.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;choice2 = null;choice3 = null;choice4 = null;choice5 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("教育程度".equalsIgnoreCase(type)){
					Item item = new Item("受教育程度","教育程度",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("初中",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("高中",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						Choice choice3 = new Choice("大专",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice3);
						Choice choice4 = new Choice("本科",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice4);
						Choice choice5 = new Choice("硕士及以上",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice5);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"cid3\":").append(choice3.getcId()).
							append(",\"cid4\":").append(choice4.getcId()).
							append(",\"cid5\":").append(choice5.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;choice2 = null;choice3 = null;choice4 = null;choice5 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("个人收入".equalsIgnoreCase(type)){
					Item item = new Item("个人月收入","个人收入",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("2000元及以下",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("2001-3000元",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						Choice choice3 = new Choice("3001-5000元",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice3);
						Choice choice4 = new Choice("5001-8000元",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice4);
						Choice choice5 = new Choice("8001-12000元",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice5);
						Choice choice6 = new Choice("12001-20000元",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice6);
						Choice choice7 = new Choice("20001元以上",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice7);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"cid3\":").append(choice3.getcId()).
							append(",\"cid4\":").append(choice4.getcId()).
							append(",\"cid5\":").append(choice5.getcId()).
							append(",\"cid6\":").append(choice6.getcId()).
							append(",\"cid7\":").append(choice7.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;choice2 = null;choice3 = null;choice4 = null;choice5 = null;choice6=null;choice7=null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("工作单位".equalsIgnoreCase(type)){
					Item item = new Item("工作单位性质","工作单位",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("行政事业单位",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("合资（包括外商独资）",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						Choice choice3 = new Choice("国营（包括集体）",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice3);
						Choice choice4 = new Choice("私营",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice4);
						Choice choice5 = new Choice("境内上市股份公司",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice5);
						Choice choice6 = new Choice("其他",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice6);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"cid3\":").append(choice3.getcId()).
							append(",\"cid4\":").append(choice4.getcId()).
							append(",\"cid5\":").append(choice5.getcId()).
							append(",\"cid6\":").append(choice6.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;choice2 = null;choice3 = null;choice4 = null;choice5 = null;choice6=null;
					} catch (Exception e) {
						e.printStackTrace();
						out.print("{\"success\":false}");
					}
				}else if("婚姻状况".equalsIgnoreCase(type)){
					Item item = new Item("婚姻状况","婚姻状况",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("已婚",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("单身",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"msg\":\"修改成功\"").
							append("}");
						out.println(sb.toString());
						
						sb.delete(0, sb.length());
						out.flush();
						out.close();
						user = null;
						choice1 = null;
						choice2 = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

}

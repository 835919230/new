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
				out.println("\"success\":false,\"msg\":\"��û�е�¼\"}");
				return;
			}
			if(type!=null){
				System.out.println(type);
				
				if("��ѡ��".equalsIgnoreCase(type)){
					Item item = new Item("��ѡ��","��ѡ��",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("ѡ��һ",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("ѡ���",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("��ѡ��".equalsIgnoreCase(type)){
					Item item = new Item("��ѡ��","��ѡ��",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("ѡ��һ",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("ѡ���",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("���������".equalsIgnoreCase(type)){
					Item item = new Item("���������","���������",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("���������".equalsIgnoreCase(type)){
					Item item = new Item("���������","���������",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("���������".equalsIgnoreCase(type)){
					Item item = new Item("���������","���������",tid,user.getUid());
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
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("�Ա�".equalsIgnoreCase(type)){
					Item item = new Item("�Ա�","�Ա�",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("��",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("Ů",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("�ֻ�".equalsIgnoreCase(type)){
					Item item = new Item("�ֻ�����","�ֻ�",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("����".equalsIgnoreCase(type)){
					Item item = new Item("����","����",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("��������".equalsIgnoreCase(type)){
					Item item = new Item("��������","��������",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("1�꼰����",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("2-3��",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						Choice choice3 = new Choice("4-5��",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice3);
						Choice choice4 = new Choice("6-9��",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice4);
						Choice choice5 = new Choice("10�꼰����",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice5);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"cid3\":").append(choice3.getcId()).
							append(",\"cid4\":").append(choice4.getcId()).
							append(",\"cid5\":").append(choice5.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("�����̶�".equalsIgnoreCase(type)){
					Item item = new Item("�ܽ����̶�","�����̶�",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("����",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("����",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						Choice choice3 = new Choice("��ר",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice3);
						Choice choice4 = new Choice("����",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice4);
						Choice choice5 = new Choice("˶ʿ������",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice5);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"cid3\":").append(choice3.getcId()).
							append(",\"cid4\":").append(choice4.getcId()).
							append(",\"cid5\":").append(choice5.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("��������".equalsIgnoreCase(type)){
					Item item = new Item("����������","��������",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("2000Ԫ������",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("2001-3000Ԫ",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						Choice choice3 = new Choice("3001-5000Ԫ",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice3);
						Choice choice4 = new Choice("5001-8000Ԫ",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice4);
						Choice choice5 = new Choice("8001-12000Ԫ",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice5);
						Choice choice6 = new Choice("12001-20000Ԫ",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice6);
						Choice choice7 = new Choice("20001Ԫ����",item.getqId(),tid,user.getUid());
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
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("������λ".equalsIgnoreCase(type)){
					Item item = new Item("������λ����","������λ",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("������ҵ��λ",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("���ʣ��������̶��ʣ�",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						Choice choice3 = new Choice("��Ӫ���������壩",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice3);
						Choice choice4 = new Choice("˽Ӫ",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice4);
						Choice choice5 = new Choice("�������йɷݹ�˾",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice5);
						Choice choice6 = new Choice("����",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice6);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"cid3\":").append(choice3.getcId()).
							append(",\"cid4\":").append(choice4.getcId()).
							append(",\"cid5\":").append(choice5.getcId()).
							append(",\"cid6\":").append(choice6.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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
				}else if("����״��".equalsIgnoreCase(type)){
					Item item = new Item("����״��","����״��",tid,user.getUid());
					try {
						ItemDAO.insert(item);
						Choice choice1 = new Choice("�ѻ�",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice1);
						Choice choice2 = new Choice("����",item.getqId(),tid,user.getUid());
						ChoiceDAO.insert(choice2);
						sb.append("{\"success\":true,\"itemId\":").
							append(item.getqId()).
							append(",\"cid1\":").append(choice1.getcId()).
							append(",\"cid2\":").append(choice2.getcId()).
							append(",\"msg\":\"�޸ĳɹ�\"").
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

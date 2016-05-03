package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;

import DAO.*;
import entity.*;
import util.*;

@WebServlet(urlPatterns = { "/ActionServlet" })
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ActionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		String action = request.getParameter("action");
		System.out.println(action);
		/**
		 * �����¼
		 */
		if(action!=null&&action.equals("login")){
			String saveme = request.getParameter("saveme");
			String account  = request.getParameter("account");
			String password = request.getParameter("password");
			if(account!=null&&password!=null&&!"".equals(account)&&!"".equals(password)){
				try {
					User user = null;
					synchronized (this) {
						user = UserDAO.findByAccount(account);
					}
					if(user.getPassword().equals(DbManager.MD5(password))){
						HttpSession session = request.getSession();
						session.setAttribute("account", account);
						session.setAttribute("username", user.getUsername());
						request.setAttribute("status", "successful");
					}
					/**
					 * ����Ƿ��ס���룬���ѡ���˾�ʹ��cookie
					 */
					if(saveme!=null&&saveme.equals("true")){
						System.out.println(saveme);
						Cookie accountCookie = new Cookie("accountCookie", account);
						Cookie passwordCookie = new Cookie("passwordCookie",password);
						accountCookie.setMaxAge(864000);
						passwordCookie.setMaxAge(864000);
						response.addCookie(accountCookie);
						response.addCookie(passwordCookie);
					}else{
						Cookie[] cookies = request.getCookies();
						if(cookies.length>0){
							for(Cookie cookie:cookies){
								if(cookie.getName().equals("accountCookie")||cookie.getName().equals("passwordCookie")){
									cookie.setMaxAge(0);
									response.addCookie(cookie);
								}
							}
						}
					}
				} catch (Exception e) {
					request.setAttribute("status", "fail");
					e.printStackTrace();
				}
			}else{
				response.sendRedirect("index.jsp");
			}
			request.getRequestDispatcher("user.jsp").forward(request, response);
		}
		/**
		 * ����ǳ�
		 */
		else if(action!=null&&action.equals("logout")){
			HttpSession session = request.getSession();
			if(session.getAttribute("account")!=null&&session.getAttribute("username")!=null){
				session.removeAttribute("account");
				session.removeAttribute("username");
			}
			response.sendRedirect("index.jsp");
		}
		/**
		 * Ajax��ѯ�˻��Ƿ����ע��
		 */
		else if(action.equals("query")){
			String account = request.getParameter("account");
			try {
				User user = null;
				synchronized (this) {
					user = UserDAO.findByAccount(account);
				}
				if(user==null){
					response.getWriter().println("{\"success\":true,\"msg\":\"���˻���δ��ʹ��\"}");
				}else if(user!=null){
					response.getWriter().println("{\"success\":false,\"msg\":\"���˻����Ѵ���\"}");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().println("{\"success\":false,\"msg\":\"����������\"}");
			}
		}
		/**
		 * ����ע��
		 */
		else if(action.equals("register")){
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			User u = null;
			if(account!=null&&password!=null){
				try {
					synchronized (this) {
						u = UserDAO.findByAccount(account);
					}
					if(u==null){
						User user = new User(account,"����",password);
						UserDAO.insert(user);
						HttpSession session = request.getSession();
						//��֤ע��֮��������¼
						session.removeAttribute("account");
						session.removeAttribute("password");
						session.setAttribute("account", account);
						session.setAttribute("username", user.getUsername());
						//�Ƴ���Ϊ�����û������ֵ�״̬��Ϣ
						session.removeAttribute("status");
						//request.getRequestDispatcher("user.jsp").forward(request, response);
						response.sendRedirect("user.jsp");
					}else{
						request.getSession().setAttribute("status", "fail");
						//request.getRequestDispatcher("register.jsp").forward(request, response);
						response.sendRedirect("index.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						DbManager.getConnection().rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}else{
				response.sendRedirect("register.jsp");
			}
		}
		/**
		 * �޸��û���AJAX
		 */
		else if(action!=null&&action.equals("username")){
			String newUsername = request.getParameter("newUsername");
			String account = request.getParameter("account");
			PrintWriter out = response.getWriter();
			if(account!=null&&newUsername!=null){
				try {
					int i = 0;
					User user = null;
					synchronized (this) {
						user = UserDAO.findByAccount(account);
						user.setUsername(newUsername);
						i = UserDAO.saveUserName(user);
					}
					if(i==1){
						out.println("{\"success\":true,\"msg\":\"�޸ĳɹ�\"}");
						HttpSession session = request.getSession();
						session.removeAttribute("username");
						session.setAttribute("username", newUsername);
					}else{
						out.println("{\"success\":false,\"msg\":\"�޸�ʧ��\"}");
					}
				} catch (Exception e) {
					e.printStackTrace();
					out.println("{\"success\":false,\"msg\":\"�������쳣\"}");
				}
			}
			out.flush();
			out.close();
		}
		/**
		 * �޸�����AJAX
		 */
		else if(action!=null&&action.equals("password")){
			String newPassword = request.getParameter("newPassword");
			String oldPassword = request.getParameter("oldPassword");
			String account = request.getParameter("account");
			PrintWriter out = response.getWriter();
			if(newPassword!=null&&oldPassword!=null&&account!=null){
				try {
					User user = null;
					synchronized (this) {
						user = UserDAO.findByAccount(account);
						if(DbManager.MD5(oldPassword).equals(user.getPassword())){
							user.setPassword(newPassword);
							UserDAO.save(user);
							out.println("{\"success\":true,\"msg\":\"�޸ĳɹ�\"}");
						}else{
							out.println("{\"success\":false,\"msg\":\"ǰ�����벻һ��\"}");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					out.println("{\"success\":false,\"msg\":\"�������쳣\"}");
				}
			}
		}
		
		else if(action.equals("delete")){ 
			Integer tId = Integer.parseInt(request.getParameter("tid"));
			if(tId!=null){
				Templet temp;
				try {
					synchronized (this) {
						temp = TempletDAO.findById(tId);
						ChoiceDAO.deleteByTemplet(temp);
						ItemDAO.deleteByTemplet(temp);
						TempletDAO.deleteById(tId);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			response.sendRedirect("user.jsp");
		}
		/**
		 * �ղ�ģ��
		 */
		else if(action!=null&&action.equals("collect")){
			HttpSession session = request.getSession();
			String account = (String) session.getAttribute("account");
			Integer tmpId = Integer.parseInt(request.getParameter("tmpId"));
			try {
				synchronized (this) {
					User user = UserDAO.findByAccount(account);
					Templet temp = TempletDAO.findById(tmpId);
					if(temp!=null){
						Collection collection = new Collection(tmpId,user.getUid());
						System.out.println(user.getUid());
						CollectionDAO.insert(collection);
					}
				}
				response.sendRedirect("preview.jsp?tid="+tmpId);;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * ����ģ��
		 */
		else if(action!=null&&action.equals("publish")){
			Integer tId = Integer.parseInt(request.getParameter("tid"));
			String pname = request.getParameter("pname");
			String select = request.getParameter("select");
			Date now = new Date();
			Date end = new Date();
			if(select.equals("1���")){
				end.setDate(now.getDate()+1);
			}else if(select.equals("3���")){
				end.setDate(now.getDate()+3);
			}else if(select.equals("5���")){
				end.setDate(now.getDate()+5);
			}else if(select.equals("1�ܺ�")){
				end.setDate(now.getDate()+7);
			}else if(select.equals("2�ܺ�")){
				end.setDate(now.getDate()+14);
			}else if(select.equals("һ�º�")){
				end.setDate(now.getDate()+30);
			}
			HttpSession session = request.getSession();
			String account = (String) session.getAttribute("account");
			if(tId!=null&&account!=null){
				try {
					Integer pId = null;
					synchronized (this) {
						Templet temp = TempletDAO.findById(tId);
						User user = UserDAO.findByAccount(account);
						/**
						 * ����publish��
						 */
						Publish publish = new Publish(10000,pname,now,end,user.getUid(),tId);
						publish.setIsEnd(false);
						PublishDAO.insert(publish);
						/**
						 * ����pitem��
						 */
						List<Item> items = ItemDAO.listByTemplet(temp);
						PItemDAO.insertManyPItems(items, publish, user);
						/**
						 * ����pchoice��
						 */
						List<PItem>pitems = PItemDAO.listByPublish(publish);
						PChoiceDAO.insertManyPChoices(publish, pitems, items, user);
						TempletDAO.saverefTimes(temp);
						pId = publish.getpId();
					}
					/**
					 * JUnit������ʱ800ms����
					 */
					response.sendRedirect("publish.jsp?pid="+pId);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		/**
		 * ����ͶƱ����
		 */
		else if(action!=null&&"vote".equals(action)){
			String flag = request.getParameter("pid");
			Publish publish = null;
			List<PItem> pitems = null;
			StringBuffer insertBuffer = new StringBuffer("insert into pchoice_copy(pcid,pcname,numbers,id,pid,uid) values");
			StringBuffer updateBuffer = new StringBuffer("update pchoice set numbers = case pcid");
			StringBuffer after = new StringBuffer("end where pcid in(");
			String account = (String) request.getSession().getAttribute("account");
			User user = null;
			if(flag!=null){
				Integer pId = Integer.parseInt(flag);
				try {
					if(account!=null){
						user = UserDAO.findByAccount(account);
					}
					publish = PublishDAO.findById(pId);
					pitems = PItemDAO.listByPublish(publish);
					int length = pitems.size();
					for(int i = 0;i < length;i++){
						PItem pitem = pitems.get(i);
						/**
						 * �����һ������
						 */
						if(pitem.getType().indexOf("��ѡ��") != -1
								|| pitem.getType().indexOf("�Ա�") != -1
								|| pitem.getType().indexOf("��������") != -1
								|| pitem.getType().indexOf("�����̶�") != -1
								|| pitem.getType().indexOf("��������") != -1
								|| pitem.getType().indexOf("������λ") != -1
								|| pitem.getType().indexOf("����״��") != -1){
							Integer pcid = Integer.parseInt(request.getParameter("radio"+(i+1)));
							updateBuffer.append(" when").append(" ").append(pcid).append(" then numbers+1");
							after.append(pcid).append(",");
						}
						/**
						 * ����ڶ�������
						 */
						else if(pitem.getType().indexOf("��ѡ��") != -1){
							String[] pcids = request.getParameterValues("checkbox"+(i+1));
							for(int j = 0;j < pcids.length;j++){
								Integer pcid = Integer.parseInt(pcids[j]);
								updateBuffer.append(" when").append(" ").append(pcid).append(" then numbers+1");
								after.append(pcid).append(",");
							}
						}
						/**
						 * �������������
						 */
						else if(pitem.getType().indexOf("���������") != -1
															|| pitem.getType().indexOf("�ֻ�") != -1
															|| pitem.getType().indexOf("����") != -1
															|| pitem.getType().indexOf("���������") != -1
															|| pitem.getType().indexOf("���������") != -1){
							String[] values = request.getParameterValues("write"+(i+1));
							String[] list = request.getParameterValues("hidden"+(i+1));
							for(int j = 0;j < values.length;j++){
								Integer pcid = Integer.parseInt(list[j]);
								PChoice pchoice = PChoiceDAO.findById(pcid);
								insertBuffer.append("(").
											 append(pchoice.getPcId()).append(",'").
											 append(StringEscapeUtils.escapeHtml4(values[j])).append("',").
											 append(0).append(",").
											 append(pchoice.getId()).append(",").
											 append(pchoice.getpId()).append(",").
											 append(pchoice.getuId()).append("),");
							}
						}
					}
					after.delete(after.length()-1, after.length()).append(")");
					updateBuffer.append(" ").append(after);
					insertBuffer.delete(insertBuffer.length()-1, insertBuffer.length());
					System.out.println(updateBuffer.toString());
					System.out.println(insertBuffer.toString());
					synchronized (this) {
						DbManager.executeUpdate(updateBuffer.toString());
						if(insertBuffer.length()>"insert into pchoice_copy(pcid,pcname,numbers,id,pid,uid) value".length())
							DbManager.executeUpdate(insertBuffer.toString());
					}
					/**
					 * �����Ҳ�����ʾ���Ϣ
					 */
					if(user!=null&&!PaticipateDAO.queryParticipate(user.getUid(), pId)){
							PaticipateDAO.insert(new Paticipate(pId,user.getUid()));
						
					}
					request.getRequestDispatcher("WEB-INF/success.jsp").forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
			response.sendRedirect("index.jsp");
		}
	}

}

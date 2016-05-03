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
		 * 处理登录
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
					 * 检查是否记住密码，如果选择了就使用cookie
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
		 * 处理登出
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
		 * Ajax查询账户是否可以注册
		 */
		else if(action.equals("query")){
			String account = request.getParameter("account");
			try {
				User user = null;
				synchronized (this) {
					user = UserDAO.findByAccount(account);
				}
				if(user==null){
					response.getWriter().println("{\"success\":true,\"msg\":\"该账户名未被使用\"}");
				}else if(user!=null){
					response.getWriter().println("{\"success\":false,\"msg\":\"该账户名已存在\"}");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().println("{\"success\":false,\"msg\":\"服务器错误\"}");
			}
		}
		/**
		 * 处理注册
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
						User user = new User(account,"匿名",password);
						UserDAO.insert(user);
						HttpSession session = request.getSession();
						//保证注册之后立即登录
						session.removeAttribute("account");
						session.removeAttribute("password");
						session.setAttribute("account", account);
						session.setAttribute("username", user.getUsername());
						//移除因为存在用户而出现的状态信息
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
		 * 修改用户名AJAX
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
						out.println("{\"success\":true,\"msg\":\"修改成功\"}");
						HttpSession session = request.getSession();
						session.removeAttribute("username");
						session.setAttribute("username", newUsername);
					}else{
						out.println("{\"success\":false,\"msg\":\"修改失败\"}");
					}
				} catch (Exception e) {
					e.printStackTrace();
					out.println("{\"success\":false,\"msg\":\"服务器异常\"}");
				}
			}
			out.flush();
			out.close();
		}
		/**
		 * 修改密码AJAX
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
							out.println("{\"success\":true,\"msg\":\"修改成功\"}");
						}else{
							out.println("{\"success\":false,\"msg\":\"前后密码不一致\"}");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					out.println("{\"success\":false,\"msg\":\"服务器异常\"}");
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
		 * 收藏模板
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
		 * 发布模板
		 */
		else if(action!=null&&action.equals("publish")){
			Integer tId = Integer.parseInt(request.getParameter("tid"));
			String pname = request.getParameter("pname");
			String select = request.getParameter("select");
			Date now = new Date();
			Date end = new Date();
			if(select.equals("1天后")){
				end.setDate(now.getDate()+1);
			}else if(select.equals("3天后")){
				end.setDate(now.getDate()+3);
			}else if(select.equals("5天后")){
				end.setDate(now.getDate()+5);
			}else if(select.equals("1周后")){
				end.setDate(now.getDate()+7);
			}else if(select.equals("2周后")){
				end.setDate(now.getDate()+14);
			}else if(select.equals("一月后")){
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
						 * 插入publish表
						 */
						Publish publish = new Publish(10000,pname,now,end,user.getUid(),tId);
						publish.setIsEnd(false);
						PublishDAO.insert(publish);
						/**
						 * 插入pitem表
						 */
						List<Item> items = ItemDAO.listByTemplet(temp);
						PItemDAO.insertManyPItems(items, publish, user);
						/**
						 * 插入pchoice表
						 */
						List<PItem>pitems = PItemDAO.listByPublish(publish);
						PChoiceDAO.insertManyPChoices(publish, pitems, items, user);
						TempletDAO.saverefTimes(temp);
						pId = publish.getpId();
					}
					/**
					 * JUnit测试用时800ms左右
					 */
					response.sendRedirect("publish.jsp?pid="+pId);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		/**
		 * 处理投票请求
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
						 * 处理第一种题型
						 */
						if(pitem.getType().indexOf("单选题") != -1
								|| pitem.getType().indexOf("性别") != -1
								|| pitem.getType().indexOf("工作年限") != -1
								|| pitem.getType().indexOf("教育程度") != -1
								|| pitem.getType().indexOf("个人收入") != -1
								|| pitem.getType().indexOf("工作单位") != -1
								|| pitem.getType().indexOf("婚姻状况") != -1){
							Integer pcid = Integer.parseInt(request.getParameter("radio"+(i+1)));
							updateBuffer.append(" when").append(" ").append(pcid).append(" then numbers+1");
							after.append(pcid).append(",");
						}
						/**
						 * 处理第二种题型
						 */
						else if(pitem.getType().indexOf("多选题") != -1){
							String[] pcids = request.getParameterValues("checkbox"+(i+1));
							for(int j = 0;j < pcids.length;j++){
								Integer pcid = Integer.parseInt(pcids[j]);
								updateBuffer.append(" when").append(" ").append(pcid).append(" then numbers+1");
								after.append(pcid).append(",");
							}
						}
						/**
						 * 处理第三种题型
						 */
						else if(pitem.getType().indexOf("单行填空题") != -1
															|| pitem.getType().indexOf("手机") != -1
															|| pitem.getType().indexOf("邮箱") != -1
															|| pitem.getType().indexOf("多项填空题") != -1
															|| pitem.getType().indexOf("多行填空题") != -1){
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
					 * 保留我参与的问卷信息
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

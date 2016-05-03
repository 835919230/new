package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.TempletDAO;
import DAO.UserDAO;
import entity.Templet;
import entity.User;

/**
 * Servlet implementation class CreateTemplateServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/createTemplate" })
public class CreateTemplateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreateTemplateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		try {
			user = UserDAO.findByAccount((String)request.getSession().getAttribute("account"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String title = request.getParameter("title");
		String motal = request.getParameter("motal");
		String isshared = request.getParameter("isshared");
		Templet temp = null;
		if(title!=null&&motal!=null){
			temp = new Templet();
			temp.settName(title);
			temp.setMotal(motal);
			temp.setdTime(new Date());
			if(isshared!=null&&"true".equals(isshared)){
				temp.setIsShared(true);
			}else{
				temp.setIsShared(false);
			}
			temp.setRefTimes(0);
			temp.setuId(user.getUid());
			try {
				TempletDAO.insert(temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect("design.jsp?tid="+temp.gettId());
		}else{
			response.sendRedirect("create.jsp");
		}
	}

}

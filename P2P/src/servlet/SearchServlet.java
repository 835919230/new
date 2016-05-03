package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Publish;
import entity.Templet;
import DAO.PublishDAO;
import DAO.TempletDAO;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/Search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keywords = null;
		synchronized (this) {
			HttpSession session = request.getSession();
			session.removeAttribute("SearchTemps");
			session.removeAttribute("SearchPublishes");
			try {
				String flag = request.getParameter("hidden");
				if(flag.equals("templet")){
					keywords = request.getParameter("search");
					List<Templet> templets = TempletDAO.listByKeywords(keywords);
					session.setAttribute("SearchTemps", templets);
					response.sendRedirect("templet.jsp?search=search");
				}else if(flag.equals("reference")){
					keywords = request.getParameter("keywords");
					List<Publish> publishes = PublishDAO.listByKeywords(keywords);
					session.setAttribute("SearchPublishes", publishes);
					response.sendRedirect("reference.jsp?search=search");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

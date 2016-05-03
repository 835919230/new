package ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.PriorityDAO;

/**
 * Servlet implementation class AddPriorityServlet
 */
@WebServlet("/AddPriority")
public class AddPriorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public AddPriorityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		String flag = request.getParameter("pid");
		try {
			if(flag!=null&&!"".equals(flag)){
				Integer pId = Integer.parseInt(flag);
				int i = 0;
				PrintWriter out = response.getWriter();
				synchronized (this) {
					i = PriorityDAO.insert(pId);
					if(i>0){
						out.println("{\"success\":true}");
					}else{
						out.println("{\"success\":false}");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

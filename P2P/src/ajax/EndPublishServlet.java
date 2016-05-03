package ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.PublishDAO;

/**
 * Servlet implementation class EndPublishServlet
 */
@WebServlet("/EndPublish")
public class EndPublishServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EndPublishServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		String flag = null;
		synchronized (this) {
			try{
				flag = request.getParameter("pid");
				if(flag!=null){
					Integer pId = Integer.parseInt(flag);
					int i = PublishDAO.setEnd(pId);
					if(i>0){
						out.println("{\"success\":true}");
					}else{
						out.println("{\"success\":false}");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}

package ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ChoiceDAO;
/**
 * �������ģ��������Ŀ��servlet
 * @author ��
 *
 */
@WebServlet("/DeleteChoice")
public class DeleteChoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DeleteChoiceServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		
		String flag = request.getParameter("choiceId");
		try {
			if(flag!=null){
				Integer choiceId = Integer.parseInt(flag);
				int i = ChoiceDAO.deleteById(choiceId);
				PrintWriter out = response.getWriter();
				if(i>0){
					out.println("{\"success\":true,\"msg\":\"�޸ĳɹ�\"}");
				}else{
					out.println("{\"success\":false,\"msg\":\"�޸�ʧ��\"}");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

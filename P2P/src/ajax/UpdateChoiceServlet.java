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
 * 处理设计模板上移题目的servlet
 * @author 诚
 *
 */
@WebServlet("/UpdateChoice")
public class UpdateChoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UpdateChoiceServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		String flag1 = null;
		String flag2 = null;
		Integer choiceId = null;
		PrintWriter out = response.getWriter();
		synchronized (this) {
			flag1 = request.getParameter("choiceId");
			flag2 = request.getParameter("cName");
			try{
				if(flag1!=null&&flag2!=null){
					choiceId = Integer.parseInt(flag1);
					int i = ChoiceDAO.saveCName(choiceId,flag2);
					Integer newChoiceId = ChoiceDAO.queryMaxId();
					if(i>0){
						out.println("{\"success\":true,\"msg\":\"修改成功\"}");
					}else{
						out.println("{\"success\":false,\"msg\":\"修改失败\"}");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}

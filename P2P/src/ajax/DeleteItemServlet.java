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
/**
 * 处理设计模板上移题目的servlet
 * @author 诚
 *
 */
@WebServlet("/DeleteItem")
public class DeleteItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DeleteItemServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		
		String flag = request.getParameter("itemId");
		try{
			if(flag!=null){
				Integer itemId = Integer.parseInt(flag);
				int i = ItemDAO.deleteById(itemId);
				int j = ChoiceDAO.deleteByItem(itemId);
				PrintWriter out = response.getWriter();
				if(i>0&&j>0){
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

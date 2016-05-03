package ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Item;
import DAO.ItemDAO;
/**
 * 处理设计模板上移题目的servlet
 * @author 诚
 *
 */
@WebServlet("/MoveUp")
public class MoveUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MoveUpServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		
		String flag1 = request.getParameter("itemId");
		String flag2 = request.getParameter("brotherId");
		Integer itemId = null;
		Integer brotherId = null;
		PrintWriter out = response.getWriter();
		
		if(flag1!=null&&flag2!=null){
			try{
				itemId 	  = Integer.parseInt(flag1);
				brotherId = Integer.parseInt(flag2);
				int i = -100;
				i=ItemDAO.exchangeId(itemId, brotherId);
				if(i!=-100){
					out.println("{\"success\":true,\"msg\":\"保存成功\",\"itemId\":"+brotherId+",\"brotherId\":"+itemId+"}");
				}else{
					out.println("{\"success\":false,\"msg\":\"保存失败\"}");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}

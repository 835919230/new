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
@WebServlet("/CopyItem")
public class CopyItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CopyItemServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		
		String flag = null;
		Integer itemId = null;
		PrintWriter out = response.getWriter();
		synchronized (this) {
			flag = request.getParameter("itemId");
			try{
				if(flag!=null){
					itemId = Integer.parseInt(flag);
					Integer copyItemId = ItemDAO.copyItem(itemId);
					Integer  cid = ChoiceDAO.queryMaxId();
					out.println("{\"success\":true,\"msg\":\"修改成功\",\"newItemId\":"+copyItemId+",\"maxCid\":"+cid+"}");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}

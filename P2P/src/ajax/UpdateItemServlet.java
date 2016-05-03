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
 * �������ģ��������Ŀ��servlet
 * @author ��
 *
 */
@WebServlet("/UpdateItem")
public class UpdateItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UpdateItemServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		String flag1 = null;
		String flag2 = null;
		Integer itemId = null;
		PrintWriter out = response.getWriter();
		synchronized (this) {
			flag1 = request.getParameter("itemId");
			flag2 = request.getParameter("qName");
			try{
				if(flag1!=null&&flag2!=null){
					itemId = Integer.parseInt(flag1);
					int i = ItemDAO.saveItemName(itemId, flag2);
					//Integer newChoiceId = ChoiceDAO.queryMaxId();
					if(i>0){
						out.println("{\"success\":true,\"msg\":\"�޸ĳɹ�\"}");
					}else{
						out.println("{\"success\":false,\"msg\":\"�޸�ʧ��\"}");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}

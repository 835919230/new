package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DbManager;

/**
 * Servlet implementation class EnsureDesignServlet
 */
@WebServlet("/EnsureDesign")
public class EnsureDesignServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnsureDesignServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tName 	= request.getParameter("templetName");
		String motal 	= request.getParameter("motal");
		String tIdFlag  = request.getParameter("tempId");
		String[] qIdFlags 	= request.getParameterValues("itemId");
		String[] qNames 	= request.getParameterValues("itemTitle");
		String[] cIdFlags 	= request.getParameterValues("choiceId");
		String[] cNames 	= request.getParameterValues("cName");
		Connection conn = null;
		PreparedStatement TempPstmt = null;
		PreparedStatement ItemPstmt = null;
		PreparedStatement ChoicePstmt = null;
		try {
			synchronized (DbManager.Lock) {
				conn = DbManager.getConnection();
				TempPstmt = conn.prepareStatement("update templet set tname = ?,motal = ? where tid = ?");
				TempPstmt.setString(1, tName);
				TempPstmt.setString(2, motal);
				TempPstmt.setInt(3, Integer.parseInt(tIdFlag));
				TempPstmt.addBatch();
				ItemPstmt = conn.prepareStatement("update item set qname= ? where qid = ?");
				for(int i = 0;i < qIdFlags.length;i++){
					ItemPstmt.setString(1, qNames[i]);
					ItemPstmt.setInt(2, Integer.parseInt(qIdFlags[i]));
					ItemPstmt.addBatch();
				}
				if(cNames.length==cIdFlags.length){
					ChoicePstmt = conn.prepareStatement("update choice set cname = ? where cid = ?");
					for(int i = 0;i < cIdFlags.length;i++){
						if(cNames[i].length()>0){
							ChoicePstmt.setString(1, cNames[i]);
							ChoicePstmt.setInt(2, Integer.parseInt(cIdFlags[i]));
							ChoicePstmt.addBatch();
						}
					}
				}
				int ans1[] = TempPstmt.executeBatch();
				int ans2[] = ItemPstmt.executeBatch();
				//int ans3[] = ChoicePstmt.executeBatch();
					response.sendRedirect("user.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
		}
	}

}

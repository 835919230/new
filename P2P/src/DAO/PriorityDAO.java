package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.Priority;
import util.DbManager;

public class PriorityDAO {
private static Object MyLock = new Object();
	
	public static Integer queryMaxId() throws Exception{
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Integer maxId = 0;
		synchronized (DbManager.Lock) {
			try{
				String sql = "select max(id) from priority";
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()){
					maxId = rs.getInt(1);
				}
				return maxId;
			}finally{
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
				if(conn!=null) conn.close();
			}
		}
	}
	
	public static Boolean queryIsShared(Integer pId) throws Exception{
		Boolean bool = false;
		synchronized (MyLock) {
			String sql = "select count(id) from priority where pid = "+pId;
			if(DbManager.getCount(sql)>0)
				bool = true;
			return bool;
		}
	}
	
	public static int insert(Integer pId) throws Exception{
		Integer Id = 0;
		synchronized (MyLock) {
			Id = queryMaxId()+1;
			String sql = "insert into priority(id,pid) values(?,?)";
			return DbManager.excuteUpdate(sql, Id, pId);
		}
	}
	
	public static int deleteBypId(Integer pId) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from priority where pid = ?";
			return DbManager.excuteUpdate(sql, pId);
		}
	}
}

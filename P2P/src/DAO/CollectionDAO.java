package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DbManager;
import entity.Collection;
import entity.Templet;
import entity.User;

public class CollectionDAO{
	
	private static Object MyLock = new Object();
	
	public static int insert(Collection collection) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (MyLock) {
			String sql  = "insert into collection values(?,?,?)";
			String sql1 = "select max(coid) from collection";
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql1);
				rs = pstmt.executeQuery();
				if(rs.next()){
					collection.setCoId(rs.getInt(1)+1);
				}
				return DbManager.excuteUpdate(sql, collection.getCoId(),collection.getUserId(),collection.getTmpId());
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
		}
	}
	/**
	 * 通过user的id和templet的id来确认该问卷是否被收藏
	 * @param user
	 * @param temp
	 * @return
	 * @throws Exception
	 */
	public static Collection findBy2(User user,Templet temp) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection collection = null;
		synchronized (DbManager.Lock) {
			String sql = "select coid,userID,tmpID from collection where userID = ? and tmpID = ?";
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user.getUid());
				pstmt.setInt(2, temp.gettId());
				rs = pstmt.executeQuery();
				if(rs.next()){
					collection = new Collection(rs.getInt("coid"),rs.getInt("userID"),rs.getInt("tmpID"));
				}
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return collection;
		}
	}
	
	public static List<Collection> listByUser(User user,int pageNum,final int pageSize) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Collection> list = null;
		synchronized (DbManager.Lock) {
			String sql = "select coid,userID,tmpID from collection where userID = ? order by coid asc limit ?,?";
			try{
				list = new ArrayList<Collection>();
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user.getUid());
				pstmt.setInt(2, (pageNum-1)*pageSize);
				pstmt.setInt(3, pageSize);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Collection collection = new Collection(rs.getInt("coid"),rs.getInt("tmpID"),rs.getInt("userID"));
					list.add(collection);
				}
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
}

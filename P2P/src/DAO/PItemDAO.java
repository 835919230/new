package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DbManager;
import entity.Item;
import entity.PItem;
import entity.Publish;
import entity.User;

public class PItemDAO {
	
	private static Object MyLock = new Object();
	
	public static Integer quesyMaxId() throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		synchronized (DbManager.Lock) {
			Integer maxId = null;
			StringBuffer sb = new StringBuffer();
			sb.append("select max(id) from pitem");
			conn = DbManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next())
				maxId = rs.getInt(1);
			
			return maxId;
		}
	}
	
	public static int insertManyPItems(List<Item> items,Publish publish,User user) throws Exception{
		synchronized (MyLock) {
			Integer maxId = quesyMaxId();
			StringBuffer sb = new StringBuffer("insert into pitem (id,name,type,pid,uid) values");
			for(int i = 0;i < items.size();i++){
					sb.append("("+(++maxId)+",'"+
										items.get(i).getqName()+
										"','"+items.get(i).getqType()+"',"+
										publish.getpId()+","+
										user.getUid()+
										"),");
			}
			int length = sb.length();
			sb.delete(length-1, length);
			System.out.println(sb.toString());
			return DbManager.excuteUpdate(sb.toString());
		}
	}
	
	public static List<PItem> listByPublish(Publish publish) throws Exception{
		List<PItem> list = null;
		String sql = "select id,name,type,pid,uid from pitem where pid = ?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				list = new ArrayList<PItem>();
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, publish.getpId());
				rs = pstmt.executeQuery();
				while(rs.next()){
					PItem pitem = new PItem(rs.getInt("id"),
											rs.getString("name"),
											rs.getString("type"),
											rs.getInt("pid"),
											rs.getInt("uid"));
					list.add(pitem);
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
	
	public static PItem findById(Integer Id) throws Exception{
		PItem pitem = null;
		String sql = "select id,name,type,pid,uid from pitem where id = ?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Id);
				rs = pstmt.executeQuery();
				if(rs.next()){
					pitem = new PItem(rs.getInt("id"),
							rs.getString("name"),
							rs.getString("type"),
							rs.getInt("pid"),
							rs.getInt("uid"));
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return pitem;
		}
	}
	
	public static int deleteByPid(Integer pId) throws Exception{
		StringBuffer sb = new StringBuffer();
		synchronized (MyLock) {
			sb.append("delete from pitem where pid = ?");
			return DbManager.excuteUpdate(sb.toString(), pId);
		}
	}
	
}

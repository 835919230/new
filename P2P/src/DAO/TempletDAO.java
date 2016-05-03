package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DbManager;
import entity.Templet;
import entity.User;

public class TempletDAO {
	
	private static Object MyLock = new Object();
	
	public static Integer queryMaxId() throws Exception{
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Integer maxId = 0;
		synchronized (DbManager.Lock) {
			try{
				String sql = "select max(tid) from templet";
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
	
	public static int insert(Templet temp) throws Exception{
		synchronized (MyLock) {
			String sql  = "insert into templet values (?,?,?,?,?,?,?,?)";
			String sql1 = "select max(tid) from templet";
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			if(rs.next()){
				temp.settId(rs.getInt(1)+1);
			}
			return DbManager.excuteUpdate(sql,temp.gettId(),temp.gettName(),temp.getIsShared(),temp.getdTime(),temp.getpTime(),temp.getMotal(),temp.getRefTimes(),temp.getuId());
		}
	}
	
	public static int saveName(Templet temp) throws Exception{
		synchronized (MyLock) {
			String sql = "update templet set tname = ? where tid = ?";
			return DbManager.excuteUpdate(sql, temp.gettName(),temp.gettId());
		}
	}
	
	public static int saveShared(Templet temp) throws Exception{
		synchronized (MyLock) {
			String sql = "update templet set isshared = ? where tid = ?";
			return DbManager.excuteUpdate(sql, temp.getIsShared(),temp.gettId());
		}
	}
	public static int savepTime(Templet temp) throws Exception{
		synchronized (MyLock) {
			String sql = "update templet set ptime = ? where tid = ?";
			return DbManager.excuteUpdate(sql, temp.getpTime(),temp.gettId());
		}
	}
	public static int saveMotal(Templet temp) throws Exception{
		synchronized (MyLock) {
			String sql = "update templet set motal = ? where tid = ?";
			return DbManager.excuteUpdate(sql, temp.getMotal(),temp.gettId());
		}
	}
	public static int saverefTimes(Templet temp) throws Exception{
		synchronized (MyLock) {
			String sql = "update templet set reftimes = ? where tid = ?";
			return DbManager.excuteUpdate(sql, temp.getRefTimes()+1,temp.gettId());
		}
	}
	
	public static int deleteById(Integer tId) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from templet where tid = ?";
			return DbManager.excuteUpdate(sql, tId);
		}
	}
	public static int deleteByUser(User user) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from templet where uid = ?";
			return DbManager.excuteUpdate(sql, user.getUid());
		}
	}
	
	public static Templet findById(Integer Id) throws Exception{
		String sql = "select * from templet where tid = ?";
		Templet temp = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Id);
				rs = pstmt.executeQuery();
				if(rs.next()){
					temp = new Templet(rs.getInt("tid"),rs.getString("tname"),
							rs.getBoolean("isshared"),rs.getDate("dtime"),rs.getDate("ptime"),
								rs.getString("motal"),rs.getInt("refTimes"),rs.getInt("uid"));
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return temp;
		}
	}
	
	public static List<Templet> listByUser(User user,Integer pageNum,final Integer pageSize) throws Exception{
		List<Templet> list = new ArrayList<Templet>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				String sql = "select tid,tname,isshared,dtime,ptime,motal,refTimes,uid from templet where uid = ? order by tid asc limit ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user.getUid());
				pstmt.setInt(2, (pageNum-1)*pageSize);
				pstmt.setInt(3, pageSize);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Templet temp = new Templet(rs.getInt("tid"),rs.getString("tname"),rs.getBoolean("isshared"),rs.getDate("dtime"),rs.getDate("ptime"),rs.getString("motal"),rs.getInt("reftimes"),rs.getInt("uid"));
					list.add(temp);
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
	
	public static List<Templet> listSharedTemp(Integer pageNum,final Integer pageSize) throws Exception{
		List<Templet> list = new ArrayList<Templet>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				String sql = "select tid,tname,isshared,dtime,ptime,motal,refTimes,uid from templet where isshared = 1 order by tid asc limit ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, (pageNum-1)*10);
				pstmt.setInt(2, pageSize);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Templet temp = new Templet(rs.getInt("tid"),rs.getString("tname"),rs.getBoolean("isshared"),rs.getDate("dtime"),rs.getDate("ptime"),rs.getString("motal"),rs.getInt("reftimes"),rs.getInt("uid"));
					list.add(temp);
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
	
	public static List<Templet> listByKeywords(String keywords) throws Exception{
		List<Templet> list = new ArrayList<Templet>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				String sql = "select tid,tname,isshared,dtime,ptime,motal,refTimes,uid from templet where isshared = 1 and tname like ? order by tid asc";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keywords+"%");
				rs = pstmt.executeQuery();
				while(rs.next()){
					Templet temp = new Templet(rs.getInt("tid"),rs.getString("tname"),rs.getBoolean("isshared"),rs.getDate("dtime"),rs.getDate("ptime"),rs.getString("motal"),rs.getInt("reftimes"),rs.getInt("uid"));
					list.add(temp);
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
}

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.User;
import util.DbManager;

public class UserDAO {
	
	private static Object MyLock = new Object();
	
	/**
	 * 插入一条记录
	 */
	public static int insert(User user) throws Exception{
		synchronized (MyLock) {
			String sql = "insert into user values (?,?,?,?)";
			String sql2 = "select max(uid) from user";
			Connection conn = DbManager.getConnection();
			Statement stmt= conn.createStatement();
			ResultSet rs= stmt.executeQuery(sql2);
			while(rs.next()){
				user.setUid(rs.getInt(1)+1);
			}
			return DbManager.excuteUpdate(sql,user.getUid(),user.getAccount(),user.getUsername(),DbManager.MD5(user.getPassword()));
		}
	}
	
	
	/**
	 * 保存用户名的密码
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static int save(User user) throws Exception{
		synchronized (MyLock) {
			String sql = "update user set username = ?,password = ? where account =?";
			return DbManager.excuteUpdate(sql, user.getUsername(),DbManager.MD5(user.getPassword()),user.getAccount());
		}
	}
	
	public static int saveUserName(User user) throws Exception{
		synchronized (MyLock) {
			String sql = "update user set username = ? where account = ?";
			return DbManager.excuteUpdate(sql, user.getUsername(),user.getAccount());
		}
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static int delete(Integer id) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from user where id = ?";
			return DbManager.excuteUpdate(sql,id);
		}
	}
	/**
	 * 查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static User findById(Integer id) throws Exception{
		String sql = "select * from user where uid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				if(rs.next()){
					User user = new User(rs.getInt("uid"),rs.getString("account"),rs.getString("username"),rs.getString("password"));
					return user;
				}else{
					return null;
				}
				
			}finally{
				if(rs!=null)rs.close();
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
			}
		}
	}
	/**
	 * 通过账户查找信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public static User findByAccount(String account) throws Exception{
		String sql = "select * from user where account = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, account);
				rs = pstmt.executeQuery();
				if(rs.next()){
					User user = new User(rs.getInt("uid"),rs.getString("account"),rs.getString("username"),rs.getString("password"));
					return user;
				}else{
					return null;
				}
			}finally{
				if(rs!=null)rs.close();
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
			}
		}
	}
	
	/**
	 * 得到User列表
	 * @return
	 * @throws Exception
	 */
	public static List<User> listUser() throws Exception{
		List<User> list = new ArrayList<User>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select * from user order by uid desc");
				while(rs.next()){
					User user = new User(rs.getInt("uid"),rs.getString("account"),rs.getString("username"),rs.getString("password"));
					list.add(user);
				}
			}finally{
				if(rs!=null)rs.close();
				if(conn!=null)conn.close();
				if(stmt!=null)stmt.close();
			}
			return list;
		}
	}
	
}

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DbManager;
import entity.PChoice;
import entity.PItem;
import entity.Publish;
import entity.Templet;
import entity.User;

public class PublishDAO {
	
	private static Object MyLock = new Object();
	private static Object MyLock2 = new Object();
	
	public static int insert(Publish publish) throws Exception{
		synchronized (MyLock2) {
			Integer maxId = quesyMaxId();
			publish.setpId(maxId+1);
			StringBuffer sb = new StringBuffer();
			sb.append("insert into publish(pid,pname,ptime,ltime,isEnd,uid,tid) values(?,?,?,?,?,?,?)");
			return DbManager.excuteUpdate(sb.toString(), publish.getpId(),
					publish.getpName(),
					publish.getpTime(),
					publish.getlTime(),
					publish.getIsEnd(),
					publish.getuId(),
					publish.gettId());
		}
	}
	
	public static Integer quesyMaxId() throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		synchronized (DbManager.Lock) {
			Integer maxId = null;
			StringBuffer sb = new StringBuffer();
			sb.append("select max(pid) from publish");
			conn = DbManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next())
				maxId = rs.getInt(1);
			
			return maxId;
		}
	}
	
	/**
	 * 列出该用户所有的问卷
	 * @param user对象 
	 * @return 问卷List
	 * @throws Exception
	 */
	public static List<Publish> listByUser(User user,Integer pageNum,final Integer pageSize) throws Exception{
		List<Publish> list = new ArrayList<Publish>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "select pid,pname,ptime,ltime,uid,tid,isEnd from publish where uid = ? limit ?,?";
		synchronized (MyLock) {
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user.getUid());
				pstmt.setInt(2, (pageNum-1)*pageSize);
				pstmt.setInt(3, pageSize);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Publish publish = new Publish(rs.getInt("pid"),
												rs.getString("pname"),
												rs.getDate("ptime"),
												rs.getDate("ltime"),
												rs.getInt("uid"),
												rs.getInt("tid"));
					publish.setIsEnd(rs.getBoolean("isEnd"));
					list.add(publish);
				}
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
	
	public static List<Publish> listByKeywords(String keywords) throws Exception{
		List<Publish> list = new ArrayList<Publish>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "select pid,pname,ptime,ltime,uid,tid,isEnd from publish where pname like ?";
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keywords+"%");
				rs = pstmt.executeQuery();
				while(rs.next()){
					Publish publish = new Publish(rs.getInt("pid"),
												rs.getString("pname"),
												rs.getDate("ptime"),
												rs.getDate("ltime"),
												rs.getInt("uid"),
												rs.getInt("tid"));
					publish.setIsEnd(rs.getBoolean("isEnd"));
					list.add(publish);
				}
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
	
	/**
	 * 分页列出问卷
	 * @return
	 * @throws Exception
	 */
	public static List<Publish> listPublish(int pageNum,int pageSize) throws Exception{
		List<Publish> list = new ArrayList<Publish>();
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		synchronized (MyLock) {
			String sql = "select pid,pname,ptime,ltime,uid,tid,isEnd from publish order by tid asc limit ?,?";
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, (pageNum-1)*pageSize);
				pstmt.setInt(2, pageSize);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Publish publish = new Publish(rs.getInt("pid"),
												rs.getString("pname"),
												rs.getDate("ptime"),
												rs.getDate("ltime"),
												rs.getInt("uid"),
												rs.getInt("tid"));
					publish.setIsEnd(rs.getBoolean("isEnd"));
					list.add(publish);
				}
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
	/**
	 * 通过问卷找到设计的模板
	 * @param tId 模板id
	 * @return 找到的模板
	 * @throws Exception
	 */
	public static Templet findTempById(Integer tId) throws Exception{
		Templet temp = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		synchronized (MyLock) {
			String sql = "select tid,tname,isshared,dtime,ptime,motal,refTimes,uid from templet where tid = ?";
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, tId);
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
	
	/**
	 * 结束一个问卷用到的方法
	 * @param pId 问卷id
	 * @return 影响的记录数
	 * @throws Exception
	 */
	public static int setEnd(Integer pId) throws Exception{
		StringBuffer sb = new StringBuffer();
		synchronized (MyLock) {
			sb.append("update publish set isEnd = ? where pid = ?");
			return DbManager.excuteUpdate(sb.toString(), 1, pId);
		}
	}
	/**
	 * 通过问卷id删除对应的唯一问卷
	 * @param pId
	 * @return
	 * @throws Exception
	 */
	public static int deletePublish(Integer pId) throws Exception{
		StringBuffer sb = new StringBuffer();
		synchronized (MyLock) {
			sb.append("delete from publish where pid = ?");
			return DbManager.excuteUpdate(sb.toString(), pId);
		}
	}
	/**
	 * 通过问卷id找到对应的问卷
	 * @DbManager.Lock是操作数据库的唯一锁
	 * @param pId
	 * @return
	 * @throws Exception
	 */
	public static Publish findById(Integer pId) throws Exception{
		Publish publish = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		synchronized (DbManager.Lock) {
			try{
				sb.append("select pid,pname,ptime,ltime,uid,tid,isEnd from publish where pid = ?");
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, pId);
				rs = pstmt.executeQuery();
				if(rs.next()){
					publish = new Publish(rs.getInt("pid"),
									      rs.getString("pname"),
									      rs.getDate("ptime"),
									      rs.getDate("ltime"),
									      rs.getInt("uid"),
									      rs.getInt("tid"));
					publish.setIsEnd(rs.getBoolean("isEnd"));
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return publish;
		}
	}
	/**
	 * @DbManager.Lock是操作数据库的唯一锁
	 * @param pId
	 * @return
	 * @throws Exception
	 */
	public static Boolean queryIsEnd(Integer pId) throws Exception{
		StringBuffer sb = new StringBuffer();
		Boolean bool = new Boolean(false);
		Integer integer = new Integer(1);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		synchronized (DbManager.Lock) {
			try{
				sb.append("select pid,pname,ptime,ltime,isEnd,uid,tid from publish where pid = ? and isEnd = ?");
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sb.toString());
				DbManager.setParams(pstmt, pId, integer);
				rs = pstmt.executeQuery();
				if(rs.next()) bool = true;
				
				return bool;
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
		}
	}
	
	public static int UpdateRefTimes(Integer tId) throws Exception{
		synchronized (MyLock) {
			StringBuffer sb = new StringBuffer();
			sb.append("update templet set refTimes = refTimes + 1 where tid = ?");
			return DbManager.excuteUpdate(sb.toString(), tId);
		}
	}
	
}

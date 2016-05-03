package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DbManager;
import entity.Paticipate;
import entity.Publish;

public class PaticipateDAO {
	
	private static Object MyLock = new Object();
	
	public static int insert(Paticipate paticipate) throws Exception{
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Integer prid = null;
		
		synchronized (MyLock) {
			conn = DbManager.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select max(prid) from participate");
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()) prid = rs.getInt(1)+1;
			sb.delete(0, sb.length());
			sb.append("insert into participate(prid,pid,uid) values (?,?,?)");
			return DbManager.excuteUpdate(sb.toString(), prid, paticipate.getpId(),paticipate.getuId());
		}
	}
	
	public static List<Publish> listPaticipates(Integer uId,int pageNum,int pageSize) throws Exception{
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		List<Publish> list = new ArrayList<Publish>();
		synchronized (MyLock) {
			try{
				synchronized (DbManager.Lock) {
					conn = DbManager.getConnection();
					sb.append("select pid from participate where uid = ? order by pid asc limit ?,?");
					pstmt = conn.prepareStatement(sb.toString());
					pstmt.setInt(1, uId);
					pstmt.setInt(2, (pageNum-1)*pageSize);
					pstmt.setInt(3, pageSize);
					rs = pstmt.executeQuery();
					sb.delete(0, sb.length());
				}
				while(rs.next()){
					Publish publish = PublishDAO.findById(rs.getInt(1));
					list.add(publish);
				}
				return list;
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
		}
	}
	
	public static Boolean queryParticipate(Integer uid,Integer pId) throws Exception{
		Boolean bool = true;
		synchronized (MyLock) {
			StringBuffer sb = new StringBuffer();
			sb.append("select count(prid) from participate where uid = ").append(uid).append(" and pid = ").append(pId);
			System.out.println(sb.toString());
			if(DbManager.getCount(sb.toString())<=0)
				bool = false;
		}
		return bool;
	}
	
}

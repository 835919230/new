package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DbManager;
import entity.Choice;
import entity.Item;
import entity.Templet;

public class ChoiceDAO {
	
	private static Object MyLock = new Object();
	private static Object MyLock2 = new Object();
	
	public static int insert(Choice choice) throws Exception{
		Integer cId = 0;
		synchronized (MyLock) {
			cId = queryMaxId()+1;
			choice.setcId(cId);
			String sql = "insert into choice values(?,?,?,?,?)";
			return DbManager.excuteUpdate(sql,cId,choice.getcName(),choice.getqId(),choice.gettId(),choice.getuId());
		}
	}
	
	public static int insertManyByItem(Item item) throws Exception{
		StringBuffer sb = new StringBuffer();
		synchronized (MyLock) {
			List<Choice> choices = listByItem(item);
			Integer maxcId = queryMaxId();
			sb.append("insert into choice(cid,cname,qid,tid,uid) values");
			for(int i = 0;i < choices.size();i++){
				sb.append("(").append((++maxcId)).append(",'").
				                      append(choices.get(i).getcName()).append("',").
				                      append(item.getqId()+1).append(",").
				                      append(item.gettId()+1).append(",").
				                      append(item.getuId()+1).append("),");
			}
			sb.delete(sb.length()-1, sb.length());
			System.out.println(sb.toString());
			return DbManager.excuteUpdate(sb.toString());
		}
	}
	
	public static int insertManyByItem(Item item,Integer maxqId) throws Exception{
		StringBuffer sb = new StringBuffer();
		synchronized (MyLock) {
			List<Choice> choices = listByItem(item);
			Integer maxcId = queryMaxId();
			sb.append("insert into choice(cid,cname,qid,tid,uid) values");
			for(int i = 0;i < choices.size();i++){
				sb.append("(").append((++maxcId)).append(",'").
				                      append(choices.get(i).getcName()).append("',").
				                      append(maxqId).append(",").
				                      append(item.gettId()).append(",").
				                      append(item.getuId()).append("),");
			}
			sb.delete(sb.length()-1, sb.length());
			System.out.println(sb.toString());
			return DbManager.excuteUpdate(sb.toString());
		}
	}
	
	
	public static int saveCName(Integer choiceId,String cName) throws Exception{
		synchronized (MyLock) {
			String sql = "update choice set cname = ? where cid = ?";
			return DbManager.excuteUpdate(sql, cName, choiceId);
		}
	}
	
	public static int deleteByItem(Integer itemId) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from choice where qid = ?";
			return DbManager.excuteUpdate(sql, itemId);
		}
	}
	
	public static int deleteById(Integer Id) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from choice where cid = ?";
			return DbManager.excuteUpdate(sql, Id);
		}
	}
	public static int deleteByTemplet(Templet temp) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from choice where tid = ?";
			return DbManager.excuteUpdate(sql, temp.gettId());
		}
	}
	
	public static Choice findById(Integer Id) throws Exception{
		synchronized (DbManager.Lock) {
			String sql = "select cid,cname,qid,tid,uid from choice where cid = ?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Choice choice = null;
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Id);
				rs = pstmt.executeQuery();
				if(rs.next()){
					choice = new Choice(rs.getInt("cid"),rs.getString("cname"),rs.getInt("qid"),rs.getInt("tid"),rs.getInt("uid"));
				}
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return choice;
		}
	}
	
	public static List<Choice> listByItem(Item item) throws Exception{
		String sql = "select cid,cname,qid,tid,uid from choice where qid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Choice> choices = new ArrayList<Choice>();
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, item.getqId());
				rs = pstmt.executeQuery();
				while(rs.next()){
					Choice choice = new Choice(rs.getInt("cid"),rs.getString("cname"),rs.getInt("qid"),rs.getInt("tid"),rs.getInt("uid"));
					choices.add(choice);
				}
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return choices;
		}
	}
	
	public static List<Choice> listByItem(Integer qId) throws Exception{
		String sql = "select cid,cname,qid,tid,uid from choice where qid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Choice> choices = new ArrayList<Choice>();
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, qId);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Choice choice = new Choice(rs.getInt("cid"),rs.getString("cname"),rs.getInt("qid"),rs.getInt("tid"),rs.getInt("uid"));
					choices.add(choice);
				}
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return choices;
		}
	}
//	
//	public static Integer selectMinCid(Integer qid) throws Exception{
//		Integer minCid = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		StringBuffer sb = new StringBuffer();
//		synchronized (DbManager.Lock) {
//			sb.append("select min(cid) from choice where qid = ?");
//			try{
//				conn = DbManager.getConnection();
//				
//				pstmt = conn.prepareStatement(sb.toString());
//				pstmt.setInt(1, qid);
//				rs = pstmt.executeQuery();
//				if(rs.next()) minCid = rs.getInt(1);
//			}finally{
//				if(conn!=null) conn.close();
//				if(pstmt!=null) pstmt.close();
//				if(rs!=null) rs.close();
//			}
//		}
//		return minCid;
//	}
//	
//	public static Integer selectMaxCid(Integer qid) throws Exception{
//		Integer maxCid = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		StringBuffer sb = new StringBuffer();
//		synchronized (DbManager.Lock) {
//			sb.append("select max(cid) from choice where qid = ?");
//			try{
//				conn = DbManager.getConnection();
//				
//				pstmt = conn.prepareStatement(sb.toString());
//				pstmt.setInt(1, qid);
//				rs = pstmt.executeQuery();
//				if(rs.next()) maxCid = rs.getInt(1);
//			}finally{
//				if(conn!=null) conn.close();
//				if(pstmt!=null) pstmt.close();
//				if(rs!=null) rs.close();
//			}
//		}
//		return maxCid;
//	}
//	
//	public static int updateQidByMinAndMax(Integer min,Integer max,Integer qid ,Integer target) throws Exception{
//		synchronized (MyLock) {
//			StringBuffer sb = new StringBuffer();
//			sb.append("update choice set qid = ? where cid > ? and cid < ? and qid = ?");
//			return DbManager.excuteUpdate(sb.toString(), qid, (min-1), (max+1), target);
//		}
//	}
	
	public static int updateManyChoices(List<Choice> choices) throws Exception{
		StringBuffer before = new StringBuffer("update choice set qid = case cid");
		StringBuffer after = new StringBuffer("end where cid in(");
		synchronized (MyLock) {
			for(int i = 0;i < choices.size();i++){
				before.append(" when ").append(choices.get(i).getcId()).append(" then ").append(choices.get(i).getqId());
				after.append(choices.get(i).getcId()).append(",");
			}
			after.delete(after.length()-1, after.length()).append(")");
			before.append(" ").append(after);
			
			System.out.println(before);
			
			return DbManager.executeUpdate(before.toString());
		}
	}
	
	
	public static int updateQid(Integer qid1,Integer qid2) throws Exception{
//		Integer mincid1 = 0;
//		Integer mincid2 = 0;
//		Integer maxcid1 = 0;
//		Integer maxcid2 = 0;
		synchronized (MyLock2) {
			List<Choice> choices1 = listByItem(qid1);
			List<Choice> choices2 = listByItem(qid2);
			for(int i = 0;i < choices1.size();i++){
				choices1.get(i).setqId(qid2);
			}
			for(int j = 0;j < choices2.size();j++){
				choices2.get(j).setqId(qid1);
			}
			return updateManyChoices(choices1)+updateManyChoices(choices2);
//			mincid1 = selectMinCid(qid1);
//			mincid2 = selectMinCid(qid2);
//			
//			maxcid1 = selectMaxCid(qid1);
//			maxcid2 = selectMaxCid(qid2);
//			
//			return updateQidByMinAndMax(mincid1, maxcid1, qid2, qid1)+updateQidByMinAndMax(mincid2, maxcid2, qid1, qid2);
		}
	}
	
	public static Integer queryMaxId() throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		String sql = "select max(cid) from choice";
		Integer newChoiceId = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()){
					newChoiceId = rs.getInt(1);
				}
				return newChoiceId;
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
		}
	}
	
	public static int copyChoice(Integer choiceId) throws Exception{
		synchronized (MyLock) {
			Integer maxId = queryMaxId()+1;
			String sql = "insert into choice (cname,qid,tid,uid) select cname,qid,tid,uid from choice where cid = ?";
			DbManager.excuteUpdate(sql, choiceId);
			Integer maxId1 = queryMaxId();
			String sql1 = "update choice set cid = ? where cid = ?";
			return DbManager.excuteUpdate(sql1, maxId, maxId1);
		}
	}
	
}

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
import entity.User;

public class ItemDAO {
	private static Object MyLock = new Object();
	private static Object MyLock2 = new Object();
	/**
	 * 插入一条记录
	 * @param item
	 * @return
	 * @throws Exception
	 */
	
	public static Integer queryMaxId() throws Exception{
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Integer maxId = 0;
		synchronized (DbManager.Lock) {
			try{
				String sql = "select max(qid) from item";
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
	
	public static int insert(Item item) throws Exception{
		synchronized (MyLock) {
			String sql = "insert into item values (?,?,?,?,?)";
			Integer qId = queryMaxId()+1;
			item.setqId(qId);
			return DbManager.excuteUpdate(sql,qId,item.getqName(),item.getqType(),item.gettId(),item.getuId());
		}
	}
	
	public static int insertWithoutId(Item item) throws Exception{
		synchronized (MyLock) {
			String sql = "insert into item values (?,?,?,?,?)";
			return DbManager.excuteUpdate(sql,item.getqId(),item.getqName(),item.getqType(),item.gettId(),item.getuId());
		}
	}
	
	/**
	 * 保存问题名和问题类型
	 * @param item
	 * @return 成功1，不成功可能抛出异常
	 * @throws Exception
	 */
	public static int saveItemName(Integer itemId,String qName) throws Exception{
		synchronized (MyLock) {
			String sql = "update item set qname = ? where qid = ?";
			return DbManager.excuteUpdate(sql, qName, itemId);
		}
	}
	/**
	 * 通过id来删除item
	 * @param qId
	 * @return
	 * @throws Exception
	 */
	public static int deleteById(Integer qId) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from item where qid = ?";
			return DbManager.excuteUpdate(sql, qId);
		}
	}
	public static int deleteByUser(User user) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from item where uid = ?";
			return DbManager.excuteUpdate(sql, user.getUid());
		}
	}
	public static int deleteByTemplet(Templet temp) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from item where tid = ?";
			return DbManager.excuteUpdate(sql, temp.gettId());
		}
	}
	public static int deleteBy2(Templet temp,User user) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from item where tid = ? and uid = ?";
			return DbManager.excuteUpdate(sql, temp.gettId(),temp.getuId());
		}
	}
	
	public static int delete2 (Integer qid1,Integer qid2) throws Exception{
		synchronized (MyLock) {
			String sql = "delete from item where qid = ? or qid = ?";
			return DbManager.excuteUpdate(sql, (qid1),(qid2));
		}
	}
	/**
	 * 模板上移和下移用到的方法
	 * @param itemId
	 * @param brotherId
	 * @return
	 * @throws Exception
	 */
	public static int exchangeId(Integer itemId,Integer brotherId) throws Exception{
		
		synchronized (MyLock2) {
			Item thisItem = findById(itemId);
			Item brotherItem = findById(brotherId);
			
			ChoiceDAO.updateQid(itemId, brotherId);
			
			delete2(brotherId,itemId);
			
			thisItem.setqId(brotherId);
			brotherItem.setqId(itemId);
			
			return (insertWithoutId(thisItem)+insertWithoutId(brotherItem));
		}
		
	}
	/**
	 * 模板设计功能里的复制功能实现方法之一
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public static Integer copyItem(Integer itemId) throws Exception{
		synchronized (MyLock2) {
			Item item = findById(itemId);
			Integer maxId =  queryMaxId()+1;
			
			ChoiceDAO.insertManyByItem(item,maxId);
			
			item.setqId(maxId);
			
			insertWithoutId(item);
			return maxId;
		}
	}
	
	public static Item findById(Integer qId) throws Exception{
		synchronized (DbManager.Lock) {
			String sql = "select * from item where qid = ?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, qId);
				rs = pstmt.executeQuery();
				if(rs.next()){
					Item item = new Item(rs.getInt("qid"),rs.getString("qname"),rs.getString("qtype"),rs.getInt("tid"),rs.getInt("uid"));
					return item;
				}else{
					return null;
				}
			}finally{
				if(rs!=null) rs.close();
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
			}
		}
	}
	/**
	 * 列出当前模板下的所有问题
	 * @param temp
	 * @return
	 * @throws Exception
	 */
	public static List<Item> listByTemplet(Templet temp) throws Exception{
		List<Item> list = new ArrayList<Item>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				String sql = "select * from item where tid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, temp.gettId());
				rs = pstmt.executeQuery();
				while(rs.next()){
					Item item = new Item(rs.getInt("qid"),rs.getString("qname"),rs.getString("qtype"),rs.getInt("tid"),rs.getInt("uid"));
					list.add(item);
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
	/**
	 * 
	 * @param temp
	 * @return 问卷的问题数量
	 * @throws Exception
	 */
	public static int getCount(Templet temp) throws Exception{
		synchronized (DbManager.Lock) {
			String sql = "select count(qid) from item where tid = ?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, temp.gettId());
				rs = pstmt.executeQuery();
				if(rs.next())
					return rs.getInt(1);
				else
					return 0;
			}finally{
				if(conn!=null) conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
		}
	}
}

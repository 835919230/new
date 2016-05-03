package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DbManager;
import entity.Choice;
import entity.Item;
import entity.PChoice;
import entity.PItem;
import entity.Publish;
import entity.User;

public class PChoiceDAO {
	
	private static Object MyLock = new Object();
	
	public static Integer quesyMaxId() throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		synchronized (DbManager.Lock) {
			Integer maxId = null;
			StringBuffer sb = new StringBuffer();
			sb.append("select max(pcid) from pchoice");
			conn = DbManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next())
				maxId = rs.getInt(1);
			
			return maxId;
		}
	}
	
	public static int insertManyPChoices(Publish publish,List<PItem> pitems,List<Item> items,User user)throws Exception
	{
		synchronized (MyLock) {
			List<Choice> choices = new ArrayList<Choice>();
			StringBuffer sb = new StringBuffer("insert into pchoice (pcid,pcname,numbers,id,pid,uid) values");
			int maxId = quesyMaxId();
			int length = items.size();
			for(int i = 0;i < length;i++){
				choices = ChoiceDAO.listByItem(items.get(i));
				int size = choices.size();
				for(int j = 0;j < size;j++){
						sb.append("("+(++maxId)+",'"
										+choices.get(j).getcName()+"',"+
										0+","+pitems.get(i).getId()+","+
										publish.getpId()+","+
										user.getUid()+"),");
				}
			}
			int strLength = sb.length();
			sb.delete(strLength-1, strLength);
			System.out.println(sb.toString());
			return DbManager.executeUpdate(sb.toString());
		}
	}
	
	public static String insertManyPChoices2(Publish publish,List<PItem> pitems,List<Item> items,User user)throws Exception
	{
		synchronized (MyLock) {
			List<Choice> choices = new ArrayList<Choice>();
			StringBuffer sb = new StringBuffer("insert into pitem (pcid,pcname,numbers,id,pid,uid) values");
			int maxId = quesyMaxId();
			for(int i = 0;i < items.size();i++){
				choices = ChoiceDAO.listByItem(items.get(i));
				int size = choices.size();
				for(int j = 0;j < size;j++){
						sb.append("("+(++maxId)+",'"
										+choices.get(j).getcName()+"',"+
										0+","+pitems.get(i).getId()+","+
										publish.getpId()+","+
										user.getUid()+"),");
				}
			}
			sb.delete(sb.length()-1, sb.length());
			return sb.toString();
		}
	}
	
	public static List<PChoice> listByPItem(PItem pitem)throws Exception{
		List<PChoice> list = null;
		String sql = "select pcid,pcname,numbers,id,pid,uid from pchoice where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				list = new ArrayList<PChoice>();
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pitem.getId());
				rs = pstmt.executeQuery();
				while(rs.next()){
					PChoice pchoice = new PChoice(rs.getInt("pcid"),
													rs.getString("pcname"),
													rs.getInt("numbers"),
													rs.getInt("id"),
													rs.getInt("pid"),
													rs.getInt("uid"));
					if(pitem.getType().indexOf("单行填空题") != -1
							|| pitem.getType().indexOf("手机") != -1
							|| pitem.getType().indexOf("邮箱") != -1
							|| pitem.getType().indexOf("多项填空题") != -1){
						if(pchoice.getPcName().equals("")){
							list.add(pchoice);
						}
					}else{
						list.add(pchoice);
					}
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
	
	public static List<PChoice> listByWrite(PItem pitem)throws Exception{
		List<PChoice> list = null;
		String sql = "select pcid,pcname,numbers,id,pid,uid from pchoice_copy where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				list = new ArrayList<PChoice>();
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pitem.getId());
				rs = pstmt.executeQuery();
				while(rs.next()){
					PChoice pchoice = new PChoice(rs.getInt("pcid"),
													rs.getString("pcname"),
													rs.getInt("numbers"),
													rs.getInt("id"),
													rs.getInt("pid"),
													rs.getInt("uid"));
					list.add(pchoice);
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return list;
		}
	}
	
	public static PChoice findById(Integer pcid) throws Exception{
		PChoice pchoice = null;
		String sql = "select pcid,pcname,numbers,id,pid,uid from pchoice where pcid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		synchronized (DbManager.Lock) {
			try{
				conn = DbManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pcid);
				rs = pstmt.executeQuery();
				if(rs.next()){
					pchoice = new PChoice(rs.getInt("pcid"),rs.getString("pcname"),rs.getInt("numbers"),
											rs.getInt("id"),rs.getInt("pid"),rs.getInt("uid"));
				}
			}finally{
				if(conn!=null)conn.close();
				if(pstmt!=null) pstmt.close();
				if(rs!=null) rs.close();
			}
			return pchoice;
		}
	}
	
	public static int deleteByPid(Integer pId) throws Exception{
		StringBuffer sb = new StringBuffer();
		
		synchronized (MyLock) {
			sb.append("delete from pchoice where pid = ?");
			int i = DbManager.excuteUpdate(sb.toString(), pId);
			sb.delete(0, sb.length());
			sb.append("delete from pchoice_copy where pid = ?");
			return i+DbManager.excuteUpdate(sb.toString(), pId);
		}
		
	}
	
}

package util;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import com.mysql.jdbc.Driver;

public class DbManager {
	
	public final static Object Lock = new Object();
	
	/**
	 * 
	 * @return 一个connection对象
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception{
		Connection conn = getConnection("questionWeb","root","root");
		return conn;
	}
	public static Connection getConnection(String dbName,String userName,String password) throws Exception{
		String url = "jdbc:mysql://localhost:3306/"+dbName
				+"?useUnicode=true&characterEncoding=utf-8&useSSL=true";
		DriverManager.registerDriver(new Driver());
		return DriverManager.getConnection(url, userName, password);
	}
	/**
	 * 
	 * @param pstmt PreparedStatement对象
	 * @param params 要设置的参数
	 * @throws Exception 异常
	 */
	public static void setParams(PreparedStatement pstmt,Object...params) throws Exception{
		if(params==null||params.length==0){
			return;
		}
		for(int i = 1;i<=params.length;i++){
			Object param = params[i-1];
			if(param==null){
				pstmt.setNull(i, Types.NULL);
			}else if(param instanceof Integer){
				pstmt.setInt(i, (Integer)param);
			}else if(param instanceof String){
				pstmt.setString(i, (String)param);
			}else if(param instanceof Double){
				pstmt.setDouble(i, (Double)param);
			}else if(param instanceof Long){
				pstmt.setLong(i, (Long)param);
			}else if(param instanceof Timestamp){
				pstmt.setTimestamp(i, (Timestamp)param);
			}else if(param instanceof Boolean){
				pstmt.setBoolean(i, (Boolean)param);
			}else if(param instanceof java.util.Date){
				java.util.Date d = (Date) param;
				java.sql.Date dd = new java.sql.Date(d.getTime());
				pstmt.setDate(i, dd);
			}
		}
	}
	/**
	 * 
	 * @param sql
	 * @return 影响的行数
	 * @throws Exception
	 */
	public static int executeUpdate(String sql) throws Exception{
		return excuteUpdate(sql,new Object[]{});
	}
	
	public static int excuteUpdate(String sql,Object...params)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt = null;
		try{
			synchronized (Lock) {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				setParams(pstmt, params);
				return pstmt.executeUpdate();
			}
		}finally{
			if(conn!=null){
				conn.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
		}
	}
	/**
	 * 
	 * @param sql select count(*) from table
	 * @return 行数
	 * @throws Exception
	 */
	public static int getCount(String sql) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		synchronized (Lock) {
			try{
				conn = getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next())
					result = rs.getInt(1);
			}finally{
				if(conn!=null) conn.close();
				if(stmt!=null) stmt.close();
				if(rs!=null) rs.close();
			}
		}
		return result;
	}
	
	public static String MD5(String instr){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		char[] charArray = instr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		
		for(int i = 0;i<charArray.length;i++){
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		
		for(int i = 0;i<md5Bytes.length;i++){
			int val = ((int)md5Bytes[i]) & 0xff;
			if(val<16){
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}

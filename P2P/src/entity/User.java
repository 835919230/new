package entity;

import java.io.Serializable;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer uid;
	private String account;
	private String username;
	private String password;
	public int getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User(String account, String username) {
		super();
		this.account = account;
		this.username = username;
	}
	public User(Integer uid, String account, String username, String password) {
		super();
		this.uid = uid;
		this.account = account;
		this.username = username;
		this.password = password;
	}
	
	public User(String account, String username, String password) {
		super();
		this.account = account;
		this.username = username;
		this.password = password;
	}
	public User() {
		super();
	}
	
}

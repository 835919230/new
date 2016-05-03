package entity;

import java.util.Date;

/**
 * 发布的模板对象
 * @author 诚
 */
public class Publish {
	private Integer pId;
	private String pName;
	private Date pTime;
	private Date lTime;
	private Integer uId;
	private Integer tId;//表示使用了哪个模板
	private Boolean isEnd;
	
	
	public Boolean getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public Date getpTime() {
		return pTime;
	}
	public void setpTime(Date pTime) {
		this.pTime = pTime;
	}
	public Date getlTime() {
		return lTime;
	}
	public void setlTime(Date lTime) {
		this.lTime = lTime;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	public Integer gettId() {
		return tId;
	}
	public void settId(Integer tId) {
		this.tId = tId;
	}
	
	public Publish(Integer pId, String pName, Date pTime, Date lTime,
			Integer uId, Integer tId) {
		super();
		this.pId = pId;
		this.pName = pName;
		this.pTime = pTime;
		this.lTime = lTime;
		this.uId = uId;
		this.tId = tId;
	}
	public Publish(String pName, Date pTime, Date lTime, Integer uId,
			Integer tId) {
		super();
		this.pName = pName;
		this.pTime = pTime;
		this.lTime = lTime;
		this.uId = uId;
		this.tId = tId;
	}
	public Publish() {
		super();
	}
	
	
}

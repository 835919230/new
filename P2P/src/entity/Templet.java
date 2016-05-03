package entity;

import java.util.Date;

public class Templet {
	private Integer tId;
	private String tName;
	private Boolean isShared;
	private Date dTime;
	private Date pTime;
	private String motal;
	private Integer refTimes;
	private Integer uId;
	
	public Integer gettId() {
		return tId;
	}
	public void settId(Integer tId) {
		this.tId = tId;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public Boolean getIsShared() {
		return isShared;
	}
	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}
	public Date getdTime() {
		return dTime;
	}
	public void setdTime(Date dTime) {
		this.dTime = dTime;
	}
	public Date getpTime() {
		return pTime;
	}
	public void setpTime(Date pTime) {
		this.pTime = pTime;
	}
	public String getMotal() {
		return motal;
	}
	public void setMotal(String motal) {
		this.motal = motal;
	}
	public Integer getRefTimes() {
		return refTimes;
	}
	public void setRefTimes(Integer refTimes) {
		this.refTimes = refTimes;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	/**
	 * Full Constructor
	 * @param tId
	 * @param tName
	 * @param isShared
	 * @param dTime
	 * @param pTime
	 * @param motal
	 * @param refTimes
	 * @param uId
	 */
	public Templet(Integer tId, String tName, Boolean isShared, Date dTime,
			Date pTime, String motal, Integer refTimes, Integer uId) {
		super();
		this.tId = tId;
		this.tName = tName;
		this.isShared = isShared;
		this.dTime = dTime;
		this.pTime = pTime;
		this.motal = motal;
		this.refTimes = refTimes;
		this.uId = uId;
	}
	
	public Templet(String tName, Boolean isShared, Date dTime, Date pTime,
			String motal, Integer refTimes, Integer uId) {
		super();
		this.tName = tName;
		this.isShared = isShared;
		this.dTime = dTime;
		this.pTime = pTime;
		this.motal = motal;
		this.refTimes = refTimes;
		this.uId = uId;
	}
	public Templet(String tName, Boolean isShared, Date dTime, String motal,
			Integer refTimes, Integer uId) {
		super();
		this.tName = tName;
		this.isShared = isShared;
		this.dTime = dTime;
		this.motal = motal;
		this.refTimes = refTimes;
		this.uId = uId;
	}
	public Templet() {
		super();
	}
	public Templet(Integer uId) {
		super();
		this.uId = uId;
	}
	
}

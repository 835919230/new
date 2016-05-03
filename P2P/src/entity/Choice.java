package entity;

public class Choice {
	private Integer cId;
	private String cName;
	private Integer qId;
	private Integer tId;
	private Integer uId;
	public Integer getcId() {
		return cId;
	}
	public void setcId(Integer cId) {
		this.cId = cId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public Integer getqId() {
		return qId;
	}
	public void setqId(Integer qId) {
		this.qId = qId;
	}
	public Integer gettId() {
		return tId;
	}
	public void settId(Integer tId) {
		this.tId = tId;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	
	public Choice(Integer cId, String cName, Integer qId, Integer tId, Integer uId) {
		super();
		this.cId = cId;
		this.cName = cName;
		this.qId = qId;
		this.tId = tId;
		this.uId = uId;
	}
	public Choice(String cName, Integer qId, Integer tId, Integer uId) {
		super();
		this.cName = cName;
		this.qId = qId;
		this.tId = tId;
		this.uId = uId;
	}
	public Choice() {
		super();
	}
	
}

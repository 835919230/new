package entity;

public class Item {
	private Integer qId;
	private String qName;
	private String qType;
	private Integer tId;
	private Integer uId;
	
	public Integer getqId() {
		return qId;
	}
	public void setqId(Integer qId) {
		this.qId = qId;
	}
	public String getqName() {
		return qName;
	}
	public void setqName(String qName) {
		this.qName = qName;
	}
	public String getqType() {
		return qType;
	}
	public void setqType(String qType) {
		this.qType = qType;
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
	public Item(Integer qId, String qName, String qType, Integer tId, Integer uId) {
		super();
		this.qId = qId;
		this.qName = qName;
		this.qType = qType;
		this.tId = tId;
		this.uId = uId;
	}
	public Item(String qName, String qType, Integer tId, Integer uId) {
		super();
		this.qName = qName;
		this.qType = qType;
		this.tId = tId;
		this.uId = uId;
	}
	public Item(String qName, String qType) {
		super();
		this.qName = qName;
		this.qType = qType;
	}
	public Item() {
		super();
	}
	
}

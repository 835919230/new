package entity;

public class Collection {
	private Integer coId;//主键
	private Integer userId;//记录是哪个用户收藏的
	private Integer tmpId;//记录收藏的模板是哪个
	
	public Integer getCoId() {
		return coId;
	}
	public void setCoId(Integer coId) {
		this.coId = coId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTmpId() {
		return tmpId;
	}
	public void setTmpId(Integer tmpId) {
		this.tmpId = tmpId;
	}
	
	public Collection(Integer coId , Integer tmpId ,Integer userId) {
		super();
		this.coId = coId;
		this.userId = userId;
		this.tmpId = tmpId;
	}
	public Collection(Integer tmpId,Integer userId) {
		super();
		this.userId = userId;
		this.tmpId = tmpId;
	}
	public Collection() {
		super();
	}
}

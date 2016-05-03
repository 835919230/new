package entity;

public class Collection {
	private Integer coId;//����
	private Integer userId;//��¼���ĸ��û��ղص�
	private Integer tmpId;//��¼�ղص�ģ�����ĸ�
	
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

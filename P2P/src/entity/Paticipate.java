package entity;

public class Paticipate {
	private Integer prId;//����
	private Integer pId;//��������ʾ�
	private Integer uId;//������û�
	public Integer getPrId() {
		return prId;
	}
	public void setPrId(Integer prId) {
		this.prId = prId;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	public Paticipate(Integer prId, Integer pId, Integer uId) {
		super();
		this.prId = prId;
		this.pId = pId;
		this.uId = uId;
	}
	public Paticipate(Integer pId, Integer uId) {
		super();
		this.pId = pId;
		this.uId = uId;
	}
	public Paticipate() {
		super();
	}
	@Override
	public String toString() {
		return "Paticipate [prId=" + prId + ", pId=" + pId + ", uId=" + uId
				+ "]";
	}
	
}

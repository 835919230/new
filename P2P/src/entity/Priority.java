package entity;
/**
 * ∂‘”¶Table £∫ priority
 * @author ≥œ
 *
 */
public class Priority {
	
	private Integer Id;
	private Integer pId;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	
	public Priority(Integer id, Integer pId) {
		super();
		Id = id;
		this.pId = pId;
	}
	public Priority(Integer pId) {
		super();
		this.pId = pId;
	}
	
}

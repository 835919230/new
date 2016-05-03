package entity;
/**
 * 发布问卷问题选项的对象
 * @author 诚
 *
 */
public class PChoice {
	private Integer pcId;
	private String pcName;
	private Integer numbers;
	private Integer id;
	private Integer pId;
	private Integer uId;
	public Integer getPcId() {
		return pcId;
	}
	public void setPcId(Integer pcId) {
		this.pcId = pcId;
	}
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public Integer getNumbers() {
		return numbers;
	}
	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}
	
	public PChoice(Integer pcId, String pcName, Integer numbers, Integer id,
			Integer pId, Integer uId) {
		super();
		this.pcId = pcId;
		this.pcName = pcName;
		this.numbers = numbers;
		this.id = id;
		this.pId = pId;
		this.uId = uId;
	}
	
	public PChoice(String pcName, Integer numbers, Integer id, Integer pId,
			Integer uId) {
		super();
		this.pcName = pcName;
		this.numbers = numbers;
		this.id = id;
		this.pId = pId;
		this.uId = uId;
	}
	public PChoice() {
		super();
	}
}

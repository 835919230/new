package entity;
/**
 * 记录发布问卷选项的对象
 * @author 诚
 *
 */
public class PItem {
	private Integer Id;
	private String Name;
	private String Type;
	private Integer pId;
	private Integer uId;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
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
	
	/**
	 * 完整的构造方法
	 * @param id
	 * @param name
	 * @param type
	 * @param pId
	 * @param uId
	 */
	public PItem(Integer id, String name, String type, Integer pId, Integer uId) {
		super();
		Id = id;
		Name = name;
		Type = type;
		this.pId = pId;
		this.uId = uId;
	}
	/**
	 * 缺少Id的构造方法
	 * @param name
	 * @param type
	 * @param pId
	 * @param uId
	 */
	public PItem(String name, String type, Integer pId, Integer uId) {
		super();
		Name = name;
		Type = type;
		this.pId = pId;
		this.uId = uId;
	}
	/**
	 * 无参构造方法
	 */
	public PItem() {
		super();
	}
}

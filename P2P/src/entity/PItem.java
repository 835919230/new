package entity;
/**
 * ��¼�����ʾ�ѡ��Ķ���
 * @author ��
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
	 * �����Ĺ��췽��
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
	 * ȱ��Id�Ĺ��췽��
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
	 * �޲ι��췽��
	 */
	public PItem() {
		super();
	}
}

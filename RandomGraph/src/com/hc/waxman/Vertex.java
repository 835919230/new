package com.hc.waxman;

public class Vertex {
	private int id;
	private Vertex prefix;
	private Cordinate cordinate;
	
	@Override
	public int hashCode() {
		final int prime = 1007;
		int result = 1;
		result = result*prime+this.cordinate.getX();
		result = result*prime+this.cordinate.getY();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Vertex))
			return super.equals(obj);
		else{
			Vertex other = (Vertex)obj;
			if(other.getCordinate().getX()==this.getCordinate().getX()&&other.getCordinate().getY()==this.getCordinate().getY()){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public Vertex() {
	}
	
	public Vertex(Cordinate cordinate) {
		this.cordinate = cordinate;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ x:")
		  .append(cordinate.getX())
		  .append(",y:")
		  .append(cordinate.getY())
		  .append(" ]");
		return sb.toString();
	}

	public Cordinate getCordinate() {
		return cordinate;
	}

	public void setCordinate(Cordinate cordinate) {
		this.cordinate = cordinate;
	}

	public Vertex getPrefix() {
		return prefix;
	}

	public void setPrefix(Vertex prefix) {
		this.prefix = prefix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}

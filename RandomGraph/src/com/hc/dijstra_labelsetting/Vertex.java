package com.hc.dijstra_labelsetting;

public class Vertex {
	private int id;
	private int prefix;
	private Vertex prev;
	
	public Vertex() {
	}
	
	public Vertex(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getPrefix() {
		return prefix;
	}

	public void setPrefix(final int prefix) {
		this.prefix = prefix;
	}
	
	

	public Vertex getPrev() {
		return prev;
	}

	public void setPrev(Vertex prev) {
		this.prev = prev;
	}

	@Override
	public int hashCode() {
		final int prime = 1007;
		int result = 0;
		result += prime;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Vertex){
			Vertex vertex = (Vertex)obj;
			if(this.id == vertex.getId()){
				return true;
			}else{
				return false;
			}
		}
		return super.equals(obj);
	}
	
	
}

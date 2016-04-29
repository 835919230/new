package com.hc.randomgraph;

public class Vertex {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Vertex() {
	}
	
	public Vertex(int id){
		this.id = id;
	}

	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof Vertex){
			Vertex v = (Vertex)arg0;
			if(v.getId()==this.id)
				return true;
			else 
				return false;
		}
		return super.equals(arg0);
	}
	
}

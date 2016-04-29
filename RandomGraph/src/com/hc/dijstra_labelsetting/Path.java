package com.hc.dijstra_labelsetting;

import java.util.ArrayList;
import java.util.List;

public class Path {
	private List<Vertex> preAdjVertexes = new ArrayList<>();
	@SuppressWarnings("unused")
	private Vertex vertex;
	
	public List<Vertex> getPreAdjVertexes() {
		return preAdjVertexes;
	}

	public void setPreAdjVertexes(List<Vertex> preAdjVertexes) {
		this.preAdjVertexes = preAdjVertexes;
	}
	
	public Path() {
	}
	
	public Path(Vertex vertex){
		this.vertex = vertex;
	}
	
	public Vertex getVertex() {
		return vertex;
	}
}

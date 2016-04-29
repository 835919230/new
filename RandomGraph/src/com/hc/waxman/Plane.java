package com.hc.waxman;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Plane {
	private Graph graph;
	private int height;
	private int width;
	
	public Plane(Graph graph, int width, int height) {
		super();
		this.height = height;
		this.graph = graph;
		this.width = width;
	}
	
	public Graph newInstance(){
		return new Graph(new HashSet<Vertex>(), new HashSet<Edge>(), new HashMap<Vertex, List<Edge>>());
	}
	
	public Plane(int width, int height) {
		this.height = height;
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Graph getGraph() {
		return graph;
	}
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public Graph generateWaxmanGraph(){
		return this.graph;
	}
	
}

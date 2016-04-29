package com.hc.waxman;

public class Edge {
	private Vertex head;
	private Vertex tail;
	private int weight;
	private int capacity;
	private boolean directed;
	
	public Edge(Vertex head, Vertex tail, int weight, int capacity, boolean directed) {
		this.head = head;
		this.tail = tail;
		this.weight = weight;
		this.capacity = capacity;
		this.directed = directed;
	}
	
	@Override
	public int hashCode() {
		final int prime = 10007;
	    int result = 1;
	    result = result*prime+(head==null?0:head.hashCode());
	    result = result*prime+(tail==null?0:head.hashCode());
		return result;
	}
	public Vertex getHead() {
		return head;
	}
	public void setHead(Vertex head) {
		this.head = head;
	}
	public Vertex getTail() {
		return tail;
	}
	public void setTail(Vertex tail) {
		this.tail = tail;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public boolean isDirected() {
		return directed;
	}
	public void setDirected(boolean directed) {
		this.directed = directed;
	}
	
}

package com.hc.dijstra_labelsetting;

public class Edge {
	private Vertex head;
	private Vertex tail; 
	private int weight;
	private int capacity;
	
	private double floCap;
	
	public Edge() {
	}
	
	public Vertex getHead() {
		return head;
	}
	public void setHead(final Vertex head) {
		this.head = head;
	}
	public Vertex getTail() {
		return tail;
	}
	public void setTail(final Vertex tail) {
		this.tail = tail;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(final int weight) {
		this.weight = weight;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(final int capacity) {
		this.capacity = capacity;
	}
	
	public double getFloCap() {
		return floCap;
	}
	public void setFloCap(double floCap) {
		this.floCap = floCap;
	}

	@Override
	public int hashCode() {
		final int prime = 7;
		int result = 1;
		result += this.head.getId() * prime;
		result += this.tail.getId() * prime;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Edge){
			Edge edge = (Edge) obj;
			if(this.head==edge.getHead()&&this.tail==edge.getTail()){
				return true;
			}else{
				return false;
			}
		}
		return super.equals(obj);
	}
	
}

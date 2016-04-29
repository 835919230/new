package com.hc.randomgraph;

public class Edge implements Cloneable{
	private int head;
	private int tail;
	private int weight;
	private int capacity;
	public int getHead() {
		return head;
	}
	public void setHead(int head) {
		this.head = head;
	}
	public int getTail() {
		return tail;
	}
	public void setTail(int tail) {
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
	
	public Edge() {
		// TODO Auto-generated constructor stub
	}
	public Edge(int head, int tail, int weight, int capacity) {
		super();
		this.head = head;
		this.tail = tail;
		this.weight = weight;
		this.capacity = capacity;
	}
	public Edge(int head, int tail, int weight) {
		super();
		this.head = head;
		this.tail = tail;
		this.weight = weight;
	}
	
	public Edge(Edge e){
		this.head = e.head;
		this.tail = e.tail;
		this.weight = e.weight;
		this.capacity = e.capacity;
	}
	@Override
	public String toString() {
		return "Edge [head=" + head + ", tail=" + tail + ", weight=" + weight + ", capacity=" + capacity + "]";
	}
	
	public boolean equals(Edge edge) {
		if(this.getHead()==edge.getHead()&&this.getTail()==edge.getTail())
			return true;
		else
			return false;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
}

package com.hc.test;

public class Pair {
	private int head;
	private int tail;
	
	public Pair() {
		// TODO Auto-generated constructor stub
	}

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
	
	
	
	public Pair(int head, int tail) {
		super();
		this.head = head;
		this.tail = tail;
	}

	public boolean equals(Pair pair) {
		// TODO Auto-generated method stub
		if((getHead() == pair.getHead()&&getTail()==pair.getTail())||(getTail()==pair.getHead()&&getHead()==pair.getTail()))
		{
			return true;
		}
		return false;
	}
}

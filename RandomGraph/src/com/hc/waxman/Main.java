package com.hc.waxman;

public class Main {
	
	static WaxmanGraphFactory waxmanGraphFactory;
	
	static final int nodeNumber = 1000;
	
	static final int width = 1000;
	
	static final int height = 1000;
	
	static final double alpha = 0.3;
	
	static final double beta = 0.7;
	
	static{
		waxmanGraphFactory = new WaxmanGraphFactory(new Plane(width,height), alpha, beta, nodeNumber);
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Graph g = waxmanGraphFactory.generateWaxmanGraph();
		System.out.println(g.toString());
		System.out.println("һ���У�"+g.getEdges().size()+"����");
		System.out.println("�ڵ���:"+g.getVertexes().size());
		long endTime = System.currentTimeMillis();
		System.out.println("ִ��ʱ�䣺"+(endTime-startTime)+"ms");
	}
}

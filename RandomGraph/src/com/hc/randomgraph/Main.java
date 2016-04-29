package com.hc.randomgraph;

public class Main {
	public static void main(String[] args) {
		Player Me = new Player();
		
		Graph g = Me.generateRandomGraph();
		System.out.println("节点有"+g.getNumVertex()+"个");
		System.out.println("一共有"+g.getEdges().size()+"条边");
		for(int i = 0;i < g.getEdges().size();i++){
			System.out.println("第"+(i+1)+"条边是:"+g.getEdges().get(i).toString());
		}
		System.out.println(g.getAdjacentEdges());
	}
}

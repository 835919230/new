package com.hc.randomgraph;

public class Main {
	public static void main(String[] args) {
		Player Me = new Player();
		
		Graph g = Me.generateRandomGraph();
		System.out.println("�ڵ���"+g.getNumVertex()+"��");
		System.out.println("һ����"+g.getEdges().size()+"����");
		for(int i = 0;i < g.getEdges().size();i++){
			System.out.println("��"+(i+1)+"������:"+g.getEdges().get(i).toString());
		}
		System.out.println(g.getAdjacentEdges());
	}
}

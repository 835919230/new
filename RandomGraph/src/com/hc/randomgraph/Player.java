package com.hc.randomgraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Player {
	
	private static final int MAX_NODE_NUMBER 	 = 50;//�������ɵ���󶨵�����
	private static final int MAX_FLAG_NUMBER     = 2; //����0������ͼ1������ͼ
	private static final int MAX_WEIGHT_NUMBER   = 10;//�������Ȩ����ֵ
	private static final int MAX_CAPACITY_NUMBER = 10;//�������������ֵ
	
//	private void BFS(Graph g){
//		Queue<Integer> queue = new LinkedList<Integer>();
//		
//	}
	
	/**
	 * �������ͼ�ķ���
	 * @return ���ɵ����ͼ Graph
	 */
	public Graph generateRandomGraph(){
		Graph g = new Graph();
		
		Random random = new Random();
		int flag = random.nextInt(MAX_FLAG_NUMBER);
		
		//���ɵĽڵ���
		int nodeNumber = random.nextInt(MAX_NODE_NUMBER)+2;
		g.setNumVertex(nodeNumber);
		
		for(int i = 0;i < nodeNumber;i++){
			Vertex v = new Vertex(i+1);
			if(!g.vertexes.contains(v))
				g.vertexes.add(v);
		}
		
		for(int i = 0;i < nodeNumber-1;i++){
			g.connect(g.vertexes.get(i), g.vertexes.get(i+1));
		}
		g.connect(g.vertexes.get(g.vertexes.size()-1), g.vertexes.get(0));
		
		int n = random.nextInt(nodeNumber);
		
		for(int i = 0;i < n;i++){
			int head = java.util.concurrent.ThreadLocalRandom.current().nextInt(nodeNumber)+1;
			System.out.println(head);
			int tail = java.util.concurrent.ThreadLocalRandom.current().nextInt(nodeNumber)+1;
			System.out.println(tail);
			if(head == tail)
				continue;
			
			Vertex v1 = new Vertex(head);
			Vertex v2 = new Vertex(tail);
			
			g.connect(v1, v2);
		}
		
		return g;
	}
}

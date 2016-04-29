package com.hc.waxman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * ����WaxMan�Ĺ���
 * @author ���Z
 */
public class WaxmanGraphFactory {
	private Plane plane;
	private double alpha;
	private double beta;
	private int vertexNumber;
	
	/**
	 * ��ʼ�������Ĺ��췽��
	 * @param plane ����һ��ƽ��
	 * @param alpha ����alpha����
	 * @param beta  ����alpha����
	 * @param vertexNumber �����ڵ���
	 */
	public WaxmanGraphFactory(Plane plane,double alpha,double beta,int vertexNumber) {
		this.plane = plane;
		this.alpha = alpha;
		this.beta = beta;
		this.vertexNumber = vertexNumber;
	}
	
	/**
	 * ��������ڵ�
	 * @return ����ڵ��list
	 */
	private List<Vertex> generateVertexes(){
		//java.util.concurrent.ThreadLocalRandom().current();
		List<Vertex> vertexes = new ArrayList<Vertex>();
		int width = this.plane.getWidth()+1;
		int height = this.plane.getHeight()+1;
		Random random = new Random();
		for(int i = 0;i < this.vertexNumber;i++){
			vertexes.add(new Vertex(
							new Cordinate(random.nextInt(width), random.nextInt(height))));
		}
		return vertexes;
	}
	
	/**
	 * ����Waxmanͼ�ķ���
	 * @return һ��waxmanͼ
	 */
	public Graph generateWaxmanGraph(){
		//��ʼ��һ��ͼ
		Graph WaxmanGraph = this.plane.newInstance();
		//��������ڵ��List
		List<Vertex> vList = generateVertexes();
		WaxmanGraph.getVertexes().addAll(vList);
		//�����ͼ�Ͻڵ�֮���������
		double maxLength = GenerateUtil.getEuclideanMetric(vList);
		Vertex[] aVertex = WaxmanGraph.getVertexes().toArray(new Vertex[0]);
		//int temp = 0;
		for(int i = 0;i < vList.size();){
			if(vList.size()==0)
				break;
			Vertex u = vList.get(i);
			vList.remove(u);
			for(Vertex v:aVertex){
				if(!u.equals(v)){
					long distance = GenerateUtil.distance(u, v);
					double p = java.util.concurrent.ThreadLocalRandom.current().nextDouble();
					double pe = this.beta*Math.exp(-1d*distance/(1d*maxLength*this.alpha));
					if(pe>=p){
						WaxmanGraph.connect(u, v);
					}
				}
			}
			//temp++;
		}
		return WaxmanGraph;
	}
}

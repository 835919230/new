package com.hc.waxman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 构建WaxMan的工厂
 * @author 何肸
 */
public class WaxmanGraphFactory {
	private Plane plane;
	private double alpha;
	private double beta;
	private int vertexNumber;
	
	/**
	 * 初始化工厂的构造方法
	 * @param plane 给定一个平面
	 * @param alpha 给定alpha参数
	 * @param beta  给定alpha参数
	 * @param vertexNumber 给定节点数
	 */
	public WaxmanGraphFactory(Plane plane,double alpha,double beta,int vertexNumber) {
		this.plane = plane;
		this.alpha = alpha;
		this.beta = beta;
		this.vertexNumber = vertexNumber;
	}
	
	/**
	 * 产生随机节点
	 * @return 随机节点的list
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
	 * 产生Waxman图的方法
	 * @return 一个waxman图
	 */
	public Graph generateWaxmanGraph(){
		//初始化一个图
		Graph WaxmanGraph = this.plane.newInstance();
		//随机产生节点的List
		List<Vertex> vList = generateVertexes();
		WaxmanGraph.getVertexes().addAll(vList);
		//计算该图上节点之间的最大距离
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

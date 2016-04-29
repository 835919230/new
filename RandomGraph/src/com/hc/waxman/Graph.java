package com.hc.waxman;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Graph {
	private static final Integer MAX_VALUE = Integer.MAX_VALUE;
	private Set<Vertex> vertexes;
	private Set<Edge> edges;
	private Map<Vertex,List<Edge>> AdjacentEdges;
	private List<Integer> distance;
	private int[][] array;
	
	public Graph(Set<Vertex> vertexes, Set<Edge> edges, Map<Vertex, List<Edge>> adjacentEdges) {
		this.vertexes = vertexes;
		this.edges = edges;
		this.AdjacentEdges = adjacentEdges;
		
	}
	
	/**
	 * 将图联通
	 * @param u
	 * @param v
	 * @return
	 */
	public boolean connect(Vertex u,Vertex v){
		Random random = new Random();
		Edge edge = new Edge(u,v,
				random.nextInt(GenerateUtil.MAX_WEIGHT)+1,
				random.nextInt(GenerateUtil.MAX_CAPACITY)+1,true);
		if(!this.getEdges().contains(edge)){
			edges.add(edge);
			if(AdjacentEdges.get(u)==null)
				AdjacentEdges.put(u, new ArrayList<>());
			AdjacentEdges.get(u).add(edge);
			
			if(AdjacentEdges.get(v)==null)
				AdjacentEdges.put(v,new ArrayList<>());
			
			AdjacentEdges.get(v).add(edge);
			
			return true;
		}else
			return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("graph:\n");
		for(Edge edge:this.edges){
			sb.append("  edge[head:(")
			  .append(edge.getHead().getCordinate().getX())
			  .append(",")
			  .append(edge.getHead().getCordinate().getY())
			  .append(")")
			  .append(" tail:(")
			  .append(edge.getTail().getCordinate().getX())
			  .append(",")
			  .append(edge.getTail().getCordinate().getY())
			  .append(")]")
			  .append("\n");
		}
		return sb.toString();
	}
	
	private void init(){
		array = new int[vertexes.size()][vertexes.size()];
		distance = new ArrayList<Integer>(vertexes.size());
		//初始化
		distance.add(0);
		for(int i = 1;i < vertexes.size();i++){
			distance.add(MAX_VALUE);
		}
		for(Edge edge:edges){
			array[edge.getHead().getId()-1][edge.getTail().getId()-1] = edge.getWeight();
		}
	}
	
	private void Update(int i){
		for(int j = 0;j < vertexes.size();j++){
			if(distance.get(j)>(array[i][j])){
				
			}
		}
	}
	
	private Vertex findMin(){
		 return null;
	}
	
	public void Dijstra(){
		init();
	}
	
	public Set<Vertex> getVertexes() {
		return vertexes;
	}
	public void setVertexes(Set<Vertex> vertexes) {
		this.vertexes = vertexes;
	}
	public Set<Edge> getEdges() {
		return edges;
	}
	public void setEdges(Set<Edge> edges) {
		this.edges = edges;
	}
	public Map<Vertex, List<Edge>> getAdjacentEdges() {
		return AdjacentEdges;
	}
	public void setAdjacentEdges(Map<Vertex, List<Edge>> adjacentEdges) {
		AdjacentEdges = adjacentEdges;
	}
	
}

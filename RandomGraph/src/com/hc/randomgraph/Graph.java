package com.hc.randomgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Graph {
	private int type;
	private int numVertex;
	private int numEdge;
	private List<Edge> edges = new ArrayList<>();
	private Map<Integer,List<Edge>> AdjacentEdges = new HashMap<>();
	
	public List<Vertex> vertexes = new ArrayList<>();
	
	public Graph() {
		super();
	}

	public Graph(List<Edge> edges){
		
	}
	
	public int calculate1(){
		int result = 1;
		int temp = getNumVertex();
		for(int i = 1;i <= 2;i++){
			result *= temp;
			temp--;
		}
//		result/=2;
		return result;
	}

	public int getNumVertex() {
		return numVertex;
	}

	public void setNumVertex(int numVertex) {
		this.numVertex = numVertex;
	}

	public int getNumEdge() {
		return numEdge;
	}

	public void setNumEdge(int numEdge) {
		this.numEdge = numEdge;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
		Set<Integer> flagSet = new HashSet<>();
		AdjacentEdges = new HashMap<>();
		
		for(int i = 0;i < edges.size();i++){
			Edge edge = edges.get(i);
			int flag = edge.getHead()*this.numVertex+edge.getTail();
			if(!flagSet.contains(flag)){
				flagSet.add(flag);
				if(!AdjacentEdges.containsKey(edge.getHead()))
					AdjacentEdges.put(edge.getHead(), new ArrayList<>());
				
				AdjacentEdges.get(edge.getHead()).add(edge);
				
				
				if(!AdjacentEdges.containsKey(edge.getTail()))
					AdjacentEdges.put(edge.getTail(), new ArrayList<>());
				
				Edge cloneEdge = null;
				
				try {
					cloneEdge = (Edge) edge.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				
				cloneEdge.setHead(edge.getTail());
				cloneEdge.setTail(edge.getHead());
				
				//if(cloneEdge.getHead()!=cloneEdge.getTail())
				AdjacentEdges.get(edge.getTail()).add(cloneEdge);
			}
		}
//		
//		for(int i = 1;i <= numVertex;i++){
//			if(!AdjacentEdges.containsKey(i))
//				AdjacentEdges.put(i, new ArrayList<Edge>());
//			for(int j = 0;j < edges.size();j++){
//				Edge edge = edges.get(j);
//				if (edge.getHead()==i) {
//					AdjacentEdges.get(i).add(edge);
//				}
//			}
//		}
	}

	public Map<Integer, List<Edge>> getAdjacentEdges() {
		return AdjacentEdges;
	}

	public void setAdjacentEdges(Map<Integer, List<Edge>> adjacentEdges) {
		AdjacentEdges = adjacentEdges;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Graph [type=" + type + ", numVertex=" + numVertex + ", numEdge=" + numEdge + ", edges=" + edges
				+ ", AdjacentEdges=" + AdjacentEdges + "]"+"\n";
	}
	
	public void connect(Vertex v1,Vertex v2){
		if(!isConnected(v1, v2)){
			Edge e = new Edge();
			e.setHead(v1.getId());
			e.setTail(v2.getId());
			e.setWeight(new Random().nextInt(10));
			e.setCapacity(new Random().nextInt(10));
			this.edges.add(e);
		}
	}
	
	private boolean isConnected(Vertex v1,Vertex v2){
		if(edges==null)
			return false;
		Edge ed = new Edge();
		ed.setHead(v1.getId());
		ed.setTail(ed.getTail());
		for(Edge e:edges){
			if(e.equals(ed))
				return true;
		}
		return false;
	}
	
}

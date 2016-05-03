package com.hc.dijstra_labelsetting;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Graph {
	private Set<Vertex> vertexes;
	private List<Edge> edges;
	private List<Vertex> tempVertexes;
	private Map<Integer,ArrayList<Edge>> AdjacentEdges;
	private Set<Vertex> flagSet;
	private Map<Integer,Vertex> vertexMap;
	private Map<Vertex,Path> pathsMap;
	
	private static final int MAX_VALUE = Integer.MAX_VALUE;
	
	private List<Integer> distance;
	
	private List<Double> disFlo;
	
	public Graph() {
		init();
	}
	/**
	 * project1 和 project2用这个构造方法
	 * @param file
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public Graph(File file) throws Exception{
		init();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		int start = 0;
		while((line = reader.readLine())!=null){
			String[] mArgs = line.split(" ");
			for(int i = 0;i < mArgs.length;i++){
				Vertex head = new Vertex(start);
				this.vertexMap.put(start, head);
				this.vertexes.add(head);
				if(!"m".equals(mArgs[i])){
					Edge e = new Edge();
					Vertex tail = new Vertex(i);
					e.setHead(head);
					e.setTail(tail);
					int weight = Integer.parseInt(mArgs[i]);
					e.setWeight(weight);
					//TODO 
					this.edges.add(e);
					if(this.AdjacentEdges.get(start)==null)
						this.AdjacentEdges.put(start, new ArrayList<>());
					this.AdjacentEdges.get(start).add(e);
				}
			}
			start++;
		}
	}
	/**
	 * project2 用这个构造方法
	 * @param in
	 * @throws Exception
	 */
	public Graph(InputStream in) throws Exception{
		init();
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(bin));
		String line = null;
		int start = 0;
		while((line = reader.readLine())!=null){
			String[] mArgs = line.split(" ");
			for(int i = 0;i < mArgs.length;i++){
				Vertex head = new Vertex(start);
				this.vertexMap.put(start, head);
				this.vertexes.add(head);
				if(!"m".equals(mArgs[i])){
					Edge e = new Edge();
					Vertex tail = new Vertex(i);
					e.setHead(head);
					e.setTail(tail);
					double weight = Double.parseDouble((mArgs[i]));
					e.setFloCap(-Math.log(weight));
					//TODO 
					this.edges.add(e);
					if(this.AdjacentEdges.get(start)==null)
						this.AdjacentEdges.put(start, new ArrayList<>());
					this.AdjacentEdges.get(start).add(e);
				}
			}
			start++;
		}
	}

	/**
	 * project3 用这个构造方法
	 * @param file1
	 * @param file2
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public Graph(File file1,File file2) throws Exception{
		init();
		BufferedInputStream in1 = new BufferedInputStream(new FileInputStream(file1));
		BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(file2));
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(in1));
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
		String line1 = null;
		String line2 = null;
		int start = 0;
		while((line1 = reader1.readLine())!=null&&(line2 = reader2.readLine())!=null){
			String[] mArgs = line1.split(" ");
			String[] mArgC = line2.split(" ");
			for(int i = 0;i < mArgs.length;i++){
				Vertex head = new Vertex(start);
				this.vertexMap.put(start, head);
				this.vertexes.add(head);
				if(!"m".equals(mArgs[i])){
					Edge e = new Edge();
					Vertex tail = new Vertex(i);
					e.setHead(head);
					e.setTail(tail);
					int weight = Integer.parseInt(mArgs[i]);
					int capacity = Integer.parseInt(mArgC[i]);
					e.setWeight(weight);
					e.setCapacity(capacity);
					//TODO 
					this.edges.add(e);
					if(this.AdjacentEdges.get(start)==null)
						this.AdjacentEdges.put(start, new ArrayList<>());
					this.AdjacentEdges.get(start).add(e);
				}
			}
			start++;
		}
	}
	
	public Set<Vertex> getFlagSet() {
		return flagSet;
	}

	public void setFlagSet(Set<Vertex> flagSet) {
		this.flagSet = flagSet;
	}

	public Map<Integer, Vertex> getVertexMap() {
		return vertexMap;
	}

	public Map<Vertex, Path> getPathsMap() {
		return pathsMap;
	}

	public void setVertexMap(Map<Integer, Vertex> vertexMap) {
		this.vertexMap = vertexMap;
	}



	public Set<Vertex> getVertexes() {
		return vertexes;
	}

	public void setVertexes(final Set<Vertex> vertexes) {
		this.vertexes = vertexes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(final List<Edge> edges) {
		this.edges = edges;
	}

	public Map<Integer, ArrayList<Edge>> getAdjacentEdges() {
		return AdjacentEdges;
	}

	public void setAdjacentEdges(final Map<Integer, ArrayList<Edge>> adjacentEdges) {
		AdjacentEdges = adjacentEdges;
	}
	public void setDistance(final List<Integer> distance) {
		this.distance = distance;
	}
	
	public List<Integer> getDistance() {
		return distance;
	}
	
	private void init(){
		this.vertexes = new HashSet<>();
		this.edges = new ArrayList<>();
		this.AdjacentEdges = new HashMap<>();
		this.distance = new ArrayList<>();
		this.flagSet = new HashSet<>();
		this.vertexMap = new HashMap<>();
		this.tempVertexes = new ArrayList<>();
		this.pathsMap = new HashMap<>();
		this.disFlo = new ArrayList<>();
	}
	
	private void destroy(){
		flagSet.clear();
		distance.clear();
		//pathsMap.clear();
	}
	
	/**
	 * DJ算法前期的准备工作
	 */
	private void prepare(Vertex v){
		pathsMap.clear();
		List<Vertex> list = new ArrayList<>();
		list.addAll(vertexes);
		list.sort(new Comparator<Vertex>() {

			@Override
			public int compare(Vertex v1, Vertex v2) {
				return (v1.getId()-v2.getId());
			}
		});
		for(Vertex vertex:list){
			if(vertex.equals(v)){
				distance.add(0);
				disFlo.add(-Math.log(1d));//给一个很小的数字
			}
			else{
				distance.add(MAX_VALUE);
				disFlo.add(-Math.log(0.0000001d));//给一个很大的数字
			}
//			disFlo.add(0.0000001d);
		}
		flagSet.add(v);
	}
	
	/**
	 * 更新distance和flagSet
	 * @param head 表示要更新节点的id（编号）
	 */
	private void update(int head){
		ArrayList<Edge> edges = AdjacentEdges.get(head);
		for(Edge edge:edges){
			this.tempVertexes.add(vertexMap.get(edge.getTail().getId()));
			int tail = edge.getTail().getId();
			int newValue = distance.get(head)+edge.getWeight();
			if(distance.get(tail) > newValue){
				distance.set(tail, newValue);
				// 增加前驱
				Vertex prefix = vertexMap.get(head);
				Vertex vertex = vertexMap.get(tail);
				
				vertex.setPrefix(prefix.getId());
				vertex.setPrev(prefix);
				
				//增加到path中，每个点都应该拥有一个path，所以用map存储
				if(pathsMap.get(tail)==null){
					pathsMap.put(vertex, new Path());
				}
				
			}
		}
	}
	
	private void updateFlo(int head){
		tmpVertex = vertexMap.get(head);
		ArrayList<Edge> edges = AdjacentEdges.get(head);
		if(edges!=null){
			for(Edge edge:edges){
				this.tempVertexes.add(vertexMap.get(edge.getTail().getId()));
				int tail = edge.getTail().getId();
				
				double newValue = disFlo.get(head)+ edge.getFloCap();
//				double newValue = disFlo.get(head)*edge.getFloCap();
//				double oldValue = disFlo.get(tail);
				double oldValue = disFlo.get(tail);
				if(oldValue > newValue){
					disFlo.set(tail, newValue);
					// 增加前驱
					Vertex prefix = vertexMap.get(head);
					Vertex vertex = vertexMap.get(tail);
					
					vertex.setPrefix(prefix.getId());
					vertex.setPrev(prefix);
					
					//增加到path中，每个点都应该拥有一个path，所以用map存储
					if(pathsMap.get(tail)==null){
						pathsMap.put(vertex, new Path());
					}
					
				}
			}
		}
	}
	
	/**
	 * 找到最小节点
	 * @return
	 */
	private Vertex findMin(){
		Vertex vertex = null;
		int min = Integer.MAX_VALUE;
		for(Vertex v:this.tempVertexes){
			if(!flagSet.contains(v)){
				int d = distance.get(v.getId());
				if(d<min){
					min = d;
					vertex = v;	
				}
			}
		}
		this.tempVertexes.clear();
		return vertex;
	}
	
	private static Vertex tmpVertex;
	
	private Vertex findMinFlo(){
		Vertex vertex = null;
		double min = Double.MAX_VALUE;
		if(this.tempVertexes.size()>=1){
			for(Vertex v:this.tempVertexes){
				if(!flagSet.contains(v)){
					double d = disFlo.get(v.getId());
					if(d==0d)
						continue;
					if(d<min){
						min = d;
						vertex = v;
//						tmpVertex = v;
					}
				}
			}
		}else{
			for(Vertex v:vertexes){
				if(!flagSet.contains(v)){
					double d = disFlo.get(v.getId());
					if(d==0d)
						continue;
					if(d<min){
						min = d;
						vertex = v;
//						tmpVertex = v;
					}
				}
			}
		}
		this.tempVertexes.clear();
		return vertex;
	}
	
	private Path constructPath(final Vertex start,final Vertex last){
		Path path = new Path(last);
		Vertex v = last;
		while(v!=null&&v!=start){
			v = v.getPrev();
			if(v!=null)
				path.getPreAdjVertexes().add(v);
		}
		//path.getPreAdjVertexes().add(start);
		//放入map存储
		pathsMap.put(last, path);
		return path;
	}
	
	private void constructAllPaths(){
		for(Integer index:this.vertexMap.keySet()){
			if(pathsMap.get(index)==null&&index!=0){
				constructPath(vertexMap.get(0), vertexMap.get(index));
			}
		}
		//输出路径
		Set<Entry<Vertex, Path>> entry = pathsMap.entrySet();
		for(Entry<Vertex,Path> en:entry){
			System.out.println("-----------------------------------");
			List<Vertex> list = en.getValue().getPreAdjVertexes();
			System.out.println("原点到节点"+en.getValue().getVertex().getId()+"的路径如下：");
			for(int i = list.size()-1;i >= 0;i--){
				System.out.println("过节点"+list.get(i).getId());
			}
			System.out.println("最后到节点"+en.getValue().getVertex().getId());
			System.out.println();
		}
	}
	
	/**
	 *普通的迪杰斯特拉 
	 */
	public Path DijstraAlg_(Vertex s){	
		//第一步，预处理
		prepare(s);
		//第二步 update
		update(s.getId());
		//第三部反复findMin和update
		while(flagSet.size()!=vertexes.size()){
			Vertex v = findMin();
			flagSet.add(v);
			update(v.getId());
		}
		Vertex last = this.vertexMap.get(this.vertexes.size()-1);
		Path path = constructPath(s,last);
		//将所有路径放入pathMap中
		constructAllPaths();
		
		destroy();
		return path;
	}
	
	/**
	 * Project1 单源单宿最短路问题
	 * @param s 开始节点
	 * @param d 结束节点
	 * @return s到d的最短路path
	 */
	public Path DijstraAlg(Vertex s,Vertex d){
		prepare(s);
		update(s.getId());
		while(flagSet.size()!=vertexes.size()){
			Vertex v = findMin();
			flagSet.add(v);
			update(v.getId());
			//退出标识
			if(flagSet.contains(d))
				break;
		}
		Path path = constructPath(s,d);
		System.out.println("-----------------------------------");
		List<Vertex> list = path.getPreAdjVertexes();
		System.out.println("原点到节点"+path.getVertex().getId()+"的路径如下：");
		for(int i = list.size()-1;i >= 0;i--){
			System.out.println("过节点"+list.get(i).getId());
		}
		System.out.println("最后到节点"+path.getVertex().getId());
		System.out.println();
		destroy();
		return path;
	}
	/**
	 * project2 
	 * @param s
	 */
	public Path DijstraAlg(Vertex s){
		prepare(s);
		Vertex d = vertexMap.get(vertexes.size()-1);//终点
		updateFlo(s.getId());
		while(flagSet.size()!=vertexes.size()){
			Vertex v = findMinFlo();
			if(v==null&&tempVertexes!=null){
				flagSet.add(tmpVertex);//这个tmpVertex是为了解决那些只进不出的节点而设计的，若一个节点只进不出，findMIn（）就会返回null
				tmpVertex = null;
				continue;
			}
			flagSet.add(v);
			updateFlo(v.getId());
		}
		Path path = constructPath(s,d);
		//将所有路径放入pathMap中
		constructAllPaths();
		destroy();
		return path;
	}
	
	private void deleteEdge(int capacity){
		List<Edge> toDeleteEdges = new ArrayList<>();
		System.out.println(this.edges.size());
		for(Edge e:edges){
			if(e.getCapacity()<capacity)
				toDeleteEdges.add(e);
		}
		this.edges.removeAll(toDeleteEdges);
		System.out.println(this.edges.size());
		for(Edge e:toDeleteEdges){
			List<Edge> tEdges = this.AdjacentEdges.get(e.getHead().getId());

			if(tEdges!=null)
				tEdges.removeAll(toDeleteEdges);
		}
	}
	/**
	 * project3 
	 * @param s
	 * @param capacity
	 */
	public Path DijstraAlg(Vertex s,int capacity){
		deleteEdge(capacity);
		return DijstraAlg_(s);
	}

}

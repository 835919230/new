package com.hc.dijstra_labelsetting;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
@SuppressWarnings({"resource","unused"})
public class Main {
	
	private static void test1(){
		Set<Vertex> flagSet = new HashSet<>();
		
		Vertex v1 = new Vertex();
		Vertex v2 = new Vertex();
		
		v1.setId(1);
		v2.setId(2);
		
		flagSet.add(v2);
		flagSet.add(v1);
		
		Iterator<Vertex> it = flagSet.iterator();
		
		if(it.hasNext())
			System.out.println(it.next().getId());
		
		List<Integer> list = new ArrayList<>();
		list.clear();
		
		float a = 2.7345f;
		
		float sum = (float) Math.log(a);
		System.out.println(sum);
	}
	
	private static void test2() throws IOException{
		File file = new File("test.txt");
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while((line = reader.readLine())!=null){
			System.out.println(line);
		}
	}
	/**
	 * 测试一般的Dijstra
	 * @throws Exception
	 */
	private static void testNormalDijstra() throws Exception{
		long start = System.currentTimeMillis();
		Graph g = new Graph(new File("test.txt"));
		Path path = g.DijstraAlg_(g.getVertexes().iterator().next());
		
		System.out.println("执行时间："+(System.currentTimeMillis()-start)+"ms");
		
		for(Vertex v:path.getPreAdjVertexes()){
			if(v!=null)
				System.out.println(v.getId());
		}
		
	}
	/**
	 * project1 用这个
	 * @throws Exception
	 */
	private static void testDijstra2() throws Exception{
		long start = System.currentTimeMillis();
		Graph g = new Graph(new File("test.txt"));
		Path path = g.DijstraAlg(g.getVertexes().iterator().next(),
								g.getVertexMap().get(3));
		
		System.out.println("执行时间："+(System.currentTimeMillis()-start)+"ms");
		
		for(Vertex v:path.getPreAdjVertexes()){
			if(v!=null)
				System.out.println(v.getId());
		}
		
	}
	/**
	 * project2 用这个
	 * @throws Exception
	 */
	private static void testDijstra3() throws Exception{
		Graph g = new Graph(new FileInputStream(new File("test2.txt")));
		Path path = g.DijstraAlg(g.getVertexMap().get(0));

	}
	
	/**
	 * project3用这个
	 * @throws Exception
	 */
	private static void testDijstra4() throws Exception{
		Graph g = new Graph(new File("test3.txt"), new File("capacity.txt"));
		Path path = g.DijstraAlg(g.getVertexMap().get(0), 2);
	}
	
	public static void main(String[] args) throws Exception{
//		testNormalDijstra();
		testDijstra2();
//		testDijstra3();
//		testDijstra4();
	}
}

package com.hc.preflow;

import java.io.*;
import java.util.*;

import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseUnsignedInt;

/**
 * Created by 83591 on 2016/5/7.
 */
public class Graph {
    int n;
    int m_;
    List<Vertex> vertices;
    Map<Vertex,List<Edge>> adjEdgeMap;
    List<Edge> edges;
    int[] a; //起点到i的可改进量
    int[] p; //最短路树上p的入边编号
    int[] d; //权重距离函数

    private void init(){
        vertices = new ArrayList<>();
        adjEdgeMap = new HashMap<>();
        edges = new ArrayList<>();
    }

    public Graph(File file) throws IOException{
        init();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        int i = 0;
        while ((line = reader.readLine())!=null){
            if (i == 0){
                String[] tag = line.split(" ");
                this.n = parseInt(tag[0]);
                this.m_ = parseInt(tag[1]);
                i++;
            }else {
                String[] tag = line.split(" ");
                Vertex head = new Vertex(parseInt(tag[0]));
                Vertex tail = new Vertex(parseInt(tag[1]));
                addEdge(head,tail,parseInt(tag[2]));
                if (!vertices.contains(head))
                    vertices.add(head);
                if (!vertices.contains(tail))
                    vertices.add(tail);
            }
        }
        a = new int[n+1];
        p = new int[m_*2+1];
    }

    void addEdge(Vertex from,Vertex to,int cap){
        Edge e1 = new Edge(from,to,cap,0);
        edges.add(e1);
        Edge e2 = new Edge(to,from,0,0);
        edges.add(e2);
        int m = edges.size();
        if (!adjEdgeMap.containsKey(from))
            adjEdgeMap.put(from,new ArrayList<>());
        adjEdgeMap.get(from).add(e1);
        if (!adjEdgeMap.containsKey(to))
            adjEdgeMap.put(to,new ArrayList<>());
        adjEdgeMap.get(to).add(e2);
        //记录下标号，到更新的时候用
        e1.m = m-2;
        e2.m = m-1;
    }

    /**
     * Project3 最短增广路径 BellmanFord
     * @param s
     * @param t
     * @return
     */
    int MaxFlow(Vertex s,Vertex t){
        long start = System.nanoTime();
        int flow = 0;
        for (;;){
            memset(this.a);
            memset(this.p);
            Queue<Vertex> queue = new LinkedList<>();
            queue.add(s);
            a[s.id] = Integer.MAX_VALUE;
            while (!queue.isEmpty()){
                Vertex x = queue.remove();
                List<Edge> adjEdge = this.adjEdgeMap.get(x);
                for (Edge edge:adjEdge){
                    Vertex to = edge.tail;
                    if (a[to.id]==0 && edge.cap > edge.flow){
                        p[to.id] = edge.m;
                        a[to.id] = min(a[x.id], edge.cap - edge.flow);
                        if (!queue.contains(to))
                            queue.add(to);
                    }
                }
                if (a[t.id] > 0) break;
            }
            if (a[t.id] <= 0) break;
            for (int u = t.id;u != s.id;u = edges.get(p[u]).head.id){
                edges.get(p[u]).flow += a[t.id];
                edges.get(p[u]^1).flow -= a[t.id];
            }
            flow += a[t.id];
        }
        System.out.println("耗时："+(System.nanoTime() - start)+"ns");
        return flow;
    }

    private void memset(int[] a) {
        for (int i = 0;i < a.length;i++)
            a[i] = 0;
    }
}

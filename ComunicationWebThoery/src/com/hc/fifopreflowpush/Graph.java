package com.hc.fifopreflowpush;


import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Created by 83591 on 2016/5/9.
 */
public class Graph {
    int n;
    int m;
    Queue<Vertex> activeVertexes;   //活跃节点
    List<Vertex> vertices;          //节点集
    List<Edge> edges;               //边集
    Map<Integer,List<Vertex>> adjVertexMap;//找出邻接点

    void addEdge(Vertex from,Vertex to,int cap){
        Edge e1 = new Edge(from,to,cap);
        edges.add(e1);
        Edge e2 = new Edge(to,from,0);
        edges.add(e2);
//        int m = edges.size();
//        if (!adjEdgeMap.containsKey(from))
//            adjEdgeMap.put(from,new ArrayList<>());
//        adjEdgeMap.get(from).add(e1);
//        if (!adjEdgeMap.containsKey(to))
//            adjEdgeMap.put(to,new ArrayList<>());
//        adjEdgeMap.get(to).add(e2);
//        //记录下标号，到更新的时候用
//        e1.m = m-2;
//        e2.m = m-1;
    }

    public Graph(File file) throws IOException {
        init();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        int i = 0;
        while ((line = reader.readLine())!=null){
            if (i == 0){
                String[] tag = line.split(" ");
                this.n = parseInt(tag[0]);
                this.m = parseInt(tag[1]);
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

                if (!adjVertexMap.containsKey(head.id))
                    adjVertexMap.put(head.id,new ArrayList<Vertex>());

                if (!adjVertexMap.containsKey(tail.id))
                    adjVertexMap.put(tail.id,new ArrayList<>());

                adjVertexMap.get(head.id).add(tail);
                adjVertexMap.get(tail.id).add(head);
            }
        }
//        a = new int[n+1];
//        p = new int[m_*2+1];
    }

    private void init() {
        activeVertexes = new LinkedList<>();
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        adjVertexMap = new HashMap<Integer,List<Vertex>>();
    }

    public int fifoPreflowPush(Vertex s,Vertex t){
        Vertex tmpActiveVertex;
        int minh;
        int flow = 0;
        boolean existAdmissible;
        preprocess(s);

        while (!activeVertexes.isEmpty()){
            minh = Integer.MAX_VALUE;
            tmpActiveVertex = activeVertexes.remove();
            existAdmissible = false;
            if (tmpActiveVertex.id == s.id || tmpActiveVertex.id == t.id) continue;
            List<Vertex> adjList = adjVertexMap.get(tmpActiveVertex.id);
            for (Vertex vertex : adjList){
                int tmpFlow = getFlow(tmpActiveVertex,vertex);
                //如果存在admissible边
                if (vertex.d+1 == tmpActiveVertex.d
                        && tmpFlow > 0){
                    if (tmpFlow >= tmpActiveVertex.e)
                        flow = tmpActiveVertex.e;
                    else flow = tmpFlow;

                    push(tmpActiveVertex,vertex,flow);

                    //如果入节点更新且不是尾节点则插入到活跃表中
                    if (isActive(vertex) && vertex.id != t.id)
                        activeVertexes.add(vertex);

                    existAdmissible = true;

                    if (tmpActiveVertex.e == 0) break;
                }
            }

            //如果不存在admiss边需要进行relabel确保得到新的admissible边
            if (!existAdmissible){
                for (Vertex vertex : adjList)
                    if (vertex.d + 1 < minh && getFlow(tmpActiveVertex,vertex) > 0)
                        minh = vertex.d+1;

                if (minh != Integer.MAX_VALUE){
                    tmpActiveVertex.d = minh;
                    activeVertexes.add(tmpActiveVertex);
                }
            }else {
                if (tmpActiveVertex.e != 0)
                    activeVertexes.add(tmpActiveVertex);
            }
        }
        flow = getMaxFlow(t);
        return flow;
    }

    private int getMaxFlow(Vertex t){
        int flow = 0;
        for (Edge edge:edges){
            if (edge.tail.id == t.id)
                flow += edge.flow;
        }
        return flow;
    }

    private void preprocess(Vertex s){
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(s);
        s.isUpdated = true;
        s.d = n;
        int tmp = n;
        while (!queue.isEmpty()){
            Vertex v = queue.remove();
            v.isUpdated = true;
            tmp--;
            List<Vertex> list = adjVertexMap.get(v.id);
            if (list == null) continue;
            for (Vertex vertex : list){
                //如果没被更新过
                if (!vertex.isUpdated){
                    queue.add(vertex);
                    vertex.isUpdated = true;
                    vertex.d = tmp;
                }
            }
        }
        //end while
        //初始化 s 点 e值
        for (Edge edge : edges){
            if (edge.head.id == s.id)
                s.e += edge.cap;
        }
        //对头结点关联的节点进行推入流，并加入到活跃节点列表中
        List<Vertex> list = adjVertexMap.get(s.id);
        for (Vertex vertex : list){
            push(s,vertex,getFlow(s,vertex));
            activeVertexes.add(vertex);
        }
    }

    private boolean isActive(Vertex vertex){
        for (Vertex v:activeVertexes){
            if (v.id == vertex.id)
                return true;
        }
        return false;
    }

    private int getFlow(Vertex head,Vertex tail){
        for (Edge edge: edges){
            if (edge.head.equals(head) && edge.tail.equals(tail)){
                return edge.flow;
            }
        }
        return 0;
    }

    private void push(Vertex head,Vertex tail,int mind){
        head.e -= mind;
        tail.e += mind;
        for (Edge edge: edges){
            if (edge.head.equals(head) && edge.tail.equals(tail)){
                edge.flow = edge.flow - mind;
                if (check(tail,head)){
                    for (Edge e: edges){
                        if (e.head.id == tail.id && e.tail.id == head.id)
                            e.flow = e.flow + mind;
                    }
                }else {
                    Edge newEdge = new Edge(tail,head,mind);
                }
            }else if (edge.head.equals(tail) && edge.tail.equals(head)){
                edge.flow = edge.flow += mind;
            }else continue;
        }
    }

    private boolean check(Vertex head,Vertex tail){
        for (Edge edge : edges){
            if (edge.head.id == head.id && edge.tail.id == tail.id)
                return true;
        }
        return false;
    }

    public void show(){
        for (Edge edge: edges){
            System.out.println(edge.flow);
        }
    }
}

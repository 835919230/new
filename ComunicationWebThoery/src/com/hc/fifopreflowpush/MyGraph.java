package com.hc.fifopreflowpush;

import java.io.*;
import java.util.*;

import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;

/**
 * Created by 83591 on 2016/5/9.
 */
public class MyGraph {
    int n;
    int m;
    List<Vertex> activeVertexes;   //活跃节点
    public List<Vertex> vertices;          //节点集
    List<Edge> edges;               //边集


    Edge addEdge(Vertex from,Vertex to,int cap){
        Edge e1 = new Edge(from,to,cap);
        edges.add(e1);
        return e1;
    }

    public MyGraph(File file) throws IOException {
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
                if (!vertices.contains(head))
                    vertices.add(head);
                if (!vertices.contains(tail))
                    vertices.add(tail);
//                head.adjVertex.add(tail);
                head.adjEdges.add(addEdge(head,tail,parseInt(tag[2])));
//                addEdge(head,tail,parseInt(tag[2]));
            }
        }

        for (Edge edge: edges){
            Vertex head = null;
            Vertex tail = null;
            for (Vertex vertex:vertices){
                if (vertex.id == edge.head.id)
                    head = vertex;
                if (vertex.id == edge.tail.id)
                    tail = vertex;
            }
            head.adjVertex.add(tail);
//            if (!adjVertexMap.containsKey(head))
//                adjVertexMap.put(head, new ArrayList<>());
////            head.adjVertex.add(tail);
////            head.adjEdges.add(edge);
//            adjVertexMap.get(head).add(tail);
//
//            if (!adjEdgeMap.containsKey(head))
//                adjEdgeMap.put(head,new ArrayList<>());
//
//            adjEdgeMap.get(head).add(edge);
            int a = 0;
        }
    }

    private void init() {
        activeVertexes = new ArrayList<>();
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
//        adjEdge = new HashMap<>();
    }

    private void push(Vertex head,Vertex tail,int flow){
        head.e -= flow;
        tail.e += flow;
//        for (Edge edge:edges){
//            if (edge.head.id == head.id && tail.id ==edge.tail.id){
//                edge.flow = edge.flow - flow;
//                //检查反向边
//                Edge inverseEdge = check(tail,head);
//                if (inverseEdge!=null){
//                    inverseEdge.flow = inverseEdge.flow + flow;
//                }else {
//                    inverseEdge = new Edge(tail,head,flow);
//                    inverseEdge.cap = edge.cap;
//                    edges.add(inverseEdge);
//                    tail.adjEdges.add(inverseEdge);
//                }
//            }
//        }
        for (int i = 0;i < edges.size();i++){
            Edge edge = edges.get(i);
            if (edge.head.id == head.id && tail.id ==edge.tail.id){
                edge.flow = edge.flow - flow;
                //检查反向边
                Edge inverseEdge = check(tail,head);
                if (inverseEdge!=null){
                    inverseEdge.flow = inverseEdge.flow + flow;
                }else {
                    inverseEdge = new Edge(tail,head,flow);
                    inverseEdge.cap = edge.cap;
                    edges.add(inverseEdge);
                    tail.adjEdges.add(inverseEdge);

                }
            }
        }
    }

    private Edge check(Vertex tail,Vertex head){
        Edge edge = null;
        List<Edge> edges = tail.adjEdges;
        for (Edge e:edges){
            if (e.tail.id ==head.id)
                edge = e;
        }
//        List<Edge> adjEdges = adjEdgeMap.get(tail);
//        for (Edge e : adjEdges){
//            if (e.tail.id == head.id)
//                edge = e;
//        }
        return edge;
    }

    private int getRestFlow(Vertex head,Vertex tail){
//        for (Edge edge:head.adjEdges){
//            if (edge.tail.id == tail.id)
//                return edge.flow;
//        }
        for (Edge edge : edges){
            if (edge.head.id == head.id && edge.tail.id == tail.id)
                return edge.flow;
        }
        return 0;
    }

    private void preProcess(Vertex s){
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(s);
        s.isUpdated = true;
        s.d = n;

        //初始化s点的e值

//        for (Edge e:s.adjEdges){
//            s.e += e.cap;
//        }
        for (Edge edge:edges){
            if (edge.head.id == s.id){
                s.e += edge.cap;
            }
        }

        int t = n;//临时变量
        while (!queue.isEmpty()){
            Vertex v = queue.remove();
            t--;
            v.isUpdated = true;
            for (Vertex vertex : v.adjVertex){
                //如果没被更新过
                if (!vertex.isUpdated){
                    queue.add(vertex);
                    vertex.isUpdated = true;
                    vertex.d = t;
                }

            }
        }

        //对头结点的关联节点进行推入流，并加入到活跃节点中
        for (Vertex vertex : s.adjVertex){
            push(s,vertex,getRestFlow(s,vertex));
            activeVertexes.add(vertex);
        }

    }

    private boolean isActive(Vertex vertex){
        return activeVertexes.contains(vertex);
    }

    public long fifoPreflowPush(Vertex s,Vertex t){
        long startTime = System.nanoTime();
        int mind;
        int minh;
        boolean existAdmissble;
        Vertex tempVertex;

        preProcess(s);
        while(!activeVertexes.isEmpty()){
            minh = Integer.MAX_VALUE;
//            tempVertex = activeVertexes.remove(0);
            tempVertex = getByFifo();
            existAdmissble = false;

            //不处理s点和t点，回溯到s点的流与到达t点的流不再流动
            if (tempVertex.id == s.id || tempVertex.id == t.id) continue;
            for (Vertex vertex:tempVertex.adjVertex){
                if (vertex!=null){
                    //如果存在admissible边
                    int rest = getRestFlow(tempVertex,vertex);
                    if (tempVertex.d == vertex.d+1 && rest > 0){
                        //找到合适的流大小
                        mind = min(tempVertex.e,rest);
                        //推！
                        push(tempVertex,vertex,mind);
                        //如果入节点更新且不是尾节点则插入到活跃队列末尾
                        if (!isActive(vertex) && vertex.id != t.id)
                            activeVertexes.add(vertex);
                        //存在了admissible边了，更新该变量
                        existAdmissble = true;

                        if (tempVertex.e <= 0) break; //安全保障
                    }
                }
            }
            //如果不存在admissible边的话，需要进行relabel
            if (!existAdmissble){
                for (Vertex vertex:tempVertex.adjVertex){
                    if (vertex!=null){
                        if (vertex.d+1 < minh && getRestFlow(tempVertex,vertex)>0)
                            minh = vertex.d+1;

                        if (minh != Integer.MAX_VALUE){
                            tempVertex.d = minh;
                            activeVertexes.add(tempVertex);
                        }
                    }
                }
            }else {
                if (tempVertex.e != 0)
                    activeVertexes.add(tempVertex);
            }

        }
        long total = (System.nanoTime() - startTime);
        System.out.println("耗时:"+total+"ns");
        return total;
    }

    public long highestLabelPreflowPush(Vertex s,Vertex t){
        long startTime = System.nanoTime();
        int mind;
        int minh;
        boolean existAdmissble;
        Vertex tempVertex;

        preProcess(s);
        while(!activeVertexes.isEmpty()){
            minh = Integer.MAX_VALUE;
//            tempVertex = activeVertexes.remove(0);
            tempVertex = getByHightest();
            existAdmissble = false;

            //不处理s点和t点，回溯到s点的流与到达t点的流不再流动
            if (tempVertex.id == s.id || tempVertex.id == t.id) continue;
            for (Vertex vertex:tempVertex.adjVertex){
                if (vertex!=null){
                    //如果存在admissible边
                    int rest = getRestFlow(tempVertex,vertex);
                    if (tempVertex.d == vertex.d+1 && rest > 0){
                        //找到合适的流大小
                        mind = min(tempVertex.e,rest);
                        //推！
                        push(tempVertex,vertex,mind);
                        //如果入节点更新且不是尾节点则插入到活跃队列末尾
                        if (!isActive(vertex) && vertex.id != t.id)
                            activeVertexes.add(vertex);
                        //存在了admissible边了，更新该变量
                        existAdmissble = true;

                        if (tempVertex.e <= 0) break; //安全保障
                    }
                }
            }
            //如果不存在admissible边的话，需要进行relabel
            if (!existAdmissble){
                for (Vertex vertex:tempVertex.adjVertex){
                    if (vertex!=null){
                        if (vertex.d+1 < minh && getRestFlow(tempVertex,vertex)>0)
                            minh = vertex.d+1;

                        if (minh != Integer.MAX_VALUE){
                            tempVertex.d = minh;
                            activeVertexes.add(tempVertex);
                        }
                    }
                }
            }else {
                if (tempVertex.e != 0)
                    activeVertexes.add(tempVertex);
            }

        }
        long total = (System.nanoTime() - startTime);
        System.out.println("耗时:"+total+"ns");
        return total;
    }


    private Vertex getByFifo(){
        return activeVertexes.remove(0);
    }

    private Vertex getByHightest(){
        int min = 0;
        Vertex v = null;
        for (Vertex vertex:activeVertexes){
            if (vertex.d > min){
                min = vertex.d;
                v = vertex;
            }
        }
        activeVertexes.remove(v);
        return v;
    }

    public void show(){
        int flow = 0;
        PrintStream out = System.out;
        for (Edge edge : edges){
            flow = edge.cap - edge.flow;
            if (flow > 0)
                out.println("经过边"+edge.head.id+"-->"+edge.tail.id+"流量为"+flow);
        }
        flow = 0;
        for (Edge edge : edges){
            if (edge.tail.id == vertices.size()-1){
                flow += edge.cap - edge.flow;
            }
        }
        out.println("最大流为："+flow);
    }
}

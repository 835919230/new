package com.hc.preflow;

import com.hc.fifopreflowpush.MyGraph;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by 83591 on 2016/5/7.
 */
public class Test {

    public static void cout(Object o){
        System.out.println(o);
    }

    public static void test1(){
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"我是第一个");
        map.put(1,"我是第二个");
        map.put(1,"我是第三个");
        cout(map.size());
        cout(map.get(1));
    }

    public static void test2(){
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        Integer i = list.remove(0);
        cout(i);
        cout(list.size());
        cout(list.remove(0));
        cout(list.size());
    }

    public static void test3(){
        Vector<Integer> vector = new Vector<>();
        vector.set(2,1);//Throws Exception
    }

    public static void test4() throws IOException{
        Graph graph1 = new Graph(new File("graphData.txt"));
        cout("最大流是："+graph1.MaxFlow(new Vertex(0),new Vertex(6)));
//        Graph graph2 = new Graph(new File("graphData2.txt"));
//        cout("最大流是："+graph2.MaxFlow(new Vertex(0),new Vertex(5)));

        Graph graph = new Graph(new File("graph1.txt"));
        cout("最大流是："+graph.MaxFlow(new Vertex(0),new Vertex(5)));

        System.out.println();
        MyGraph myGraph = new MyGraph(new File("graph1.txt"));
        List<com.hc.fifopreflowpush.Vertex> list = myGraph.vertices;
        com.hc.fifopreflowpush.Vertex s = null,t = null;
        for (com.hc.fifopreflowpush.Vertex vertex : list){
            if (vertex.id == 0) s = vertex;
            if (vertex.id == list.size()-1) t = vertex;
        }
        myGraph.fifoPreflowPush(s,t);
        myGraph.show();
        System.out.println();
    }

    static void testTime() throws IOException{

        Graph graph = new Graph(new File("graph1.txt"));
        int flow = graph.MaxFlow(new Vertex(0),new Vertex(5));
        for (int i = 0;i < 10;i++);
        cout("最大流是："+flow);
    }

    public static void main(String[] args)throws IOException{
        testTime();
    }
}

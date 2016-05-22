package com.hc.fifopreflowpush;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Created by 83591 on 2016/5/9.
 */
public class Main {
    static PrintStream out = System.out;
    static void fifoTest1() throws IOException{
        out.println("FifoTest1---------------------------------------------");
        long sum = 0;
        MyGraph myGraph=null;
        for (int i = 0;i < 10;i++){
            myGraph = new MyGraph(new File("graph1.txt"));
            List<Vertex> list = myGraph.vertices;
            Vertex s = null,t = null;
            for (Vertex vertex : list){
                if (vertex.id == 0) s = vertex;
                if (vertex.id == list.size()-1) t = vertex;
            }
            if (i != 0)
                sum += myGraph.fifoPreflowPush(s,t);
            else
                myGraph.fifoPreflowPush(s,t);
        }
        sum = sum / 9L;
        myGraph.show();
        out.println("\n平均耗时"+sum+"ns");
    }

    static void fifoTest2() throws IOException{
        out.println("FifoTest2---------------------------------------------");
        long sum = 0;
        MyGraph myGraph = null;
        for (int i = 0;i < 10;i++){
            myGraph = new MyGraph(new File("graphData.txt"));
            List<Vertex> list = myGraph.vertices;
            Vertex s = null,t = null;
            for (Vertex vertex : list){
                if (vertex.id == 0) s = vertex;
                if (vertex.id == list.size()-1) t = vertex;
            }
            myGraph.fifoPreflowPush(s,t);
            if (i != 0)
                sum += myGraph.fifoPreflowPush(s,t);
            else
                myGraph.fifoPreflowPush(s,t);
        }
        sum = sum / 9L;
        myGraph.show();
        out.println("\n平均耗时"+sum+"ns");
    }

    static void highestTest1() throws IOException{
        out.println("highestTest1---------------------------------------------");
        long sum = 0;
        MyGraph myGraph = null;
        for (int i = 0;i < 10;i++){
            myGraph = new MyGraph(new File("graph1.txt"));
            List<Vertex> list = myGraph.vertices;
            Vertex s = null,t = null;
            for (Vertex vertex : list){
                if (vertex.id == 0) s = vertex;
                if (vertex.id == list.size()-1) t = vertex;
            }
            myGraph.highestLabelPreflowPush(s,t);
            if (i != 0)
                sum += myGraph.fifoPreflowPush(s,t);
            else
                myGraph.fifoPreflowPush(s,t);
        }
        sum = sum / 9L;
        myGraph.show();
        out.println("\n平均耗时"+sum+"ns");
    }

    static void highestTest2() throws IOException{
        out.println("highestTest2---------------------------------------------");
        long sum = 0;
        MyGraph myGraph = null;
        for (int i = 0;i < 10;i++){
            myGraph = new MyGraph(new File("graphData.txt"));
            List<Vertex> list = myGraph.vertices;
            Vertex s = null,t = null;
            for (Vertex vertex : list){
                if (vertex.id == 0) s = vertex;
                if (vertex.id == list.size()-1) t = vertex;
            }
            myGraph.highestLabelPreflowPush(s,t);
            if (i != 0)
                sum += myGraph.fifoPreflowPush(s,t);
            else
                myGraph.fifoPreflowPush(s,t);
        }
        sum = sum / 9L;
        myGraph.show();
        out.println("\n平均耗时"+sum+"ns");
    }

    public static void main(String[] args) throws IOException{
        fifoTest1();
        fifoTest2();
        highestTest1();
        highestTest2();
    }
}

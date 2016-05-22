package com.hc.fifopreflowpush;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 83591 on 2016/5/9.
 */
public class Vertex {
    public int id;
    int e;  //流大小
    boolean isUpdated;
    int d;  //高度函数

    List<Vertex> adjVertex; //邻接点
    List<Edge> adjEdges;    //邻接边

    public Vertex(int id){
        this.id = id;
        this.e = 0;
        isUpdated = false;
        adjVertex = new ArrayList<>();
        adjEdges = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vertex){
            Vertex v = (Vertex) obj;
            if (v.id == this.id)
                return true;
            else return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        return prime*this.id + prime;
    }
}

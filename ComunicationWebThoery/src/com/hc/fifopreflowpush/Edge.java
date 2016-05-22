package com.hc.fifopreflowpush;

/**
 * Created by 83591 on 2016/5/9.
 */
public class Edge {
    Vertex head;
    Vertex tail;
    int cap;
    int flow;//残存流量


    public Edge(Vertex u,Vertex v,int c){
        this.head = u;
        this.tail = v;
        this.cap = c;
        this.flow = c;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge){
            Edge edge = (Edge) obj;
            if (edge.head.id == this.head.id&&
                    edge.tail.id == this.tail.id){
                return true;
            }else return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        final int prime = 1007;
        return (prime*this.head.id+this.tail.id)+prime;
    }
}

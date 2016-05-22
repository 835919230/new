package com.hc.preflow;

/**
 * Created by 83591 on 2016/5/7.
 */
public class Edge {
    Vertex head;
    Vertex tail;
    int cap;
    int flow;

    int m;

    public Edge(Vertex u,Vertex v,int c,int f){
        this.head = u;
        this.tail = v;
        this.cap = c;
        this.flow = f;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge){
            Edge edge = (Edge) obj;
            if (edge.head.id == this.head.id&&
                    edge.tail.id == this.tail.id&&
                    edge.cap == this.cap&&
                    edge.flow == this.flow){
                return true;
            }else return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        final int prime = 1007;
        return (prime+this.head.id+this.tail.id+this.cap+this.flow);
    }
}

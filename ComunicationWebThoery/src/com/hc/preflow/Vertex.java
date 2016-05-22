package com.hc.preflow;

/**
 * Created by 83591 on 2016/5/7.
 */
public class Vertex {
    int id;
    public Vertex(int id){
        this.id = id;
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
        return prime*this.id;
    }
}

package com.hc.waxman;

public class Cordinate {
	private long id;
	private int x;
	private int y;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Cordinate() {
	}
	public Cordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	  public boolean equals(final Object obj) {
	    if (this == obj) {
	      return true;
	    }
	    if (obj == null) {
	      return false;
	    }
	    if (this.getClass() != obj.getClass()) {
	      return false;
	    }
	    final Cordinate other = (Cordinate) obj;
	    if (this.x != other.x) {
	      return false;
	    }
	    if (this.y != other.y) {
	      return false;
	    }
	    return true;
	  }
	
	@Override
	public int hashCode() {
		final int prime = 1009;
	    int result = 1;
	    result = prime + (int) (this.id ^ (this.id >>> 32));
	    return result;
	}
}

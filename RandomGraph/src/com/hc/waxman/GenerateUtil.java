package com.hc.waxman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GenerateUtil {
	
	public static final int MAX_WEIGHT = 10;
	public static final int MAX_CAPACITY = 10;
	
	/**
	 * cmp作为排序vertex的规则
	 */
	private static Comparator<Vertex> cmp = new Comparator<Vertex>() {
		
		@Override
		public int compare(final Vertex left,final Vertex right) {
			Cordinate cLeft = left.getCordinate();
			Cordinate cRight = right.getCordinate();
			if(cLeft.getY()<cRight.getY()||
					(cLeft.getY()==cRight.getY()&&cLeft.getX()<cRight.getX()))
			{
				return 1;
			}else if(cLeft.equals(cRight)){
				return 0;
			}else{
				return -1;
			}
		}
	};
	
	/**
	 * 计算两个向量的内积
	 * 为 求凸包
	 *   和 求凸包直径服务
	 * 内积<0才是同一旋转方向
	 * @param prev 前一个节点
	 * @param cur  当前节点
	 * @param next 下一个节点
	 * @return 两个向量内积
	 */
	public static long crossProduct(final Vertex prev,final Vertex cur,final Vertex next){
		int x1 = prev.getCordinate().getX()-cur.getCordinate().getX();
		int x2 = next.getCordinate().getX()-cur.getCordinate().getX();
		
		int y1 = prev.getCordinate().getY()-cur.getCordinate().getY();
		int y2 = next.getCordinate().getY()-cur.getCordinate().getY();
		
		long result = (1L*x1*x2)+(1L*y1*y2);
		return result;
	}
	
	/**
	 * 求两个节点之间的平面距离
	 * @param v1 节点1
	 * @param v2 节点2
	 * @return 两个节点之间的平面距离
	 */
	public static long distance(final Vertex v1,final Vertex v2){
		long deltaX = v1.getCordinate().getX()-v2.getCordinate().getX();
		deltaX*=deltaX;
		
		long deltaY = v1.getCordinate().getY()-v2.getCordinate().getY();
		deltaY*=deltaY;
		return (deltaX+deltaY);
	}
	
	public static long max(final long a,final long b){
		return a>b?a:b;
	}
	
	/**
	 * Graham's Scan法求凸包
	 * 参考百度百科- - 
	 * @param vertexes 所有节点的list
	 * @return 凸包list
	 */
	public static List<Vertex> getConvexHull(final List<Vertex> vertexes){
		final List<Vertex> convexHull = new ArrayList<Vertex>();
		//先根据我们先前定下的排序规则排序
		Collections.sort(vertexes, cmp);
		
		final int size = vertexes.size();
		if(size<3)
			convexHull.addAll(vertexes);
		else{
			/**
			 * 先拿前三个
			 */
			convexHull.add(vertexes.get(0));
			convexHull.add(vertexes.get(1));
			convexHull.add(vertexes.get(2));
			int top = 2;
			//从第四个节点开始循环
			for(int i = 3;i < size;i++){
				while(top>0&&
						crossProduct(convexHull.get(top-1), convexHull.get(top), vertexes.get(i)) >= 0){
					convexHull.remove(convexHull.size()-1);
					top--;
				}
				convexHull.add(vertexes.get(i));
				top++;
			}
		}
		return convexHull;
	}
	
	/**
	 * 旋转卡壳求凸包直径
	 * @param convexHull 凸包集合
	 * @return 凸包直径
	 */
	public static long rotatingCalipers(List<Vertex> convexHull){
		final int size = convexHull.size();
		int j = 1;
		long maxLength = 0;
		for(int i = 0;i < (size-1);i++){
			//模拟旋转
			while(crossProduct(convexHull.get(i+1), convexHull.get(j+1), convexHull.get(i)) >
					crossProduct(convexHull.get(i+1), convexHull.get(j), convexHull.get(i)))
			{
				j = (j+1)%(size-1);
			}
			//更新最大长度
			maxLength = max(maxLength, 
								max(distance(convexHull.get(i), convexHull.get(j)),
										distance(convexHull.get(i+1), convexHull.get(j+1))));
		}
		return maxLength;
	}
	
	public static double getEuclideanMetric(List<Vertex> vertexes){
		return Math.sqrt(rotatingCalipers(getConvexHull(vertexes)));
	}
}

package com.hc.waxman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GenerateUtil {
	
	public static final int MAX_WEIGHT = 10;
	public static final int MAX_CAPACITY = 10;
	
	/**
	 * cmp��Ϊ����vertex�Ĺ���
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
	 * ���������������ڻ�
	 * Ϊ ��͹��
	 *   �� ��͹��ֱ������
	 * �ڻ�<0����ͬһ��ת����
	 * @param prev ǰһ���ڵ�
	 * @param cur  ��ǰ�ڵ�
	 * @param next ��һ���ڵ�
	 * @return ���������ڻ�
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
	 * �������ڵ�֮���ƽ�����
	 * @param v1 �ڵ�1
	 * @param v2 �ڵ�2
	 * @return �����ڵ�֮���ƽ�����
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
	 * Graham's Scan����͹��
	 * �ο��ٶȰٿ�- - 
	 * @param vertexes ���нڵ��list
	 * @return ͹��list
	 */
	public static List<Vertex> getConvexHull(final List<Vertex> vertexes){
		final List<Vertex> convexHull = new ArrayList<Vertex>();
		//�ȸ���������ǰ���µ������������
		Collections.sort(vertexes, cmp);
		
		final int size = vertexes.size();
		if(size<3)
			convexHull.addAll(vertexes);
		else{
			/**
			 * ����ǰ����
			 */
			convexHull.add(vertexes.get(0));
			convexHull.add(vertexes.get(1));
			convexHull.add(vertexes.get(2));
			int top = 2;
			//�ӵ��ĸ��ڵ㿪ʼѭ��
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
	 * ��ת������͹��ֱ��
	 * @param convexHull ͹������
	 * @return ͹��ֱ��
	 */
	public static long rotatingCalipers(List<Vertex> convexHull){
		final int size = convexHull.size();
		int j = 1;
		long maxLength = 0;
		for(int i = 0;i < (size-1);i++){
			//ģ����ת
			while(crossProduct(convexHull.get(i+1), convexHull.get(j+1), convexHull.get(i)) >
					crossProduct(convexHull.get(i+1), convexHull.get(j), convexHull.get(i)))
			{
				j = (j+1)%(size-1);
			}
			//������󳤶�
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

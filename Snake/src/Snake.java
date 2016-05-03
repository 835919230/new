
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

//蛇类
public class Snake{
	//这是蛇身体的集合
	LinkedList<SnakeBody> body = new LinkedList<SnakeBody>();
	//这是方向常量
	static final int UP=0;
	static final int RIGHT=1;
	static final int DOWN=2;
	static final int LEFT=3;
	
	private SnakeBody snakebody;
	private SnakeBody tmp;
	
	public Snake(){
		init();
		//this.body=body;
	}
	
	public void init(){
		int initX = Normal.getWidth()/2;
		int initY = Normal.getHeight()/2;
		for(int i=0;i<3;i++){//初始化三个长度
			body.add(new SnakeBody(initX,initY++,UP));
		}
	}
	//蛇移动的方法
	public void move(){
		System.out.println("蛇当前的位置是："+this.body.getFirst().getX()+","+this.body.getFirst().getY()+"蛇头方向是："+this.body.getFirst().getDirection());
		snakebody = body.getLast();
		
		body.removeLast();
		snakebody.setX(body.getFirst().getX());
		snakebody.setY(body.getFirst().getY());
		snakebody.setDirection(body.getFirst().getDirection());
		
		switch(body.getFirst().getDirection()){
		case UP:
			snakebody.setY(snakebody.getY()-1);
			break;
		case RIGHT:
			snakebody.setX(snakebody.getX()+1);
			break;
		case DOWN:
			snakebody.setY(snakebody.getY()+1);
			break;
		case LEFT:
			snakebody.setX(snakebody.getX()-1);
			break;
		}
		body.addFirst(snakebody);
	}
	
	public void eatFood(){
		System.out.println("蛇吃到了食物！");
		SnakeBody tail = new SnakeBody(this.body.getLast().getX(),this.body.getLast().getY(),this.body.getLast().getDirection());
		switch(body.getLast().getDirection()){
		case UP:
			tail.setY(tail.getY()+1);
			break;
		case RIGHT:
			tail.setX(tail.getX()-1);
			break;
		case DOWN:
			tail.setY(tail.getY()-1);
			break;
		case LEFT:
			tail.setX(tail.getX()+1);
			break;
		}
		body.addLast(tail);
	}
	
	public void drawMe(Graphics g){
		
		g.setColor(Color.BLUE);
		for(int i=0;i<body.size();i++){
			SnakeBody s = body.get(i);
			g.draw3DRect(s.getX()*SnakeBody.getCellwidth(),
						 s.getY()*SnakeBody.getCellwidth(),
						 SnakeBody.getCellwidth(),
						 SnakeBody.getCellwidth(),
						 true);
		}
	}
	
	public boolean isEatBody(){
		SnakeBody head = body.getFirst();
		for(int i=1;i<body.size();i++)
		{
			if(head.getX()==body.get(i).getX()&&head.getY()==body.get(i).getY())
			{
				System.out.println("蛇吃到了身体");
				System.out.println("蛇当前的位置是："+this.body.getFirst().getX()+","+this.body.getFirst().getY());
				return true;
			}
		}
		return false;
	}
	
	public boolean isOverBand()
	{
		SnakeBody head = body.getFirst();
		if(head.getX()>Normal.getWidth()||head.getX()<0||head.getY()>Normal.getHeight()||head.getY()<0){
			System.out.println("蛇越界了！");
			System.out.println("蛇当前的位置是："+this.body.getFirst().getX()+","+this.body.getFirst().getY());
			return true;
		}
		return false;
	}
	
}
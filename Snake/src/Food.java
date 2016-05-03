
import java.awt.Color;
import java.awt.Graphics;

public class Food {
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

	public Food(){
		int newX = (int) (Math.random()*Normal.getWidth());
		int newY = (int) (Math.random()*Normal.getHeight());
		this.x = newX;
		this.y = newY;
		System.out.println("�µ�ʳ���ѳ�ʼ����λ���ǣ�"+this.x+","+this.y);
	}
	
	public Food(int x,int y)
	{
		this.x=x;
		this.y=y;
		System.out.println("ʳ���ѳ�ʼ����λ���ǣ�"+this.x+","+this.y);
	}
	
	public void drawMe(Graphics g){
		g.setColor(Color.RED);
		g.draw3DRect(this.x*SnakeBody.getCellwidth(), this.y*SnakeBody.getCellwidth(), SnakeBody.getCellwidth(), SnakeBody.getCellwidth(), true);	
	}
	/*������
	public void show(){
		System.out.println(this.x+" "+this.y);
	}
	public static void main(String[] args) {
		Food f = new Food();
		f.show();
	}
	*/
}

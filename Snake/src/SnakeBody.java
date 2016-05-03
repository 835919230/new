//…ﬂ…ÌÃÂ¿‡
public class SnakeBody {
	private int x;
	private int y;
	private static final int CELLWIDTH=10;
	private static final int CELLHEIGHT=10;
	private int direction;
	
	
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

	public SnakeBody(int x,int y,int direction){
		this.x=x;
		this.y=y;
		this.direction=direction;
	}

	public static int getCellwidth() {
		return CELLWIDTH;
	}

	public static int getCellheight() {
		return CELLHEIGHT;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
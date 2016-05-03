//逻辑类
public class Controller implements Runnable{
    private Snake snake;
	private Food food;
	private boolean isGameOver;
	
	public Snake getSnake() {
		return snake;
	}

	public Food getFood() {
		return food;
	}

	public Controller(Snake snake,Food food,boolean isOver) {
		this.snake = snake;
		this.food = food;
		this.isGameOver = isOver;
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!this.isGameOver){
				snake.move();
				if(snake.body.getFirst().getX()==food.getX()&&snake.body.getFirst().getY()==food.getY()){
					snake.eatFood();
					this.food = new Food();
				}
			}
			if(snake.isEatBody()||snake.isOverBand()){
				this.setGameOver(true);
				break;
			}
		}
	}
}
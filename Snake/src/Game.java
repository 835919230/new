
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame implements KeyListener{
	private Snake snake;
	private Food food;
	private Panel panel;
	private Controller player;
	public Game(){
		snake= new Snake();
		food = new Food(10,10);
		player = new Controller(snake, food, false);
		panel = new Panel(player);
		Thread tPanel = new Thread(panel);
		tPanel.start();
		this.setTitle("我的贪吃蛇游戏");
		this.add(panel);
		this.setLocation(400,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(330,355);
		this.addKeyListener(this);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game g =new Game();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			if(player.getSnake().body.getFirst().getDirection()!=2)
			{
				player.getSnake().body.getFirst().setDirection(0);
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(player.getSnake().body.getFirst().getDirection()!=3){
				player.getSnake().body.getFirst().setDirection(1);
			}
			break;
		case KeyEvent.VK_DOWN:
			if(player.getSnake().body.getFirst().getDirection()!=0){
				player.getSnake().body.getFirst().setDirection(2);
			}
			break;
		case KeyEvent.VK_LEFT:
			if(player.getSnake().body.getFirst().getDirection()!=1){
				player.getSnake().body.getFirst().setDirection(3);
			}
			break;
		}
		//this.repaint();
		//System.out.println(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
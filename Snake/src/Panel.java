
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable{
	private Controller player;
	
	public Panel(Controller player){
		this.player=player;
		Thread t =new Thread(player);
		t.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 330, 360);
		g.setColor(Color.blue);
		player.getSnake().drawMe(g);
		g.setColor(Color.red);
		player.getFood().drawMe(g);
	}
	@Override
	public void run() {
		while(true){
			this.repaint();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
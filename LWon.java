/* Author: Andy Sun
 * Date: December 17
 * Luigi Won Screen of Pong
 */

import java.awt.*;
import java.awt.event.KeyEvent;

public class LWon {
	public LWon(){	
	}
	
	// draw the screen
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.setFont(new Font("Consolas", Font.PLAIN, 40));
		g.drawString("Luigi Won!", 200, 130);
		
		g.setFont(new Font("Consolas", Font.PLAIN, 20));
		g.setColor(Color.red);
		g.drawString("Tips: -The shell will gain some of your velocity", 30, 230);
		g.drawString("      -Standing still will increase the shell speed", 30, 260);
		
		g.setFont(new Font("Consolas", Font.PLAIN, 18));
		g.setColor(Color.white);
		g.drawString("> Hit Y to play again", 170, 350);
		
		
	}
	
	// if player pressed y, return true to GamePanel (which restarts game)
	public Boolean keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'y' || e.getKeyChar() == 'y') {
			return true;
		}
		return false;
	}
	
	// if player released y, return true to GamePanel (which restarts game)
	public Boolean keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'y' || e.getKeyChar() == 'y') {
			return true;
		}
		return false;
	}
	
}

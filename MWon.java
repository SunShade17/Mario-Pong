/* Author: Andy Sun
 * Date: December 17
 * Mario Won Screen of Pong
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class MWon {
	public MWon() {
		
	}
	
	// draws text to screen
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Consolas", Font.PLAIN, 40));
		g.drawString("Mario Won!", 200, 130);
		
		g.setFont(new Font("Consolas", Font.PLAIN, 20));
		g.setColor(Color.green);
		g.drawString("Tips: -The shell will gain some of your velocity", 30, 230);
		g.drawString("      -Standing still will increase the shell speed", 30, 260);
		
		g.setFont(new Font("Consolas", Font.PLAIN, 18));
		g.setColor(Color.white);
		g.drawString("> Hit Y to play again", 170, 350);

		
		
	}
	
	// returns true to GamePanel if y is pressed
	public Boolean keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'y' || e.getKeyChar() == 'Y') {
			return true;
		}
		return false;
	}
	
	// returns true to GamePanel if y is released
	public Boolean keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'y' || e.getKeyChar() == 'Y') {
			return true;
		}
		return false;
	}
	
	
}

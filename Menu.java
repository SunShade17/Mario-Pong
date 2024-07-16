/* Author: Andy Sun
 * Date: December 17
 * Menu Screen of Pong
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Menu {
	public Menu() {
	}
	
	// draws everything to screen
	public void draw(Graphics g) {

		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.PLAIN, 40));
		g.drawString("Welcome to", 200, 130);
		g.setColor(Color.red);
		g.drawString("Mario", 20, 180);
		g.setColor(Color.white);
		g.drawString("vs", 140, 180);
		g.setColor(Color.green);
		g.drawString("Luigi", 195, 180);
		g.setColor(Color.white);
		g.drawString("Festive Pong!", 325, 180);
		
		g.setFont(new Font("Consolas", Font.PLAIN, 30));
		g.setColor(Color.cyan);
		g.drawString("First to 5 wins", 180, 280);
		
		g.setFont(new Font("Consolas", Font.PLAIN, 18));
		g.setColor(Color.white);
		g.drawString("> Hit space to begin", 210, 310);
		g.drawString("Turn down volume", 230, 400);
		g.drawString("Made by Andy Sun", 230, 450);
		
		
	}
	
	// returns true to GamePanel if space is pressed (allowing game to start)
	public Boolean keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 32 || e.getKeyCode() == 73) {
			return true;
		}
		return false;
	}
	
	// returns true to GamePanel if space is released (allowing game to start)
	public Boolean keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 32 || e.getKeyCode() == 73) {
			return true;
		}
		return false;
	}
}



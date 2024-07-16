/* Author: Andy Sun
 * Date: December 17
 * Paddle class (left paddle) of a Pong game
 */

import java.awt.*; //needed for Color
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Paddle extends Rectangle {

	// constants
	static final int PADDLE_WIDTH = 15;
	static final int PADDLE_HEIGHT = 45;
	public final int SPEED = 5;
	private final Image IMAGE;

	// global variables
	public int yVelocity;
	public char edge;

	// constructor calls rectangle construtor 
	public Paddle(int x, int y) {
		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		IMAGE = new ImageIcon("mario.png").getImage();
	}

	// called from GamePanel when any keyboard input is detected
	// updates paddle direction, and does nothing if one of the keys is not pressed
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
			setYDirection(SPEED * -1);
			move();
		}

		if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
			setYDirection(SPEED);
			move();
		}
	}

	// called from GamePanel when any key is released
	// makes the paddle stop moving
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
			setYDirection(0);
			move();
		}

		if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
			setYDirection(0);
			move();
		}
	}

	// called when the movement of the ball changes in the y-direction
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}

	// moves the paddle up or down by its velocity
	public void move() {
		y = y + yVelocity;
	}

	// checks collision with the ball
	public boolean checkCollision(EnemyBall b) {
		// variables
		int rightX = x + PADDLE_WIDTH;
		int bottomY = y + PADDLE_HEIGHT;

		// detect which edge was hit, storing it in a char
		if (b.x <= rightX && b.x >= rightX - Math.abs(b.cx)) { // right edge
			if (b.y + EnemyBall.SIZE >= y && b.y <= bottomY) {
				edge = 'r';
			}
		} else if (b.y + EnemyBall.SIZE >= y && b.y + EnemyBall.SIZE <= y + EnemyBall.MAX_SPEED) { // top edge
			if (b.x >= 0 - EnemyBall.SIZE && b.x <= rightX) {
				edge = 't';
			}
		} else if (b.y <= bottomY && b.y >= bottomY - Math.abs(b.cx)) { // bottom edge
			if (b.x >= 0 - EnemyBall.SIZE && b.x <= rightX) {
				edge = 'b';
			}
		}

		// check if the ball is between the x values
		if (b.x >= (x - EnemyBall.SIZE) && b.x <= rightX) {
			// check if ball is between the y values
			if (b.y + EnemyBall.SIZE >= y && b.y <= bottomY) {
				return true;
			}
		}

		return false; // ball has not collided

	}

	// draws the paddle to screen (a festive Mario)
	public void draw(Graphics g) {

		g.drawImage(IMAGE, x, y, null);
	}

}
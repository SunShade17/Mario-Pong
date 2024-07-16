/* Author: Andy Sun
 * Date: December 17
 * EnemyBall class of a Pong game
 */
import java.awt.*;
import javax.swing.ImageIcon;

public class EnemyBall extends Rectangle {

	// global variables / constants
	public int x, y, cx, cy;
	static final int MAX_SPEED = 7;
	public static final int SIZE = 20;
	private final Image IMAGE; // shell picture

	// ball constructor assigns values to instance variables
	public EnemyBall(int x, int y) {
		super(x, y, SIZE, SIZE);
		this.x = GamePanel.GAME_WIDTH / 2;
		this.y = GamePanel.GAME_HEIGHT / 2;
		cx = -2;
		cy = 0;
		IMAGE = new ImageIcon("shell.png").getImage();
	}

	// moves the ball according to its x and y velocity
	public void move() {
		y = y + cy;
		x = x + cx;
	}

	// called to make the ball bounce off the top & bottom edge
	public void bounceOffEdges(int top, int bottom) {

		// if the y value is at the bottom of the screen
		if (y >= bottom - SIZE) {
			if (y > bottom - SIZE) { // prevent ball from phasing into wall
				y = bottom - SIZE;
			}
			reverseY(); // bounce
		}
		// if y value is at top of screen
		else if (y <= top) {
			if (y < top) { // prevent ball from phasing
				y = top;
			}
			reverseY(); // bounce
		}
	}

	// reverse the ball's change in x value
	public void reverseX() {
		cx *= -1;
		// prevent ball from going over the max speed
		if (cy < MAX_SPEED * -1) {
			cy = MAX_SPEED * -1;
		} else if (cy > MAX_SPEED) {
			cy = MAX_SPEED;
		}
		if (cx < MAX_SPEED * -1) {
			cx = MAX_SPEED * -1;
		} else if (cx > MAX_SPEED) {
			cx = MAX_SPEED;
		}

	}

	// reverse the ball's change in y value
	public void reverseY() {
		cy *= -1;
		// prevent ball from going over the max speed
		if (cy < MAX_SPEED * -1) {
			cy = MAX_SPEED * -1;
		} else if (cy > MAX_SPEED) {
			cy = MAX_SPEED;
		}
		if (cx < MAX_SPEED * -1) {
			cx = MAX_SPEED * -1;
		} else if (cx > MAX_SPEED) {
			cx = MAX_SPEED;
		}
	}

	// introduce a slight margin of error to the y velocity
	public void randomMargin() {
		cy += Math.pow(-1, (int) (Math.random() * 2 + 1));
	}

	// draws the ball to screen (an annoying red shell)
	public void draw(Graphics g) {
		g.drawImage(IMAGE, x, y, null);
	}
}
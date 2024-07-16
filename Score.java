/* Author: Andy Sun
 * Date: December 17
 * Score class of a Pong game
 */
import java.awt.*;
import javax.swing.ImageIcon;

public class Score extends Rectangle {
	
	// global variables and constants
	public static int GAME_WIDTH;// width of the window
	public static int GAME_HEIGHT;// height of the window
	public static int score1, score2;
	private final Image IMAGE;

	// constructor sets scores to 0, establishes dimensions of window, and initializes background image
	public Score(int w, int h) {
		score1 = 0;
		score2 = 0;
		Score.GAME_WIDTH = w;
		Score.GAME_HEIGHT = h;
		IMAGE = new ImageIcon("background.jpeg").getImage();
	}

	// updates current score and draws to screen
	public void draw(Graphics g) {
		g.drawImage(IMAGE, 0, 0, null); // background image
		g.setColor(Color.white); 
		g.setFont(new Font("Consolas", Font.PLAIN, 30));
		// sets locations of scores approximately equal
		g.drawString("Mario: " + String.valueOf(score1), (int) (GAME_WIDTH * 0.1), (int) (GAME_HEIGHT * 0.2)); 
		g.drawString("Luigi: " + String.valueOf(score2), (int) (GAME_WIDTH * 0.65), (int) (GAME_HEIGHT * 0.2)); 
		g.fillRect(GAME_WIDTH / 2 - 2, 0, 4, GAME_HEIGHT); // middle line
	}
}
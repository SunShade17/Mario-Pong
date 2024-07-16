/* Author: Andy Sun
 * Date: December 17
 * Paddle class (right paddle) of a Pong game
 */

import java.awt.*; //needed for Color
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Paddle2 extends Rectangle{

    
	public int yVelocity;
	
    //constants
    static final int PADDLE_WIDTH = 15;
    static final int PADDLE_HEIGHT = 45;
    public final int SPEED = 5;
    public char edge;
    private final Image IMAGE;

    // constructor calls rectangle constructor
    public Paddle2(int x, int y) {
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
        IMAGE = new ImageIcon("luigi.png").getImage();
    }
    
 // called from GamePanel when any keyboard input is detected
 	// updates paddle direction, and does nothing if one of the keys is not pressed
 	public void keyPressed(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_UP){
        setYDirection(SPEED*-1);
        move();
      }

      if(e.getKeyCode() == KeyEvent.VK_DOWN){
        setYDirection(SPEED);
        move();
      }
    }

 	// called from GamePanel when any key is released
 	// makes the paddle stop moving
 	 public void keyReleased(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_UP){
        setYDirection(0);
        move();
      }

      if(e.getKeyCode() == KeyEvent.VK_DOWN){
        setYDirection(0);
        move();
      }
    }
    
 	// called when the movement of the ball changes in the y-direction
 	public void setYDirection(int yDirection){
      yVelocity = yDirection;
    }
    
 	// moves the paddle up or down
    public void move() {
    	y = y + yVelocity;
    }
    
    // checks collision with the ball
    public boolean checkCollision(EnemyBall b){
    	int ballRight = b.x + EnemyBall.SIZE;
        int bottomY = y + height;
        
        // detect which edge was hit, storing it in a char
        if(ballRight >= x && ballRight <= x + Math.abs(b.cx)) { // left edge
        	if(b.y + EnemyBall.SIZE >= y && b.y <= bottomY){
                edge = 'l';
            }
        } else if(b.y + EnemyBall.SIZE >= y && b.y + EnemyBall.SIZE <= y + EnemyBall.MAX_SPEED) {
        	if(b.x <= x + PADDLE_WIDTH + EnemyBall.SIZE && ballRight >= x){
        		edge = 't';
        	}
        } else if(b.y <= bottomY && b.y >= bottomY - Math.abs(b.cx)) { // bottom edge
        	if(b.x <= x + PADDLE_WIDTH + EnemyBall.SIZE && ballRight >= x){
        		edge = 'b';
        	}
        }

        //check if the Ball is between the x values
        if(ballRight >= x && b.x <= x + PADDLE_WIDTH){
            //check if Ball is between the y values
            if(b.y + EnemyBall.SIZE >= y && b.y <= bottomY){
                return true;
            }
        }

        return false; // no collision

    }
    
 // draws the paddle to screen (a luigi bearing a gift)
    public void draw(Graphics g){
    	g.drawImage(IMAGE,  x, y, null);
    }
    
    
}
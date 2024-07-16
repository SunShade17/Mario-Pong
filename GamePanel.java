/* Author: Andy Sun
 * Date: December 17
 * GamePanel class of a Pong game
 * Note: the comments use "mario/luigi" and "player" interchangeable; mario is the left paddle/player 1, luigi is the right paddle/player 2
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	// dimensions of window
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;

	// declare objects
	public Thread gameThread;
	public Image image;
	public Graphics graphics;
	public Paddle player;
	public Paddle2 player2;
	public EnemyBall ball;
	public Score score;
	public Menu menu = new Menu();
	public MWon mWon = new MWon(); // mario won screen
	public LWon lWon = new LWon(); // luigi won screen
	public boolean space = false; // check if space was pressed to begin
	public boolean playAgain = true; // play again boolean
	public boolean winM, winL = false; // boolean to see if mario or luigi won
	// load all the sound effect files
	public File reset = new File("here we go.wav"), intro = new File("intro.wav"), marioW = new File("mario won.wav"),
			marioH = new File("mario hurt.wav"), luigiH = new File("luigi hurt.wav"), luigiW = new File("luigi won.wav"),
			marioHit = new File("mario hit.wav"), luigiHit = new File("luigi hit.wav");
	
	// constructor class; assign values to declared objects & add MouseListener & Threads
	public GamePanel() {
		player = new Paddle(0, GAME_HEIGHT / 2);
		player2 = new Paddle2(GAME_WIDTH - Paddle2.PADDLE_WIDTH, GAME_HEIGHT / 2);
		ball = new EnemyBall(GAME_WIDTH / 2, GAME_HEIGHT / 2);
		score = new Score(GAME_WIDTH, GAME_HEIGHT); // start counting the score
		this.setFocusable(true); // make everything in this class appear on the screen
		this.addKeyListener(this); // start listening for keyboard input

		// add the MousePressed method from the MouseAdapter to listen for mouse input
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// ball.mousePressed(e);
			}
		});
		this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

		// make this class run at the same time as other classes 
		gameThread = new Thread(this);
		gameThread.start();
		try {
			playSound(intro);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		}
	}

	// overrides paint from java.awt library
	public void paint(Graphics g) {
		// uses "double buffering" to paint image
		image = createImage(GAME_WIDTH, GAME_HEIGHT); // draw off screen
		graphics = image.getGraphics();
		draw(graphics); // update the positions of everything on the screen
		g.drawImage(image, 0, 0, this); // redraw everything on the screen

	}

	// call the draw methods in each class to update positions
	public void draw(Graphics g) {
		if (!space) { // space is not pressed at start of game
			menu.draw(g);
			playAgain = true;
		} else if (winM) { // mario won screen
			mWon.draw(g);
		} else if (winL) { // luigi won screen
			lWon.draw(g);
		} else if (playAgain) { // neither player won
			score.draw(g);
			ball.draw(g);
			player.draw(g);
			player2.draw(g);
		} 
	}

	// call the move methods in other classes to update positions; called constantly from run()
	public void move() {
		ball.move();
		player.move();
		if (player.edge == 't' && player.checkCollision(ball)) { // dont allow paddle to squish ball into ceiling
			if (player.y < EnemyBall.SIZE)
				player.y = EnemyBall.SIZE + 1;
		} else if (player.edge == 'b' && player.checkCollision(ball)) // don't allow paddle to squish ball into floor
			if (player.y + Paddle.PADDLE_HEIGHT > GAME_HEIGHT - EnemyBall.SIZE)
				player.y = GAME_HEIGHT - EnemyBall.SIZE - Paddle.PADDLE_HEIGHT - 1;
		player2.move();
		if (player2.edge == 't' && player.checkCollision(ball)) { // dont allow paddle to squish ball into ceiling
			if (player2.y < EnemyBall.SIZE)
				player2.y = EnemyBall.SIZE + 1;
		} else if (player2.edge == 'b' && player.checkCollision(ball)) // don't allow paddle to squish ball into floor
			if (player2.y + Paddle.PADDLE_HEIGHT > GAME_HEIGHT - EnemyBall.SIZE)
				player2.y = GAME_HEIGHT - EnemyBall.SIZE - Paddle.PADDLE_HEIGHT - 1;
	}

	// handles all collision detection and responds accordingly
	public void checkCollision() {

		ball.bounceOffEdges(0, GAME_HEIGHT); // bounces ball from edges

		// checks collision with left paddle
		if (player.checkCollision(ball)) {
			if (player.edge == 'r') { // ball hit right side of paddle
				if (ball.cx < 0) { // only bounce if the ball was traveling towards the paddle
					ball.reverseX();
					ball.reverseY();
					ball.randomMargin(); // margin of error in bounce
					try {
						playSound(marioHit); // play sound
					} catch (Exception e) {
						e.printStackTrace();
					} 
					if (player.yVelocity == 0) { // if player standing still, make angle a bit more flat & faster
						if (ball.cy > 0) {
							ball.cy--;
						} else
							ball.cy++;
						ball.cx++;
					} else { // if paddle is moving
						if (ball.cx > 3) { // slow down ball x speed
							ball.cx--;
						}
						ball.cy += player.yVelocity / 2; // add y paddle speed to ball
					}
				}
			} else if (player.edge == 't') { // top edge
				if (ball.cx < 0) { // avoid bouncing back and forth
					if (ball.cy > 0)
						ball.reverseY(); // only reverse Y direction if ball was traveling in the opposite direction of
											// paddle
					ball.cy -= Math.abs(player.yVelocity); // add player paddle velocity
					ball.reverseX(); 
					try { // sound effect
						playSound(marioHit);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			} else if (player.edge == 'b') { // bottom edge
				if (ball.cx < 0) { // only bounce if ball was moving up
					if (ball.cy < 0)
						ball.reverseY();
					ball.cy += Math.abs(player.yVelocity); // adds player paddle velocity
					ball.reverseX(); 
					try {
						playSound(marioHit); // play sound
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		// same collision check process for the right paddle
		else if (player2.checkCollision(ball)) { 
			if (player2.edge == 'l') { // left edge of paddle
				if (ball.cx > 0) {
					ball.reverseX();
					ball.reverseY();
					ball.randomMargin();
					try {
						playSound(luigiHit); // play sound
					} catch (Exception e) {
						e.printStackTrace();
					} 
					if (player2.yVelocity == 0) { // if player standing still, make angle a bit more flat & faster
						if (ball.cy > 0) {
							ball.cy--;
						} else
							ball.cy++;
						ball.cx--;
					} else { // add paddle speed to ball
						ball.cy += player2.yVelocity / 2;
						if (ball.cx < -3) {
							ball.cx++;
						}
					}
				}
			} else if (player2.edge == 't') { // top edge
				if (ball.cx > 0) {
					if (ball.cy > 0)
						ball.reverseY();
					ball.cy -= Math.abs(player2.yVelocity);
					ball.reverseX(); // ball can only bounce left
					try {
						playSound(luigiHit);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			} else if (player2.edge == 'b') { // bottom edge
				if (ball.cx > 0) {
					if (ball.cy < 0)
						ball.reverseY();
					ball.cy += Math.abs(player2.yVelocity);
					ball.reverseX();
					try {
						playSound(luigiHit);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
		
		// force players to remain on screen
		if (player.y <= 0) {
			player.y = 0;
		}
		if (player.y >= GAME_HEIGHT - Paddle.PADDLE_HEIGHT) {
			player.y = GAME_HEIGHT - Paddle.PADDLE_HEIGHT;
		}
		if (player2.y <= 0) {
			player2.y = 0;
		}
		if (player2.y >= GAME_HEIGHT - Paddle2.PADDLE_HEIGHT) {
			player2.y = GAME_HEIGHT - Paddle2.PADDLE_HEIGHT;
		}
		
		// if either player scored a point, reset
		// if a player won (score >= 5), direct to corresponding win screen
		if (ball.x > GAME_WIDTH) { // mario scored a point
			Score.score1++;
			try {
				playSound(luigiH); // luigi hurt sound effect
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			}
			reset(-1);
			if(Score.score1 >= 5) { // mario won
				winM = true;
				playAgain = false;
				try { // sound effect
					playSound(marioW);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			
		}
		else if (ball.x + EnemyBall.SIZE < 0) { // luigi scored a point
			Score.score2++;
			try {
				playSound(marioH); // mario hurt sound effect
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			}
			reset(1);
			if(Score.score2 >= 5) { // luigi won
				winL = true; 
				playAgain = false;
				try {
					playSound(luigiW); // sound effect
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}

	}

	// resets the positions after each point
	public void reset(int i) {
		ball.x = GAME_WIDTH / 2; // ball back in center
		ball.y = GAME_HEIGHT / 2;
		ball.cy = (int) (Math.random() * 2 + 1) * (int) (Math.pow(-1, (int) (Math.random() * 2 + 1))); // random y direction
		if (i == 1) { // player 1 won; loser gets the ball
			ball.cx = -2;
		} else { // player 2 won
			ball.cx = 2;
		}
	}

	// plays sound effects
	public static void playSound(File f) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		// object declarations
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start(); // play sound effect
	}

	// the method that makes the game continue running without end, calling other methods 
	public void run() {
		// slows down CPU
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long now;

		while (true) { // infinite game loop
			now = System.nanoTime();
			delta = delta + (now - lastTime) / ns;
			lastTime = now;

			// only move objects around and update screen if enough time has passed
			if (delta >= 1) {
				// only moves / checks collision under specific scenarios to not move it in the background
				if (space && playAgain && !winM && !winL) { 
					move();
					checkCollision();
				}
				repaint();
				delta--;
			}
		}
	}

	// if a key is pressed, send it to respective classes to process
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
		player2.keyPressed(e);
		if (space == false) // prevent presses after starting
			if (menu.keyPressed(e)) {
				space = true; // allow game to start
				try { // play sound effect
					playSound(reset);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
			}
		if (winM == true || winL == true) { // prevent presses from registering outside end screen
			if (mWon.keyPressed(e) || lWon.keyPressed(e)) {
				// reset variables
				playAgain = true;
				winM = false;
				winL = false;
				Score.score1 = 0;
				Score.score2 = 0;
				try { // play sound effect
					playSound(reset);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	// if a key is released, send it to respective classes for processing
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
		player2.keyReleased(e);
		if (space == false) // prevent releases after starting
			if (menu.keyReleased(e)) {
				space = true;
				try { // play sound effect
					playSound(reset);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
			}
		if (winM == true || winL == true) { // prevent releases from registering outside end screen
			if (mWon.keyReleased(e) || lWon.keyReleased(e)) {
				playAgain = true;
				winM = false;
				winL = false;
				Score.score1 = 0;
				Score.score2 = 0;
				try {
					playSound(reset);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	// overrides keyTyped out of necessity
	public void keyTyped(KeyEvent e) {

	}

}
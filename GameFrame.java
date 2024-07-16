/* Author: Andy Sun
 * Date: December 17
 * GameFrame class of a Pong game
 */

import java.awt.Color;

import javax.swing.*;

public class GameFrame extends JFrame{

  GamePanel panel; // initialize gamepanel

  public GameFrame(){
    
	panel = new GamePanel(); //run GamePanel constructor
    this.add(panel);
    this.setTitle("Mario Pong by Andy Sun"); //set title for frame
    this.setBackground(Color.black);
    this.setResizable(false); //frame can't change size
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
    this.pack();//makes components fit in window - don't need to set JFrame size, as it will adjust accordingly
    this.setVisible(true); //makes window visible to user
    this.setLocationRelativeTo(null);//set window in middle of screen
  }
  
}
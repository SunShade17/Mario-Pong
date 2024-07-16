/* Author: Andy Sun
 * Date: December 17
 * Main class of a Pong game
 */
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

class Main {
  public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	 new GameFrame(); // runs GameFrame constructor
    
    File sound = new File("music.wav"); // plays background music
    AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
    Clip clip = AudioSystem.getClip();
    FloatControl gainControl;
    clip.open(audioIn);
    gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(-30.0f); // lowers volume
    clip.start();
    clip.loop(Clip.LOOP_CONTINUOUSLY); // loops
    
    
  }
}
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * handles our keyboard input
 * @author beshoi
 *
 */
//this class is a subclass of keyadapter
public class KeyInput extends KeyAdapter {
	
	Game game;
	
	public KeyInput (Game game){
		this.game = game;
	}
	
	//over ride super class
	public void keyPressed (KeyEvent e){
		game.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e){
		game.keyReleased(e);
	}

}

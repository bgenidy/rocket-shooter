import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MouseInput implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		/*
		 * 	public Rectangle playButton = new Rectangle(Game.Width/ 2 + 120, 150, 100, 50);
	public Rectangle helpButton = new Rectangle(Game.Width/ 2 + 120, 250, 100, 50);
	public Rectangle quitButton = new Rectangle(Game.Width/ 2 + 120, 350, 100, 50);
		 */
		
		//play button
		if (mx >= Game.Width/ 2 + 120 && mx <= Game.Width/2 + 220){
			if(my >= 150 && my <= 200){
				//pressing the play button
				Game.State = Game.STATE.GAME;
			}
		}
		
		
		//quit button
		if (mx >= Game.Width/ 2 + 120 && mx <= Game.Width/2 + 220){
			if(my >= 350 && my <= 400){
				//pressing the quit button
				System.exit(1);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

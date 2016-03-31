import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.classes.EntityA;

/**
 * this class is also implementing the 4 methods inside entity 
 * inside there so the methods remain the same
 * @author beshoi
 *
 */
//this class contains the bullet for rocket shooter
public class Bullet extends GameObject implements EntityA{
	
	private Textures tex;
	private Game game;
	
	Animation anim;
	
	//main constructor
	public Bullet(double x, double y, Textures tex, Game game){
		super(x, y);
		this.tex = tex;
		this.game = game;
		
		//animation for the bullet
		anim = new Animation(5, tex.missile[0], tex.missile[1], tex.missile[2]);
	}
	
	public void tick(){
		y-=10;
		
		
		anim.runAnimation();
	}
	
	public void render (Graphics g){
		anim.drawAnimation(g, x, y, 0);
	}
	
	public Rectangle getBounds(){
		return new Rectangle ((int)x, (int)y, 32, 32);
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}

}

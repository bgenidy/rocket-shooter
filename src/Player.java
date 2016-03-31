import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.classes.EntityA;
//http://www.youtube.com/watch?v=FjTDgspqIBo&list=PLWms45O3n--6KCNAEETGiVTEFvnqA7qCi&index=10
import com.game.src.main.classes.EntityB;

/**
 * this class will conatin my space ship player
 * and is a subclass of GameObject
 * @author beshoi
 *
 */
//entityA is friendly fire doesn't cloide with other A's
public class Player extends GameObject implements EntityA{
	
	
	private double velX = 0;
	private double velY = 0;
	
	
	private Textures tex;
	
	Game game;
	Controller controller;
	Animation anim;
	//constructor
	public Player (double x, double y, Textures tex, Game game, Controller controller){
		
		super(x,y);
		this.tex = tex;
		this.game =game;
		this.controller = controller;
		anim = new Animation(5, tex.player[0], tex.player[1], tex.player[2]);
	}
	
	public void tick(){
		x+= velX;
		y+= velY;
		
		if (x <= 0)
			x = 0;
		if (x >= 622)
			x = 622;
		if (y <= 0)
			y = 0;
		if (y >= 448)
			y = 448;
		
		for (int i = 0; i < game.eb.size(); i++){
			EntityB tempEnt = game.eb.get(i);
			
			if (Physics.Collision(this, tempEnt)){
				controller.removeEntity(tempEnt);
				Game.HEALTH -= 10;
				game.setEnemy_killed(game.getEnemy_killed() +1);
			}
		}
		anim.runAnimation();
	}
	
	public Rectangle getBounds(){
		return new Rectangle ((int)x, (int)y, 32, 32);
	}
	
	public void render(Graphics g){
		anim.drawAnimation(g, x, y, 0);
		
	}
	

	
	//accessor
	public double getX(){
		return x;
	}
	
	public  double getY(){
		return y;
	}
	
	//mutator
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	/**
	 * the two velocities are being created for simpler
	 * keyboard movement
	 * @param velX
	 */
	public void setVelX(double velX){
		this.velX = velX;
	}
	
	public void setVelY(double velY){
		this.velY = velY;
	}

}

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

//entityB is enemy collides with A but not any other B's
public class Enemy extends GameObject implements EntityB{

	Random r = new Random();
	private Textures tex;
	private Game game;
	private Controller c;
	
	private int speed = r.nextInt(3)+ 1; //this allows for the player to come down at different speeds
	
	Animation anim;
	public Enemy(double x, double y, Textures tex, Controller c, Game game){
		super(x,y);
		this.tex = tex;
		this.c = c;
		this.game = game;
		
		anim = new Animation(5, tex.enemy[0], tex.enemy[1], tex.enemy[2]);
	}
	
	public void tick(){
		y+= speed;
		
		//resets the allien space ship to come back up to the top after going all the way down
		if (y > (Game.Height * Game.Scale)){
			speed = r.nextInt(3)+ 1; //changes the speed every time it spawns back up again
			y = 0;
		
			x = r.nextInt(Game.Width * Game.Scale); //ok what that does is it creates a new random position when the alien ship reaches the bottom
		}
		
		for (int i = 0; i < game.ea.size(); i++){
			EntityA tempEnt = game.ea.get(i);
			
			//remvoes instant if they collide
			if (Physics.Collision(this,  tempEnt)){
				c.removeEntity(tempEnt);
				c.removeEntity(this);
				game.setEnemy_killed(game.getEnemy_killed() + 1);//add one to the enemies killed after each kill
				
			}
		}
		
		
		anim.runAnimation();
	}
	
	public void render(Graphics g){
		anim.drawAnimation(g, x, y, 0);
	}
	
	//bounds for the collision 
	public Rectangle getBounds(){
		return new Rectangle ((int)x, (int)y, 32, 32);
	}
	
	public double getY(){
		return y;
	}
	
	public void setY(double y){
		this.y = y;
	}

	
	public double getX() {
		return x;
	}

}

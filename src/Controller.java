import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.text.html.parser.Entity;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

/*
 * this class will use the entity interface to make a cleaner sleaker
 * and less code requirement to run our rocket shooter game
 */
public class Controller {
	
	//friendly fire
	private LinkedList<EntityA> ea = new LinkedList<EntityA>(); //array of entities which makes it allot cleaner
	
	//enemy fire
	private LinkedList<EntityB> eb = new LinkedList<EntityB>(); //array of entities which makes it allot cleaner
	
	Random r = new Random();
	private Game game;
	

	EntityA enta;
	EntityB entb;
	
	Textures tex;
	
	public Controller (Textures tex, Game game){
		this.tex = tex;
		this.game = game;
	}
	
	public void createEnemy(int enemy_count){
		for (int i = 0; i < enemy_count; i++){
			addEntity(new Enemy(r.nextInt(640), -10, tex, this, game));
		}
	}
	public void tick(){
		//EntityA Class
		for (int i = 0; i < ea.size(); i++){
			enta = ea.get(i);
			
			enta.tick();
		}
		
		//EntityB Class
				for (int i = 0; i < eb.size(); i++){
					entb = eb.get(i);
					
					entb.tick();
				}
	}
	
	public void render(Graphics g){
		//EntityA Class
		for(int i = 0; i < ea.size(); i++){
			enta = ea.get(i);
			
			enta.render(g);
		}
		
		//EntityB Class
		for(int i = 0; i < eb.size(); i++){
			entb = eb.get(i);
				
			entb.render(g);
		}
		
	}
	
	public void addEntity(EntityA block){
		ea.add(block);
	}
	
	public void removeEntity(EntityA block){
		ea.remove(block);
	}
	
	public void addEntity(EntityB block){
		eb.add(block);
	}
	
	public void removeEntity(EntityB block){
		eb.remove(block);
	}
	
	//returns list with a method
	public LinkedList<EntityA> getEntityA(){
		return ea;
	}
	
	public LinkedList<EntityB> getEntityB(){
		return eb;
	}
	

}

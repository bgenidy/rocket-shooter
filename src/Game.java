import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

//we are creating a subclass of Canvas called Game and implementing to the interface runnable
public class Game extends Canvas implements Runnable {
	
	public static final int Width = 320;
	public static final int Height = Width /12 * 9;
	public static final int Scale = 2;
	public final String Title = "Rocket Shooter Game";
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB); //buffers the whole window 
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private boolean is_shooting = false;
	
	private int enemy_count = 1;
	private int enemy_killed = 0;

	private Player p;
	private Controller c;
	private Textures tex;
	private Menu menu;
	
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
	
	public static int HEALTH = 200;
	
	//the enum is being used to create a start menu for the game
	public static enum STATE{
		MENU,
		GAME
	};
	public static STATE State = STATE.MENU;
	
	public void init(){
		requestFocus(); //allows you to have motion on the frame right away
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try{
			spriteSheet = loader.loadImage("/spriteSheet.png"); //this loads up my image inside the res folder
			background = loader.loadImage("/background.png"); //loads up the background
		}catch(IOException e){
			e.printStackTrace();
		}
		addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput());
		
		tex = new Textures(this);
		
		c = new Controller(tex, this);//initializing the contoller variable
		p = new Player (200, 200, tex, this, c);
		menu = new Menu();
		
		c.createEnemy(enemy_count);
		
		ea = c.getEntityA();
		eb = c.getEntityB();
	}
	
	private synchronized void start(){
		if (running) // if already running don't start another one
			return;
		
		//if not then start that thread up
		running = true; 
		thread = new Thread(this);
		thread.start();
		
		
	}
	//synchronized deals with threads
	private synchronized void stop(){
		if(!running) //this does the opposite of the above if not running then keep it that way
			return;
		
		running = false;
		//it is used incase thread.join() might fail in which case it never executes it
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	/**
	 * This is the method that is needed to implement runnable
	 */
	public void run(){
		init(); //calls the method init to be executed
		long lastTime = System.nanoTime(); //gives more prceise frame time
		final double amountOfTicks = 60.0; 
		double ns = 1000000000 /amountOfTicks;
		double delta = 0; //calculates time past
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis(); //gives you the current time
				
		
		//game loop
		while (running){
			long now = System.nanoTime(); //get the diffrence of code from the first line in this method tell now
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			//this keeps resetting the fps and ticks so that we don't keep increasing in numbers
			if (System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
		}
		
		//after loop is done then game finishes
		stop();
	}
	
	//every thing in the game that updates
	private void tick(){
		// allows you to do this when your in the right menu
		if (State == STATE.GAME){
			p.tick();
			c.tick();
		}
		
		//pretty much if enemies killed is equal to or greater then enemy count
		//then we add 2 more allien ships and reset kills
		if(enemy_killed >= enemy_count){
			enemy_count +=2;
			enemy_killed = 0;
			c.createEnemy(enemy_count);
		}
	}
	
	//every thing that renders in the game
	private void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		
		//if there is no buffer then create one
		if (bs == null){
			createBufferStrategy(3); //where implementing 3 buffers so it loads the image we see as the third time
			return;
		}
		
		Graphics g = bs.getDrawGraphics(); //draws out our buffer
		
		//this is the drawing space
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		g.drawImage(background, 0, 0, null);
		
		//to allow you to do this only if your in the right menu
		if(State == STATE.GAME){
			p.render(g);//now both the player and bullet are sincked
			c.render(g);
			
			//setting the color of the health bar
			g.setColor(Color.red);
			g.fillRect(440, 5, 200, 30);
			
			g.setColor(Color.green);
			g.fillRect(440, 5, HEALTH, 30);
			
			g.setColor(Color.white);
			g.drawRect(440, 5, 200, 30);
			if (HEALTH <= 0){
				Font fnt0 = new Font("arial", Font.BOLD, 50);
				g.setFont(fnt0);
				g.setColor(Color.white);
				System.exit(1);
			}
		}
		
		else if (State == STATE.MENU){
			menu.render(g);
		}
		
		//end of drawing space
		g.dispose(); //gets rid of the null and buffer thing because its already created
		bs.show(); //shows the graphics
		
	}
	
	//keyboard functionality
	public void keyPressed (KeyEvent e){
		int key = e.getKeyCode();
		
		
		if(State == STATE.GAME){
		if (key == KeyEvent.VK_RIGHT){
			p.setVelX(5);
		}
		
		else if (key == KeyEvent.VK_LEFT){
			p.setVelX(-5);
		}
		
		else if (key == KeyEvent.VK_DOWN){
			p.setVelY(5);
		}
		
		else if (key == KeyEvent.VK_UP){
			p.setVelY(-5);
		}
		//this is_shooting makes you have to keep clicking the space bar to shoot
		else if (key == KeyEvent.VK_SPACE && !is_shooting){
			is_shooting = true;
			c.addEntity(new Bullet(p.getX(), p.getY(), tex, this));
		}
		}
	}
	
	public void keyReleased(KeyEvent e){
int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_RIGHT){
			p.setVelX(0);
		}
		
		else if (key == KeyEvent.VK_LEFT){
			p.setVelX(0);
		}
		
		else if (key == KeyEvent.VK_DOWN){
			p.setVelY(0);
		}
		
		else if (key == KeyEvent.VK_UP){
			p.setVelY(0);
		}
		
		else if (key == KeyEvent.VK_SPACE){
			is_shooting = false;
		}
		
	}
	
	public static void main (String args[]){
		Game game = new Game(); //this is creating and initiallizing the game class which contains our main method
		
		game.setPreferredSize(new Dimension (Width * Scale, Height * Scale)); //these variables are the final variable already stated above
		game.setMaximumSize(new Dimension (Width * Scale, Height * Scale));
		game.setMinimumSize(new Dimension (Width * Scale, Height * Scale));
	
		JFrame frame = new JFrame(game.Title); //creating a frame for our game
		frame.add(game); //this adds our game to the frame
		frame.pack(); //so that every thing fits on the screen viewed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//allows it to close when the x button is clicked
		frame.setResizable(false); //expanding the game is not allowed
		frame.setLocationRelativeTo(null); //allows screen to display in the middle of the page
		frame.setVisible(true); //so people can actually see the frame of my awesome game
		
		game.start(); //we are calling the working method in Game
		
		
	}
	
	//accessor for the spriteSheet
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
	//////////////////////////////
	//getters and setters for space ship count and killed
	public int getEnemy_count() {
		return enemy_count;
	}

	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}

	public int getEnemy_killed() {
		return enemy_killed;
	}

	public void setEnemy_killed(int enemy_killed) {
		this.enemy_killed = enemy_killed;
	}
	//////////////////////////////////////////
	//end of getters and setters

	

}

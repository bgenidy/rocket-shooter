import java.awt.Rectangle;

//super class for almost all our things including
//our bullet, player, and enemy class
public class GameObject {
	
	public double x, y;
	
	public GameObject(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getBounds(){
		return new Rectangle ((int)x, (int)y, 32, 32);
	}

}

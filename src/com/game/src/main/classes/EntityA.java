package com.game.src.main.classes;
import java.awt.Graphics;
import java.awt.Rectangle;


//interface used for the metthods we repeat
public interface EntityA {
	public void tick();
	public void render(Graphics g);
	public Rectangle getBounds();
	
	public double getX();
	public double getY();

}

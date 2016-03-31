import java.awt.image.BufferedImage;


public class SpriteSheet {
	
	private BufferedImage image;
	
	//basic constructor
	public SpriteSheet(BufferedImage image){
		this.image = image;
	}
	
	//this method will grab our images from the sprite sheet we created
	public BufferedImage grabImage(int col, int row, int width, int height){
		
		BufferedImage img = image.getSubimage((col * 32) - 32, (row * 32) -32, width, height); //col is starting point
		return img;
	}

}

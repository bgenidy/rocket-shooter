import java.awt.image.*;
import java.io.IOException;

import javax.imageio.*;

/**
 * in this class we are creating a buffer for our images when they
 * get imported into the game
 * @author beshoi
 *
 */
public class BufferedImageLoader {
	
	private BufferedImage image;
	
	public BufferedImage loadImage(String path) throws IOException{
		image = ImageIO.read(getClass().getResource(path)); 
		return image;
	}

}

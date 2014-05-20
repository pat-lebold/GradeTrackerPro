package gradetrackerpro.graphics.buttons;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class ButtonCancel extends ASizeChangingButton{
	public ButtonCancel(double x, double y, int width, int height, int sizeChange){
		super(x,y,width,height,sizeChange);
	}
	public void pushData(String title, String[] data){
		super.pushData("cancel",null);
	}
	public void render(Graphics g){
		try{
			BufferedImage image = ImageIO.read(new File("cancel.gif"));
			g.drawImage(image,(int)super.getX(),(int)super.getY(),super.getWidth(),super.getHeight(),null);
		}catch(IOException e){
			g.setColor(Color.red);
			g.fillOval((int)super.getX(),(int)super.getY(),super.getWidth()-1,super.getHeight()-1);
			g.setColor(Color.black);
			g.drawOval((int)super.getX(),(int)super.getY(),super.getWidth()-1,super.getHeight()-1);
		}
	}
}

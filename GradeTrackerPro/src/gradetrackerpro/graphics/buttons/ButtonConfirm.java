package gradetrackerpro.graphics.buttons;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class ButtonConfirm extends ASizeChangingButton{
	public ButtonConfirm(double x, double y, int width, int height, int sizeChange){
		super(x,y,width,height,sizeChange);
	}
	public void pushData(String title, String[] data){
		super.pushData("confirm",null);
	}
	public void render(Graphics g){
		try{
			BufferedImage image = ImageIO.read(new File("accept.gif"));
			g.drawImage(image,(int)super.getX(),(int)super.getY(),super.getWidth(),super.getHeight(),null);
		}catch(IOException e){
			g.setColor(new Color(255,127,39,255));
			g.fillRect((int)super.getX(),(int)super.getY()+super.getHeight()/2-super.getHeight()/8,super.getWidth(),super.getHeight()/4);
			g.fillRect((int)super.getX()+super.getWidth()/2-super.getWidth()/8,(int)super.getY(),super.getWidth()/4,super.getHeight());
		}
	}
}

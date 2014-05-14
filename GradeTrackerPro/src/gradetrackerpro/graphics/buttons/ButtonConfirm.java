package gradetrackerpro.graphics.buttons;
import java.awt.Color;
import java.awt.Graphics;
public class ButtonConfirm extends ASizeChangingButton{
	public ButtonConfirm(double x, double y, int width, int height, int sizeChange){
		super(x,y,width,height,sizeChange);
	}
	public void pushData(String title, String[] data){
		super.pushData("confirm",null);
	}
	public void render(Graphics g){
		g.setColor(Color.green);
		g.fillOval((int)super.getX(),(int)super.getY(),super.getWidth(),super.getHeight());
		g.setColor(Color.black);
		g.drawOval((int)super.getX(),(int)super.getY(),super.getWidth(),super.getHeight());
	}
}

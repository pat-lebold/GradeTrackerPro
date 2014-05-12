package gradetrackerpro.graphics.buttons;
import java.awt.Color;
import java.awt.Graphics;
public class ButtonCancel extends ASizeChangingButton{
	public ButtonCancel(int x, int y, int width, int height, int sizeChange){
		super(x,y,width,height,sizeChange);
	}
	public void pushData(String title, String[] data){
		super.pushData("cancel",null);
	}
	public void render(Graphics g){
		g.setColor(Color.red);
		g.fillOval(super.getX(),super.getY(),super.getWidth(),super.getHeight());
		g.setColor(Color.black);
		g.drawOval(super.getX(),super.getY(),super.getWidth(),super.getHeight());
	}
}

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
		g.setColor(Color.green.darker());
		g.fillRect((int)super.getX(),(int)super.getY()+super.getHeight()/2-super.getHeight()/8,super.getWidth(),super.getHeight()/4);
		g.fillRect((int)super.getX()+super.getWidth()/2-super.getWidth()/8,(int)super.getY(),super.getWidth()/4,super.getHeight());
	}
}

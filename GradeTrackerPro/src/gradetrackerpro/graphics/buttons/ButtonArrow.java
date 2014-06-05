package gradetrackerpro.graphics.buttons;

import gradetrackerpro.Direction;
import java.awt.Color;
import java.awt.Graphics;

public class ButtonArrow extends AColorButton{
	private Direction side;
	private boolean enabled;
	public ButtonArrow(double x, double y, int width, int height, Direction side){
		super(x,y,width,height,"Arrow",new Color(0,0,0,50),new Color(0,0,0,100),new Color(0,0,0,150));
		this.side=side;
		this.enabled=true;
	}
	public void setEnabled(boolean enabled){
		this.enabled=enabled;
	}
	public void pushData(String title, String[] data){
		if(this.enabled){
			if(this.side==Direction.LEFT)
				super.pushData("arrow-left",null);
			else
				super.pushData("arrow-right",null);
		}
	}
	public void render(Graphics g){
		if(this.enabled){
			g.setColor(super.color);
			if(this.side==Direction.LEFT){
				g.fillRect((int)super.getX()+super.getWidth()/3,(int)(super.getY()+super.getHeight()/4), super.getWidth()*2/3, super.getHeight()/2);
				int[] x = {(int)super.getX()+super.getWidth()/3,(int)super.getX(),(int)super.getX()+super.getWidth()/3};
				int[] y = {(int)super.getY(),(int)super.getY()+super.getHeight()/2,(int)super.getY()+super.getHeight()};
				g.fillPolygon(x,y,3);
			}
			else{
				g.fillRect((int)super.getX(),(int)(super.getY()+super.getHeight()/4), super.getWidth()*2/3, super.getHeight()/2);
				int[] x = {(int)super.getX()+super.getWidth()*2/3,(int)super.getX()+super.getWidth(),(int)super.getX()+super.getWidth()*2/3};
				int[] y = {(int)super.getY(),(int)super.getY()+super.getHeight()/2,(int)super.getY()+super.getHeight()};
				g.fillPolygon(x,y,3);
			}
		}
	}
}
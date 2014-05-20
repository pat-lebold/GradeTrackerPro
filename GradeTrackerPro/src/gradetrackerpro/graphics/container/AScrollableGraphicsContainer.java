package gradetrackerpro.graphics.container;
import gradetrackerpro.graphics.AGraphicsEntity;
import gradetrackerpro.transmission.IReceiver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
public abstract class AScrollableGraphicsContainer extends AGraphicsContainer implements IReceiver{
	protected int realHeight;
	//----------------------
	protected final int slideWidth = 16;
	private int slideX;
	private double slideHeight;
	protected double slideY;
	private Color slideColor;
	private double slideDensity;
	//----------------------
	private boolean scrolling;
	private boolean mouseInScrollBar;
	private int lastY;
	private int newY;
	public AScrollableGraphicsContainer(double x, double y, int width, int height, Color slideColor, int slideOffset){
		super(x,y,width,height);
		this.slideColor = slideColor;
		this.slideX = this.getWidth() - slideOffset;
		setupScrollBar();
		this.scrolling = false;
		this.mouseInScrollBar = false;
	}
	@Override
	public void setLocation(double x, double y){
		double dy = y - super.getY();
		this.slideY += dy;
		super.setLocation(x,y);
		this.updateComponents(dy);
	}
	public void addComponent(AGraphicsEntity component){
		super.addComponent(component);
		this.reevaluateRealHeight();
		this.setupScrollBar();
	}
	public void removeComponent(AGraphicsEntity component){
		super.removeComponent(component);
		this.reevaluateRealHeight();
		this.setupScrollBar();
	}
	public int getMin(){
		ArrayList<AGraphicsEntity> components = super.pullComponents();
		double min = Double.MAX_VALUE;
		for(AGraphicsEntity component:components){
			if(min>component.getY())
				min = component.getY();
		}
		return (int)min;
	}
	protected void reevaluateRealHeight(){
		ArrayList<AGraphicsEntity> components = super.pullComponents();
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for(AGraphicsEntity component:components){
			if(min>component.getY())
				min = component.getY();
			if(max<component.getY()+component.getHeight())
				max = component.getY()+component.getHeight();
		}
		this.realHeight = (int)(max - min)+8;
	}
	private void setupScrollBar(){
		double y_diff = (this.slideY - super.getY()) * this.slideDensity;
		if(this.realHeight>super.getHeight()){
			this.slideDensity = (double)this.realHeight/(super.getHeight());
			this.slideHeight = super.getHeight() * (double)(super.getHeight())/this.realHeight;
			this.slideY = super.getY() + y_diff/slideDensity;
		}
		else{
			this.slideHeight = 0;
			this.slideY = super.getY();
			this.updateComponents((int)y_diff);
			return;
		}
	}
	public void scrollUp(){
		this.slideY--;
		if(this.slideY<this.getY()){
			this.slideY = this.getY();
		}
		else{
			this.updateComponents(this.slideDensity);
		}
	}
	public void scrollDown(){
		this.slideY++;
		if(this.slideY+this.slideHeight>super.getY()+super.getHeight()+1){
			this.slideY = super.getY()+super.getHeight()-this.slideHeight+1;
		}
		else{
			this.updateComponents(-this.slideDensity);
		}
	}
	protected void updateComponents(double dy){
		for(AGraphicsEntity entity: super.pullComponents()){
			entity.setLocation(entity.getX(),entity.getY()+dy);
			if(entity.getY()>=super.getY()+super.getHeight())
				entity.setVisibility(false);
			else if(entity.getY()+entity.getHeight()<=super.getY())
				entity.setVisibility(false);
			else
				entity.setVisibility(true);
		}
		super.pushData("update", null);
	}
	public void ping(String title, String[] data){
		if(title.equals("mouse-data")){
			int event = Integer.parseInt(data[2]);
			if(event==MouseEvent.MOUSE_PRESSED){
				if(this.mouseInScrollBar)
					this.scrolling = true;
				else
					this.scrolling = false;
			}
			else if(event==MouseEvent.MOUSE_RELEASED){
				this.scrolling = false;
			}
			else if(event==MouseEvent.MOUSE_MOVED||event==MouseEvent.MOUSE_DRAGGED){
				int mouseX = (int)Double.parseDouble(data[0]);
				int mouseY = (int)Double.parseDouble(data[1]);
				this.lastY = this.newY;
				this.newY = mouseY;
				if(this.scrolling){
					for(int n=0;n<Math.abs(this.lastY-this.newY);n++){
						if(this.lastY > this.newY)
							this.scrollUp();
						else if(this.lastY <this. newY)
							this.scrollDown();
					}
				}
				if(mouseX > this.slideX && mouseX < this.slideX + this.slideWidth && mouseY > this.slideY && mouseY < this.slideY + this.slideHeight)
					this.mouseInScrollBar = true;
				else
					this.mouseInScrollBar = false;
			}
		}
	}
	public void render(Graphics g){
		super.renderComponents(g);
		g.setColor(this.slideColor);
		g.fillRect(this.slideX,(int)this.slideY,this.slideWidth,(int)this.slideHeight);
	}
}
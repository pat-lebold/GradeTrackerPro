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
	private double slideY;
	private Color slideColor;
	private double slideDensity;
	//----------------------
	private boolean scrolling;
	private boolean mouseInScrollBar;
	private int lastY;
	private int newY;
	public AScrollableGraphicsContainer(int x, int y, int width, int height, Color slideColor, int slideOffset){
		super(x,y,width,height);
		this.slideColor = slideColor;
		this.slideX = this.getWidth() - slideOffset;
		setupScrollBar();
		this.scrolling = false;
		this.mouseInScrollBar = false;
	}
	public void setLocation(int x, int y){
		int dy = y - super.getY();
		this.slideY += dy;
		super.setLocation(x,y);
		this.updateComponents(dy);
	}
	public void addComponent(AGraphicsEntity component){
		super.addComponent(component);
		this.reevaluateRealHeight();
	}
	public void removeComponent(AGraphicsEntity component){
		super.removeComponent(component);
		this.reevaluateRealHeight();
	}
	private void reevaluateRealHeight(){
		ArrayList<AGraphicsEntity> components = super.pullComponents();
		int max = 0;
		for(AGraphicsEntity component:components){
			if(max<component.getY()+component.getHeight())
				max = component.getY()+component.getHeight();
		}
		this.realHeight = max - this.getY() + 8;
		this.setupScrollBar();
		this.updateComponents(0);
	}
	private void setupScrollBar(){
		double oldSlideY = this.slideY;
		this.slideY = this.getY() + 8;
		if(super.getHeight() < this.realHeight){
			this.updateComponents((int)(this.slideDensity*(this.slideY-oldSlideY)));
			this.slideHeight = super.getHeight() * (double)(super.getHeight()-24)/this.realHeight;
		}
		else
			this.slideHeight = 0;
		this.slideDensity = (double)this.realHeight/super.getHeight();
	}
	public void scrollUp(){
		this.slideY--;
		if(this.slideY<this.getY()+8){
			this.slideY = this.getY() + 8;
		}
		else{
			this.updateComponents((int)this.slideDensity);
		}
	}
	public void scrollDown(){
		this.slideY++;
		if(this.slideY+this.slideHeight>super.getY()+super.getHeight()-8){
			this.slideY = super.getY()+super.getHeight()-8-this.slideHeight;
		}
		else{
			this.updateComponents(-(int)this.slideDensity);
		}
	}
	private void updateComponents(int dy){
		for(AGraphicsEntity entity: super.pullComponents()){
			entity.setLocation(entity.getX(),(int)(entity.getY()+dy));
			if(entity.getY()>=super.getY()&&entity.getY()+entity.getHeight()<=super.getY()+super.getHeight()){
				entity.setVisibility(true);
			}
			else{
				entity.setVisibility(false);
			}
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
				int mouseX = Integer.parseInt(data[0]);
				int mouseY = Integer.parseInt(data[1]);
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
package gradetrackerpro.graphics.container;

import gradetrackerpro.graphics.LoadWidget;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class LoadElementContainer extends AScrollableGraphicsContainer {
	private ArrayList<LoadWidget> loadWidgets;
	public LoadElementContainer(int x, int y, int width, int height, Color slideColor) {
		super(x, y, width, height, slideColor, 16);
		this.loadWidgets = new ArrayList<LoadWidget>();
	}
	public void addWidget(LoadWidget widget){
		this.addComponent(widget);
		this.loadWidgets.add(widget);
		widget.addReceiver(this);
		this.relocateElements();
	}
	private void relocateElements(){
		double y = super.getY();
		double dy = super.getY() - super.getMin();
		y -= dy;
		for(LoadWidget widget:this.loadWidgets){
			super.removeComponent(widget);
			widget.setLocation(widget.getX(),y);
			super.addComponent(widget);
			y += widget.getHeight() + 8;
		}
		super.reevaluateRealHeight();
		super.updateComponents(0);
	}
	public void ping(String title, String[] data){
		if(title.equals("mouse-data")){
			for(LoadWidget widget:this.loadWidgets){
				widget.ping(title,data);
			}
			super.ping(title,data);
		}
		else if(title.equals("key-data")){
			//nothing
		}
		else if(title.equals("load-course")){
			super.pushData(title,data);
		}
		else
			super.ping(title,data);
	}
	public void render(Graphics g){
		g.setClip((int)super.getX(),(int)super.getY(),super.getWidth(),super.getHeight());
		super.render(g);
		g.setColor(new Color(0,0,0,25));
		g.fillRect((int)super.getX(), (int)super.getY(), super.getWidth()-1, super.getHeight()-1);
		g.setClip(0,0,250,400);
	}
}

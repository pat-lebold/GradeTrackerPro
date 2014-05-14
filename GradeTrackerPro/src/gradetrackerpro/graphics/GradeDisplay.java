package gradetrackerpro.graphics;

import java.awt.Color;
import java.awt.Graphics;

import gradetrackerpro.course.Grade;
import gradetrackerpro.graphics.container.AGraphicsContainer;
import gradetrackerpro.graphics.text.Label;
import gradetrackerpro.transmission.IReceiver;

public class GradeDisplay extends AGraphicsContainer implements IReceiver{

	private Grade grade;
	private Label display;
	
	public GradeDisplay(double x, double y, int width, int height, Grade grade) {
		super(x, y, width, height);
		this.grade=grade;
		this.display = new Label(x,y,width,height,this.grade.getName()+": "+this.grade.getEarned()+"/"+this.grade.getTotal()+" "+this.grade.getPercentEarned()+"%",Color.black);
		super.addComponent(display);
	}
	
	@Override
	public void setLocation(double x, double y){
		super.setLocation(x,y);
		this.display.setLocation(x,y);
	}
	
	@Override
	public void render(Graphics g) {
		super.renderComponents(g);
		g.setColor(new Color(0,0,0,25));
		g.fillRect((int)super.getX(),(int)super.getY(),super.getWidth()-1,super.getHeight()-1);
		g.setColor(Color.black);
		g.drawRect((int)super.getX(),(int)super.getY(),super.getWidth()-1,super.getHeight()-1);
	}

	@Override
	public void ping(String title, String[] data) {
		// TODO Auto-generated method stub
		
	}

}

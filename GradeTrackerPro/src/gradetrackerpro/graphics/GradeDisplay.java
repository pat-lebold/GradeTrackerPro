package gradetrackerpro.graphics;

import java.awt.Color;
import java.awt.Graphics;

import gradetrackerpro.course.Grade;
import gradetrackerpro.graphics.buttons.ButtonCancel;
import gradetrackerpro.graphics.container.AGraphicsContainer;
import gradetrackerpro.graphics.text.Label;
import gradetrackerpro.transmission.IReceiver;

public class GradeDisplay extends AGraphicsContainer implements IReceiver{

	private Grade grade;
	private Label display;
	private ButtonCancel cancel;
	
	public GradeDisplay(double x, double y, int width, int height, Grade grade) {
		super(x, y, width, height);
		this.grade=grade;
		this.display = new Label(x,y,width-32,height,this.grade.getName()+": "+this.grade.getEarned()+"/"+this.grade.getTotal()+" "+this.grade.getPercentEarned()+"%",Color.black);
		this.cancel = new ButtonCancel(this.display.getX()+this.display.getWidth(),super.getY()+6,20,20,2);
		super.addComponent(this.cancel);
		cancel.addReceiver(this);
		super.addComponent(display);
	}
	
	public Grade getGrade(){
		return this.grade;
	}
	
	@Override
	public void setLocation(double x, double y){
		super.setLocation(x,y);
		this.display.setLocation(x,y);
		double dy = y-this.cancel.getY();
		this.cancel.setLocation(this.cancel.getX(),this.cancel.getY()+dy+6);
	}
	
	@Override
	public void render(Graphics g) {
		super.renderComponents(g);
		g.setColor(new Color(0,0,0,50));
		g.fillRect((int)super.getX(),(int)super.getY(),super.getWidth()-1,super.getHeight()-1);
	}

	@Override
	public void ping(String title, String[] data) {
		if(title.equals("mouse-data")){
			int x = (int)Double.parseDouble(data[0]);
			int y = (int)Double.parseDouble(data[1]);
			int event = Integer.parseInt(data[2]);
			this.cancel.mouseAction(x,y,event);
		}
		else if(title.equals("cancel")){
			String[] pushdata = {""+super.getX(),""+super.getY()};
			super.pushData("remove-grade", pushdata);
		}
		
	}
	
	public boolean equals(Object o){
		if(!(o instanceof GradeDisplay))
			return false;
		GradeDisplay other = (GradeDisplay)o;
		if(!other.grade.getName().equals(this.grade.getName()))
			return false;
		if(other.grade.getEarned()!=this.grade.getEarned())
			return false;
		if(other.grade.getTotal()!=this.grade.getTotal())
			return false;
		return true;
	}

}

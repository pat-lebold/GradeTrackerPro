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
	
	public GradeDisplay(int x, int y, int width, int height, Grade grade) {
		super(x, y, width, height);
		this.grade=grade;
		this.display = new Label(x,y,width,height,this.grade.getName()+": "+this.grade.getEarned()+"/"+this.grade.getTotal()+" "+this.grade.getPercentEarned()+"%",Color.white);
		super.addComponent(display);
	}
	
	@Override
	public void render(Graphics g) {
		super.renderComponents(g);
		

	}

	@Override
	public void ping(String title, String[] data) {
		// TODO Auto-generated method stub
		
	}

}

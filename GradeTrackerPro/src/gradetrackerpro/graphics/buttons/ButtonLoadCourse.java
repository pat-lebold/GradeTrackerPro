package gradetrackerpro.graphics.buttons;

import java.awt.Color;
public class ButtonLoadCourse extends AColorButton{
	public ButtonLoadCourse(int x, int y, int width, int height){
		super(x,y,width,height,"Load Course",new Color(0,0,0,50),new Color(0,0,0,100),new Color(0,0,0,150));
	}
	public void pushData(String title, String[] data){
		super.pushData("load-course-screen",null);
	}
}
package gradetrackerpro.graphics.screens;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import gradetrackerpro.ProgramManager;
import gradetrackerpro.course.Course;
import gradetrackerpro.graphics.buttons.AColorButton;
import gradetrackerpro.graphics.buttons.ButtonExit;
import gradetrackerpro.graphics.buttons.ButtonHome;
import gradetrackerpro.graphics.container.CourseContainer;
import gradetrackerpro.transmission.IReceiver;
import gradetrackerpro.transmission.ITrigger;
import javax.swing.JPanel;
@SuppressWarnings("serial")
public class ScreenCourse extends JPanel implements ITrigger, IReceiver{
	private ArrayList<IReceiver> receivers;
	private Course course;
	private CourseContainer courseContainer;
	private AColorButton exitButton;
	private AColorButton homeButton;
	@SuppressWarnings("unused")
	private BufferedImage background;
	private BufferedImage header;
	public ScreenCourse(Course course,BufferedImage background,BufferedImage header){
		this.background=background;
		this.header=header;
		this.setBackground(ProgramManager.BACKGROUND_COLOR);
		this.receivers = new ArrayList<IReceiver>();
		this.course = course;
		this.courseContainer = new CourseContainer(8,48,ProgramManager.SCREEN_WIDTH-16,ProgramManager.SCREEN_HEIGHT-104,new Color(0,0,0,50),this.course);
		this.courseContainer.addReceiver(this);
		this.homeButton = new ButtonHome(ProgramManager.SCREEN_WIDTH/30,this.courseContainer.getX()+this.courseContainer.getHeight()+56,ProgramManager.SCREEN_WIDTH*13/30,ProgramManager.SCREEN_HEIGHT*2/24);
		this.homeButton.addReceiver(this);
		this.exitButton = new ButtonExit(ProgramManager.SCREEN_WIDTH*16/30,this.courseContainer.getX()+this.courseContainer.getHeight()+56,ProgramManager.SCREEN_WIDTH*13/30,ProgramManager.SCREEN_HEIGHT*2/24);
		this.exitButton.addReceiver(this);
		ScreenMouseHandler mouseHandler = new ScreenMouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
	}
	public void addReceiver(IReceiver receiver){
		this.receivers.add(receiver);
	}
	public void removeReceiver(IReceiver receiver){
		this.receivers.remove(receiver);
	}
	public void ping(String title, String[] data){
		if(title.equals("mouse-data")){
			int x = (int)Double.parseDouble(data[0]);
			int y = (int)Double.parseDouble(data[1]);
			int event = Integer.parseInt(data[2]);
			this.exitButton.mouseAction(x,y,event);
			this.homeButton.mouseAction(x,y,event);
			this.courseContainer.ping(title,data);
			this.pushData("update",null);
		}
		else if(title.equals("key-data")){
			this.courseContainer.ping(title,data);
		}
		else
			pushData(title,data);
	}
	public void pushData(String title, String[] data){
		for(IReceiver receiver:this.receivers){
			receiver.ping(title,data);
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	    //g.drawImage(this.background,0,0,null);
	    g.drawImage(this.header,8,8,234,28,null);
		this.courseContainer.render(g);
		this.exitButton.render(g);
		this.homeButton.render(g);
		g.setColor(Color.black);
		g.drawRect(0, 0, super.getWidth()-1, super.getHeight()-1);
	}
	private class ScreenMouseHandler extends MouseAdapter{
		public void mouseMoved(MouseEvent event){
			String[] data = {""+event.getX(),""+event.getY(),""+MouseEvent.MOUSE_MOVED};
			ping("mouse-data",data);
		}
		public void mouseDragged(MouseEvent event){
			String[] data = {""+event.getX(),""+event.getY(),""+MouseEvent.MOUSE_DRAGGED};
			ping("mouse-data",data);
		}
		public void mousePressed(MouseEvent event){
			String[] data = {""+event.getX(),""+event.getY(),""+MouseEvent.MOUSE_PRESSED};
			ping("mouse-data",data);
		}
		public void mouseReleased(MouseEvent event){
			String[] data = {""+event.getX(),""+event.getY(),""+MouseEvent.MOUSE_RELEASED};
			ping("mouse-data",data);
		}
	}
}

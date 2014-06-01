package gradetrackerpro.graphics.screens;
import gradetrackerpro.ProgramManager;
import gradetrackerpro.graphics.buttons.ButtonExit;
import gradetrackerpro.graphics.buttons.ButtonHelp;
import gradetrackerpro.graphics.buttons.ButtonLoadCourse;
import gradetrackerpro.graphics.buttons.ButtonNewCourse;
import gradetrackerpro.graphics.buttons.IButton;
import gradetrackerpro.transmission.IReceiver;
import gradetrackerpro.transmission.ITrigger;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class ScreenHome extends JPanel implements ITrigger, IReceiver{
	private ArrayList<IReceiver> receivers;
	private IButton newCourseButton;
	private IButton loadCourseButton;
	private IButton exitButton;
	private IButton helpButton;
	private BufferedImage background;
	private BufferedImage header;
	public ScreenHome(BufferedImage background,BufferedImage header){
		this.setBackground(ProgramManager.BACKGROUND_COLOR);
		this.background=background;
		this.header=header;
		this.receivers=new ArrayList<IReceiver>();
		this.newCourseButton=new ButtonNewCourse(ProgramManager.SCREEN_WIDTH/6,ProgramManager.SCREEN_HEIGHT*4/24,ProgramManager.SCREEN_WIDTH*2/3,40);
		this.newCourseButton.addReceiver(this);
		this.loadCourseButton=new ButtonLoadCourse(ProgramManager.SCREEN_WIDTH/6,ProgramManager.SCREEN_HEIGHT*7/24,ProgramManager.SCREEN_WIDTH*2/3,40);
		this.loadCourseButton.addReceiver(this);
		this.helpButton=new ButtonHelp(ProgramManager.SCREEN_WIDTH/6,ProgramManager.SCREEN_HEIGHT*10/24,ProgramManager.SCREEN_WIDTH*2/3,40);
		this.helpButton.addReceiver(this);
		this.exitButton=new ButtonExit(ProgramManager.SCREEN_WIDTH/6,ProgramManager.SCREEN_HEIGHT*13/24,ProgramManager.SCREEN_WIDTH*2/3,40);
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
	public void pushData(String title, String[] data){
		for(IReceiver receiver:this.receivers){
			receiver.ping(title, data);
		}
	}
	public void ping(String title, String[] data){
		if(title.equals("mouse-data")){
			int x = (int)Double.parseDouble(data[0]);
			int y = (int)Double.parseDouble(data[1]);
			int event = Integer.parseInt(data[2]);
			this.newCourseButton.mouseAction(x,y,event);
			this.loadCourseButton.mouseAction(x,y,event);
			this.helpButton.mouseAction(x,y,event);
			this.exitButton.mouseAction(x,y,event);
			pushData("update",null);
		}
		else if(title.equals("key-data")){
			//do nothing
		}
		else
			pushData(title,data);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.background,0,0,null);
		g.drawImage(this.header,0,0,250,60,null);
		g.setColor(Color.black);
		g.drawRect(0,0,this.getWidth()-1,this.getHeight()-1);
		this.newCourseButton.render(g);
		this.loadCourseButton.render(g);
		this.helpButton.render(g);
		this.exitButton.render(g);
	}
	private class ScreenMouseHandler extends MouseAdapter{
		public void mouseMoved(MouseEvent event){
			String[] data = {""+event.getX(),""+event.getY(),""+MouseEvent.MOUSE_MOVED};
			ping("mouse-data",data);
		}
		public void mouseDragged(MouseEvent event){
			String[] data = {""+event.getX(),""+event.getY(),""+MouseEvent.MOUSE_DRAGGED};
			ping("mouse-data",data);
			if(event.getY()<=60)
				pushData("mouse-data",data);
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
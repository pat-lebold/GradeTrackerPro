package gradetrackerpro.graphics.screens;
import gradetrackerpro.ProgramManager;
import gradetrackerpro.graphics.buttons.AColorButton;
import gradetrackerpro.graphics.buttons.ButtonExit;
import gradetrackerpro.graphics.buttons.ButtonGo;
import gradetrackerpro.graphics.buttons.ButtonHome;
import gradetrackerpro.graphics.text.TextBox;
import gradetrackerpro.transmission.IReceiver;
import gradetrackerpro.transmission.ITrigger;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
@SuppressWarnings("serial")
public class ScreenChooseCourseName extends JPanel implements IReceiver, ITrigger{
	private ArrayList<IReceiver> receivers;
	private TextBox nameTextBox;
	private AColorButton exitButton;
	private AColorButton goButton;
	private AColorButton homeButton;
	private String courseName;
	private BufferedImage background;
	private BufferedImage header;
	public ScreenChooseCourseName(BufferedImage background, BufferedImage header){
		this.background=background;
		this.header=header;
		this.setBackground(ProgramManager.BACKGROUND_COLOR);
		this.courseName = "Course Name";
		this.receivers = new ArrayList<IReceiver>();
		this.nameTextBox = new TextBox(16,ProgramManager.SCREEN_HEIGHT/6,ProgramManager.SCREEN_WIDTH*4/6,ProgramManager.SCREEN_HEIGHT*2/24,"Course Name","name-box");
		this.nameTextBox.addReceiver(this);
		this.goButton = new ButtonGo(this.nameTextBox.getX()+this.nameTextBox.getWidth()+8,ProgramManager.SCREEN_HEIGHT/6,ProgramManager.SCREEN_WIDTH/6,ProgramManager.SCREEN_HEIGHT*2/24);
		this.goButton.addReceiver(this);
		this.homeButton = new ButtonHome(ProgramManager.SCREEN_WIDTH/6,this.nameTextBox.getY()+this.nameTextBox.getHeight()+8,ProgramManager.SCREEN_WIDTH*4/6,40);
		this.homeButton.addReceiver(this);
		this.exitButton = new ButtonExit(ProgramManager.SCREEN_WIDTH/6,this.homeButton.getY()+this.homeButton.getHeight()+8,ProgramManager.SCREEN_WIDTH*4/6,40);
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
			this.nameTextBox.ping("mouse-data", data);
			this.goButton.mouseAction(x, y, event);
			this.exitButton.mouseAction(x, y, event);
			this.homeButton.mouseAction(x, y, event);
			this.pushData("update",null);
		}
		else if(title.equals("key-data")){
			this.nameTextBox.ping("key-data",data);
			this.pushData("update", null);
		}
		else if(title.equals("name-box")){
			this.courseName = data[0];
		}
		else if(title.equals("go")){
			String[] pushData = {this.courseName};
			this.pushData("new-course-create", pushData);
		}
		else
			this.pushData(title,data);
	}
	public void pushData(String title, String[] data){
		for(IReceiver receiver:this.receivers)
			receiver.ping(title,data);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	    g.drawImage(this.background,0,0,null);
	    g.drawImage(this.header,0,0,250,60,null);
		g.setColor(Color.black);
		g.drawRect(0,0,this.getWidth()-1,this.getHeight()-1);
		this.nameTextBox.render(g);
		this.goButton.render(g);
		this.exitButton.render(g);
		this.homeButton.render(g);
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
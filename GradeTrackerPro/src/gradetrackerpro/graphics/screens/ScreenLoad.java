package gradetrackerpro.graphics.screens;

import gradetrackerpro.ProgramManager;
import gradetrackerpro.graphics.LoadWidget;
import gradetrackerpro.graphics.buttons.AColorButton;
import gradetrackerpro.graphics.buttons.ButtonExit;
import gradetrackerpro.graphics.buttons.ButtonHome;
import gradetrackerpro.graphics.container.LoadElementContainer;
import gradetrackerpro.transmission.DataManager;
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
public class ScreenLoad extends JPanel implements IReceiver, ITrigger{
	private BufferedImage header;
	private AColorButton homeButton;
	private AColorButton exitButton;
	private ArrayList<IReceiver> receivers;
	private LoadElementContainer container;
	public ScreenLoad(BufferedImage header){
		this.header=header;
		this.receivers = new ArrayList<IReceiver>();
		this.setBackground(ProgramManager.BACKGROUND_COLOR);
		this.homeButton = new ButtonHome(ProgramManager.SCREEN_WIDTH/36,ProgramManager.SCREEN_HEIGHT-8-40,ProgramManager.SCREEN_WIDTH*16/36,40);
		this.homeButton.addReceiver(this);
		this.exitButton = new ButtonExit(ProgramManager.SCREEN_WIDTH*19/36,ProgramManager.SCREEN_HEIGHT-8-40,ProgramManager.SCREEN_WIDTH*16/36,40);
		this.exitButton.addReceiver(this);
		this.container = new LoadElementContainer(8,65,ProgramManager.SCREEN_WIDTH-16,ProgramManager.SCREEN_HEIGHT-121,new Color(0,0,0,50));
		this.container.addReceiver(this);
		ScreenMouseHandler mouseHandler = new ScreenMouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addWidgets(DataManager.getCourseNames());
	}
	private void addWidgets(ArrayList<String> fileNames){
		for(String name:fileNames){
			LoadWidget widget = new LoadWidget(this.container.getX()+8,this.container.getY()+8,this.container.getWidth()-40,40,name);
			widget.addReceiver(this);
			this.container.addWidget(widget);
		}
	}
	@Override
	public void pushData(String title, String[] data) {
		for(IReceiver receiver:this.receivers){
			receiver.ping(title,data);
		}
	}
	@Override
	public void addReceiver(IReceiver receiver) {
		this.receivers.add(receiver);
	}
	@Override
	public void removeReceiver(IReceiver receiver) {
		this.receivers.remove(receiver);
	}
	@Override
	public void ping(String title, String[] data) {
		if(title.equals("mouse-data")){
			int x = (int)Double.parseDouble(data[0]);
			int y = (int)Double.parseDouble(data[1]);
			int event = Integer.parseInt(data[2]);
			this.homeButton.mouseAction(x, y, event);
			this.exitButton.mouseAction(x, y, event);
			this.container.ping(title,data);
			pushData("update",null);
		}
		else if(title.equals("key-data")){
			//do nothing
		}
		else if(title.equals("load-course")){
			this.pushData(title,data);
		}
		else
			this.pushData(title,data);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.header,0,0,250,60,null);
		g.setColor(Color.black);
		g.drawRect(0,0,this.getWidth()-1,this.getHeight()-1);
		this.exitButton.render(g);
		this.homeButton.render(g);
		this.container.render(g);
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

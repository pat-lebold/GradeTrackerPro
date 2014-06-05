package gradetrackerpro.graphics.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gradetrackerpro.Direction;
import gradetrackerpro.ProgramManager;
import gradetrackerpro.graphics.buttons.ButtonArrow;
import gradetrackerpro.graphics.buttons.ButtonHome;
import gradetrackerpro.transmission.DataManager;
import gradetrackerpro.transmission.IReceiver;
import gradetrackerpro.transmission.ITrigger;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ScreenHelp extends JPanel implements ITrigger, IReceiver {

	private BufferedImage header;

	private ArrayList<IReceiver> receivers;

	private ButtonHome homeButton;
	private ButtonArrow leftArrowButton;
	private ButtonArrow rightArrowButton;

	private ArrayList<BufferedImage> tutorials;
	private int index;

	public ScreenHelp(BufferedImage header){
		this.header=header;
		this.receivers = new ArrayList<IReceiver>();
		this.tutorials = DataManager.pullTutorials();
		this.index = 0;
		this.setBackground(ProgramManager.BACKGROUND_COLOR);
		this.homeButton = new ButtonHome(ProgramManager.SCREEN_WIDTH*3/8,ProgramManager.SCREEN_HEIGHT-8-40,ProgramManager.SCREEN_WIDTH/4,40);
		this.homeButton.addReceiver(this);
		this.leftArrowButton = new ButtonArrow(ProgramManager.SCREEN_WIDTH/48,ProgramManager.SCREEN_HEIGHT-8-40,ProgramManager.SCREEN_WIDTH/3,40,Direction.LEFT);
		this.leftArrowButton.addReceiver(this);
		this.leftArrowButton.setEnabled(false);
		this.rightArrowButton = new ButtonArrow(ProgramManager.SCREEN_WIDTH*31/48,ProgramManager.SCREEN_HEIGHT-8-40,ProgramManager.SCREEN_WIDTH/3,40,Direction.RIGHT);
		this.rightArrowButton.addReceiver(this);
		if(this.receivers.size()<=1)
			this.rightArrowButton.setEnabled(false);
		ScreenMouseHandler mouseHandler = new ScreenMouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
	}

	@Override
	public void ping(String title, String[] data) {
		if(title.equals("mouse-data")){
			int x = (int)Double.parseDouble(data[0]);
			int y = (int)Double.parseDouble(data[1]);
			int event = Integer.parseInt(data[2]);
			this.homeButton.mouseAction(x,y,event);
			this.leftArrowButton.mouseAction(x,y,event);
			this.rightArrowButton.mouseAction(x,y,event);
			this.pushData("update",null);
		}
		else if(title.equals("key-data")){
			//do nothing
		}
		else if(title.equals("left-arrow")){
			index--;
			if(index<=0)
				this.leftArrowButton.setEnabled(false);
			else
				this.leftArrowButton.setEnabled(true);
		}
		else if(title.equals("right-arrow")){
			index++;
			if(index>=this.tutorials.size()-1)
				this.rightArrowButton.setEnabled(false);
			else
				this.rightArrowButton.setEnabled(true);
		}
		else
			pushData(title,data);
	}
	@Override
	public void pushData(String title, String[] data) {
		for(IReceiver receiver:this.receivers)
			receiver.ping(title, data);
	}
	@Override
	public void addReceiver(IReceiver receiver) {
		this.receivers.add(receiver);
	}
	@Override
	public void removeReceiver(IReceiver receiver) {
		this.receivers.remove(receiver);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.header,0,0,250,60,null);
		this.homeButton.render(g);
		this.leftArrowButton.render(g);
		this.rightArrowButton.render(g);
		if(this.index>0&&this.index<this.tutorials.size()&&this.tutorials.get(this.index)!=null)
			g.drawImage(this.tutorials.get(this.index),ProgramManager.SCREEN_WIDTH/10,this.header.getHeight()+8,ProgramManager.SCREEN_WIDTH*4/5,ProgramManager.SCREEN_HEIGHT*4/5,null);
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

package gradetrackerpro.graphics.screens;
import gradetrackerpro.ProgramManager;
import gradetrackerpro.graphics.buttons.ButtonExit;
import gradetrackerpro.graphics.buttons.ButtonLoadCourse;
import gradetrackerpro.graphics.buttons.ButtonNewCourse;
import gradetrackerpro.graphics.buttons.IButton;
import gradetrackerpro.transmission.IReceiver;
import gradetrackerpro.transmission.ITrigger;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class ScreenHome extends JPanel implements ITrigger, IReceiver{
  private ArrayList<IReceiver> receivers;
  private IButton newCourseButton;
  private IButton loadCourseButton;
  private IButton exitButton;
  public ScreenHome(){
    this.setBackground(ProgramManager.BACKGROUND_COLOR);
    this.receivers=new ArrayList<IReceiver>();
    this.newCourseButton=new ButtonNewCourse(ProgramManager.SCREEN_WIDTH/6,ProgramManager.SCREEN_HEIGHT*4/24,ProgramManager.SCREEN_WIDTH*2/3,40);
    this.newCourseButton.addReceiver(this);
    this.loadCourseButton=new ButtonLoadCourse(ProgramManager.SCREEN_WIDTH/6,ProgramManager.SCREEN_HEIGHT*7/24,ProgramManager.SCREEN_WIDTH*2/3,40);
    this.loadCourseButton.addReceiver(this);
    this.exitButton=new ButtonExit(ProgramManager.SCREEN_WIDTH/6,ProgramManager.SCREEN_HEIGHT*10/24,ProgramManager.SCREEN_WIDTH*2/3,40);
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
      this.exitButton.mouseAction(x,y,event);
      pushData("update",null);
    }
    else
      pushData(title,data);
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.setColor(Color.black);
    g.drawRect(0,0,this.getWidth()-1,this.getHeight()-1);
    drawTitle(g);
    this.newCourseButton.render(g);
    this.loadCourseButton.render(g);
    this.exitButton.render(g);
  }
  private void drawTitle(Graphics g){
    g.setColor(new Color(0,0,0,25));
    g.fillRect(0,0,this.getWidth(),40);
	g.setColor(Color.black);
    g.drawLine(0,40,this.getWidth(),40);
    g.setFont(new Font("Serif",Font.PLAIN,24));
    FontMetrics metrics = g.getFontMetrics();
    int width = metrics.stringWidth("GradeTrackerPro");
    int height = metrics.getHeight();
    g.setColor(Color.black);
    g.drawString("GradeTrackerPro",this.getWidth()/2-width/2,20+height/4);
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
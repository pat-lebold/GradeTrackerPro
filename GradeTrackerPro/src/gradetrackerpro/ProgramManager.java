package gradetrackerpro;
import gradetrackerpro.course.Course;
import gradetrackerpro.graphics.screens.ScreenChooseCourseName;
import gradetrackerpro.graphics.screens.ScreenCourse;
import gradetrackerpro.graphics.screens.ScreenHelp;
import gradetrackerpro.graphics.screens.ScreenHome;
import gradetrackerpro.graphics.screens.ScreenLoad;
import gradetrackerpro.transmission.DataManager;
import gradetrackerpro.transmission.IReceiver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
public class ProgramManager implements IReceiver{
	public static final Color BACKGROUND_COLOR = new Color(255,255,255);
	public static final int SCREEN_WIDTH = 250;
	public static final int SCREEN_HEIGHT = 400;
	private boolean loaded;
	private JFrame frame;
	private ScreenHome home;
	private Course course;
	private BufferedImage background;
	private BufferedImage header;
	private int lastX;
	private int lastY;
	public ProgramManager(){
		this.loaded=false;
		this.lastX=-1;
		this.lastY=-1;
		this.frame = new JFrame("GradeTrackerPro v1.0");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(250,400);
		this.frame.setLocation(100,100);
		this.frame.setResizable(false);
		this.frame.setUndecorated(true);
		this.background = new BufferedImage(250,400,BufferedImage.TYPE_INT_ARGB);
		this.paintBackground(this.background.getGraphics());
		try {
			this.header = ImageIO.read(new File("img/header.png"));
		} catch (IOException e) {
			System.out.println("NOT FOUND");
			this.header = new BufferedImage(250,200,BufferedImage.TYPE_INT_ARGB);
		}
		this.home=new ScreenHome(this.background, this.header);
		this.home.addReceiver(this);
		this.frame.getContentPane().add(home);
		ScreenKeyHandler keyboard = new ScreenKeyHandler();
		this.frame.setFocusable(true);
		this.frame.requestFocusInWindow();
		this.frame.addKeyListener(keyboard);
	}
	private void paintBackground(Graphics g){
		g.setColor(new Color(255,128,39));
		g.drawLine(ProgramManager.SCREEN_WIDTH/2, ProgramManager.SCREEN_HEIGHT-30,ProgramManager.SCREEN_WIDTH , ProgramManager.SCREEN_HEIGHT-30);
		g.setColor(new Color(150,150,150));
		g.drawLine(ProgramManager.SCREEN_WIDTH*2/3, ProgramManager.SCREEN_HEIGHT-20,ProgramManager.SCREEN_WIDTH , ProgramManager.SCREEN_HEIGHT-20);
		g.setColor(new Color(0,0,0));
		g.drawLine(ProgramManager.SCREEN_WIDTH*3/4, ProgramManager.SCREEN_HEIGHT-10,ProgramManager.SCREEN_WIDTH , ProgramManager.SCREEN_HEIGHT-10);
		/*double rand = Math.random()*3+4;
		for(int n=0;n<rand;n++){
			int width = (int)(Math.random()*15+20);
			int x = (int)(Math.random()*(250-width));
			int y = (int)(Math.random()*(175-width)+225);
			int alpha = 100+(int)(Math.random()*100);
			g.setColor(new Color(255,127,39,alpha));
			g.fillRect(x, y, width, width);
		}*/
	}
	public void begin(){
		this.frame.setVisible(true);
	}
	public void ping(String title, String[] data){
		if(title.equals("update"))
			frame.repaint();
		else if(title.equals("exit")){
			DataManager.saveCourse(course);
			frame.dispose();
		}
		else if(title.equals("home")){
			this.loaded=false;
			DataManager.saveCourse(course);
			this.frame.getContentPane().removeAll();
			this.home=new ScreenHome(this.background, this.header);
			this.home.addReceiver(this);
			this.frame.getContentPane().add(this.home);
			this.frame.revalidate();
		}
		else if(title.equals("key-data")){
			if(this.frame.getContentPane().getComponents().length>0)
				((IReceiver)this.frame.getContentPane().getComponents()[0]).ping(title, data);
		}
		else if(title.equals("new-course-name")){
			this.frame.getContentPane().removeAll();
			ScreenChooseCourseName screen = new ScreenChooseCourseName(this.background, this.header);
			screen.addReceiver(this);
			this.frame.getContentPane().add(screen);
			this.frame.revalidate();
		}
		else if(title.equals("new-course-create")){
			this.course = new Course(data[0]);
			this.course.addReceiver(this);
			this.frame.getContentPane().removeAll();
			ScreenCourse screen = new ScreenCourse(this.course,this.background,this.header);
			screen.addReceiver(this);
			this.frame.getContentPane().add(screen);
			this.frame.revalidate();
		}
		else if(title.equals("load-course-screen")){
			this.frame.getContentPane().removeAll();
			ScreenLoad screen = new ScreenLoad(this.header);
			screen.addReceiver(this);
			this.frame.getContentPane().add(screen);
			this.frame.revalidate();
		}
		else if(title.equals("load-course")){
			if(this.loaded)
				return;
			this.loaded=true;
			this.course = DataManager.loadCourse(data[0]);
			this.course.addReceiver(this);
			this.frame.getContentPane().removeAll();
			ScreenCourse screen = new ScreenCourse(this.course,this.background,this.header);
			screen.addReceiver(this);
			this.frame.getContentPane().add(screen);
			this.frame.revalidate();
		}
		else if(title.equals("help")){
			this.frame.getContentPane().removeAll();
			ScreenHelp screen = new ScreenHelp(this.header);
			screen.addReceiver(this);
			this.frame.getContentPane().add(screen);
			this.frame.revalidate();
		}
		else if(title.equals("mouse-data")){
			int x = (int)Double.parseDouble(data[0]);
			int y = (int)Double.parseDouble(data[1]);
			if(this.lastX==-1&&this.lastY==-1){
				this.lastX=x;
				this.lastY=y;
			}
			else{
				Point currentLocation = this.frame.getLocation();
				int newX = (int)currentLocation.getX()+x-lastX;
				int newY = (int)currentLocation.getY()+y-lastY;
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				double width = screenSize.getWidth();
				double height = screenSize.getHeight();
				if(newX<0)
					newX=0;
				if(newY<0)
					newY=0;
				if(newX+this.frame.getWidth()>width)
					newX=(int)width-this.frame.getWidth();
				if(newY+this.frame.getHeight()>height)
					newY=(int)height-this.frame.getHeight();
				this.frame.setLocation(newX,newY);
			}
		}
	}
	private class ScreenKeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent event){
			String[] data = {""+event.getKeyCode(),""+event.getKeyChar(),""+KeyEvent.KEY_PRESSED};
			ping("key-data",data);
		}
	}
}
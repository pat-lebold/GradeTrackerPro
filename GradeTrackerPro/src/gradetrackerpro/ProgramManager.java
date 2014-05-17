package gradetrackerpro;
import gradetrackerpro.course.Course;
import gradetrackerpro.graphics.screens.ScreenChooseCourseName;
import gradetrackerpro.graphics.screens.ScreenCourse;
import gradetrackerpro.graphics.screens.ScreenHome;
import gradetrackerpro.transmission.IReceiver;

import java.awt.Color;
import java.awt.Graphics;
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
	private JFrame frame;
	private ScreenHome home;
	private Course course;
	private BufferedImage background;
	private BufferedImage header;
	public ProgramManager(){
		this.frame = new JFrame("GradeTrackerPro v1.0");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(250,400);
		this.frame.setLocation(100,100);
		this.frame.setResizable(false);
		this.frame.setUndecorated(true);
		this.background = new BufferedImage(250,400,BufferedImage.TYPE_INT_ARGB);
		this.paintBackground(this.background.getGraphics());
		try {
			this.header = ImageIO.read(new File("header.png"));
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
		double rand = Math.random()*3+4;
		for(int n=0;n<rand;n++){
			int width = (int)(Math.random()*15+20);
			int x = (int)(Math.random()*(250-width));
			int y = (int)(Math.random()*(175-width)+225);
			int alpha = 75+(int)(Math.random()*125);
			g.setColor(new Color(255,127,39,alpha));
			g.fillRect(x, y, width, width);
		}
	}
	public void begin(){
		this.frame.setVisible(true);
	}
	public void ping(String title, String[] data){
		if(title.equals("update"))
			frame.repaint();
		else if(title.equals("exit"))
			frame.dispose();
		else if(title.equals("home")){
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
	}
	private class ScreenKeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent event){
			String[] data = {""+event.getKeyCode(),""+event.getKeyChar(),""+KeyEvent.KEY_PRESSED};
			ping("key-data",data);
		}
	}
}
package gradetrackerpro.graphics.container;
import gradetrackerpro.course.Grade;
import gradetrackerpro.course.GradeGrouping;
import gradetrackerpro.graphics.GradeCreationWidget;
import gradetrackerpro.graphics.GradeDisplay;
import gradetrackerpro.graphics.buttons.AColorButton;
import gradetrackerpro.graphics.buttons.ASizeChangingButton;
import gradetrackerpro.graphics.buttons.ButtonAdd;
import gradetrackerpro.graphics.buttons.ButtonCancel;
import gradetrackerpro.graphics.text.Label;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
public class GroupContainer extends AScrollableGraphicsContainer {
	private int percent;
	private Label title;
	private ASizeChangingButton cancelButton;
	private AColorButton addButton;
	private GradeCreationWidget createGrade;
	private GradeGrouping group;
	private ArrayList<GradeDisplay> gradeDisplays;
	public GroupContainer(double x, double y, int width, int height, Color slideColor, int percent, GradeGrouping group) {
		super(x,y,width,height,slideColor,4);
		this.group=group;
		this.gradeDisplays = new ArrayList<GradeDisplay>();
		this.percent=percent;
		this.title = new Label(super.getX()+8,super.getY()+8,super.getWidth()/2,super.getHeight()*3/24,this.percent+"%",Color.black);
		super.addComponent(this.title);
		this.cancelButton = new ButtonCancel(super.getX()+super.getWidth()-36-super.getHeight()*4/24,super.getY()+8,super.getHeight()*4/24,super.getHeight()*4/24,super.getHeight()/65);
		this.cancelButton.addReceiver(this);
		super.addComponent(this.cancelButton);
		this.addButton = this.createNewAddButton("new-grade");
		super.addComponent(this.addButton);
	}
	private void relocateElements(){
		double y = super.getY() + super.getHeight()*4/24 + 16;
		double dy = super.getY() - super.getMin();
		y -= dy;
		for(GradeDisplay display:this.gradeDisplays){
			display.setLocation(display.getX(),y);
			y += display.getHeight() + 8;
		}
		if(this.createGrade!=null){
			super.removeComponent(this.createGrade);
			this.createGrade.setLocation(this.createGrade.getX(),y);
			super.addComponent(this.createGrade);
			y += this.createGrade.getHeight() + 8;
		}
		else{
			super.removeComponent(this.addButton);
			this.addButton=this.createNewAddButton("new-grade");
			super.addComponent(this.addButton);
		}
		super.reevaluateRealHeight();
		super.updateComponents(0);
	}
	public GradeGrouping getGroup(){
		return this.group;
	}
	private ButtonAdd createNewAddButton(String message){
		int buttonY = super.realHeight + super.getMin() - (int)super.getY();
		ButtonAdd button = new ButtonAdd(super.getX()+8,buttonY+super.getY(),32,32,message);
		button.addReceiver(this);
		return button;
	}
	private void addGradeDisplay(Grade grade){
		int y = super.realHeight + super.getMin() - (int)super.getY();
		GradeDisplay display = new GradeDisplay(super.getX()+8,y+super.getY()+4,super.getWidth()-32,32, grade);
		super.addComponent(display);
		display.addReceiver(this);
		this.gradeDisplays.add(display);
		this.addReceiver(display);
	}
	public void ping(String title, String[] data){
		if(title.equals("mouse-data")){
			if(this.addButton==null&&super.pullComponents().contains(this.createGrade)){
				super.removeComponent(createGrade);
				this.createGrade = null;
			}
			int x = (int)Double.parseDouble(data[0]);
			int y = (int)Double.parseDouble(data[1]);
			int event = Integer.parseInt(data[2]);
			this.cancelButton.mouseAction(x,y,event);
			if(this.addButton.getVisibility())
				this.addButton.mouseAction(x,y,event);
			if(this.createGrade!=null)
				this.createGrade.ping(title,data);
			for(int n=this.gradeDisplays.size()-1;n>=0;n--)
				this.gradeDisplays.get(n).ping(title, data);
			super.ping(title, data);
		}
		else if(title.equals("key-data")){
			if(this.createGrade!=null)
				this.createGrade.ping(title,data);
		}
		else if(title.equals("cancel")){
			String[] pushData = {super.getX()+"",super.getY()+"",this.percent+""};
			super.pushData("cancel-group", pushData);
		}
		else if(title.equals("add-new-grade")){
			super.removeComponent(this.addButton);
			this.createGrade = new GradeCreationWidget(super.getX()+8,this.addButton.getY(),super.getWidth()-24-super.slideWidth,40);
			this.createGrade.addReceiver(this);
			this.addReceiver(createGrade);
			this.addButton.setVisibility(false);
			super.removeComponent(this.addButton);
			super.addComponent(this.createGrade);
			super.ping("update", null);
		}
		else if(title.equals("remove-widget")){
			if(this.createGrade==null)
				return;
			super.removeComponent(this.createGrade);
			this.createGrade=null;
			this.addButton = this.createNewAddButton("new-grade");
			super.addComponent(this.addButton);
			super.ping("update",null);
		}
		else if(title.equals("create-grade")){
			super.removeComponent(this.createGrade);
			this.createGrade.setVisibility(false);
			this.createGrade = null;
			String name = data[0];
			int actual = Integer.parseInt(data[1]);
			int total = Integer.parseInt(data[2]);
			Grade grade = new Grade(name,actual,total);
			this.group.addGrade(grade);
			this.updateTotalGrade();
			this.addGradeDisplay(grade);
			this.addButton = createNewAddButton("new-grade");
			super.addComponent(this.addButton);
			super.pushData("update", null);
		}
		else if(title.equals("remove-grade")){
			GradeDisplay display = null;
			int x = (int)Double.parseDouble(data[0]);
			int y = (int)Double.parseDouble(data[1]);
			for(GradeDisplay d:this.gradeDisplays){
				if(d.getX()==x&&(int)d.getY()==y){
					display = d;
					break;
				}
			}
			this.group.removeGrade(display.getGrade());
			super.removeComponent(display);
			this.removeReceiver(display);
			this.gradeDisplays.remove(display);
			this.relocateElements();
			this.updateTotalGrade();
			super.ping("update",null);
		}
	}
	@Override
	public void setLocation(double x, double y){
		double dy = super.getY()-y;
		super.setLocation(x, y);
		if(this.createGrade!=null)
			this.createGrade.setLocation(this.createGrade.getX(), this.createGrade.getY()+dy);
	}
	private void updateTotalGrade(){
		double contribution = this.group.getValue();
		String[] data = {contribution+""};
		super.pushData("update-grade", data);
	}
	public int getPercent(){
		return this.percent;
	}
	public void render(Graphics g){
		int y = (int)super.getY();
		int height = super.getHeight();
		if(y<65)
			y = 65;
		if(super.getY()+super.getHeight()>344)
			height = 344-(int)super.getY();
		g.setClip(0,y,250,height);
		super.render(g);
		g.setColor(new Color(0,0,0,25));
		g.fillRect((int)super.getX(), (int)super.getY(), super.getWidth()-1, super.getHeight()-1);
		g.setClip(0,65,250,279);
	}
}

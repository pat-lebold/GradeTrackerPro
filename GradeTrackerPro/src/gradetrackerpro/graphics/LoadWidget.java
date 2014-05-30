package gradetrackerpro.graphics;

import java.awt.Color;
import java.awt.Graphics;

import gradetrackerpro.graphics.buttons.ButtonConfirm;
import gradetrackerpro.graphics.container.AGraphicsContainer;
import gradetrackerpro.graphics.text.Label;
import gradetrackerpro.transmission.IReceiver;

public class LoadWidget extends AGraphicsContainer implements IReceiver{

	private String fileName;
	private Label title;
	private ButtonConfirm confirm;
	
	public LoadWidget(double x, double y, int width, int height, String fileName) {
		super(x, y, width, height);
		this.fileName=fileName;
		this.title = new Label(x, y, width*4/5, height, fileName, Color.black);
		super.addComponent(this.title);
		this.confirm = new ButtonConfirm(x+width*4/5+4, y+4, width/5-8, height-8, 4);
		this.confirm.addReceiver(this);
		super.addComponent(confirm);
	}
	
	public void setLocation(double x, double y){
		super.setLocation(x, y);
		this.title.setLocation(x, y);
		this.confirm.setLocation(x+super.getWidth()*4/5,y);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0,0,0,25));
		g.fillRect((int)super.getX(), (int)super.getY(), super.getWidth(), super.getHeight());
		super.renderComponents(g);
	}

	@Override
	public void ping(String title, String[] data) {
		if(title.equals("mouse-data")){
			int x = (int)Double.parseDouble(data[0]);
			int y = (int)Double.parseDouble(data[1]);
			int event = Integer.parseInt(data[2]);
			this.confirm.mouseAction(x, y, event);
			super.pushData("update", null);
		}
		else if(title.equals("confirm")){
			String[] pushdata = {this.fileName};
			super.pushData("load-course", pushdata);
		}
		else
			super.pushData(title,data);
	}

}

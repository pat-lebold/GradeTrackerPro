package gradetrackerpro.graphics;
import gradetrackerpro.graphics.buttons.ASizeChangingButton;
import gradetrackerpro.graphics.buttons.ButtonCancel;
import gradetrackerpro.graphics.buttons.ButtonConfirm;
import gradetrackerpro.graphics.container.AGraphicsContainer;
import gradetrackerpro.graphics.text.NumberTextBox;
import gradetrackerpro.graphics.text.TextBox;
import gradetrackerpro.transmission.IReceiver;
import java.awt.Color;
import java.awt.Graphics;
public class GroupCreationWidget extends AGraphicsContainer implements IReceiver {
	private int percent;
	private TextBox text;
	private ASizeChangingButton confirm;
	private ASizeChangingButton cancel;
	public GroupCreationWidget(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.text = new NumberTextBox(super.getX()+4,super.getY()+4,super.getWidth()/2-8,super.getHeight()-8,"%","text-percent");
		this.text.addReceiver(this);
		super.addComponent(this.text);
		this.confirm = new ButtonConfirm(this.text.getX()+this.text.getWidth()+8,this.text.getY(),super.getHeight()-8,super.getHeight()-8,(super.getHeight()-8)/16);
		this.confirm.addReceiver(this);
		super.addComponent(this.confirm);
		this.cancel = new ButtonCancel(this.confirm.getX()+this.confirm.getWidth()+8,this.confirm.getY(),this.confirm.getHeight(),this.confirm.getHeight(),(super.getHeight()-8)/16);
		this.cancel.addReceiver(this);
		super.addComponent(this.cancel);
		this.percent = 0;
	}
	public void ping(String title, String[] data){
		if(title.equals("key-data")){
			this.text.ping(title, data);
		}
		else if(title.equals("mouse-data")){
			int x = Integer.parseInt(data[0]);
			int y = Integer.parseInt(data[1]);
			int event = Integer.parseInt(data[2]);
			this.text.ping(title, data);
			this.confirm.mouseAction(x,y,event);
			this.cancel.mouseAction(x,y,event);
		}
		else if(title.equals("text-percent")){
			if(data[0].equals(""))
				this.percent = 0;
			else
				this.percent = Integer.parseInt(data[0]);
		}
		else if(title.equals("confirm")){
			String[] pushData = {""+percent};
			super.pushData("create-group", pushData);
		}
		else if(title.equals("cancel")){
			super.pushData("remove-widget",null);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(super.getX(),super.getY(),super.getWidth(),super.getHeight());
		this.text.render(g);
		this.confirm.render(g);
		this.cancel.render(g);
	}

}

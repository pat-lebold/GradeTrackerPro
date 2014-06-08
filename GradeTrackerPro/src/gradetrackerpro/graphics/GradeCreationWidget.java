package gradetrackerpro.graphics;
import gradetrackerpro.graphics.buttons.ASizeChangingButton;
import gradetrackerpro.graphics.buttons.ButtonCancel;
import gradetrackerpro.graphics.buttons.ButtonConfirm;
import gradetrackerpro.graphics.container.AGraphicsContainer;
import gradetrackerpro.graphics.text.NumberTextBox;
import gradetrackerpro.graphics.text.TextBox;
import gradetrackerpro.transmission.IReceiver;
import java.awt.Graphics;
public class GradeCreationWidget extends AGraphicsContainer implements IReceiver {
	private int earned;
	private int total;
	private TextBox textName;
	private NumberTextBox textEarned;
	private NumberTextBox textTotal;
	private ASizeChangingButton confirm;
	private ASizeChangingButton cancel;
	public GradeCreationWidget(double x, double y, int width, int height) {
		super(x, y, width, height);
		this.textName = new TextBox(super.getX()+4,super.getY()+4,super.getWidth()/3-8,22,"name","text-name");
		this.textName.addReceiver(this);
		this.textEarned = new NumberTextBox(this.textName.getX()+this.textName.getWidth()+4,this.textName.getY(),super.getWidth()/8+4,22,"try","text-earned");
		this.textEarned.addReceiver(this);
		this.textTotal = new NumberTextBox(this.textEarned.getX()+this.textEarned.getWidth()+4,this.textEarned.getY(),super.getWidth()/8+4,22,"tot","text-total");
		this.textTotal.addReceiver(this);
		this.confirm = new ButtonConfirm(this.textTotal.getX()+this.textTotal.getWidth()+2,this.textTotal.getY(),22,22,2);
		this.confirm.addReceiver(this);
		this.cancel = new ButtonCancel(this.confirm.getX()+this.confirm.getWidth()+2,this.confirm.getY(),22,22,2);
		this.cancel.addReceiver(this);
		this.earned = 0;
		this.total = 0;
	}
	public void ping(String title, String[] data){
		if(title.equals("key-data")){
			this.textName.ping(title, data);
			this.textEarned.ping(title,data);
			this.textTotal.ping(title,data);
		}
		else if(title.equals("mouse-data")){
			int x = (int)Double.parseDouble(data[0]);
			int y = (int)Double.parseDouble(data[1]);
			int event = Integer.parseInt(data[2]);
			this.textName.ping(title, data);
			this.textEarned.ping(title,data);
			this.textTotal.ping(title, data);
			this.confirm.mouseAction(x,y,event);
			this.cancel.mouseAction(x,y,event);
		}
		else if(title.equals("text-earned")){
			if(data[0].equals(""))
				this.earned=0;
			else
				this.earned = Integer.parseInt(data[0]);
		}
		else if(title.equals("text-total")){
			if(data[0].equals(""))
				this.total=0;
			else
				this.total = Integer.parseInt(data[0]);
		}
		else if(title.equals("confirm")){
			String[] pushData = {this.textName.getFullText(),""+earned,""+total};
			super.pushData("create-grade", pushData);
		}
		else if(title.equals("cancel")){
			super.pushData("remove-widget",null);
		}
		else if(title.equals("update")){
			super.pushData("update",null);
		}
	}
	
	@Override
	public void setLocation(double x, double y){
		double dy = y-super.getY();
		this.textName.setLocation(this.textName.getX(), this.textName.getY()+dy);
		this.textEarned.setLocation(this.textEarned.getX(),this.textEarned.getY()+dy);
		this.textTotal.setLocation(this.textTotal.getX(),this.textTotal.getY()+dy);
		this.confirm.setLocation(this.confirm.getX(), this.confirm.getY()+dy);
		this.cancel.setLocation(this.cancel.getX(), this.cancel.getY()+dy);
		super.setLocation(x, y+dy);
	}

	@Override
	public void render(Graphics g) {
		this.textName.render(g);
		this.textEarned.render(g);
		this.textTotal.render(g);
		this.confirm.render(g);
		this.cancel.render(g);
	}

}

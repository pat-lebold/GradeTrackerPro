package gradetrackerpro.graphics.buttons;
import gradetrackerpro.graphics.AGraphicsEntity;
import gradetrackerpro.transmission.IReceiver;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
public abstract class ASizeChangingButton extends AGraphicsEntity implements IButton {
	private ArrayList<IReceiver> receivers;
	private int sizeChange;
	private boolean in;
	public ASizeChangingButton(int x, int y, int width, int height, int sizeChange){
		super(x,y,width,height);
		this.receivers = new ArrayList<IReceiver>();
		this.sizeChange=sizeChange;
		this.in=false;
	}
	public void pushData(String title, String[] data) {
		for(IReceiver receiver:this.receivers){
			receiver.ping(title,data);
		}
	}
	public void addReceiver(IReceiver receiver) {
		this.receivers.add(receiver);
	}
	public void removeReceiver(IReceiver receiver) {
		this.receivers.remove(receiver);
	}
	public void onEnter() {
		if(!in){
			super.setLocation(super.getX()+this.sizeChange,super.getY()+this.sizeChange);
			super.setWidth(super.getWidth()-this.sizeChange*2);
			super.setHeight(super.getHeight()-this.sizeChange*2);
		}
		this.in=true;
	}
	public void onExit() {
		if(in){
			super.setLocation(super.getX()-this.sizeChange,super.getY()-this.sizeChange);
			super.setWidth(super.getWidth()+this.sizeChange*2);
			super.setHeight(super.getHeight()+this.sizeChange*2);
		}
		this.in=false;
	}
	public void onPress(){
		this.pushData();
	}
	public void onRelease(){}
	public void pushData(){
		pushData("",null);
	}
	public void mouseAction(int x, int y, int event){
		if(event==MouseEvent.MOUSE_MOVED||event==MouseEvent.MOUSE_DRAGGED){
			if(x>super.getX()&&x<super.getX()+super.getWidth()&&y>super.getY()&&y<super.getY()+super.getHeight())
				this.onEnter();
			else
				this.onExit();
		}
		else if(event==MouseEvent.MOUSE_PRESSED&&x>super.getX()&&x<super.getX()+super.getWidth()&&y>super.getY()&&y<super.getY()+super.getHeight())
			this.onPress();
		else if(event==MouseEvent.MOUSE_RELEASED)
			this.onRelease();
	}

}

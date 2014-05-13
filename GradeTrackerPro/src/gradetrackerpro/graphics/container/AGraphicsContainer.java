package gradetrackerpro.graphics.container;
import gradetrackerpro.graphics.AGraphicsEntity;
import gradetrackerpro.transmission.IReceiver;
import gradetrackerpro.transmission.ITrigger;

import java.awt.Graphics;
import java.util.ArrayList;
public abstract class AGraphicsContainer extends AGraphicsEntity implements ITrigger{
	private ArrayList<AGraphicsEntity> components;
	private ArrayList<IReceiver> receivers;
	public AGraphicsContainer(int x, int y, int width, int height){
		super(x,y,width,height);
		this.components = new ArrayList<AGraphicsEntity>();
		this.receivers = new ArrayList<IReceiver>();
	}
	public void addComponent(AGraphicsEntity entity){
		this.components.add(entity);
	}
	public void removeComponent(AGraphicsEntity entity){
		this.components.remove(entity);
	}
	public ArrayList<AGraphicsEntity> pullComponents(){
		return this.components;
	}
	public void empty(){
		this.components = new ArrayList<AGraphicsEntity>();
	}
	public void pushData(String title, String[] data){
		for(IReceiver receiver:this.receivers){
			receiver.ping(title,data);
		}
	}
	public void addReceiver(IReceiver receiver){
		this.receivers.add(receiver);
	}
	public void removeReceiver(IReceiver receiver){
		this.receivers.remove(receiver);
	}
	public void renderComponents(Graphics g){
		for(AGraphicsEntity entity:this.components){
			if(entity.getVisibility()){
				entity.render(g);
			}
		}
	}
}
package gradetrackerpro.graphics.text;
import gradetrackerpro.graphics.AGraphicsEntity;
import gradetrackerpro.transmission.IReceiver;
import gradetrackerpro.transmission.ITrigger;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
public class TextBox extends AGraphicsEntity implements ITrigger, IReceiver{
	private ArrayList<IReceiver> receivers;
	private String message;
	private String defaultText;
	protected String hardText;
	protected String softText;
	protected Font font;
	private boolean enabled;
	public TextBox(double x, double y, int width, int height, String defaultText, String message){
		super(x,y,width,height);
		this.receivers = new ArrayList<IReceiver>();
		this.defaultText=defaultText;
		this.font = new Font("Serif",Font.PLAIN,24);
		this.enabled = false;
		this.hardText = "";
		this.softText = this.defaultText;
		this.message=message;
	}
	public void setEnabled(boolean enabled){
		this.enabled=enabled;
	}
	public String getFullText(){
		return this.hardText + this.softText;
	}
	public void setFullText(String fullText){
		if(fullText==null){
			this.softText = "";
			if(this.hardText.equals(""))
				this.softText = this.defaultText;
			return;
		}
		this.softText = fullText.substring(this.hardText.length());
	}
	public void setHardText(String hardText){
		this.hardText = hardText;
	}
	public void render(Graphics g){
		g.setColor(Color.white);
		g.fillRect((int)super.getX(), (int)super.getY(), super.getWidth()-1, super.getHeight()-1);
		g.setColor(Color.black);
		g.drawRect((int)super.getX(),(int)super.getY(),super.getWidth()-1,super.getHeight()-1);
		if(this.enabled)
			g.drawRect(1+(int)super.getX(),1+(int)super.getY(),super.getWidth()-3,super.getHeight()-3);
		this.drawText(g);
	}
	public void drawText(Graphics g){
		g.setFont(this.font);
		FontMetrics metrics = g.getFontMetrics();
		int fontHeight = metrics.getMaxAscent() + metrics.getDescent();
		int hardWidth = metrics.stringWidth(this.hardText);
		int finalWidth = metrics.stringWidth(this.hardText + this.softText);
		if(finalWidth > super.getWidth()-8){
			this.font = new Font("Serif",Font.PLAIN,this.font.getSize()-1);
			drawText(g);
		}
		else{
			this.font = new Font("Serif",Font.PLAIN,24);
			g.setColor(Color.black);
			g.drawString(this.hardText, 4+(int)super.getX(), (int)super.getY() + super.getHeight()/2 + fontHeight/4);
			g.setColor(new Color(255,127,39,255));
			g.drawString(this.softText, hardWidth + 4 + (int)super.getX(), (int)super.getY() + super.getHeight()/2 + fontHeight/4);
		}
	}
	public void addReceiver(IReceiver receiver){
		this.receivers.add(receiver);
	}
	public void removeReceiver(IReceiver receiver){
		this.receivers.remove(receiver);
	}
	public void ping(String title, String[] data){
		if(title.equals("mouse-data")){
			int mouseX = (int)Double.parseDouble(data[0]);
			int mouseY = (int)Double.parseDouble(data[1]);
			int event = Integer.parseInt(data[2]);
			if(event==MouseEvent.MOUSE_PRESSED){
				if(mouseX>super.getX()&&mouseX<super.getX()+super.getWidth()&&mouseY>super.getY()&&mouseY<super.getY()+super.getHeight())
					this.setEnabled(true);
				else
					this.setEnabled(false);
				pushData("update",null);
			}
		}
		if(title.equals("key-data")){
			if(this.enabled){
				int keyCode = Integer.parseInt(data[0]);
				if(keyCode==KeyEvent.VK_BACK_SPACE){
					if(this.hardText.length()==1)
						this.hardText = "";
					else if(this.hardText.length()>1)
						this.hardText = this.hardText.substring(0,this.hardText.length()-1);
					String[] pushData = {hardText};
					this.pushData(this.message, pushData);
				}
				else{
					char keyChar = data[1].toCharArray()[0];
					if(Character.isAlphabetic(keyChar) || Character.isDigit(keyChar) || keyChar == ' '){
						this.hardText += keyChar;
						String[] pushData = {this.hardText};
						this.pushData(this.message,pushData);
					}
				}	
				this.setFullText(null);
				this.pushData("update",null);
			}
		}
	}
	public void pushData(String title, String[] data){
		for(IReceiver receiver:this.receivers)
			receiver.ping(title,data);
	}
}


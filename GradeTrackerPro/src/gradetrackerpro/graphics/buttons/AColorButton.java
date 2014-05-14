package gradetrackerpro.graphics.buttons;
import gradetrackerpro.graphics.AGraphicsEntity;
import gradetrackerpro.transmission.IReceiver;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
public abstract class AColorButton extends AGraphicsEntity implements IButton{
  private ArrayList<IReceiver> receivers;
  private String name;
  protected Color color;
  private Color baseColor;
  private Color hoverColor;
  private Color pressColor;
  private boolean mouseIn;
  private boolean mouseDown;
  public AColorButton(double x, double y, int width, int height, String name, Color baseColor, Color hoverColor, Color pressColor){
    super(x,y,width,height);
    this.receivers=new ArrayList<IReceiver>();
    this.name=name;
    this.baseColor=baseColor;
    this.hoverColor=hoverColor;
    this.pressColor=pressColor;
    this.color=this.baseColor;
    this.mouseIn=false;
    this.mouseDown=false;
  }
  public void onEnter(){
    this.mouseIn=true;
    if(this.mouseDown)
      this.color=this.pressColor;
    else
    this.color=this.hoverColor;
  }
  public void onExit(){
    this.mouseIn=false;
    if(!mouseDown)
      this.color=this.baseColor;
    else
      this.color=this.pressColor;
  }
  public void onPress(){
    this.mouseDown=true;
    this.color=this.pressColor;
    this.pushData();
  }
  public void onRelease(){
    this.mouseDown=false;
    if(this.mouseIn)
      this.color=this.hoverColor;
    else
      this.color=this.baseColor;
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
  private void pushData(){
    pushData("",null);
  }
  public void pushData(String title, String[] data){
    for(IReceiver receiver:this.receivers)
      receiver.ping(title,data);
  }
  public void addReceiver(IReceiver receiver){
    this.receivers.add(receiver);
  }
  public void removeReceiver(IReceiver receiver){
    this.receivers.remove(receiver);
  }
  public void render(Graphics g){
    g.setColor(this.color);
    g.fillRect((int)super.getX(), (int)super.getY(), super.getWidth(), super.getHeight());
    g.setFont(new Font("Serif",Font.PLAIN,24));
     FontMetrics metrics = g.getFontMetrics();
   int width = metrics.stringWidth(this.name);
   int height = metrics.getHeight();
   g.setColor(Color.black);
   g.drawString(this.name,(int)super.getX()+(int)super.getWidth()/2-width/2,(int)super.getY()+super.getHeight()/2+height/4);
  }
}
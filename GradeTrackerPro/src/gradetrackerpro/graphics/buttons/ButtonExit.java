package gradetrackerpro.graphics.buttons;

import java.awt.Color;
public class ButtonExit extends AColorButton{
  public ButtonExit(double x, double y, int width, int height){
    super(x,y,width,height,"Exit",new Color(0,0,0,50),new Color(0,0,0,100),new Color(0,0,0,150));
  }
  public void pushData(String title, String[] data){
    super.pushData("exit",null);
  }
}
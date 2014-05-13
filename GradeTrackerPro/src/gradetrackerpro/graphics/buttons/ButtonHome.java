package gradetrackerpro.graphics.buttons;

import java.awt.Color;
public class ButtonHome extends AColorButton{
  public ButtonHome(int x, int y, int width, int height){
    super(x,y,width,height,"Home",new Color(0,0,0,50),new Color(0,0,0,100),new Color(0,0,0,150));
  }
  public void pushData(String title, String[] data){
    super.pushData("home",null);
  }
}
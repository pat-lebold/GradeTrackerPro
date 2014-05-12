package gradetrackerpro.graphics.buttons;

import java.awt.Color;
public class ButtonGo extends AColorButton{
  public ButtonGo(int x, int y, int width, int height){
    super(x,y,width,height,"Go",new Color(255,255,255,100),new Color(255,255,255,150),new Color(255,255,255,200));
  }
  public void pushData(String title, String[] data){
    super.pushData("go",null);
  }
}
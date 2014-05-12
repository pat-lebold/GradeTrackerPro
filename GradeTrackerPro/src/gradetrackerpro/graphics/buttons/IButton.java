package gradetrackerpro.graphics.buttons;

import gradetrackerpro.graphics.IRenderable;
import gradetrackerpro.transmission.ITrigger;

public interface IButton extends ITrigger, IRenderable{
  public void onEnter();
  public void onExit();
  public void onPress();
  public void onRelease();
  public void mouseAction(int x, int y, int event);
}
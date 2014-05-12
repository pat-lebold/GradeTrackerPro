package gradetrackerpro.graphics;


public abstract class AGraphicsEntity implements IRenderable{
  private int x;
  private int y;
  private int width;
  private int height;
  private boolean visibility;
  public AGraphicsEntity(int x, int y, int width, int height){
    this.x=x;
    this.y=y;
    this.width=width;
    this.height=height;
    this.visibility=true;
  }
  public int getX(){
    return this.x;
  }
  public int getY(){
    return this.y;
  }
  public int getWidth(){
    return this.width;
  }
  public int getHeight(){
    return this.height;
  }
  public void setLocation(int x, int y){
    this.x=x;
    this.y=y;
  }
  public void setWidth(int width){
	  this.width=width;
  }
  public void setHeight(int height){
	  this.height=height;
  }
  public void setVisibility(boolean visibility){
    this.visibility = visibility;
  }
  public boolean getVisibility(){
    return this.visibility;
  }
}
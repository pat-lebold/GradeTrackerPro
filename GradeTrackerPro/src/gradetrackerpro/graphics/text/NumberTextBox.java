package gradetrackerpro.graphics.text;
import java.awt.event.KeyEvent;
public class NumberTextBox extends TextBox {
	public NumberTextBox(double x,double y,int width,int height,String defaultText,String message) {
		super(x,y,width,height,defaultText,message);
	}
	public void ping(String title, String[] data){
		if(title.equals("key-data")){
			int keyCode = Integer.parseInt(data[0]);
			if(keyCode==KeyEvent.VK_BACK_SPACE)
				super.ping(title,data);
			char keyChar = data[1].toCharArray()[0];
			if(Character.isDigit(keyChar))
				super.ping(title,data);
		}
		else
			super.ping(title,data);
	}

}

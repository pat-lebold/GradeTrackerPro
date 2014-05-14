package gradetrackerpro.graphics.text;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
public class Label extends TextBox {
	private Color color;
	public Label(double x, double y, int width, int height, String text, Color color) {
		super(x, y, width, height, "", "");
		this.color=color;
		super.hardText=text;
		super.ping("update", null);
	}
	@Override
	public void render(Graphics g){
		g.setFont(super.font);
		FontMetrics metrics = g.getFontMetrics();
		int fontHeight = metrics.getMaxAscent() + metrics.getDescent();
		int hardWidth = metrics.stringWidth(super.hardText);
		int finalWidth = metrics.stringWidth(super.hardText + super.softText);
		if(finalWidth > super.getWidth()-8){
			super.font = new Font("Serif",Font.PLAIN,super.font.getSize()-1);
			this.render(g);
		}
		else{
			super.font = new Font("Serif",Font.PLAIN,24);
			g.setColor(this.color);
			g.drawString(super.hardText, 4+(int)super.getX(), (int)super.getY() + super.getHeight()/2 + fontHeight/4);
			g.setColor(Color.gray);
			g.drawString(super.softText, hardWidth + 4 + (int)super.getX(), (int)super.getY() + super.getHeight()/2 + fontHeight/4);
		}
	}
}

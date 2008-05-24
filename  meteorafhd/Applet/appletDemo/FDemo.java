package appletDemo;

import java.applet.*;
import java.awt.*;

public class FDemo extends Applet {
	String s;
	public void init(){
		s = "xxx";
	}
	public void paint(Graphics g){
		Font f = new Font("¿¬Ìå",Font.BOLD,20);
		g.setFont(f);
		g.setColor(Color.RED);
		g.drawString(s,70,80);
	}
}

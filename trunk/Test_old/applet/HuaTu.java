package applet;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
public class HuaTu implements MouseMotionListener,ActionListener{
	static final int r=3;
	static int ox, oy;
	static int flag=1;
	public static void main(String arg[]){
		JFrame f=new JFrame("Paint");
		Container p=f.getContentPane();
		p.setLayout(new FlowLayout());
		JButton jButton1 = new JButton("ºì");
		JButton jButton2 = new JButton("ºÚ");
		JButton jButton3 = new JButton("ÍË³ö");
		//Graphics g.setColor(new Color(255,0,0));
		//ButtonEvent arg=new ButtonEvent(arg);
		jButton1.addActionListener(new HuaTu());
		jButton2.addActionListener(new  HuaTu());
		jButton3.addActionListener(new HuaTu());
		p.addMouseMotionListener(new  HuaTu());
		p.add(jButton1);
		p.add(jButton2);
		p.add(jButton3);  
		f.setSize(200,200);
		f.show();
	}
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("ºì")){
			flag=1;
		}
		else if (e.getActionCommand().equals("ºÚ")){
			flag=2;
		}
		else if (e.getActionCommand().equals("ÍË³ö"))
			System.exit(0);
	}
	public void mouseDragged(MouseEvent e){
		Container c=(Container)e.getSource();
		Graphics g=c.getGraphics();
		if (ox>=0) {
			if(flag==1){
				g.setColor(new Color(168,0,255));
				g.drawLine(ox,oy,e.getX(),e.getY());}
			else
			{ g.setColor(new Color(0,0,0));
			g.drawLine(ox,oy,e.getX(),e.getY());}
		}
		ox=e.getX();oy=e.getY();
	}
	public void mouseMoved(MouseEvent e){
		ox=-1;oy=-1;
	}
}

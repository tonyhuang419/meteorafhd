package menu;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class FrameX extends JFrame{
	JMenuBar jmb = new JMenuBar(); 
	JMenu jm = new JMenu("test");
	JMenuItem jmi = new JMenuItem("xx");
	FrameX(String title){
		super(title);
		this.setSize(500,500);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMeun();
		add(jmb);
		this.setVisible(true);
	}
	void addMeun(){
		jm.add(jmi);
		jmb.add(jm);
		jmi.addActionListener(new menuListen());
	}
//	class menuListen extends MouseAdapter{
//		public void mouseClicked(MouseEvent e){
//			System.out.println("1");
//		}
//	}
	class menuListen implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("1");
		}	
	}
}


public class MenuDemo {
	public static void main(String[] args) {
		new FrameX("demo");
	}
}

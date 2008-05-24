package rmi2;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

class FrameX extends javax.swing.JFrame{

	private static final long serialVersionUID = 1281663456295277246L;
	
	JTextField jt1 = new JTextField("name",20);
	JTextField jt2 = new JTextField("dept",20);
	JButton jb = new JButton("send");
	String name,dept;
	public FrameX(String title){
		super(title);
		this.setLayout(new FlowLayout());
		this.add(jt1);
		this.add(jt2);
		this.add(jb);
		jb.addActionListener(new btListen());
	}
	
	class btListen implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			name = jt1.getText();
			dept = jt2.getText();
			RClient rc = new RClient();
			rc.go(name,dept);
		}	
	}
}

public class RClientUI {
	public static void main(String[] args) {
		FrameX f = new FrameX("Demo");
		f.setSize(500,400);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

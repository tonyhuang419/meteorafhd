package rmi2;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RServerUI extends JFrame{

	private static final long serialVersionUID = 6141204467440540130L;
	int delay = 1000;
	int period = 1000;
	Timer timer = new Timer();

	JTextArea jta = new JTextArea(10,30);
	Container contentpane = getContentPane();
	JScrollPane scrollpane = new JScrollPane(jta);	
	JLabel jtime = new JLabel();   
	
	public RServerUI(String title){
		super(title);
		this.setLayout(new FlowLayout());
		contentpane.add(scrollpane);
		this.add(jtime);
		setSize(500,400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Date   now = new   Date();
				jtime.setText(now.toString());
			}
		}, delay, period);	
	}
	public void setAreaName(String _name){
		jta.setText(_name);
	}
	public void setAreaDept(String _dept){
		jta.setText(_dept);
	}
	public String  getAreaTest(){
		return jta.getText();
	}
}

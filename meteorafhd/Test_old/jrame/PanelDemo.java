package jrame;

//import java.awt.*;
//import javax.swing.*;
//
//
//class FrameX extends JFrame{
//	JPanel jp = new JPanel();
//	JButton jb = new JButton("Sure");
//	FrameX(String title){
//		super(title);
////		this.setSize(500,500);
////		this.setVisible(true);
////		this.setLayout(new FlowLayout());
////		add(jp);
////		add(jb);
//		Container c = new Container();
//		this.add
//		
//	}
//}
//
//public class JframeX {
//	public static void main(String[] args) {
//		FrameX f = new FrameX("Demo");
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//	}
//}
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
class  PanelDemo extends JFrame {
	
	public PanelDemo( String title){
		super(title);
		//Container c=getContentPane();
		JPanel cpane = new JPanel();
		JButton ok = new JButton("确定");
		JButton cancel = new JButton("取消");
		JButton out = new JButton("Out Scode");
		out.addMouseListener(new MouseL());
		//cpane.add(ok);
		this.setLayout(new FlowLayout());
		cpane.add(ok);
		cpane.add(cancel);
		add(cpane);
		add(out);
		//c.add(cpane,BorderLayout.SOUTH);
	}
	class MouseL extends MouseAdapter{
		public void mouseClicked(MouseEvent arg0) {
			try {
				OutSC();
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}	
	}
	public void OutSC() throws IOException{
		String scode="";		
		BufferedReader br = new BufferedReader(new FileReader("PanelDemo.java"));
		scode = scode + br.readLine();
		while(br.readLine()!=null)
			scode = scode + br.readLine();
		
		File f = new File("c:\\sc.txt");
		if(!f.exists()){
			f.createNewFile();
			PrintWriter bw = new  PrintWriter(new BufferedWriter(new FileWriter(f)));
			bw.write(scode);
			bw.flush();
			bw.close();
		}
		else{
			PrintWriter bw = new  PrintWriter(new BufferedWriter(new FileWriter(f)));
			bw.write(scode);
			bw.flush();
			bw.close();
		}
	}

	public static void main(String args[]) {
		PanelDemo pd=new PanelDemo("JPanel测试");
		pd.setSize(300,200);
		pd.setVisible(true);
		pd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
} 

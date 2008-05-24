package su_1;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

class FrameX extends JFrame{
	JTable jt = new JTable(9,9);
	JButton jb = new JButton("Produce");
	int[][][] answer = new int[Struct.size][Struct.size][2];
	FrameX(String title){
		jt.setRowHeight(30);
		jt.setFont(new Font("ËÎÌו",Font.PLAIN,20));
		jt.setRowSelectionAllowed(false);
		this.setLayout(new GridLayout(2,1));
		this.add(jt);	
		this.add(jb);
		jb.addActionListener(new buttonListen());
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				jt.setValueAt(0,i,j);			
	}

	public class DisplayNum extends Thread{
		public void run(){
			display();
		}
		public void display(){
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++)
					jt.setValueAt(0,i,j);
			try{
				for(int i=0;i<9;i++){
					for(int j=0;j<9;j++){
						sleep(50);
						jt.setValueAt(answer[i][j][0]+"",i, j);
					}			
				}
			}catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}

	class buttonListen implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Table t = new Table();
			t.setTime();
			while( ! t.solve() ){
				t.setJudgement(false);
				t.setTime();
				t.solve();
			}
			answer = t.getTable();
			new DisplayNum().start();
		}	
	}	
}

public class FaceSuDoku {
	public static void main(String[] args) {
		FrameX fx = new FrameX("It a Demo");
		fx.setSize(400,500);
		fx.setVisible(true);
		fx.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

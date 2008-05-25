package ace;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class timeDemo {
	static Frame jf = new Frame();
	static Button jb = new Button("click me");
	static Label jl = new Label("time");
	public static void main(String[] args) {
		jf.setLayout(new FlowLayout());
		jf.add(jl);
		jf.add(jb);
		jb.addMouseListener(new listen());
		jf.setSize(500,500);
		jf.setVisible(true);	
	}
	static class listen extends MouseAdapter{
		public void mouseClicked(MouseEvent arg0) {
			Thread t =Thread.currentThread();
			try{
					for(int i=10;i>0;i--){
						jl.setText(i+"");
						System.out.println(i);
						Thread.sleep(500);
					}
				}
				catch(java.lang.InterruptedException ie){
					System.out.println("sth wrong");
				}
				finally{
					System.exit(0);
				}
		}		
	}

}

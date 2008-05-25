package ace;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class PanelX extends JPanel {

	JSlider js = new JSlider(0, 360, 10);

	JButton jb = new JButton("xx");

	int x = 0;

	PanelX() {
		this.setLayout(new FlowLayout());
		add(js);
		add(jb);
		jb.addMouseListener(new MouseL());
		js.addChangeListener(new SliderListen());
		
	}

	public void paintComponent(Graphics g) {
		if (x > 100) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.black);
		}
		g.clearRect(0,0,500, 500);
		g.fillArc(200, 200, 200, 200, 30, x);
		
		repaint();
	}



	class SliderListen implements ChangeListener {
		public void stateChanged(ChangeEvent ce) {
			x = js.getValue();
			System.out.println(x);
		}
	}

	class MouseL extends MouseAdapter {

		public void mouseClicked(MouseEvent arg0) {

		}
	}
}
public class AceDemo {
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		jf.setSize(500, 500);
		jf.add(new PanelX());
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
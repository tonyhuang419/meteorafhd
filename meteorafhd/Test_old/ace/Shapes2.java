package ace;

//import java.awt.Color;
//import java.awt.Graphics;

//import javax.swing.JFrame;
//import javax.swing.JPanel;

//public class MainClass extends JPanel {

//public void paint(Graphics g) {
//g.setColor (Color.gray);
//g.draw3DRect (25, 10, 50, 75, true);
//g.draw3DRect (25, 110, 50, 75, false);
//g.fill3DRect (100, 10, 50, 75, true);
//g.fill3DRect (100, 110, 50, 75, false);
//}

//public static void main(String[] args) {
//JFrame frame = new JFrame();
//frame.getContentPane().add(new MainClass());

//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//frame.setSize(200, 200);
//frame.setVisible(true);
//}
//}

//import java.awt.Graphics;

//import javax.swing.JFrame;
//import javax.swing.JPanel;

//public class MainClass extends JPanel {

//public void paint(Graphics g) {
//g.drawArc(25, 25, 120, 120, 45, 270);

//}

//public static void main(String[] args) {
//JFrame frame = new JFrame();
//frame.getContentPane().add(new MainClass());

//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//frame.setSize(200,200);
//frame.setVisible(true);
//}
//}

//import java.awt.*;
//import java.awt.geom.*;
//import javax.swing.*;

//public class Shapes2 extends JFrame
//{
//public Shapes2(){
//super("Drawing 2D shapes");

//getContentPane().setBackground(Color.WHITE);
//setSize(400,400);
//setVisible(true);
//}

//import java.awt.*;
//import java.awt.geom.*;
//import java.awt.image.*;
//import javax.swing.*;

//public class Shapes extends JFrame
//{
//public Shapes(){
//super("Drawing 2D shapes");

//setSize(425,160);
//setVisible(true);
//}

//public void paint(Graphics g)
//{
//super.paint(g);

//Graphics2D g2d = (Graphics2D)g; // ǿ��ת��ΪGraphics2D����

////GradientPaint���Խ�����ɫ����ͼ�Σ���ɫ��ȵ���ʼ����(5,30)����������(35,100)
//g2d.setPaint(new GradientPaint(5,35,Color.BLUE,35,100,Color.YELLOW,true));
////��Graphics2D���fill��������һ������Shape����
//g2d.fill(new Ellipse2D.Double(5,35,65,100));

//g2d.setPaint(Color.RED);
//g2d.setStroke(new BasicStroke(10.0f)); // BasicStroke���ṩһ���캯��ָ���߿�Ϊ10������
//g2d.draw(new Rectangle2D.Double(80,35,65,100));

////BufferedImage(java.awt.image��)���󴴽����ģʽ
////BufferedImage����Ŀ�ȸ߶Ⱦ�Ϊ10�����أ���ʹ��RGB��ɫ���е���ɫ�洢ͼ��
//BufferedImage buffImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);

////����createGraphics��������һ������BufferedImage�ڲ����л��Ƶ�Graphics2D����
//Graphics2D gg = buffImage.createGraphics();

////�������ģʽ
//gg.setColor(Color.YELLOW);
//gg.fillRect(0,0,10,10);
//gg.setColor(Color.BLACK);
//gg.drawRect(1,1,6,6);
//gg.setColor(Color.BLUE);
//gg.fillRect(1,1,3,3);
//gg.setColor(Color.RED);
//gg.fillRect(4,4,3,3);

////TexturePaint����ʹ�ô洢�����������BufferedImage�е�ͼ����Ϊһ�����ͼ�ε��������
//g2d.setPaint(new TexturePaint(buffImage,new Rectangle(10,10)));
//g2d.fill(new RoundRectangle2D.Double(155,35,75,100,50,50));

//g2d.setPaint(Color.WHITE);
//g2d.setStroke(new BasicStroke(6.0f));
////Arc2D.PIE������ʾ������ֱ�߷�ջ���
//g2d.draw(new Arc2D.Double(240,35,75,100,0,270,Arc2D.PIE));

//g2d.setPaint(Color.GREEN);
//g2d.draw(new Line2D.Double(395,35,320,150));

//float dashes[] = {10}; // �����������߶�

//g2d.setPaint(Color.YELLOW);
////BasicStroke.CAP_ROUND��ʾֱ�߶˵�ΪԲ�ǣ�BasicStroke.JOIN_ROUND��ʾֱ�����ӵ���Բ��
////���һ������ָ����1�����߶���dashes�����е���ʼ����
//g2d.setStroke(new BasicStroke(4,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,10,
//dashes,0));
//g2d.draw(new Line2D.Double(320,35,395,150));
//}

//public static void main(String args[])
//{
//Shapes application = new Shapes();
//application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//}
//}

//
//import java.awt.*;
//import java.awt.geom.*;
//import javax.swing.*;
//
//public class Shapes2 extends JFrame
//{
//	public Shapes2(){
//		super("Drawing 2D shapes");
//
//		getContentPane().setBackground(Color.WHITE);
//		setSize(400,400);
//		setVisible(true);
//	}
//
//	public void paint(Graphics g)
//	{
//		super.paint(g);
//
//		int xPoints[] = {55,67,109,73,83,55,27,37,1,43};
//		int yPoints[] = {0,36,36,54,96,72,96,54,36,36};
//
//		Graphics2D g2d = (Graphics2D)g;
//		GeneralPath star = new GeneralPath(); // GeneralPath��(java.awt.geom��)�Ķ������ͨ��·��
//
//		star.moveTo(xPoints[0],yPoints[0]); // moveTo����ָ��star�ĵ�1������
//
//		for(int count=1;count<xPoints.length;count++)
//			star.lineTo(xPoints[count],yPoints[count]); // lineTo��������star����������ֱ��
//
//		star.closePath();
//
//		g2d.translate(200,200); // �ƶ�����ԭ��
//
//		for(int count=1;count<=20;count++){
//			g2d.rotate(Math.PI / 10.0); // rotate������ת��һ����ʾ��ͼ�Σ��Ի��ȷ�ʽָ����ת�Ƕ�
//
//			g2d.setColor(new Color((int)(Math.random() * 256),(int)(Math.random() * 256),
//					(int)(Math.random() * 256)));
//
//			g2d.fill(star);
//		}
//	}
//
//	public static void main(String args[])
//	{
//		Shapes2 application = new Shapes2();
//		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
//}
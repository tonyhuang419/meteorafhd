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

//Graphics2D g2d = (Graphics2D)g; // 强制转换为Graphics2D引用

////GradientPaint类以渐变颜色绘制图形，颜色剃度的起始坐标(5,30)，结束坐标(35,100)
//g2d.setPaint(new GradientPaint(5,35,Color.BLUE,35,100,Color.YELLOW,true));
////用Graphics2D类的fill方法绘制一个填充的Shape对象
//g2d.fill(new Ellipse2D.Double(5,35,65,100));

//g2d.setPaint(Color.RED);
//g2d.setStroke(new BasicStroke(10.0f)); // BasicStroke类提供一构造函数指定线宽为10个象素
//g2d.draw(new Rectangle2D.Double(80,35,65,100));

////BufferedImage(java.awt.image包)对象创建填充模式
////BufferedImage对象的宽度高度均为10个象素，并使用RGB颜色表中的颜色存储图像
//BufferedImage buffImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);

////调用createGraphics方法创建一个可在BufferedImage内部进行绘制的Graphics2D对象
//Graphics2D gg = buffImage.createGraphics();

////创建填充模式
//gg.setColor(Color.YELLOW);
//gg.fillRect(0,0,10,10);
//gg.setColor(Color.BLACK);
//gg.drawRect(1,1,6,6);
//gg.setColor(Color.BLUE);
//gg.fillRect(1,1,3,3);
//gg.setColor(Color.RED);
//gg.fillRect(4,4,3,3);

////TexturePaint对象使用存储在与其关联的BufferedImage中的图像作为一个填充图形的填充纹理
//g2d.setPaint(new TexturePaint(buffImage,new Rectangle(10,10)));
//g2d.fill(new RoundRectangle2D.Double(155,35,75,100,50,50));

//g2d.setPaint(Color.WHITE);
//g2d.setStroke(new BasicStroke(6.0f));
////Arc2D.PIE常量表示用两条直线封闭弧形
//g2d.draw(new Arc2D.Double(240,35,75,100,0,270,Arc2D.PIE));

//g2d.setPaint(Color.GREEN);
//g2d.draw(new Line2D.Double(395,35,320,150));

//float dashes[] = {10}; // 用于描述虚线段

//g2d.setPaint(Color.YELLOW);
////BasicStroke.CAP_ROUND表示直线端点为圆角，BasicStroke.JOIN_ROUND表示直线连接点是圆角
////最后一个参数指定第1个虚线段在dashes数组中的起始索引
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
//		GeneralPath star = new GeneralPath(); // GeneralPath类(java.awt.geom包)的对象代表通用路径
//
//		star.moveTo(xPoints[0],yPoints[0]); // moveTo方法指定star的第1个顶点
//
//		for(int count=1;count<xPoints.length;count++)
//			star.lineTo(xPoints[count],yPoints[count]); // lineTo方法绘制star中两顶点间的直线
//
//		star.closePath();
//
//		g2d.translate(200,200); // 移动绘制原点
//
//		for(int count=1;count<=20;count++){
//			g2d.rotate(Math.PI / 10.0); // rotate方法旋转下一个显示的图形，以弧度方式指定旋转角度
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
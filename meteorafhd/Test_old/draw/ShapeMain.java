package draw;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class ShapeMain extends JFrame implements ActionListener,MouseListener,MouseMotionListener{
	int x,y,x1,y1,x2,y2,width,height;
	boolean isFirstPoint = true;
//	��ʼ����ʼ��������
	int drawType = PaintingGround.LINE;
//	��ʼ����ʼ�������
	boolean isFill = false;
//	��ӿؼ�
	ButtonGroup btg = new ButtonGroup();
	Button btLine = new Button("��");
	Button btRectangle = new Button("����");
	Button btRound = new Button("Բ");
	Button btEllipse = new Button("��Բ");
	Button tbFillState = new Button("���");
	Button button3 = new Button("�ı�����");
	Button button2 = new Button("���");
	Button button1 = new Button("ѡ����ɫ");

	Panel buttonPanel = new Panel();
	PaintingGround paintingGround = new PaintingGround();
//	Main Method
	public static void main(String[] args) {
//		������ʾ���
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
		}
		new ShapeMain();
	}

//	���캯��
	public ShapeMain() {
//		�ؼ���ӵ��ؼ�����
//		btg.add(btLine);
//		btg.add(btRectangle);
//		btg.add(btRound);
//		btg.add(btEllipse);


		buttonPanel.add(btLine);
		buttonPanel.add(btRectangle);
		buttonPanel.add(btRound);
		buttonPanel.add(btEllipse);
		buttonPanel.add(tbFillState);

//		�������������������岼��
		Container cp = this;

		cp.setLayout(new BorderLayout());

		cp.add(BorderLayout.NORTH,buttonPanel);
		cp.add(BorderLayout.CENTER,paintingGround);
//		cp.add(BorderLayout.SOUTH,jf);
//		jf.setJMenuBar(mb);
		setLocation(300,150);
		setSize(600,480);
		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		�����괥���¼�
		paintingGround.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent evn) {
				isFirstPoint = true;
			}
		});
//		��������������жϲ����û�ͼ����
		paintingGround.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent evn) {
				if(isFirstPoint) {
					x1 = evn.getX();
					y1 = evn.getY();
					isFirstPoint = false;
				}
				else {
					x2 = evn.getX();
					y2 = evn.getY();
					switch(drawType) {
					case PaintingGround.LINE:
//						����
						paintingGround.drawLine(x1,y1,x2,y2);
						break;
					case PaintingGround.RECTANGLE:
//						������
						paintingGround.drawRect(x1,y1,x2-x1,y2-y1);
						break;
					case PaintingGround.ROUND:
//						��Բ
//						������빫ʽ
						int size = Math.abs((int)Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)));
						paintingGround.drawRound(x1,y1,size);
						break;
					case PaintingGround.ELLIPSE:
//						����Բ
						paintingGround.drawEllipse(x1,y1,x2-x1,y2-y1);
						break;
					default:
						break;
					}
				}
			}
		});
//		�����ؼ��Ĵ����¼�
		btLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evn) {
				drawType = PaintingGround.LINE;
			}
		});

		btRectangle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evn) {
				drawType = PaintingGround.RECTANGLE;
			}
		});

		btRound.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evn) {
				drawType = PaintingGround.ROUND;
			}
		});

		btEllipse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evn) {
				drawType = PaintingGround.ELLIPSE;
			}
		});

		tbFillState.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evn) {
				isFill = tbFillState.isShowing();
				paintingGround.setFillState(isFill);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
//		TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent e) {
//		TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
//		TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
//		TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
//		TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
//		TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
//		TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent e) {
//		TODO Auto-generated method stub

	}
}

class PaintingGround extends JPanel {
	public static final int LINE = 1;
	public static final int RECTANGLE = 2;
	public static final int ROUND = 3;
	public static final int ELLIPSE = 4;

	private int x,y;
	private int x1,y1,x2,y2;
	private int width, height,size;
	private int drawType = 0;
	private boolean isFill = false;
//	���캯��
	public PaintingGround() {
		setBackground(Color.black);
	}
//	�ж�����ʵ�Ļ��ǿ��ĵ�,
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.white);
		if(isFill) {
			switch(drawType) {
			case LINE:
				g.drawLine(x1,y1,x2,y2);
				break;
			case RECTANGLE:
				g.fillRect(x,y,width,height);
				break;
			case ROUND:
				g.fillOval(x,y,size,size);
				break;
			case ELLIPSE:
				g.fillOval(x,y,width,height);
				break;
			default:
				break;
			}
		}
		else {
			switch(drawType) {
			case LINE:
				g.drawLine(x1,y1,x2,y2);
				break;
			case RECTANGLE:
				g.drawRect(x,y,width,height);
				break;
			case ROUND:
				g.drawOval(x,y,size,size);
				break;
			case ELLIPSE:
				g.drawOval(x,y,width,height);
				break;
			default:
				break;
			}
		}
	}

	public void drawLine(int x1, int y1, int x2,int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;

		drawType = LINE;
		repaint();
	}
//	�����ʵ�ַ�ʽ
	public void drawRect(int x,int y,int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		drawType = RECTANGLE;
		repaint();
	}

	public void drawRound(int x,int y,int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		drawType = ROUND;
		repaint();
	}

	public void drawEllipse(int x,int y,int width,int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		drawType = ELLIPSE;
		repaint();
	}

	public void setFillState(boolean isFill) {
		this.isFill = isFill;
	}

}
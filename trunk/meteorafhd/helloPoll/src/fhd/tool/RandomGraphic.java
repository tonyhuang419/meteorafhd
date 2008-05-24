package fhd.tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * ����������ֻ���ĸ������ͼ��ʽ��ʾ�������˹�ʶ��ʹ�������ʶ��
 *  ��Сϵͳ�������Զ������Ŀ����ԡ�
 *  ���ɵ�ͼ����ɫ�ɺ졢�ڡ�������4�������϶��ɣ����ֻ���ĸ��ֱ����λ����
 *  һ����Χ��Ҳ������ģ����ٱ������Զ�ʶ��ļ��ʡ�
 *  �������ֵ�0��1��2�׺���ĸ��o��l,z������ʹ��������ʶ����˲���������
 *  ����ĸ�Ļ�ϴ���
 *  ���ɵĴ���ĸͳһ��Сд��������󳤶�Ϊ16��
 *
 * @version
 *  @Since
 * @See Also
 * @author lchen
 * Create Date 2005-12-16
 *
 */

public class RandomGraphic {
	//�ַ��ĸ߶ȺͿ�ȣ���λΪ����
	private int wordHeight = 10;
	private int wordWidth = 15;
	//�ַ���С
	private int fontSize = 16;
	//����ַ�������
	private  static final int MAX_CHARCOUNT = 16;
	//��ֱ������ʼλ��
	private final int initypos = 5;
	//Ҫ���ɵ��ַ��������ɹ��������õ�
	private int charCount = 0;
	//��ɫ���飬�����ִ�ʱ���ѡ��һ��
	private static final Color[] CHAR_COLOR = {Color.RED,Color.BLUE,
		Color.GREEN,Color.MAGENTA,Color.CYAN,Color.ORANGE,Color.PINK,Color.DARK_GRAY};
	//�����������
	private Random r = new Random();
	/**
	 * ����ͼ��ĸ�ʽ������JPEG��ʽ,����Ϊ�ļ�ʱ��չ��Ϊ.jpg��
	 * �����ҳ��ʱ��Ҫ����MIME type Ϊimage/jpeg
	 */
	public static String GRAPHIC_JPEG = "JPEG";
	/**
	 * ����ͼ��ĸ�ʽ������PNG��ʽ,����Ϊ�ļ�ʱ��չ��Ϊ.png��
	 * �����ҳ��ʱ��Ҫ����MIME type Ϊimage/png
	 */
	public static String GRAPHIC_PNG = "PNG";

	//�ù���������������
	protected RandomGraphic(int charCount){
		this.charCount = charCount;
	}
	/**
	 * ��������Ĺ�������
	 * @param charCount  Ҫ���ɵ��ַ�������������1��16֮��
	 *
	 * Return ����RandomGraphic����ʵ��
	 * @throws Exception  ����charCount����ʱ�׳�
	 */
	public static RandomGraphic createInstance(int charCount) throws Exception{
		if (charCount < 1 || charCount > MAX_CHARCOUNT){
			throw new Exception("Invalid parameter charCount,charCount should between in 1 and 16");
		}
		return new RandomGraphic(charCount);
	}
	/**
	 * �������һ�����ִ�������ͼ��ʽ���ƣ����ƽ���������out��
	 *
	 * @param graphicFormat �������ɵ�ͼ���ʽ��ֵΪGRAPHIC_JPEG��GRAPHIC_PNG
	 * @param out  ͼ���������
	 * @return   ������ɵĴ���ֵ
	 * @throws IOException
	 */
	public String drawNumber(String graphicFormat,OutputStream out) throws IOException{
//		������ɵĴ���ֵ
		String charValue = "";
		charValue = randNumber();
		return draw(charValue,graphicFormat,out);
	}
	/**
	 * �������һ����ĸ��������ͼ��ʽ���ƣ����ƽ���������out��
	 *
	 * @param graphicFormat �������ɵ�ͼ���ʽ��ֵΪGRAPHIC_JPEG��GRAPHIC_PNG
	 * @param out  ͼ���������
	 * @return   ������ɵĴ���ֵ
	 * @throws IOException
	 */
	public String drawAlpha(String graphicFormat,OutputStream out) throws IOException{
//		������ɵĴ���ֵ
		String charValue = "";
		charValue = randAlpha();
		return draw(charValue,graphicFormat,out);
	}
	/**
	 * ��ͼ��ʽ�����ַ��������ƽ���������out��
	 * @param charValue  Ҫ���Ƶ��ַ���
	 * @param graphicFormat �������ɵ�ͼ���ʽ��ֵΪGRAPHIC_JPEG��GRAPHIC_PNG
	 * @param out  ͼ���������
	 * @return   ������ɵĴ���ֵ
	 * @throws IOException
	 */
	protected String draw(String charValue,String graphicFormat,OutputStream out) throws IOException{

		//����ͼ��Ŀ�Ⱥ͸߶�
		int w = (charCount+2) * wordWidth;
		int h = wordHeight * 3;

		//�����ڴ�ͼ����
		BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = bi.createGraphics();

		//���ñ���ɫ
		Color backColor = Color.WHITE;
		g.setBackground(backColor);
		g.fillRect(0,0,w,h);

		//����font
		g.setFont(new Font(null,Font.BOLD,fontSize));
		//����charValue,ÿ���ַ���ɫ���
		for(int i = 0; i < charCount; i++){
			String c = charValue.substring(i,i+1);
			Color color =  CHAR_COLOR[randomInt(0,CHAR_COLOR.length)];
			g.setColor(color);
			int xpos = (i+1) * wordWidth;
			//��ֱ���������
			int ypos = randomInt(initypos+wordHeight,initypos+wordHeight*2);
			g.drawString(c,xpos,ypos);
		}
		g.dispose();
		bi.flush();
		// �������
		ImageIO.write(bi,graphicFormat,out);

		return charValue;
	}
	protected String randNumber(){
		String charValue = "";
		//����������ִ�
		for (int i = 0; i < charCount; i++){
			charValue += String.valueOf(randomInt(0,10));
		}
		return charValue;
	}
	private String randAlpha(){
		String charValue = "";
		//���������ĸ��
		for (int i = 0; i < charCount; i++){
			char c = (char) (randomInt(0,26)+'a');
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	/**
	 * ����[from,to)֮���һ���������
	 *
	 * @param from ��ʼֵ
	 * @param to ����ֵ
	 * @return  [from,to)֮���һ���������
	 */
	protected int randomInt(int from,int to){
		//Random r = new Random();
		return from+r.nextInt(to-from);
	}

	/**
	 * @param args
	 * @throws Exception
	 */
//	public static void main(String[] args) throws Exception {
//		
//		System.out.println(RandomGraphic.createInstance(5).drawAlpha(RandomGraphic.GRAPHIC_PNG,new FileOutputStream("c:/myimg.png")));
//	}
}

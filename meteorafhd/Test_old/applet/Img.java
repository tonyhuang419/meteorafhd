package applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Img extends Applet   {
	private Font font= new Font( "Times New Roman",Font.PLAIN,17);
	BufferedImage image = null;
	public Color getRandColor(int fc,int bc){
		Random random = new Random();
		if(fc> 255)
			fc=255;
		if(bc> 255)
			bc=255;
		int r=fc+random.nextInt(bc-fc);
		int g=fc+random.nextInt(bc-fc);
		int b=fc+random.nextInt(bc-fc);
		return new Color(r,g,b);
	}

	public void start(){
		int width=100;
		int height=18;
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200,250));
		g.fillRect(1,1,width-1,height-1);
		g.setColor(new Color(102,102,102));
		g.drawRect(0,0,width-1,height-1);
		g.setFont(font);
		g.setColor(getRandColor(160,200));
		for(int i=0 ;i <155;i++){
			int x=random.nextInt(width-1);
			int y=random.nextInt(height-1);
			int x1=random.nextInt(6)+1;
			int y1=random.nextInt(12)+1;
			g.drawLine(x,y,x+x1,y+y1);
		}
		for(int i=0 ;i <70;i++){
			int x=random.nextInt(width-1);
			int y=random.nextInt(height-1);
			int x1=random.nextInt(12)+1;
			int y1=random.nextInt(6)+1;
			g.drawLine(x,y,x+x1,y+y1);
		}
		String rand= " ";
		for(int i=0;i <6;i++){
			int itmp=random.nextInt(26)+65;
			char ctmp=(char)itmp;
			rand+=String.valueOf(ctmp);
			g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
			g.drawString(String.valueOf(ctmp),15*i+10,16);
		}
		g.dispose();
		try{
			ImageIO.write(image, "JPEG ",new File("c:\\1.jpg"));
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	public void paint(Graphics g){
		g.drawImage(image,0,0,this);
	}
} 

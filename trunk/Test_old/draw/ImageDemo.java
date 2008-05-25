package draw;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

public class ImageDemo extends Applet{
	private Image pic[];
	private int totalImage,currentImage,sleepTime;
	private Graphics gContext;
	private Image buffer;
	private boolean b;
	private MediaTracker imageTrack;

	public void init(){
		totalImage=10;
		pic=new Image[totalImage];
		currentImage=0;
		sleepTime=2000;

		buffer=this.createImage(1024,768);
		gContext=buffer.getGraphics();

		gContext.setColor(Color.white);
		gContext.fillRect(0,0,1024,768);

		imageTrack=new MediaTracker(this);

		for(int i=0;i<pic.length;i++){
			pic[i]=this.getImage(this.getDocumentBase(),"Image/pic00"+
					(i+1)+".jpg");
			imageTrack.addImage(pic[i],i);
		}

		try{
			imageTrack.waitForID(0);
		}
		catch(InterruptedException e){
		}
	}

	public void start(){
		gContext.drawImage(pic[0],0,0,this);
		currentImage=1;
	}

	public void update(Graphics g){
		paint(g);
	}

	public void paint(Graphics g){
		g.drawImage(buffer,0,0,this);

		if(imageTrack.checkID(currentImage,true)){
			gContext.fillRect(0,0,1024,768);
			gContext.drawImage(pic[currentImage],0,0,this);
			currentImage=++currentImage%totalImage;
		}
		else{
			this.postEvent(new Event(this,Event.MOUSE_ENTER,""));
		}

		try{
			Thread.sleep(sleepTime);
		}
		catch(InterruptedException e){
			this.showStatus(e.toString());
		}
		repaint();
	}
}
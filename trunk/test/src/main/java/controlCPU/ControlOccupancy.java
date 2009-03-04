package controlCPU;

import java.util.Timer;
import java.util.TimerTask;

public class ControlOccupancy {
	boolean sign = false;

	public void controlCpuOne ()
	{
		int delay = 500;  
		int period = 500;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(sign == true ){
					sign = false;
				}
				else{
					sign = true;
				}
			}
		}, delay, period);

		for(;;){
			if(sign==false){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void controlCpuTwo(){
		int busyTime = 500;
		int idelTime = 500; 

		long startTime = 0;
		while (true) {
			startTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - startTime < busyTime);
			try {
				Thread.sleep(idelTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]){
		new ControlOccupancy().controlCpuOne();
		new ControlOccupancy().controlCpuTwo();
	}
}

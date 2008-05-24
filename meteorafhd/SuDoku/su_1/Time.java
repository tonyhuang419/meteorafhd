package su_1;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
class Test
{
	public static void main(String args[])
	{

		int numberOfMillisecondsInTheFuture = 10000; // 10 sec
		Date timeToRun = new Date(System.currentTimeMillis()+numberOfMillisecondsInTheFuture);//当前时间后的10秒
		Timer timer1 = new Timer();//定义计时器

		timer1.schedule(new TimerTask() {
			public void run() {
				// Task here ...
				System.out.println("bbbbbb");
			}
		}, timeToRun);//过10秒以后在打印

		int delay = 5000;   // delay for 5 sec.
		int period = 1000;  // repeat every sec.
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {//过5秒后没过一秒打印一次
			public void run() {
				System.out.println("ssssss");
				// Task here ...
			}
		}, delay, period);

	}
}

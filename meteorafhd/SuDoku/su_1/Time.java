package su_1;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
class Test
{
	public static void main(String args[])
	{

		int numberOfMillisecondsInTheFuture = 10000; // 10 sec
		Date timeToRun = new Date(System.currentTimeMillis()+numberOfMillisecondsInTheFuture);//��ǰʱ����10��
		Timer timer1 = new Timer();//�����ʱ��

		timer1.schedule(new TimerTask() {
			public void run() {
				// Task here ...
				System.out.println("bbbbbb");
			}
		}, timeToRun);//��10���Ժ��ڴ�ӡ

		int delay = 5000;   // delay for 5 sec.
		int period = 1000;  // repeat every sec.
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {//��5���û��һ���ӡһ��
			public void run() {
				System.out.println("ssssss");
				// Task here ...
			}
		}, delay, period);

	}
}

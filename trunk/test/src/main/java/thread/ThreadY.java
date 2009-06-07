package thread;

import org.apache.commons.lang.math.RandomUtils;

public class ThreadY {


	public static void main(String[] args) {
		new FunY().start();
		new FunY().start();
		new FunY().start();
		new FunY().start();

	}

}

class FunY extends Thread{
	public void run(){
		System.out.println("start...");
		String s;
		System.out.println("doing...");
		int i = RandomUtils.nextInt();
		System.out.println("s修改为:" + i);
		s = i+"";
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(s);
		System.out.println("end...");
	}
}

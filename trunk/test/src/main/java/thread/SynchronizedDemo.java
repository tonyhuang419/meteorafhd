package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.math.RandomUtils;

//http://dev.csdn.net/article/71/71246.shtm

public class SynchronizedDemo {

	public static void main(String[] args){
		Fun fun = new Fun();
		CallA c = new CallA(fun);
		int x = 100;
		ExecutorService exec = Executors.newFixedThreadPool(2);
		while(--x>0){
			exec.execute(c);
		}
		exec.shutdown();
	}
	
}

class CallA implements Runnable{
	Fun fun;
	CallA(Fun fun){
		this.fun = fun;
	}
	public void run(){
		fun.a();
	}
}

class Fun {
	public void a(){
		System.out.println("start...");
		String s;
		System.out.println("doing...");
		int i = RandomUtils.nextInt();
		System.out.println("s修改为:" + i);
		s = i+"";
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s);
//		synchronized(s){
//			try {
//				System.out.println("sleep now");
//				Thread.sleep(3000);
//				System.out.println(Thread.currentThread().getName()+"s:"+s);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		System.out.println("end...");
	}
}
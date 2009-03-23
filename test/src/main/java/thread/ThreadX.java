package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadX {


	public static void main(String[] args) {
		int i=100;
		ExecutorService exec = Executors.newFixedThreadPool(2);
		/**
		 * 线程会一次分配完，然后执行啊执行
		 */
		while(i>0){
			System.out.println(".."+i);
			exec.execute(new TestX(i--));
		}
		exec.shutdown();
	}

}


class TestX implements Runnable{
	int i;

	TestX(int i){
		this.i = i;
	}

	public void run(){
		System.out.println(i);
		new KK().x();
		if(i==100){
			try {
				throw new Exception("aaa");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

class KK{
	public void x(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

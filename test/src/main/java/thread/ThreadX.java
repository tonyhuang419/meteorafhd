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
			i--;
			System.out.println(i);
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
		try {
			Thread.sleep(5000);
			System.out.println("'"+Thread.activeCount());
			System.out.println(i);
			if(i==2){
				try {
					throw new Exception("aaa");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

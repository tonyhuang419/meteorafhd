package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSyncDemo {

	public static void main(String[] args) {
		int i=1000;
		ExecutorService exec = Executors.newFixedThreadPool(15);
		ExecutorService exec2 = Executors.newFixedThreadPool(15);

		while(i-->0){
			exec.execute(new ThreadSyncX(i));
			exec2.execute(new ThreadSyncX(i));
		}
		exec.shutdown();
		exec2.shutdown();
	}
}

class ThreadSyncX implements Runnable{
	int i;
	public ThreadSyncX(int i){
		this.i = i;
	}

	@Override
	public void run() {
		int s = ThreadSyncCommon.getStr(i);
		if(i!=s && s!=-1){
			System.out.println("error");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+"|" + i +"|" + s );
	}
}


class ThreadSyncCommon {
	static byte[] lock = new byte[0];
	static int count = 0;

	static public int getStr( int i ){
		if(++count>10){
			System.out.println("========同时请求数过大：10========");
			return -1;
		}

		synchronized (lock) {
			int s = i;
			count--;

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return s;
		}
	}


}


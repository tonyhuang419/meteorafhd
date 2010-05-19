package thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSyncDemo {


	public static void main(String[] args) {
		int i=1000;
		ExecutorService exec = Executors.newFixedThreadPool(20);

		while(i-->0){
			exec.execute(new ThreadSyncX(i));
		}
		exec.shutdown();
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
	static List<Integer> queue = new LinkedList<Integer>(); 
	static byte[] lock = new byte[0];

	static public int getStr( int i ){
		if(!add(i)){
			return -1;
		}

		synchronized (lock) {
			int s = remove();
			return s;
		}
	}

	static int remove( ){
		synchronized (queue) {
			return  queue.remove(0)	;
		}
	}


	static boolean add(int args){
		synchronized (queue) {
			if(queue.size()<10){
				queue.add(args);
				return true;
			}
			else{
				System.out.println("========同时请求数过大：10========");
				return false;
			}
		}
	}

}


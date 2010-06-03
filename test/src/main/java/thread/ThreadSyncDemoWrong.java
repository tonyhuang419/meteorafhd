package thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSyncDemoWrong {


	public static void main(String[] args) {
		int i=1000;
		ExecutorService exec = Executors.newFixedThreadPool(2);

		while(i-->0){
			exec.execute(new ThreadSyncXWrong(i));
		}
		exec.shutdown();
	}

}

class ThreadSyncXWrong implements Runnable{
	int i;
	public ThreadSyncXWrong(int i){
		this.i = i;
	}

	@Override
	public void run() {
		int s = ThreadSyncCommonWrong.getStr(i);
		if(i!=s && s!=-1){
			System.out.println(i+","+s+"errorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerrorerror");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+"|" + i +"|" + s );
	}
}


class ThreadSyncCommonWrong {
	static LinkedList<Integer> queue = new LinkedList<Integer>(); 
	static byte[] lock = new byte[0];

	static public int getStr( int i ){
		if(!add(i)){
			return -1;
		}

		/**
		 * 在这里线程会产生竞争，而数据保存在静态变量中，最终导致线程获取值错误
		 */
		synchronized (lock) {
			System.out.println("////////");
			int s = remove();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("--------");
			return s;
		}
	}

	static int remove( ){
		synchronized (queue) {
			Integer s = queue.removeFirst();
			System.out.println(Thread.currentThread().getName()+"remove/" + s);
			return  s 	;
		}
	}


	static boolean add(int args){
		synchronized (queue) {
			if(queue.size()<2){
				queue.add(args);
				System.out.println( Thread.currentThread().getName()+"put/" + args+"/"+queue.size());
				return true;
			}
			else{
				System.out.println("========同时请求数过大========");
				return false;
			}
		}
	}

}


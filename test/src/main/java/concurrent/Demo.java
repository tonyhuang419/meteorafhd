package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo  implements Callable<Demo>{   //Runnable{ //{
	private CountDownLatch signal;
	private int i;
	

	Demo(int i , CountDownLatch signal){
		this.i = i;
		this.signal = signal;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CountDownLatch signal = new CountDownLatch(3);
		int i=0;
		ExecutorService exec = Executors.newFixedThreadPool(10);
		while(i<100){
			List<Demo> list = new ArrayList<Demo>();
			list.add(new Demo(i,signal));
			list.add(new Demo(++i,signal));
			list.add(new Demo(++i,signal));
			try {
				exec.invokeAll(list);
				//				try {
				//					exec.invokeAny(list);
				//				} catch (ExecutionException e) {
				//					e.printStackTrace();
				//				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			i++;   
			
			try {
	            signal.await();  //如果signal没到0，就会竟如阻塞状态
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

			//exec.execute(new Tet(i));
			//i++;
		}
		exec.shutdown();
	}

	@Override
	public Demo call() throws Exception {
		System.out.println(i);
		Thread.sleep(1000);
		signal.countDown();
		return null;
	}



	//	@Override
	//	public void run() {
	//		System.out.println(i);
	//		try {
	//			Thread.sleep(1000);
	//		} catch (InterruptedException e) {
	//			e.printStackTrace();
	//		}
	//	}


}

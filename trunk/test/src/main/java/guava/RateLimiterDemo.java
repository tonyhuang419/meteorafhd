package guava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterDemo {
	
	final RateLimiter rateLimiter = RateLimiter.create(10,10,TimeUnit.SECONDS);
	
	public void submitTasks(List<Runnable> tasks, Executor executor) {
		for (Runnable task : tasks) {
			rateLimiter.acquire(3); // may wait
			executor.execute(task);
		}
	}
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(10);
		List<Runnable> list = new ArrayList<Runnable>();
		int i=100;
		while(i-->0){
			list.add(new PrintX(i));
		}
		new RateLimiterDemo().submitTasks(list, exec);
	}
}

class PrintX implements Runnable{
	int i;
	public PrintX(int i){
		this.i = i;
	}
	public void run(){
		try {
			double d = Math.random();
			long l = Long.valueOf((int)(d*1000));
			System.out.println("sleep:"+l);
			Thread.sleep(Long.valueOf(l));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(i);
	}
}
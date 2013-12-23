package guava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterDemo {
	
	final RateLimiter rateLimiter = RateLimiter.create(100.0);
	
	public void submitTasks(List<Runnable> tasks, Executor executor) {
		for (Runnable task : tasks) {
			rateLimiter.acquire(); // may wait
			executor.execute(task);
		}
	}
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(15);
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
		System.out.println(i);
	}
}
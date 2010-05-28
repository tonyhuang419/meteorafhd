package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.jcip.annotations.GuardedBy;

public class ThreatAnnotation {

	@GuardedBy("this")
	synchronized public static void X(){
		System.out.println("........");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		int i=100;
		ExecutorService exec = Executors.newFixedThreadPool(10);
		while(i>0){
			exec.execute(new ThreatAnnotationX());
		}
		exec.shutdown();
	}
}


class ThreatAnnotationX implements Runnable {

	public void run(){
		ThreatAnnotation.X();
	}
}

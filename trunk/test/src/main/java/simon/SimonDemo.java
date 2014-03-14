package simon;

import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;

public class SimonDemo {

	public static void main(String[] args) {
		Stopwatch stopwatch = SimonManager.getStopwatch("stopwatch");
		for (int i = 0; i < 10; i++) {
			strangeMethod();
		}
		System.out.println("Stopwatch: " + stopwatch);
		System.out.println(stopwatch);
//		System.out.println(stopwatch.sample());
	}

	/**
	 * Method that lasts randomly from ~0 to ~2500 ms.
	 */
	private static void strangeMethod() {
		Split split = SimonManager.getStopwatch("stopwatch").start();
		long random = (long) (Math.random() * 50);
		try {
			Thread.sleep(random * random);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		split.stop();
		System.out.println( SimonManager.getStopwatch("stopwatch").sample());
	}


}

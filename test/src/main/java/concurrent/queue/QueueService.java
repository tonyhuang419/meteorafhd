package concurrent.queue;

import org.apache.commons.lang.math.RandomUtils;


public class QueueService {

	public String getString(String str){
		String strx="";
		/**
		 * if u use "str" to locker, you will find out the synchronized is unused
		 */
		synchronized (strx) {
			System.out.println(Thread.currentThread().getName());
			try {
				System.out.println("sleep...");
				Thread.sleep(RandomUtils.nextLong()%1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			strx = str;
		}
		printStr(strx);
		return strx;
	}
	
	
	private void printStr(String str){
		System.out.println(str);
	}
	
}

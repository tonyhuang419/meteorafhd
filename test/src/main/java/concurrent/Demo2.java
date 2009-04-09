package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo2 {

	final Lock lock = new ReentrantLock();  
	final Condition notFull  = lock.newCondition();   
	final Condition notEmpty = lock.newCondition();   

	final Object[] items = new Object[100];  
	int putptr, takeptr, count;  

	public void put(Object x) throws InterruptedException {  
		lock.lock();  
		try {  
			while (count == items.length)   
				notFull.await();  
			items[putptr] = x;   
			if (++putptr == items.length) putptr = 0;  
			++count;  
			notEmpty.signal();  
		} finally {  
			System.out.println("put");
			lock.unlock();  
		}  
	}  

	public Object take() throws InterruptedException {  
		lock.lock();  
		try {  
			while (count == 0)   
				notEmpty.await();  
			Object x = items[takeptr];
			System.out.println("take:"+x.toString());
			if (++takeptr == items.length) takeptr = 0;  
			--count;  
			notFull.signal();  
			return x;  
		} finally {  
			System.out.println("take");
			lock.unlock();  
		}  
	}  
	
	public static void main(String[] args) throws InterruptedException {
		Demo2 d = new Demo2();
		
		d.put("x");
		d.take();
	}
	
}

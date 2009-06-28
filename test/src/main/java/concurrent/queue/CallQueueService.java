package concurrent.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallQueueService {

	public static void main(String[] args) {
		QueueService queueService = new QueueService();
		QueueThread queueThread = new QueueThread();
		queueThread.setQueueService(queueService);
		
		ExecutorService exec = Executors.newFixedThreadPool(10);
		int i=0;
		while(i<500){
			exec.execute(queueThread);
			i++;
		}
		exec.shutdown();
	}
}

class QueueThread implements Runnable{
	private QueueService queueService;

	public void run() {
		String str = queueService.getString( Thread.currentThread().getName());
		System.out.println( Thread.currentThread().getName()+":" + str   );
	}

	public QueueService getQueueService() {
		return queueService;
	}

	public void setQueueService(QueueService queueService) {
		this.queueService = queueService;
	}
	
}
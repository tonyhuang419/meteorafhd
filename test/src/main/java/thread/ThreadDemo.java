package thread;

/**
 * 不建议用String作为锁对象
 * 因为一旦String被修改，锁就会失效
 */
public class ThreadDemo {


	public static void main(String[] args){
		ThreadXX t = new ThreadXX();
		new Thread(t).start();
		new Thread(t).start();
	}

}

class ThreadXX implements Runnable{
	//不建议用String作为锁对象
	//String s = new String(); 
	byte[] s = new byte[1];
	private void sysout(){
		System.out.println(",,,,,");
		synchronized(s){
			this.cc(); 
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("....");
		}
	}
	
	private void cc(){
		synchronized(s){
		//  String被修改，导致所对象失效
		//	s ="zzzzzzzz";
			s[0] = 'a';
		}
	}

	@Override
	public void run() {
		this.sysout();
	}

}
package thread;

public class ThreadDemo {


	public static void main(String[] args){
		ThreadXX t = new ThreadXX();
		new Thread(t).start();
		new Thread(t).start();
	}

}

class ThreadXX implements Runnable{
	String s = new String();
	private void sysout(){
		System.out.println(",,,,,");
		synchronized(s){
//			this.cc();  //取消我的注释后再运行
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
			s ="zzzzzzzz";
		}
	}

	@Override
	public void run() {
		this.sysout();
	}

}
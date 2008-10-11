package taskTest;

import java.util.Date;

public class MyTimer {
	public static void main(String[] args) {
		// TODO 自动生成方法存根
		MyTimer mt=new MyTimer();
		mt.myTime();
	}

	public void myTime(){
		//for (int i=0;i<10;i++){
			System.out.println("我就是要输出！标准方法输出 "
					+ new Date(System.currentTimeMillis()));
		//}
	}
	
	public void myTime2(){
		//for (int i=0;i<10;i++){
			System.out.println("我就是要输出！标准方法输出2 "
					+ new Date(System.currentTimeMillis()));
		//}
	}
}

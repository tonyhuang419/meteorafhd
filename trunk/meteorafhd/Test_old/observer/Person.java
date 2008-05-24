package observer;

import java.util.Observable;
import java.util.Observer;

class Person implements Observer {
	String name;
	int changeTimes=0;//记录本人看到的记录更改次数

	public Person(String name) {
		this.name = name;
	}

	/*
	 * 本人看到了通知
	 *
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {

		String first=
			name + "说: 我已经看到了通知，" + arg;
		String other=name + "说: 我考！，都该了N次了，N>="+ changeTimes + arg;
		System.out.println(changeTimes==0?first:other);
		changeTimes++;
	}
}

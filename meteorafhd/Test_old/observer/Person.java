package observer;

import java.util.Observable;
import java.util.Observer;

class Person implements Observer {
	String name;
	int changeTimes=0;//��¼���˿����ļ�¼���Ĵ���

	public Person(String name) {
		this.name = name;
	}

	/*
	 * ���˿�����֪ͨ
	 *
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {

		String first=
			name + "˵: ���Ѿ�������֪ͨ��" + arg;
		String other=name + "˵: �ҿ�����������N���ˣ�N>="+ changeTimes + arg;
		System.out.println(changeTimes==0?first:other);
		changeTimes++;
	}
}

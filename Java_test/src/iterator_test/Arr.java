package iterator_test;

import java.util.Iterator;

public class Arr implements Iterator{
	private int length = 5;
	private int index = 0;
	private People[] p = new People[length];
	Arr(){
		p[0] = new People("Peter",19);
		p[1] = new People("Jane",16);
		p[2] = new People("Mathew",20);
		p[3] = new People("Lucas",21);
		p[4] = new People("Jack",18);
	}
	
	public Iterator iterator() {
		return new Arr();
	}
	
	public boolean hasNext() {
		if(index < length){
			return true;
		}
		else
			return false;
	}

	public Object next() {
		if(hasNext() == true){
			return p[index++];
		}
		else
			return null;
	}

	public void remove() {
		// TODO Auto-generated method stub		
	}	
}

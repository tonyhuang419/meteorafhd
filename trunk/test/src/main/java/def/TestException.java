package def;

public class TestException {

	public static void main(String[] args) throws Exception {
		int a=1;
		if(a<10){
			throw new MyException(a);
		}
	}
}


class MyException extends Exception{
	int num;
	MyException(int a){
		num = a;
	}
	public String toString(){
		return num+"<10! 值必须大于10";
	}
}


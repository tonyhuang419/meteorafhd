package elanceTest;

import java.util.Scanner;

public class Car {

	//	Distance Travelled = u*t+((a*t*t)/2) where,
	//	u = initial velocity of the car (36 km/hr)
	//	a = acceleration of the car (5 m/s2)
	//	t = time duration in seconds

	public long disCalc(long t){
		long dis = 36*1000/3600*t+((5*t*t)/2);
		return dis;
	}

	public static void main(String[] args){
		int a,b;
		Scanner in = new Scanner(System.in);
		a = in.nextInt();
		b = in.nextInt();
		Car c = new Car();
		System.out.println(c.disCalc(a));
		System.out.println(c.disCalc(b));
	}
}

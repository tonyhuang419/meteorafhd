package gcodeJam.juice;

import gcodeJam.utilTools.UtilTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyJuice {

	public char getMinPrice(List<Integer> orderApple , List<Integer> orderBannana ,
			List<Integer> orderCarrot , Juice minJuice ){
		char change='x';
		Integer a = minJuice.apple ;
		Integer b = minJuice.banana ;
		Integer c = minJuice.carrot ;

		if( orderApple.size() > 0 ){
			a = orderApple.get(0);
		}

		if(orderBannana.size() > 0){
			b = orderBannana.get(0);
		}

		if(orderCarrot.size() > 0){
			c = orderCarrot.get(0);
		}
		int min = Math.min(a, Math.min(b,c));
		if(min==a){
			change = 'a';
			orderApple.remove(a);
		}
		else if(min==b){
			change = 'b';
			orderBannana.remove(b);
		}
		else if(min==c){
			change = 'c';
			orderCarrot.remove(c);
		}

		minJuice.apple = a;
		minJuice.banana = b;
		minJuice.carrot = c;
		return change;
	}

	public List<List<Juice>> readFile( ) throws Exception{
		List<List<Juice>> juiceList = new ArrayList<List<Juice>>();
		List<Juice> jl = new ArrayList<Juice>();
		List<List<String[]>> ls = UtilTools.fileParse2("src/main/java/gcodeJam/juice/A-small-practice.in",3);
		for( List<String[]> list : ls ){
			jl = new ArrayList<Juice>();
			for(String[] strArr : list){
//				System.out.println(strArr[0] + " - "+ strArr[1] + " - " +  strArr[2] );
				jl.add(new Juice(Integer.valueOf(strArr[0]) , Integer.valueOf(strArr[1]) , Integer.valueOf(strArr[2])));
			}
			juiceList.add(jl);
		}
		return juiceList;
	}

	public int analyze(List<Juice> listJuice){
		Juice minJuice = new Juice(10000,10000,10000);
		Juice maxJuice = new Juice(0,0,0);
		List<Integer> orderApple = new ArrayList<Integer>();
		List<Integer> orderBannana = new ArrayList<Integer>();
		List<Integer> orderCarrcot = new ArrayList<Integer>();
		int size = listJuice.size();
		for(Juice juice : listJuice){
			maxJuice.apple = juice.apple > maxJuice.apple ? juice.apple : maxJuice.apple;
			maxJuice.banana = juice.banana > maxJuice.banana ? juice.banana : maxJuice.banana;
			maxJuice.carrot = juice.carrot > maxJuice.carrot ? juice.carrot : maxJuice.carrot;

			minJuice.apple = juice.apple < minJuice.apple ? juice.apple : minJuice.apple;
			minJuice.banana  = juice.banana < minJuice.banana ? juice.banana : minJuice.banana;
			minJuice.carrot = juice.carrot < minJuice.carrot ? juice.carrot : minJuice.carrot;

			if(!orderApple.contains(juice.apple)){
				orderApple.add(juice.apple);
			}
			if(!orderBannana.contains(juice.banana)){
				orderBannana.add(juice.banana);
			}
			if(!orderCarrcot.contains(juice.carrot)){
				orderCarrcot.add(juice.carrot);
			}

		}

		Collections.sort(orderApple);
		Collections.sort(orderBannana);
		Collections.sort(orderCarrcot);

		//System.out.println(maxApple);
		//System.out.println(maxBanana);
		//System.out.println(maxCarrot);
		//System.out.println(minApple);
		//System.out.println(minBanana);
		//System.out.println(minCarrot);

		int sumMax = maxJuice.apple + maxJuice.banana + maxJuice.carrot;
		int sumMin = minJuice.apple + minJuice.banana + minJuice.carrot;
		if(sumMax<=10000){
			return size;
		}
		else if(sumMin > 10000){
			return 0;
		}
		else{
			List<Juice> correspondJuice = new ArrayList<Juice>();
			List<Juice> notCorrespondJuice = new ArrayList<Juice>();
			while ( sumMin <= 10000 && ( orderApple.size()>0 ||  orderBannana.size()>0 || orderCarrcot.size()>0 )){
				char change = this.getMinPrice(orderApple, orderBannana, orderCarrcot , minJuice );
				sumMin = minJuice.apple + minJuice.banana + minJuice.carrot;
				for(Juice juice : listJuice){
					if( juice.apple <= minJuice.apple 
							&&  juice.banana <= minJuice.banana
							&& juice.carrot <= minJuice.carrot
							&& sumMin <= 10000 ){
						correspondJuice.add(juice);
					}
				}
				for(Juice juice : correspondJuice ){
					listJuice.remove(juice);
				}
				for(Juice juice : notCorrespondJuice ){
					listJuice.remove(juice);
				}
			}
			return correspondJuice.size();
		}
	}

	public static void main(String[] args) throws Exception{
		MyJuice myJuice = new MyJuice();
		List<List<Juice>> listJuice = myJuice.readFile();

		for(List<Juice> juiceList:listJuice){
			int x = myJuice.analyze(juiceList);
			System.out.println(x);
		}

		//		List<Juice> listJucie = new ArrayList<Juice>();
		//
		//		listJucie.add(new Juice(10000,0,0));
		//		listJucie.add(new Juice(0,10000,0));
		//		listJucie.add(new Juice(0,0,10000));
		//		int x = juice.analyze(listJucie);
		//		System.out.println(x);
		//
		//
		//		listJucie.clear();
		//		listJucie.add(new Juice(5000,0,0));
		//		listJucie.add(new Juice(0,2000,0));
		//		listJucie.add(new Juice(0,0,4000));
		//		x = juice.analyze(listJucie);
		//		System.out.println(x);
		//
		//
		//		listJucie.clear();
		//		listJucie.add(new Juice(0,1250,0));
		//		listJucie.add(new Juice(3000,0,3000));
		//		listJucie.add(new Juice(1000,1000,1000));
		//		listJucie.add(new Juice(2000,1000,2000));
		//		listJucie.add(new Juice(1000,3000,2000));
		//		x = juice.analyze(listJucie);
		//		System.out.println(x);

	}
}

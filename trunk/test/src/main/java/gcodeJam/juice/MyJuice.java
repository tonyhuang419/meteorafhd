package gcodeJam.juice;

import gcodeJam.utilTools.UtilTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * not complete
 */
public class MyJuice {

	class CompareReverseInteger implements Comparator<Integer>{
		public int compare(Integer i , Integer j) {
			if(i < j){
				return 1;
			}
			else if( i ==  j ){
				return 0;
			}
			return -1;
		}
	}

	public List<Integer> getOrderPrice( List<Juice> juiceList , int type ){
		List<Integer> tempList = new ArrayList<Integer>();
		if(type==Juice.APPLE_Code){
			for(Juice juice:juiceList){
				if(!tempList.contains(juice.apple)){
					tempList.add(juice.apple);
				}
			}
			Collections.sort(tempList , new CompareReverseInteger());
		}
		else if(type==Juice.BANANA_CODE){
			for(Juice juice:juiceList){
				if(!tempList.contains(juice.banana)){
					tempList.add(juice.banana);
				}
			}
			Collections.sort(tempList ,  new CompareReverseInteger() );
		}
		else if(type==Juice.CARROT_CODE){
			for(Juice juice:juiceList){
				if(!tempList.contains(juice.carrot)){
					tempList.add(juice.carrot);
				}
			}
			Collections.sort(tempList , new CompareReverseInteger());
		}
		return tempList;
	}

	public int getPostion(List<Integer> list , int x){
		int pos = list.size();
		for(Integer i:list){
			if(i<=x){
				break;
			}
			pos++;
		}
		return pos;
	}

	public List<List<Juice>> readFile( ) throws Exception{
		List<List<Juice>> juiceList = new ArrayList<List<Juice>>();
		List<Juice> jl = new ArrayList<Juice>();
		List<List<String[]>> ls = UtilTools.fileParse2("src/main/java/gcodeJam/juice/A-small-practice.in",3);
		for( List<String[]> list : ls ){
			jl = new ArrayList<Juice>();
			for(String[] strArr : list){
				//System.out.println(strArr[0] + " - "+ strArr[1] + " - " +  strArr[2] );
				jl.add(new Juice(Integer.valueOf(strArr[0]) , Integer.valueOf(strArr[1]) , Integer.valueOf(strArr[2])));
			}
			juiceList.add(jl);
		}
		return juiceList;
	}

	public int getMaxPeople( List<Integer> apple ,List<Integer> banana , List<Integer> carrot ,
			int appleCount , int bananaCount,int carrotCount , int maxPeople){
		int tempMax =  ( apple.size() - this.getPostion(apple , appleCount) )
		+ ( banana.size() - this.getPostion(banana , bananaCount) ) + ( this.getPostion(carrot , carrotCount) );
		if( tempMax > maxPeople){
			return tempMax;
		}
		return maxPeople;
	}

	public int analyze(List<Juice> listJuice){
		List<Juice> orderApple = new ArrayList<Juice>();
		List<Juice> orderBanana = new ArrayList<Juice>();
		List<Juice> orderCarrot = new ArrayList<Juice>();
		orderApple.addAll(listJuice);
		orderBanana.addAll(listJuice);
		orderCarrot.addAll(listJuice);
		List<Integer> apple = this.getOrderPrice(orderApple, Juice.APPLE_Code);
		List<Integer> banana = this.getOrderPrice(orderApple, Juice.BANANA_CODE);
		List<Integer> carrot = this.getOrderPrice(orderApple, Juice.CARROT_CODE);
		int maxPrice = apple.get(0) + banana.get(0) + carrot.get(0);
		if( maxPrice <= 10000 ){
			return listJuice.size();
		}

		int maxJuiceCount = 10000;
		int appleCount = 0;
		int bananaCount = 0;
		int carrotCount = 0;
		int maxPeople = this.getMaxPeople(apple, banana, carrot, appleCount, bananaCount, carrotCount , 0);

		while( (appleCount + bananaCount + carrotCount) < 10000 ){
			appleCount++;
			maxPeople = this.getMaxPeople(apple, banana, carrot, appleCount, bananaCount, carrotCount , maxPeople);
			while( (appleCount + bananaCount + carrotCount) < 10000  ){
				bananaCount++;
				carrotCount = maxJuiceCount - appleCount - bananaCount - carrotCount;
				maxPeople = this.getMaxPeople(apple, banana, carrot, appleCount, bananaCount, carrotCount , maxPeople);
				//				System.out.println("==="+maxPeople);
			}
		}

		appleCount = 0;
		bananaCount = 0;
		carrotCount = 0;
		while( (appleCount + bananaCount + carrotCount) < 10000 ){
			appleCount++;
			maxPeople = this.getMaxPeople(apple, banana, carrot, appleCount, bananaCount, carrotCount , maxPeople);
			while( (appleCount + bananaCount + carrotCount) < 10000  ){
				carrotCount++;
				bananaCount = maxJuiceCount - appleCount - bananaCount - carrotCount;
				maxPeople = this.getMaxPeople(apple, banana, carrot, appleCount, bananaCount, carrotCount , maxPeople);
			}
		}

		return maxPeople;
	}

	public static void main(String[] args) throws Exception{
		MyJuice myJuice = new MyJuice();
		List<List<Juice>> listJuice = myJuice.readFile();
		int i=0;
		for(List<Juice> juiceList:listJuice){
			int x = myJuice.analyze(juiceList);
			System.out.println("Case #"+ ++i +": "+x);
		}

		List<Juice> listJucie = new ArrayList<Juice>();

		listJucie.add(new Juice(10000,0,0));
		listJucie.add(new Juice(0,10000,0));
		listJucie.add(new Juice(0,0,10000));
		int x = myJuice.analyze(listJucie);
		System.out.println(x);


		listJucie.clear();
		listJucie.add(new Juice(5000,0,0));
		listJucie.add(new Juice(0,2000,0));
		listJucie.add(new Juice(0,0,4000));
		x = myJuice.analyze(listJucie);
		System.out.println(x);


		listJucie.clear();
		listJucie.add(new Juice(0,1250,0));
		listJucie.add(new Juice(3000,0,3000));
		listJucie.add(new Juice(1000,1000,1000));
		listJucie.add(new Juice(2000,1000,2000));
		listJucie.add(new Juice(1000,3000,2000));
		x = myJuice.analyze(listJucie);
		System.out.println(x);

	}
}

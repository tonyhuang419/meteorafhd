package liftVisual;

class Lift extends Thread{
	private boolean[] taskList;
	public int state; //0:stop 1:up 2:down
	private int ask;
	public int now,top,bottom;
	private int floorNum;
	
	public Lift(int num){
		floorNum = num;
		taskList = new boolean[num];
		state = 0;
		ask = 0;
		now = 0;
		top = 0;
		bottom = floorNum-1;
	}
	
	public void askFloor(int asked){
		ask = asked;
		try{
			ask--;
			taskList[ask] = true;
			if( ask >= now  && ask >= top ){
				top = ask;
				if(state==0){
					state = 1;
					notice();
				}
			}
			else if(ask<=now && ask<=bottom){
				bottom = ask;
				if(state==0){
					state = 2;
					notice();
				}
			}
			else{
				System.out.println("in the middle");
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	private synchronized void notice(){
		notify();	
	}	
	public synchronized void run(){
		try{
			while(true){
				while(state==0){
					wait();
				}
				while(state == 1){
					for(; now<=top; now++){
						sleep(1000);
						if(this.getName().equals("a"))
							LiftDemo.setLabelText(now+1+"");
						else if(this.getName().equals("b"))
							LiftDemo.setLabel1Text(now+1+"");
						else if(this.getName().equals("c"))
							LiftDemo.setLabel2Text(now+1+"");
						else
							System.out.println("run/while(state == 1)/sth wrong");

						if(taskList[now]==true){
							recoverButton();
							if(Building.getAskBuilding(now, 0) == true){
								Building.setAskBuildingFalse(now, 0);
								recoverBuildingUpButton();
							}
							if(now == top){
								Building.setAskBuildingFalse(now, 1);
								recoverBuildingDownButton();
							}
							System.out.println("open");
							taskList[now] = false;
							sleep(2000);
						}
					}
					now--;
					top=0;
					if(bottom!=floorNum-1)
						state = 2;
					else{
						state=0;
					}
				}
				while(state==2){
					for(; now>=bottom; now--){
						sleep(1000);
						if(this.getName().equals("a"))
							LiftDemo.setLabelText(now+1+"");
						else if(this.getName().equals("b"))
							LiftDemo.setLabel1Text(now+1+"");
						else if(this.getName().equals("c"))
							LiftDemo.setLabel2Text(now+1+"");
						else
							System.out.println("run/state==2/sth wrong");
						if(taskList[now]==true){
							recoverButton();
							if(Building.getAskBuilding(now, 1) == true){
								Building.setAskBuildingFalse(now, 1);
								recoverBuildingDownButton();
							}
							if(now == bottom){
								Building.setAskBuildingFalse(now, 0);
								recoverBuildingUpButton();
							}
							System.out.println("open");
							taskList[now] = false;
							sleep(2000);
						}
					}
					now++;
					bottom = floorNum-1;
					if(top!=1)
						state = 1;
					else{
						state=0;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void recoverButton(){
		if(this.getName().equals("a")){
			if(now==0)
				LiftDemo.setLiftButtonEnable(11);
			else if(now==1)
				LiftDemo.setLiftButtonEnable(12);
			else if(now==2)
				LiftDemo.setLiftButtonEnable(13);
			else if(now==3)
				LiftDemo.setLiftButtonEnable(14);
			else if(now==4)
				LiftDemo.setLiftButtonEnable(15);
			else if(now==5)
				LiftDemo.setLiftButtonEnable(16);
			else
				System.out.println("recoverButton/sth wrong");
		}
		else if(this.getName().equals("b")){
			if(now==0)
				LiftDemo.setLiftButtonEnable(21);
			else if(now==1)
				LiftDemo.setLiftButtonEnable(22);
			else if(now==2)
				LiftDemo.setLiftButtonEnable(23);
			else if(now==3)
				LiftDemo.setLiftButtonEnable(24);
			else if(now==4)
				LiftDemo.setLiftButtonEnable(25);
			else if(now==5)
				LiftDemo.setLiftButtonEnable(26);
			else
				System.out.println("recoverButton/sth wrong");
		}
		else if(this.getName().equals("c")){
			if(now==0)
				LiftDemo.setLiftButtonEnable(31);
			else if(now==1)
				LiftDemo.setLiftButtonEnable(32);
			else if(now==2)
				LiftDemo.setLiftButtonEnable(33);
			else if(now==3)
				LiftDemo.setLiftButtonEnable(34);
			else if(now==4)
				LiftDemo.setLiftButtonEnable(35);
			else if(now==5)
				LiftDemo.setLiftButtonEnable(36);
			else
				System.out.println("recoverButton/sth wrong");
		}
		else
			System.out.println("recoverButton/sth wrong");
	}
	
	private void recoverBuildingUpButton(){
		if(this.getName().equals("a")){
			if(now==0)
				LiftDemo.setBuildingButtonEnable(911);
			else if(now==1)
				LiftDemo.setBuildingButtonEnable(921);
			else if(now==2)
				LiftDemo.setBuildingButtonEnable(931);
			else if(now==3)
				LiftDemo.setBuildingButtonEnable(941);
			else if(now==4)
				LiftDemo.setBuildingButtonEnable(951);
			else
				System.out.println("recoverBuildingUpButton/sth wrong");
		}
		else if(this.getName().equals("b")){
			if(now==0)
				LiftDemo.setBuildingButtonEnable(911);
			else if(now==1)
				LiftDemo.setBuildingButtonEnable(921);
			else if(now==2)
				LiftDemo.setBuildingButtonEnable(931);
			else if(now==3)
				LiftDemo.setBuildingButtonEnable(941);
			else if(now==4)
				LiftDemo.setBuildingButtonEnable(951);
			else
				System.out.println("recoverBuildingUpButton/sth wrong");
		}
		else if(this.getName().equals("c")){
			if(now==0)
				LiftDemo.setBuildingButtonEnable(911);
			else if(now==1)
				LiftDemo.setBuildingButtonEnable(921);
			else if(now==2)
				LiftDemo.setBuildingButtonEnable(931);
			else if(now==3)
				LiftDemo.setBuildingButtonEnable(941);
			else if(now==4)
				LiftDemo.setBuildingButtonEnable(951);
			else
				System.out.println("recoverBuildingUpButton/sth wrong");
		}
		else
			System.out.println("sth wrong..recoverBuildingUpButton/sth wrong");
	}
	
	
	private void recoverBuildingDownButton(){
		if(this.getName().equals("a")){
			if(now==1)
				LiftDemo.setBuildingButtonEnable(922);
			else if(now==2)
				LiftDemo.setBuildingButtonEnable(932);
			else if(now==3)
				LiftDemo.setBuildingButtonEnable(942);
			else if(now==4)
				LiftDemo.setBuildingButtonEnable(952);
			else if(now==5)
				LiftDemo.setBuildingButtonEnable(962);
			else
				System.out.println("recoverBuildingUpButton/sth wrong");
		}
		else if(this.getName().equals("b")){
			if(now==1)
				LiftDemo.setBuildingButtonEnable(922);
			else if(now==2)
				LiftDemo.setBuildingButtonEnable(932);
			else if(now==3)
				LiftDemo.setBuildingButtonEnable(942);
			else if(now==4)
				LiftDemo.setBuildingButtonEnable(952);
			else if(now==5)
				LiftDemo.setBuildingButtonEnable(962);
			else
				System.out.println("recoverBuildingUpButton/sth wrong");
		}
		else if(this.getName().equals("c")){
			if(now==1)
				LiftDemo.setBuildingButtonEnable(922);
			else if(now==2)
				LiftDemo.setBuildingButtonEnable(932);
			else if(now==3)
				LiftDemo.setBuildingButtonEnable(942);
			else if(now==4)
				LiftDemo.setBuildingButtonEnable(952);
			else if(now==5)
				LiftDemo.setBuildingButtonEnable(962);
			else
				System.out.println("recoverBuildingUpButton/sth wrong");
		}
		else
			System.out.println("recoverBuildingUpButton/sth wrong");
	}
}

package liftVisual;

public class Building{
	static private int floorNum = 6;
	static private boolean[][] askBuilding = new boolean[floorNum][2]; //0:no 1:up 2:down
	public Lift liftOne;
	public Lift liftTwo;
	public Lift liftThree;
	
	public Building(){
		liftOne = new Lift(floorNum);
		liftTwo = new Lift(floorNum);
		liftThree = new Lift(floorNum);
	}

	public void setAskBuilding(int floorNum,int state) {
		askBuilding[floorNum][state] = true;
		findLatestLift(floorNum,state);
	}
	
	static public void setAskBuildingFalse(int floorNum,int state) {
		askBuilding[floorNum][state] = false;
	}
	
	static public boolean getAskBuilding(int x,int y) {
		return askBuilding[x][y];
	}
	
	
	Lift nearest,middle,far;
	public void findLatestLift(int floorNum,int state){
		if (Math.abs(liftOne.now - floorNum) <= Math.abs(liftTwo.now - floorNum) && 
				Math.abs(liftOne.now - floorNum) <= Math.abs(liftThree.now - floorNum) ){
			nearest = liftOne;
			if(Math.abs(liftTwo.now - floorNum) <= Math.abs(liftThree.now - floorNum)){
				middle = liftTwo;
				far = liftThree;
			}
			else{
				middle = liftThree;
				far = liftTwo;
			}
		}
		else if(Math.abs(liftTwo.now - floorNum) <= Math.abs(liftThree.now - floorNum)){
			nearest = liftTwo;
			if(Math.abs(liftOne.now - floorNum) <= Math.abs(liftThree.now - floorNum)){
				middle = liftOne;
				far = liftThree;
			}
			else{
				middle = liftThree;
				far = liftOne;		
			}
		}
		else{
			nearest = liftThree;
			if(Math.abs(liftOne.now - floorNum) <= Math.abs(liftTwo.now - floorNum)){
				middle = liftOne;
				far = liftTwo;
			}
			else{
				middle = liftTwo;
				far = liftOne;
			}
		}
		
		if(nearest.state == 0){
			nearest.askFloor(floorNum+1);
		}
		else if(nearest.state == state+1){
			nearest.askFloor(floorNum+1);
		}
		else if(middle.state == 0){
			middle.askFloor(floorNum+1);
		}
		else if(middle.state == state+1){
			middle.askFloor(floorNum+1);
		}
		else if(far.state == 0){
			far.askFloor(floorNum+1);
		}
		else if(middle.state == state+1){
			far.askFloor(floorNum+1);
		}
		else
			nearest.askFloor(floorNum+1);
	}



}
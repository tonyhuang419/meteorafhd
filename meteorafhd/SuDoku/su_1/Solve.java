package su_1;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class Table{
	Struct tableX = new Struct();
	boolean judgement = false;
	private int[][] testarea =
	{       {0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},};
	private int[] candidate= new int[Struct.size];
	
		
	private  void produceOneRow(){
		Date time = new Date();
		int seed = (int)time.getTime();
		Random ranNum = new Random(seed);
		int p,t;
		for(int i=0;i<Struct.size;i++)
			candidate[i]=i+1;
		for(int i=0;i<Struct.size;i++){
			p = ranNum.nextInt(9);
			t = candidate[p];
			candidate[p] = candidate[i];
			candidate[i] = t;
		}
		for(int i=0;i<Struct.size;i++)
			testarea[0][i] = candidate[i];
	}
	
	public boolean solve(){
		judgement = false;
		produceOneRow();
		tableX.init(testarea);
		int tempNum;
		for(int r=0;r<Struct.size;r++){
			for(int c=0;c<Struct.size;c++){
				if(judgement==true)
					return false;
				if(tableX.isTableLock(r, c))
					continue;
				else{
					while(true){
						tempNum = tableX.getMapNumber(r,c,tableX.getTableNumber(r, c));
						if(tempNum == -1){
							if(c==0){
								tableX.setTableNumber(r, 0, 0);
								r--;
								for(int i=0;i<Struct.size;i++){
									tableX.setTableNumber(r, i, 0);
									tableX.unlockMapNum(r, i);
								}								
							}
							else{
								tableX.setTableNumber(r, c, 0);
								c--;
								tableX.unlockMapNum(r, tableX.getTableNumber(r, c)-1);
							}
						}
						else if(tableX.checkAll(r,c,tempNum)){
							tableX.setTableNumber(r, c, tempNum);
							tableX.lockMapNum(r, tempNum-1);
							break;
						}
						else if(!tableX.checkAll(r,c,tempNum)){
							tableX.setTableNumber(r, c, tempNum);
							continue;
						}
						else
							System.out.println("sth wrong");
					}
				}
				//tableX.display();
			}
		}
		//tableX.display();
		return true;
	}
	public void setTime(){
		int numberOfMillisecondsInTheFuture = 2000; 
		Date timeToRun = new Date(System.currentTimeMillis()+numberOfMillisecondsInTheFuture);//当前时间后的10秒
		Timer timer1 = new Timer();

		timer1.schedule(new TimerTask() {
			public void run() {
				judgement = true;
			}
		}, timeToRun);
	}

	public void setJudgement(boolean judgement) {
		this.judgement = judgement;
	}
	
	public int[][][] getTable() {
		return tableX.getAllTableNumber();
	}
}

//public class Solve {
//	public static void main(String[] args) {
//		Table t = new Table();
//		t.solvePuzzle();
//		t.solve();
//	}
//}
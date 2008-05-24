package su_1;

class Struct {
	static int size=9;
	private int[][][] table = new int[size][size][2];
	private int[][][] map = new int[size][size][2];
	

//  1;lock  0:unlock
	public void init(int[][] p){
	
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				map[i][j][0] = j+1;
				map[i][j][1] = 0;
			}
		}
		//WARNING!!!!!:more num locked,more time expend
		int temp;
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(p[i][j]!=0){
					table[i][j][0] = p[i][j];
					table[i][j][1] = 1;
					temp = p[i][j];
					if(map[i][temp-1][0]==p[i][j]){
						map[i][temp-1][1]=1;
					}

				}
				else{
					table[i][j][0] = 0;
					table[i][j][1] = 0;
				}
			}
		}
	}
	public void clear(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				for(int k=0;k<2;k++)
				map[i][j][0] = 0;
				map[i][j][1] = 0;
				table[i][j][0] = 0;
				table[i][j][1] = 0;
			}
		}	
	}
	//---------table--------------------------------------------------
	public void setTableNumber(int r,int c,int _num){
        if(_num<0||_num>9){   	}//throw new IllegalArgumentException("number is out of 0~9 :"+_num);
        else if(table[r][c][1]==1){   	}//throw new IllegalStateException("table("+r+","+c+") is fixed");
        else table[r][c][0] =_num;
	}
	public int getTableNumber(int r,int c){
		return table[r][c][0];
	}
	public void lockTableNum(int r,int c){
		table[r][c][1]=1;
	}
	public void unlockTableNum(int r,int c){
		table[r][c][1]=0;
	}
	public boolean isTableLock(int r,int c){
		if(table[r][c][1]==0)
			return false;
		else
			return true;
		
	}
	public int[][][] getAllTableNumber(){
		return table;
	}
	//------------check first, set second--------------------------------------------------
	private boolean checkTableRow(int num,int row){
		for(int i=0;i<size;i++){
			if(num == table[row][i][0])
				return false;
		}
		return true;
	}
	private boolean checkTableCol(int num,int col){
		for(int i=0;i<size;i++){
			if(num == table[i][col][0])
				return false;
		}
		return true;
	}
	private boolean checkTableNine(int num,int row,int col){
		int m = row/3*3;
		int n = col/3*3;
		for(int i=m;i<m+3;i++){
			for(int j=n;j<n+3;j++){
				if(table[i][j][0]==num)
					return false;
			}
		}
		return true;
	}
	public boolean checkAll(int r,int c,int num){
		if(this.checkTableCol(num, c)
				&&this.checkTableRow(num, r)
				&&this.checkTableNine(num, r, c)){
			return true;
		}
		return false;
	}
	
	//-------------Map--------------------------------	
	public int getMapNumber(int r,int c,int tableNum){
		if(tableNum==size)
			return -1;
		while(this.isMapLock(r, tableNum)){
			if(tableNum<size-1){
				tableNum++;
				continue;
			}
			else
				return -1;
		}
		return map[r][tableNum][0];	
	}
	
	public void lockMapNum(int r,int c){
		map[r][c][1]=1;
	}
	public void unlockMapNum(int r,int c){
		map[r][c][1]=0;
	}
	public boolean isMapLock(int r,int c){
		if(map[r][c][1]==0)
			return false;
		else
			return true;
		
	}

	public void display(){
		System.out.println("-------------num-------------");
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				for(int k=0;k<2;k++){
					System.out.print(table[i][j][k]);
				}
				System.out.print("-");
			}
			System.out.println("");
		}
		System.out.println("-------------map-------------");
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				for(int k=0;k<2;k++){
					System.out.print(map[i][j][k]);
				}
				System.out.print("-");
			}
			System.out.println("");
		}
	}
	
}

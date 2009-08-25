package gcodeJam.alwaysTurnLeft;

import gcodeJam.utilTools.UtilTools;

import java.util.List;

import org.apache.commons.lang.StringUtils;


public class MyAlwaysTurnLeft {

	public String combineCharArr(char[] org , char[] dest){
		for(int i=0;i<4;i++){
			if(dest[i]=='1'){
				org[i] = '1';
			}
		}
		return String.valueOf(org);
	}

	public String combineCharArr(char[] org , char direction){
		char dest[]="0000".toCharArray();
		switch(direction){
		case Maze.NORTH:
			dest = Maze.NORTH_CODE.toCharArray();
			break;
		case Maze.SOUTH:
			dest = Maze.SOUTH_CODE.toCharArray();
			break;
		case Maze.WEST:
			dest = Maze.WEST_CODE.toCharArray();
			break;
		case Maze.EAST:
			dest = Maze.EAST_CODE.toCharArray();
			break;
		}
		return this.combineCharArr(org, dest);
	}

	public char turnDirection(char orgDirection , char turnDirection){
		switch (orgDirection){
		case Maze.NORTH:
			switch (turnDirection){
			case Maze.TURN_RIGHT:
				return Maze.EAST;
			case Maze.TURN_LEFT:
				return Maze.WEST;
			default:
				return orgDirection;
			}
		case Maze.SOUTH:
			switch (turnDirection){
			case Maze.TURN_RIGHT:
				return Maze.WEST;
			case Maze.TURN_LEFT:
				return Maze.EAST;
			default:
				return orgDirection;
			}
		case Maze.WEST:
			switch (turnDirection){
			case Maze.TURN_RIGHT:
				return Maze.NORTH;
			case Maze.TURN_LEFT:
				return Maze.SOUTH;
			default:
				return orgDirection;
			}
		case Maze.EAST:
			switch (turnDirection){
			case Maze.TURN_RIGHT:
				return Maze.SOUTH;
			case Maze.TURN_LEFT:
				return Maze.NORTH;
			default:
				return orgDirection;
			}
		default:
			return orgDirection;
		}
	}

	public char getContraryDirection(char direction){
		switch (direction){
		case Maze.NORTH:
			return Maze.SOUTH;
		case Maze.SOUTH:
			return Maze.NORTH;
		case Maze.WEST:
			return Maze.EAST;
		case Maze.EAST:
			return Maze.WEST;
		}
		return direction;
	}

	public String getInfo(String orgInfo , char direction , char preActionCode ){
		if(orgInfo==null){
			orgInfo = "0000";
		}
		switch (direction){
		case Maze.NORTH:
			if(preActionCode==Maze.GO){
				return  this.combineCharArr( orgInfo.toCharArray() , Maze.NORTHSOUTH_CODE.toCharArray() );
			}
			return  this.combineCharArr( orgInfo.toCharArray() , Maze.NORTH_CODE.toCharArray());
		case Maze.SOUTH:
			if(preActionCode==Maze.GO){
				return  this.combineCharArr( orgInfo.toCharArray() , Maze.NORTHSOUTH_CODE.toCharArray() );
			}
			return  this.combineCharArr( orgInfo.toCharArray() , Maze.SOUTH_CODE.toCharArray());
		case Maze.WEST:
			if(preActionCode==Maze.GO){
				return  this.combineCharArr( orgInfo.toCharArray() , Maze.EASTWEST_CODE.toCharArray() );
			}
			return  this.combineCharArr( orgInfo.toCharArray() , Maze.WEST_CODE.toCharArray());
		case Maze.EAST:
			if(preActionCode==Maze.GO){
				return  this.combineCharArr( orgInfo.toCharArray() , Maze.EASTWEST_CODE.toCharArray());
			}
			return this.combineCharArr( orgInfo.toCharArray() , Maze.EAST_CODE.toCharArray());
		}
		return orgInfo;
	}

	public String converCodeToChar(String code){
		code = StringUtils.reverse(code);
		Integer i = Integer.valueOf(code, 2);
		return Integer.toHexString(i) ;
	}


	public void move(char[] actionString, String[][] mazeArr, Maze maze , String sequence ) {
		for(int i=1; i<actionString.length-1;i++){
			if("1".equals(sequence) && i==1){
				mazeArr[maze.x][maze.y]  = this.combineCharArr("0000".toCharArray(), Maze.NORTH_CODE.toCharArray());
			}
			switch (actionString[i]){
			case 'W':
				mazeArr[maze.x][maze.y] = this.getInfo(mazeArr[maze.x][maze.y], maze.direction , actionString[i-1] );
				switch (maze.direction){
				case Maze.NORTH:
					maze.x--;
					break;
				case Maze.SOUTH:
					maze.x++;
					break;
				case Maze.WEST:
					maze.y--;
					break;
				case Maze.EAST:
					maze.y++;
					break;
				}
				break;
			case Maze.TURN_LEFT:
				maze.direction = this.turnDirection(maze.direction, Maze.TURN_LEFT);
				break;
			case Maze.TURN_RIGHT:
				maze.direction = this.turnDirection(maze.direction, Maze.TURN_RIGHT);
				break;
			}
		}
		if(mazeArr[maze.x][maze.y]==null){
			mazeArr[maze.x][maze.y] = "0000";
		}
		mazeArr[maze.x][maze.y]  = this.combineCharArr(mazeArr[maze.x][maze.y].toCharArray(), maze.direction );
	}


	public String[][] analyze(char[] entrance_to_exit , char[] exit_to_entrance){
		String[][] mazeArr = new String[10000][10000];
		Maze maze = new Maze(0,5000 , Maze.SOUTH);
		this.move(entrance_to_exit, mazeArr, maze,"1");
		maze.direction = this.getContraryDirection(maze.direction);
		this.move(exit_to_entrance, mazeArr, maze,"");
		return mazeArr;
	}

	public static void main(String[] args) throws Exception{
		MyAlwaysTurnLeft m = new MyAlwaysTurnLeft();
		//m.analyze("WW".toCharArray(), "WW".toCharArray());

//		List<String[]> ls = UtilTools.fileParse("src/main/java/gcodeJam/alwaysTurnLeft/B-small-practice.in",2);
		List<String[]> ls = UtilTools.fileParse("src/main/java/gcodeJam/alwaysTurnLeft/B-large-practice.in",2);
		int i=0;
		for(String[] strArr:ls){
			if(StringUtils.isNotBlank(strArr[0]) 
					&& StringUtils.isNotBlank(strArr[1])){
				String[][] maze = m.analyze(strArr[0].toCharArray(), strArr[1].toCharArray());
				System.out.println("Case #"+ ++i +":");

				int sign=0;
				for(int j=0;j<300;j++){
					for(int k=0;k<300;k++){
						if(maze[j][k]!=null){
							//							System.out.print(maze[j][k]+" ");
							System.out.print(m.converCodeToChar(maze[j][k]));
							sign = 1;
						}
					}
					if(sign==1){
						System.out.println("");
						sign=0;
					}
				}
			}
		}
	}

}

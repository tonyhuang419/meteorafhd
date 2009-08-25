package gcodeJam.alwaysTurnLeft;

import gcodeJam.utilTools.UtilTools;

import java.util.List;

import org.apache.commons.lang.StringUtils;


public class MyAlwaysTurnLeft {

	public String combineString(char[] org , char[] dest){
		for(int i=0;i<4;i++){
			if(dest[i]=='1'){
				org[i] = '1';
			}
		}
		return String.valueOf(org);
	}

	public char setDirection(char org , char turn){
		switch (org){
		case 'N':
			switch (turn){
			case 'R':
				return 'E';
			case 'L':
				return 'W';
			default:
				return org;
			}
		case 'S':
			switch (turn){
			case 'R':
				return 'W';
			case 'L':
				return 'E';
			default:
				return org;
			}
		case 'W':
			switch (turn){
			case 'R':
				return 'N';
			case 'L':
				return 'S';
			default:
				return org;
			}
		case 'E':
			switch (turn){
			case 'R':
				return 'S';
			case 'L':
				return 'N';
			default:
				return org;
			}
		default:
			return org;
		}
	}

	public char getContraryDirection(char direction){
		switch (direction){
		case 'N':
			return 'S';
		case 'S':
			return 'N';
		case 'W':
			return 'E';
		case 'E':
			return 'W';
		}
		return direction;
	}

	public String getInfo(String orgInfo , char direction , char preActionCode ){
		if(orgInfo==null){
			orgInfo = "0000";
		}
		switch (direction){
		case 'N':
			if(preActionCode=='W'){
				return  this.combineString( orgInfo.toCharArray() , "1100".toCharArray());
			}
			return  this.combineString( orgInfo.toCharArray() , "1000".toCharArray());
		case 'S':
			if(preActionCode=='W'){
				return  this.combineString( orgInfo.toCharArray() , "1100".toCharArray());
			}
			return  this.combineString( orgInfo.toCharArray() , "0100".toCharArray());
		case 'W':
			if(preActionCode=='W'){
				return  this.combineString( orgInfo.toCharArray() , "0011".toCharArray());
			}
			return  this.combineString( orgInfo.toCharArray() , "0010".toCharArray());
		case 'E':
			if(preActionCode=='W'){
				return  this.combineString( orgInfo.toCharArray() , "0011".toCharArray());
			}
			return this.combineString( orgInfo.toCharArray() , "0001".toCharArray());
		}
		return orgInfo;
	}


	public String converCodeToChar(String code){
		code = StringUtils.reverse(code);
		Integer i = Integer.valueOf(code, 2);
		return Integer.toHexString(i) ;
	}

	public String[][] analyze(char[] entrance_to_exit , char[] exit_to_entrance){
		String[][] maze = new String[300][300];
		int locRow = 0;
		int locColumn = 150;
		char direction='S';
		for(int i=1; i<entrance_to_exit.length-1;i++){
			if(i==1){
				maze[locRow][locColumn]  = this.combineString("0000".toCharArray(), "1000".toCharArray());
			}
			switch (entrance_to_exit[i]){
			case 'W':
				maze[locRow][locColumn] = this.getInfo(maze[locRow][locColumn], direction , entrance_to_exit[i-1] );
				switch (direction){
				case 'N':
					locRow--;
					break;
				case 'S':
					locRow++;
					break;
				case 'W':
					locColumn--;
					break;
				case 'E':
					locColumn++;
					break;
				}
				break;
			case 'L':
				direction = this.setDirection(direction, 'L');
				break;
			case 'R':
				direction = this.setDirection(direction, 'R');
				break;
			}
			//System.out.println(locRow + "," + locColumn );
		}

		direction = this.getContraryDirection(direction);
		for(int i=1; i<exit_to_entrance.length-1;i++){
			switch (exit_to_entrance[i]){
			case 'W':
				maze[locRow][locColumn] = this.getInfo(maze[locRow][locColumn], direction ,  exit_to_entrance[i-1] );
				switch (direction){
				case 'N':
					locRow--;
					break;
				case 'S':
					locRow++;
					break;
				case 'W':
					locColumn--;
					break;
				case 'E':
					locColumn++;
					break;
				}
				break;
			case 'L':
				direction = this.setDirection(direction, 'L');
				break;
			case 'R':
				direction = this.setDirection(direction, 'R');
				break;
			}
		}
		return maze;
	}

	public static void main(String[] args) throws Exception{
		MyAlwaysTurnLeft m = new MyAlwaysTurnLeft();
//		m.analyze("WW".toCharArray(), "WW".toCharArray());
		
		List<String[]> ls = UtilTools.fileParse("src/main/java/gcodeJam/alwaysTurnLeft/B-small-practice.in",2);
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

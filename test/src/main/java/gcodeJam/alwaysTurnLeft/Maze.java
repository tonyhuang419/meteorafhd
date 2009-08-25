package gcodeJam.alwaysTurnLeft;

public class Maze {

	final public static String NORTH_CODE = "1000";
	final public static String SOUTH_CODE = "0100";
	final public static String WEST_CODE = "0010";
	final public static String EAST_CODE = "0001";
	final public static String NORTHSOUTH_CODE = "1100";
	final public static String EASTWEST_CODE = "0011";
	final public static char NORTH = 'N';
	final public static char SOUTH = 'S';
	final public static char WEST = 'W';
	final public static char EAST = 'E';
	final public static char TURN_LEFT = 'L';
	final public static char TURN_RIGHT = 'R';
	final public static char GO = 'W';
	
	public int x;
	public int y;
	public char direction;
	
	public Maze(int x,int y , char direction){
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	
}

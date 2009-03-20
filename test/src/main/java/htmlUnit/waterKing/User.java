package htmlUnit.waterKing;

public class User {
	
	private String username;
	
	private String password;
	
	private int readLevel;
	
	private int pageNum;
	
	public User(){
		
	}
	
	public User(String username ,String password ,int readLevel,int pageNum ){
		this.username = username;
		this.password = password;
		this.readLevel = readLevel;
		this.pageNum = pageNum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getReadLevel() {
		return readLevel;
	}

	public void setReadLevel(int readLevel) {
		this.readLevel = readLevel;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	
}

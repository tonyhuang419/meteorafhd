package def;

public class BaseClass {
	
	private String baseString;
	
	public Stu s = new Stu();	
	
	protected void hellWorld(){
		System.out.println(" hello world ");
	}

	public String getBaseString() {
		return baseString;
	}

	public void setBaseString(String baseString) {
		this.baseString = baseString;
	}
	
}

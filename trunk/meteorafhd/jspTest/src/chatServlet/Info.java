package chatServlet;

public class Info{
	private String name;
	private String info;
	private String time;
	
	public Info(String name,String info,String time){
		this.name = name;
		this.info = info;
		this.time = time;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}	
}

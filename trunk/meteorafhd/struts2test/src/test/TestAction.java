package test;


public class TestAction {
	private Stu s = new Stu();


	public String execute(){
		s.setAge(19);
		s.setName(s.getName());

		return "success";  
	}

	public Stu getS() {
		return s;
	}

	public void setS(Stu s) {
		this.s = s;
	}


}

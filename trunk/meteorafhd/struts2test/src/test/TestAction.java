package test;


public class TestAction {
	private Stu s = null;


	public String execute()  { 
		if( "jack".equals(s.getName())){
			System.out.println(s.getName());
			System.out.println(s.getAge());
			s.setName("tom");
			s.setAge(20);	
		}
		else{
			System.out.println(s.getName());
			System.out.println(s.getAge());
			s = new Stu();
			s.setName("jack");
			s.setAge(19);
		}
		return "success";  
	}

	public Stu getS() {
		return s;
	}

	public void setS(Stu s) {
		this.s = s;
	}



}

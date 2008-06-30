package reflect;

public class Stu {
	private String name = null;
	private int age = -1;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		Stu s = (Stu)obj;
//		if(this.age == s.age &&  this.name.equals(s.name)){
//			return true;
//		}else{
//			return false;
//		}
//	}
	
}

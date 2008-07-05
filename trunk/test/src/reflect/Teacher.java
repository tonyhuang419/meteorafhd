package reflect;

import java.io.Serializable;

public class Teacher implements Serializable{

	private static final long serialVersionUID = -8677168135902763109L;
	private String name = null;
	private int age = -1;
	private boolean sex = false;
	private String marks = null;
	
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
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}

package commons.lang;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Test {




	public static void main(String args[]){
		Stu s = new Stu("jack", 19);
		System.out.println(s.toString());
		System.out.println(s.hashCode());
		System.out.println(s.compareTo(new Stu("jack",19)));
		System.out.println(s.compareTo(new Stu("jack",20)));
		System.out.println(s.compareTo(new Stu()));
		
		System.out.println(s.equals(new Stu("jack",19)));
		System.out.println(s.equals(new Stu("jack",20)));
		System.out.println(s.equals(new Stu()));
	}
}

class Stu{
	String name;
	int age;

	Stu(){ }

	Stu(String name,int age){
		this.name = name;
		this.age  = age;
	}

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

	public int compareTo(Object o) {
		return CompareToBuilder.reflectionCompare(this, o);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}

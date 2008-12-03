package commons.collections;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateFormatUtils;

public class Employee {
	private String name;

	private int age;
	private Date dateJoined;
	private String grade;
	private double salary;

	public Employee() {

	}

	public Employee(String name, int age, Date dateJoined, String grade, double salary) {
		this.name = name;
		this.age = age;
		this.dateJoined = dateJoined;
		this.grade = grade;
		this.salary = salary;
	}


	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("name", name)
		.append("age", age)
		.append("dateJoined", DateFormatUtils.format(dateJoined, "yyyy-MM-dd"))
		.append("grade", grade)
		.append("salary", salary)
		.toString();
	}

	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
}

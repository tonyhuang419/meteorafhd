package com.fhd.test2;

public class Student {
	private long id;
	private String studentName;
	private Teacher teacher;

	public Student() {}

	public long getid() {
		return id;
	}
	public void setid(long id) {
		this.id = id;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;	    
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Student(String studentName, Teacher teacher) {
		this.studentName = studentName;
		this.teacher = teacher;
	}
}

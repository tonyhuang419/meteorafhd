package com.fhd.test2;

public class Teacher {

	private long id;
	private String teacherName;
	private Course course;

	public Teacher(){}
	
	public Teacher(String teacherName) {
		this.teacherName = teacherName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}

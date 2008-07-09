package com.fhd.test4;

public class Teacher {

	private long id;
	private String teacherName;

	public Teacher(){}
	
	public long getid() {
		return id;
	}
	public void setid(long id) {
		this.id = id;
	}
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teachername) {

		this.teacherName = teachername;

	}
	public Teacher(String teacherName) {
		this.teacherName = teacherName;
	}
}

package com.fhd.test;

public class Message
{
	private int id;
	private String title;
	private String content;

	public Message(){
	}
	public void setId(int id) {
		this.id = id; 
	}
	public void setTitle(String title) {
		this.title = title; 
	}
	public void setContent(String content) {
		this.content = content; 
	}
	public int getId() {
		return (this.id); 
	}
	public String getTitle(){
		return (this.title); 
	}
	public String getContent() {
		return (this.content); 
	}
}


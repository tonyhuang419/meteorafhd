package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.jpa.JPASupport;

@Entity
public class Comment extends JPASupport {
	private static final long serialVersionUID = -9149586379548031631L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	private String author;
	private String title;
	private String content;
	private Date createdTime;
	
	

    
}


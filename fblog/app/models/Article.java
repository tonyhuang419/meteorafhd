package models;

import java.util.Date;

import javax.jdo.annotations.Transactional;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import play.db.jpa.JPASupport;

import com.google.appengine.api.datastore.Text;

@Entity
public class Article extends JPASupport {
	private static final long serialVersionUID = 1897622244083058137L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	public Long id;
	public String title;
	@Enumerated
	public Text content;
	public Long readCount;
	public Date lastReadTime;
	public Date lastModifyTime;
	public Date createdTime;
	@Transient
	public String contentX;

	
	public Article(){ }
	
	public Article (String title , String contentX ){
		this.title = title;
		this.content = new Text(contentX);
		this.readCount = 0L;
		this.createdTime = new Date();
		this.lastModifyTime = new Date();
		this.save();
	}


}


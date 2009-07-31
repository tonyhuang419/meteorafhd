package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.jpa.JPASupport;

@Entity
public class MicroArticle extends JPASupport {
	private static final long serialVersionUID = 3333266204517881198L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	public Long id;
	public String content;
	public Date createdTime;
	public Boolean isActive;
	
	public MicroArticle(){ }
		
	public MicroArticle (String title , String content ){
		this.content = content;
		this.createdTime = new Date();
		this.isActive = true;
		this.save();
	}
	
	public static List<MicroArticle> getActiveArticle(){
		return MicroArticle.findBy(" isActive = true " );
	}
	
	public static void disable(Long id ){
		MicroArticle article = MicroArticle.findById(id);
		article.isActive = false;
		MicroArticle.em().persist(article);
	}
	
	public static void enable(Long id){
		MicroArticle article = MicroArticle.findById(id);
		article.isActive = true;
		MicroArticle.em().persist(article);
	}
	
	public static void deleteArticle(Long id){
		MicroArticle article = MicroArticle.findById(id);
		MicroArticle.em().remove(article);
	}
	
}


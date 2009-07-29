package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.jpa.JPA;
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
	public Boolean isActive;
	
	public Article(){ }
		
	public Article (String title , Text content ){
		this.title = title;
		this.content = content;
		this.readCount = 0L;
		this.createdTime = new Date();
		this.lastModifyTime = new Date();
		this.isActive = true;
		this.save();
	}
	
	public static List<Article> getActiveArticle(){
		return Article.findBy(" isActive = true " );
	}
	
	public static void modActicle(Long id , String title , String content){
		Article article = Article.findById(id);
		article.title = title;
		article.content = new Text(content);
		article.lastModifyTime = new Date();
		Article.em().persist(article);
	}

	public static void disable(Long id ){
		Article article = Article.findById(id);
		article.isActive = false;
		Article.em().persist(article);
	}
	
	public static void enable(Long id){
		Article article = Article.findById(id);
		article.isActive = true;
		Article.em().persist(article);
	}
	
	public static void deleteArticle(Long id){
		Article article = Article.findById(id);
		Article.em().remove(article);
	}
	
	
}


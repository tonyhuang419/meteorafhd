package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	public Date lastModifyTime;
	public Date createdTime;
	public Boolean isActive;
	public Long type;  //1：blog  2：twitter
	
	public Article(){ }
		
	public Article (String title , Text content , Long type ){
		this.title = title;
		this.content = content;
		this.readCount = 0L;
		this.createdTime = new Date();
		this.lastModifyTime = new Date();
		this.isActive = true;
		this.type = type;
		this.save();
	}
	
	public static List<Article> getActiveArticle(){
		return Article.findBy(" isActive = true " );
	}
	
	public static List<Article> getActiveBlog(String active){
		if("ALL".equals(active)){
			return Article.findBy(" type=1 " );
		}
		else{
			return Article.findBy(" isActive = true and type=1 " );
		}
	}
	
	public static List<Article> getActiveTwitter(String active){
		if("ALL".equals(active)){
			return Article.findBy(" type=2 " );
		}
		else{
			return Article.findBy(" isActive = true and type=2 " );
		}
	}
	
	public static void modBlogActicle(Long id , String title , String content){
		Article article = Article.findById(id);
		article.title = title;
		article.content = new Text(content);
		article.lastModifyTime = new Date();
		Article.em().persist(article);
	}
	
	public static void modTwitterActicle(Long id , String content){
		Article article = Article.findById(id);
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
	
	public static void addReadCount(Long id){
		Article article = Article.findById(id);
		article.readCount = article.readCount +1;
		Article.em().persist(article);
	}
	
	
}


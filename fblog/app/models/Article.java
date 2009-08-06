package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;

import play.db.jpa.JPASupport;
import utilTools.PageInfo;
import utilTools.UtilTools;

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
		return Article.find(" isActive = true " ).all();
	}
	
	public static List<Article> getActiveBlog(String active){
		if("ALL".equals(active)){
			return Article.find(" type=1 " ).all();
		}
		else{
			return Article.find(" isActive = true and type=1 " ).all();
		}
	}
	
	public static List<Article> getActiveTwitter(String active){
		if("ALL".equals(active)){
			return Article.find(" type=2 " ).all();
		}
		else{
			return Article.find(" isActive = true and type=2 " ).all();
		}
	}
	
	/**
	 * 
	 * @param active
	 * @param page
	 * @param type 1:blog 2:twitter
	 * @return
	 */
	public static PageInfo getActiveArticles(String active , int page , String type){
		StringBuffer jsql = new StringBuffer(" select a from models.Article a where  ");
		if("ALL".equals(active)){
			jsql.append("type=").append(type);
		}
		else{
			jsql.append(" isActive = true and type= ").append(type);
		}
		jsql.append(" order by createdTime desc ");
		return UtilTools.getPageInfo(Article.em(), jsql.toString() , page);
	}
	
	/**
	 * get  menu
	 */
	@SuppressWarnings("unchecked")
	public static List<Article> getNumBlog(String active , int num ){
		StringBuffer jsql = new StringBuffer(" select a from models.Article a where type = 1");
		if( !"ALL".equals(active)){
			jsql.append(" and isActive = true ");
		}
		jsql.append(" order by createdTime desc ");
		Query query = Article.em().createQuery(jsql.toString());
		query.setMaxResults(10);
		return query.getResultList();
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
		Comment.removeByArticleId(id);
	}
	
	public static void addReadCount(Long id){
		Article article = Article.findById(id);
		article.readCount = article.readCount + 1;
		Article.em().persist(article);
	}
	
	
}


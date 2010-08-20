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
	public Long commentNum;
	
	public Article(){ }
		
	public Article (String title , Text content , Long type ){
		this.title = title;
		this.content = content;
		this.readCount = 0L;
		this.createdTime = new Date();
		this.lastModifyTime = new Date();
		this.isActive = true;
		this.type = type;
		commentNum = 0L;
		this.save();
	}
	
	public static List<Article> getRssArticles( ){
		return Article.find(" lastModifyTime>=?1 and lastModifyTime <= ?2" +
				"  and isActive = true order by lastModifyTime desc " , UtilTools.getDelayMonth(-1) , new Date() ).all();
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
		query.setMaxResults(num);
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
	
	public static void addCommentNum(Long id){
		Article article = Article.findById(id);
		if(article.commentNum==null){
			article.commentNum = 1L;
		}
		else{
			article.commentNum = article.commentNum + 1;
		}
		Article.em().persist(article);
	}
	
}


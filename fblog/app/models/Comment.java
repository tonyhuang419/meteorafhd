package models;

import java.util.Date;
import java.util.List;

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
	public Long id;
	public String author;
	public String content;
	public Date createdTime;
	public Long articleId;
	public Boolean isActive;
	public String commentIP;

	public Comment (String author , String content , Long articleId , String commentIP){
		this.author = author;
		this.content = content;
		this.articleId = articleId;
		this.createdTime = new Date();
		this.isActive = true;
		this.commentIP = commentIP;
		Article.addCommentNum(articleId);
		this.save();
	}
	
	public static List<Comment> getCommentsByArticleId(Long articleId ) {
		return Comment.find("articleId = " + articleId +" order by id desc" ).all();
	}
	
	public static void removeByArticleId(Long articleId){
		Comment.em().createQuery( "delete from models.Comment c where c.articleId = " + articleId);
	}
    
}


package controllers;

import java.util.List;

import models.Article;
import models.Comment;
import play.data.validation.Required;
import play.mvc.Controller;
import utilTools.PageInfo;
import utilTools.UtilTools;

public class Client extends Controller {

	/**
	 * main page first page
	 */
	public static void index() {
		PageInfo pi = Article.getActiveArticles("true", 1 , "1");
		List<Article> recentBlog = Article.getNumBlog("true",  10);
		String pageTag = UtilTools.getPageTag(pi, "Client/blog");
		render(pi, pageTag , recentBlog);
	}

	/**
	 * blog with page
	 */
	public static void blog(int page) {
		PageInfo pi = Article.getActiveArticles("true", page , "1");
		List<Article> recentBlog = Article.getNumBlog("true",  10);
		String pageTag = UtilTools.getPageTag(pi,"/Client/blog");
		render("Client/index.html", pi, pageTag, recentBlog);
	}
	
	/**
	 * twitter with page
	 */
	public static void twitter(int page) {
		PageInfo pi = Article.getActiveArticles("true", page , "2");
		String pageTag = UtilTools.getPageTag(pi,"/Client/twitter");
		render(pi,pageTag);
	}
	
	
	/**
	 * blog Detail
	 */
	public static void detail(Long id){
		Article article = Article.findById(id);
		Article.addReadCount(id);
		List<Comment> comments = Comment.getCommentsByArticleId(id);
		render(article , comments);
	}

	
	/**
	 * add comment for blog
	 */
	public static void addComment(@Required String author , 
			@Required String content , @Required Long articleId ) {
		new Comment(author, content , articleId );
		detail(articleId);
	}

}
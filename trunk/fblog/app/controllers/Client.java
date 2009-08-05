package controllers;

import java.util.List;

import models.Article;
import models.Comment;
import play.data.validation.Required;
import play.mvc.Controller;
import utilTools.PageInfo;
import utilTools.UtilTools;

public class Client extends Controller {

	public static void index() {
		List<Article> blogs = Article.getActiveBlog("true");
		List<Article> twitters = Article.getActiveTwitter("true");
		PageInfo pi = Article.getActiveBlogByPage("true", 1);
		String pageTag = UtilTools.getPageTag(pi,"client");
		render(blogs,twitters,pi,pageTag);
	}

	public static void detail(Long id){
		Article article = Article.findById(id);
		Article.addReadCount(id);
		List<Comment> comments = Comment.getCommentsByArticleId(id);
		render(article , comments);
	}

	public static void addComment(@Required String author , 
			@Required String content , @Required Long articleId ) {
		new Comment(author, content , articleId );
		detail(articleId);
	}

	public static void page(@Required int page ) {
		PageInfo pi = Article.getActiveBlogByPage("true", page);
		String pageTag = UtilTools.getPageTag(pi,"client");
		 render("Client/index.html", pi, pageTag); 
	}

}
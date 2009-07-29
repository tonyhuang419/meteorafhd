package controllers;

import java.util.List;

import models.Article;
import models.Comment;
import play.data.validation.Required;
import play.mvc.Controller;
import UtilTools.ArticleVo;
import UtilTools.UtilTools;

public class Client extends Controller {

	public static void index() {
		List<Article> articles = Article.getActiveArticle();
		List<ArticleVo> articleVos = UtilTools.articleToArticlesVo(articles);
		render(articleVos);
	}
	
	public static void detail(Long id){
		Article article = Article.findById(id);
		ArticleVo articleVo = UtilTools.articleToArticlesVo(article);
		List<Comment> comments = Comment.getCommentsByArticleId(id);
		render(articleVo , comments);
	}
	
	public static void addComment(@Required String author , 
			@Required String content , @Required Long articleId ) {
		new Comment(author, content , articleId );
		detail(articleId);
	}

}
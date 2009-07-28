package controllers;

import java.util.List;

import models.Article;
import models.Comment;
import play.mvc.Controller;
import UtilTools.ArticleVo;
import UtilTools.UtilTools;

public class Client extends Controller {

	public static void index() {
		List<Article> articles = Article.findAll();
		List<ArticleVo> articleVos = UtilTools.articleToArticlesVo(articles);
		render(articleVos);
	}
	
	public static void detail(Long id){
		Article article = Article.findById(id);
		ArticleVo articleVo = UtilTools.articleToArticlesVo(article);
		List<Comment> comments = Comment.getCommentsByArticleId(id);
		render(articleVo , comments);
	}

}
package controllers;

import java.util.List;

import models.Article;
import play.data.validation.Required;
import play.mvc.Controller;
import UtilTools.ArticleVo;
import UtilTools.UtilTools;

public class Manage extends Controller {

	public static void index() {
		List<Article> articles = Article.findAll();
		List<ArticleVo> articleVos = UtilTools.articleToArticlesVo(articles);
		render(articleVos);
	}

	public static void addArticle(@Required String title , 
			@Required String contentX ) {
		new Article(title, contentX );
		index();
	}

}
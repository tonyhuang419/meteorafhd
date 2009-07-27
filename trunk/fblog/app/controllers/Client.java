package controllers;

import java.util.List;

import UtilTools.ArticleVo;
import UtilTools.UtilTools;

import models.Article;
import play.mvc.Controller;

public class Client extends Controller {

	public static void index() {
		List<Article> articles = Article.findAll();
		List<ArticleVo> articleVos = UtilTools.articleToArticlesVo(articles);
		render(articleVos);
	}

}
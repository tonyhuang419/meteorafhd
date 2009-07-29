package controllers;

import java.util.List;

import models.Article;
import play.data.validation.Required;
import play.mvc.Controller;
import UtilTools.ArticleVo;
import UtilTools.UtilTools;

import com.google.appengine.api.datastore.Text;

public class Manage extends Controller {

	public static void index() {
		List<Article> articles = Article.findAll();
		List<ArticleVo> articleVos = UtilTools.articleToArticlesVo(articles);
		render(articleVos);
	}

	public static void addArticle(@Required String title , 
			@Required String content ) {
		new Article(title, new Text(content) );
		index();
	}
	
	public static void edit(@Required Long id ) {
		Article article = Article.findById(id);
		ArticleVo articleVo = UtilTools.articleToArticlesVo(article);
		render(articleVo);
	}
	
	public static void saveEdit(@Required Long articleId  , @Required String title  , @Required String content ){
		Article.modActicle(articleId, title, content);
		index();
	}
	

}
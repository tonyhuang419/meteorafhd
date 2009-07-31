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
		List<Article> blogs = Article.getActiveBlog();
		List<Article> twitters = Article.getActiveTwitter();
		List<ArticleVo> blogVos = UtilTools.articleToArticlesVo(blogs);
		List<ArticleVo> twitterVos = UtilTools.articleToArticlesVo(twitters);
		render(blogVos,twitterVos);
	}

	public static void addArticle(String title , 
			@Required String content , @Required Long type ) {
		new Article(title, new Text(content) , type );
		index();
	}

	public static void edit(@Required Long articleId ) {
		Article article = Article.findById(articleId);
		ArticleVo articleVo = UtilTools.articleToArticlesVo(article);
		render(articleVo);
	}

	public static void saveEdit(@Required Long articleId  , @Required String title  , @Required String content ){
		Article.modActicle(articleId, title, content);
		index();
	}

	public static void disable(@Required Long articleId ){
		Article.disable(articleId);
		index();
	}

	public static void enable(@Required Long articleId ){
		Article.enable(articleId);
		index();
	}

	public static void del(@Required Long articleId ){
		Article.deleteArticle(articleId);
		index();
	}

}
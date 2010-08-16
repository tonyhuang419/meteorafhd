package controllers;

import models.Article;

import org.apache.commons.lang.StringUtils;

import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import utilTools.PageInfo;

import com.google.appengine.api.datastore.Text;

public class Manage extends Controller {

	@Before(unless={"login"})
	static void checkAuthentification() {
		if(session.get("user") == null){
			login("");
		}
	}

	public static void login(String password){
		if( (StringUtils.isNotEmpty(password) 
				&& password.equals("adminx")) || validateCookie() ){
			session.put("user", "fhdone");
			index(1L);
		}
		render();
	}

	private static boolean validateCookie(){
		if(request.cookies!=null && request.cookies.get("password")!=null ){
			String pass = request.cookies.get("password").value;
			return "adminx".equals(pass);
		}
		return false;
	}

	public static void index(Long page) {
		PageInfo blogs = Article.getActiveArticles("true", page.intValue() , "1");
		PageInfo twitters = Article.getActiveArticles("true", page.intValue() , "2");
		render(blogs,twitters);
	}

	public static void add(){
		render();
	}

	public static void addArticle(String title , 
			@Required String content , @Required Long type ) {
		new Article(title, new Text(content) , type );
		index(1L);
	}

	public static void editBlog(@Required Long articleId ) {
		Article article = Article.findById(articleId);
		render(article);
	}

	public static void editTwitter(@Required Long articleId ) {
		Article article = Article.findById(articleId);
		render(article);
	}

	public static void saveBlogEdit(@Required Long articleId  , @Required String title  , @Required String content ){
		Article.modBlogActicle(articleId, title, content);
		index(1L);
	}

	public static void saveTwitterEdit(@Required Long articleId  ,@Required String content ){
		Article.modTwitterActicle(articleId, content);
		index(1L);
	}

	public static void disable(@Required Long articleId ){
		Article.disable(articleId);
		index(1L);
	}

	public static void enable(@Required Long articleId ){
		Article.enable(articleId);
		index(1L);
	}

	public static void del(@Required Long articleId ){
		Article.deleteArticle(articleId);
		index(1L);
	}

}
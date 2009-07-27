package controllers;

import java.util.List;

import models.Articles;
import play.data.validation.Required;
import play.mvc.Controller;

public class Manage extends Controller {

	public static void index() {
		List<Articles> articles = Articles.findAll();
		render(articles);
	}

	public static void addArticle(@Required String title , 
			@Required String content ) {
		new Articles(title, content );
		index();
	}

}
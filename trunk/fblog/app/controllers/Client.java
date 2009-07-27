package controllers;

import java.util.List;

import models.Articles;
import play.mvc.Controller;

public class Client extends Controller {

	public static void index() {
		List<Articles> articles = Articles.findAll();
		render(articles);
	}

}
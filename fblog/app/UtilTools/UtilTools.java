package UtilTools;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import models.Article;

import org.apache.commons.beanutils.BeanUtils;


public class UtilTools {

	public static ArticleVo articleToArticlesVo(Article article) {
		ArticleVo avo = new ArticleVo();
		try {
			BeanUtils.copyProperties(avo, article);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if(article.content!=null){
			avo.content = article.content.getValue();
		}
		return avo;
	}

	public static List<ArticleVo>  articleToArticlesVo(List<Article> articleList){
		List<ArticleVo> listAvo = new ArrayList<ArticleVo>();
		for(Article article : articleList){
			listAvo.add(UtilTools.articleToArticlesVo(article));
		}
		return listAvo;
	}

}

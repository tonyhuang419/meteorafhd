package UtilTools;
import models.Article;
import play.db.jpa.JPASupport;


public class UtilTools {

	public static PageInfo getPageInfo(JPASupport jpa , String jsql , int pageNo){
		PageInfo p = new PageInfo();
		p.pageNo = pageNo;
		p.total = Article.find(jsql).all().size();
		p.data = Article.find(jsql).page(p.getStartOfPage(), p.pageSize);
		return p;
	}

}

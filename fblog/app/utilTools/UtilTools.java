package utilTools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Article;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;


public class UtilTools {

	public static PageInfo getPageInfo(EntityManager em , String jsql , int pageNo){
		if(pageNo==0){
			pageNo = 1;
		}
		PageInfo p = new PageInfo();
		p.pageNo = pageNo;
		Query query = em.createQuery(jsql);
		p.total = query.getResultList().size();
		query.setFirstResult(p.getStartOfPage());
		query.setMaxResults(p.pageSize);
		p.data = query.getResultList();
		return p;
	}

	public static String getPageTag(PageInfo pi , String baseUrl){
		StringBuffer sb = new StringBuffer();
		int total = pi.getTotalPageCount();
		//数字样式 < <<1 2 3 4 5 6 7 8 9 10>> > 
		//如果只有一页，则无需分页
		sb.append("&nbsp;");
		if (total == 1) {
			sb.append("&laquo;&nbsp;&lsaquo;&nbsp;<strong>1</strong>&nbsp;&rsaquo;&nbsp;&raquo;");
		} else {      
			if (pi.pageNo > 1) {
				//当前不是第一组，要显示“<< <”
				//<<：返回前一组第一页
				//<：返回前一页
				sb.append("<a href='"+baseUrl+"?page=1"+"'>&laquo;</a>&nbsp;");
				sb.append("<a href='"+baseUrl+"?page=" + (pi.pageNo - 1) ).append("'>&lsaquo;</a>&nbsp;" );
			}else{                        	
				sb.append("&laquo;&nbsp;&lsaquo;&nbsp;" );
			}

			int v=(pi.pageNo-4)>0?(pi.pageNo-4):1;
			int v1=(pi.pageNo+4)<total?(pi.pageNo+4):total;
			if(v1==total){
				v=total-10>1?v:1;
			}else if(v==1&&v1<total){
				v1=total>10?10:total;
			}
			//10个为一组显示
			for (int i = v; i <= v1; i++) {
				if (pi.pageNo == i) { //当前页要加粗显示
					sb.append("<strong>"+i+"</strong>&nbsp;");                                 
				}else{
					sb.append("<a href='"+baseUrl+"?page=" + i +"'>" + i + "</a>&nbsp;");
				}                            
			}
			//如果多于1组并且不是最后一组，显示“> >>”
			if (pi.pageNo<total) {
				//>>：返回下一组最后一页
				//>：返回下一页
				sb.append("<a href='"+baseUrl+"?page=" + (pi.pageNo + 1)).append("'>&rsaquo;</a>&nbsp;" );
				sb.append("<a href='"+baseUrl+"?page=" + total).append("'>&raquo;</a>&nbsp;" );
			}else{
				sb.append("&raquo;&nbsp;&nbsp;&nbsp;" );
			}
		}
		sb.append("</span>");
		return sb.toString();
	}

	// Rome中RSS的可选标准   
	// rss_0.9, rss_0.91, rss_0.92, rss_0.93, rss_0.94, rss_1.0, rss_2.0, atom_0.3 , atom_1.0   
	// http://fileit.in/p/4
	private static final String RSS_TYPE = "rss_2.0";    
	public static SyndFeed createFeed() {   
		SyndFeed feed = new SyndFeedImpl(); 
		feed.setFeedType(RSS_TYPE);  
		feed.setTitle("TBlog");   
		feed.setLink("http://meteorafhd.appspot.com/");   
		feed.setDescription("TBlog");   
		feed.setEntries(getEntries());   
		return feed;   
	}   

	public static List<SyndEntry> getEntries() {   
		List<SyndEntry> entries  = new ArrayList<SyndEntry>(); 
		SyndEntry entry;   
		SyndContent description;   

		List<Article> articles = Article.getRssArticles();
		for (Article article : articles) {   
			entry = new SyndEntryImpl();   
			entry.setTitle(UtilTools.getTitle(article));   
			entry.setLink(UtilTools.getArticleLink(article));   
			entry.setPublishedDate(article.lastModifyTime);   

			description = new SyndContentImpl();   
			description.setType("text/html");   
			description.setValue(article.content.getValue());   

			entry.setDescription(description);   
			entries.add(entry);   
		}   
		return entries;   
	} 


	public static String getTitle(Article article){
		if( article.type == 1l){
			return article.title;
		}
		if( article.type == 2l){
			return article.content.getValue();
		}
		else{
			return "";
		}
	}

	public static final String baseLink = "http://meteorafhd.appspot.com/";
	public static String getArticleLink(Article article){
		StringBuffer link = new StringBuffer(baseLink);
		if( article.type == 1l){
			link.append("client/detail?id=").append(article.id);
		}
		else{
			link.append("client/twitter?id=").append(article.id);
		}
		return link.toString();
	}

	public static Date getDelayMonth( int delay ){
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.MONTH, delay ); 
		return ca.getTime();
	}

}

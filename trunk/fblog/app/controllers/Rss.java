package controllers;

import java.io.IOException;

import play.mvc.Controller;
import utilTools.UtilTools;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class Rss extends Controller {

	private static final String MIME_TYPE = "application/xml; charset=GBK";   

	// Rome中RSS的可选标准   
	// rss_0.90, rss_0.91, rss_0.92, rss_0.93, rss_0.94, rss_1.0, rss_2.0, atom_0.3    
	private static final String RSS_TYPE = "rss_2.0";    


	public static void outputRssFeed() {  
		SyndFeed feed = UtilTools.createFeed();
		feed.setFeedType(RSS_TYPE);  
		response.contentType = MIME_TYPE;   
		SyndFeedOutput output = new SyndFeedOutput();   
		try {  
			String s = output.outputString(feed);
			response.out.write(s.getBytes());
		} catch (IOException e) {   
			e.printStackTrace();   
		} catch (FeedException e) {   
			e.printStackTrace();   
		}   
	}  



}

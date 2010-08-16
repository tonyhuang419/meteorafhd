package controllers;

import java.io.IOException;

import play.mvc.Controller;
import utilTools.UtilTools;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class Rss extends Controller {

	private static final String MIME_TYPE = "application/xml; charset=UTF-8";   

	public static void outputRssFeed() {  
		SyndFeed feed = UtilTools.createFeed();
		response.contentType = MIME_TYPE;   
		SyndFeedOutput output = new SyndFeedOutput();   
		try {  
			String s = output.outputString(feed);
			response.out.write(s.getBytes("UTF-8"));
			response.out.close();
		} catch (IOException e) {   
			e.printStackTrace();   
		} catch (FeedException e) {   
			e.printStackTrace();   
		}   
	}  
}

package UtilTools;


import java.util.Date;

public class ArticleVo {

	public Long id;
	public String title;
	public String content;
	public Long readCount;
	public Date lastReadTime;
	public Date lastModifyTime;
	public Date createdTime;
	public Boolean isActive;
	public Long type;  //1：blog  2：twitter
	
	public ArticleVo(){}
	

}


package com.exam.utils;

import java.io.Serializable;

public class PageInfo  implements Serializable
{
	private static final long serialVersionUID = 8868552783646096157L;
	private int curPage;
	private int totalCount;
	private int pageSize;
	private Object data;
	private static int ROWS_PER_PAGE = 20;
	
	public PageInfo()
	{
		curPage = 1;
		totalCount = 0;
		pageSize = ROWS_PER_PAGE;
	}

	public long getTotalCount()
	{
		return (long)totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public Object getResult()
	{
		return data;
	}

	public void setResult(Object data)
	{
		this.data = data;
	}

	public int getTotalPageCount()
	{
		if(totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}

	public void setCurPage(int curPage)
	{
//		this.curPage = curPage;
		setCurPage(Math.min(getCurPage(), getTotalPageCount()));
	}

	public int getCurPage()
	{
		return curPage;
	}

	public void setPageSize(int rows)
	{
		if(rows > 0)
			pageSize = rows;
		else
			pageSize = ROWS_PER_PAGE;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public int getDefaultRowsPerPage()
	{
		return ROWS_PER_PAGE;
	}

	public int getStartOfPage()
	{
		return (curPage - 1) * pageSize;
	}

//	public static void main(String args[])
//	{
//		PageInfo pi = new PageInfo();
//		pi.setTotalCount(21);
//		pi.setCurPage(2);
//		System.out.println(pi.getStartOfPage());
//		System.out.println(pi.getPageSize());
//	}

}

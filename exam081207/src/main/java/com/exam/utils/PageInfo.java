package com.exam.utils;

import java.io.Serializable;

public class PageInfo  implements Serializable
{
	private static final long serialVersionUID = 8868552783646096157L;
	private Long pageNo;
	private Long total;
	private int pageSize;
	private Object data;
	
	public PageInfo()
	{
		pageNo = 1L;
		total = 0L;
		pageSize = 20;
	}

	public Long getTotalPageCount()
	{
		if(total % pageSize == 0)
			return total / pageSize;
		else
			return total / pageSize + 1;
	}
	
	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = Math.min(getPageNo(), getTotalPageCount());
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Long getStartOfPage()
	{
		return (this.getPageNo() - 1) * pageSize;
	}

	

	public static void main(String args[])
	{
		PageInfo pi = new PageInfo();
		pi.setTotal(150L);
		pi.setPageNo(3L);
		System.out.println(pi.getStartOfPage());
		System.out.println(pi.getPageSize());
		System.out.println(pi.getPageNo());
	}

}

package fhdone.venusframework.utils;

import java.io.Serializable;

public class PageInfo  implements Serializable
{
	private static final long serialVersionUID = 8868552783646096157L;
	private int pageNo;
	private int total;
	private int pageSize;
	private Object data;
	
	public PageInfo()
	{
		pageNo = 1;
		total = 0;
		pageSize = 20;
	}

	public int getTotalPageCount()
	{
		if(total % pageSize == 0)
			return total / pageSize;
		else
			return total / pageSize + 1;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
//		this.pageNo = Math.min(getPageNo(), getTotalPageCount());
		this.pageNo = pageNo;
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

	public int getStartOfPage()
	{
		return (this.getPageNo() - 1) * pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	

	public static void main(String args[])
	{
		PageInfo pi = new PageInfo();
		pi.setTotal(150);
		pi.setPageNo(3);
		System.out.println(pi.getStartOfPage());
		System.out.println(pi.getPageSize());
		System.out.println(pi.getPageNo());
	}

}

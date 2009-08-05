package utilTools;


public class PageInfo 
{
	public int pageNo;
	public int total;
	public int pageSize;
	public Object data;
	
	public PageInfo(){
		pageNo = 1;
		total = 0;
		pageSize = 2;
	}

	public int getTotalPageCount(){
		if(total % pageSize == 0)
			return total / pageSize;
		else
			return total / pageSize + 1;
	}
	

	public int getStartOfPage(){
		if(pageNo==1){
			return 1;
		}
		else{
			return (pageNo - 1) * pageSize;
		}
	}


}

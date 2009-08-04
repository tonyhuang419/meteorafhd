package UtilTools;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class UtilTools {

	public static PageInfo getPageInfo(EntityManager em , String jsql , int pageNo){
		PageInfo p = new PageInfo();
		p.pageNo = pageNo;
        Query query = em.createQuery(jsql);
        p.total = query.getResultList().size();
		query.setFirstResult((p.getStartOfPage() - 1) * p.pageSize);
        query.setMaxResults(p.pageSize);
        p.data = query.getResultList();
		return p;
	}
	
	public static String getPageTag(PageInfo pi , String className){
		StringBuffer sb = new StringBuffer();
		int total = pi.getTotalPageCount();
		String url= className + "/"+"page";
		//数字样式 < <<1 2 3 4 5 6 7 8 9 10 > >>
		//如果只有一页，则无需分页
		sb.append("&nbsp;");
		if (total == 1) {
			sb.append("<strong>1</strong>&nbsp;");
		} else {      
			if (pi.pageNo > 1) {
				//当前不是第一组，要显示“<< <”
				//<<：返回前一组第一页
				//<：返回前一页
				sb.append("<a href='"+url+"?page=1"+"'>&laquo;</a>&nbsp;");
				sb.append("<a href='"+url+"?page=" + (pi.pageNo - 1) ).append("'>&lsaquo;</a>&nbsp;" );
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
					sb.append("<a href='"+url+"?page=" + i +"'>" + i + "</a>&nbsp;");
				}                            
			}
			//如果多于1组并且不是最后一组，显示“> >>”
			if (pi.pageNo<total) {
				//>>：返回下一组最后一页
				//>：返回下一页
				sb.append("<a href='"+url+"?page=" + (pi.pageNo + 1)).append("'>&rsaquo;</a>&nbsp;" );
				sb.append("<a href='"+url+"?page=" + total).append("'>&raquo;</a>&nbsp;" );
			}else{
				sb.append("&raquo;&nbsp;&nbsp;&nbsp;" );
			}
		}
		sb.append("</span>");
		return sb.toString();
	}

}

package fhd.right.action;

import javax.servlet.http.HttpSession;

import com.fhd.poll.db.Poll;

public class ModifyPollAction extends BaseAjax{
	
	public String modify(int mjid,String name,long price,boolean deal,HttpSession session){	
		if(session.getAttribute("username").equals("")){
			return "未登录";
		}
		poll = pollDAOBean.getPoll(mjid);
		poll.setName(name);
		poll.setPrice(price);
		poll.setDeal(deal);
		pollDAOBean.update(poll);
		return "更新成功";
	}

	public String del(int mjid,HttpSession session){
		if(session.getAttribute("username").equals("")){
			return "未登录";
		}		
		pollDAOBean.delete(mjid);
		return "已经删了";
	}
//	此方法可通过调用addInfo.jsp
//	public String create(String name,long price,boolean deal){
//		if(name.length()==0){
//			return "啥！没名字，当爷耍猴的啊";
//		}
//		else if(price<100){
//			return "没见过这么便宜得,涨点吧……";
//		}
//		else{
//			d.create(name,price,deal);
//			return "卖出去了";
//		}
//	}
}
package fhd.right.action;

import javax.servlet.http.HttpSession;

import com.fhd.poll.db.Poll;

public class CreatePollAction extends BaseAjax implements ICreatePollAction {

	public String create(String name,long price,boolean deal,String checkNo,HttpSession session){
		String checkNo2 = (String)session.getAttribute("checkNO");
		
		if(session.getAttribute("username").equals("")){
			return "未登录";
		}
		
		else if(name.length()==0){
			return "啥！没名儿，当爷耍猴的啊，NND……";
		}
		else if(name.length()<1){
			return "名儿太短，不准卖！！！";			
		}
		else if(pollDAOBean.isReName(name)==true){
			return "这名儿已经卖了，换个吧...";	
		}
		else if(price<100){
			return "出来卖！没见过有这么便宜得，涨点吧……好歹也对得起自个儿啊";
		}
		else if(!checkNo2.equals(checkNo)){
			return "验证码不对" ;
		}
		else{		
			poll.setName(name);
			poll.setPrice(price);
			poll.setDeal(deal);
			pollDAOBean.save(poll);
			session.setAttribute("checkNO", null);
			return "恭喜您，卖出去了！呵呵……";
		}
	}

}
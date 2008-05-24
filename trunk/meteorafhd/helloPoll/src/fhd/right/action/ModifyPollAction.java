package fhd.right.action;

import javax.servlet.http.HttpSession;

import com.fhd.poll.db.Poll;

public class ModifyPollAction extends BaseAjax{
	
	public String modify(int mjid,String name,long price,boolean deal,HttpSession session){	
		if(session.getAttribute("username").equals("")){
			return "δ��¼";
		}
		poll = pollDAOBean.getPoll(mjid);
		poll.setName(name);
		poll.setPrice(price);
		poll.setDeal(deal);
		pollDAOBean.update(poll);
		return "���³ɹ�";
	}

	public String del(int mjid,HttpSession session){
		if(session.getAttribute("username").equals("")){
			return "δ��¼";
		}		
		pollDAOBean.delete(mjid);
		return "�Ѿ�ɾ��";
	}
//	�˷�����ͨ������addInfo.jsp
//	public String create(String name,long price,boolean deal){
//		if(name.length()==0){
//			return "ɶ��û���֣���үˣ��İ�";
//		}
//		else if(price<100){
//			return "û������ô���˵�,�ǵ�ɡ���";
//		}
//		else{
//			d.create(name,price,deal);
//			return "����ȥ��";
//		}
//	}
}
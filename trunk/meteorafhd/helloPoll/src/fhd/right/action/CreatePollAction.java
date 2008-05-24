package fhd.right.action;

import javax.servlet.http.HttpSession;

import com.fhd.poll.db.Poll;

public class CreatePollAction extends BaseAjax implements ICreatePollAction {

	public String create(String name,long price,boolean deal,String checkNo,HttpSession session){
		String checkNo2 = (String)session.getAttribute("checkNO");
		
		if(session.getAttribute("username").equals("")){
			return "δ��¼";
		}
		
		else if(name.length()==0){
			return "ɶ��û��������үˣ��İ���NND����";
		}
		else if(name.length()<1){
			return "����̫�̣���׼��������";			
		}
		else if(pollDAOBean.isReName(name)==true){
			return "�������Ѿ����ˣ�������...";	
		}
		else if(price<100){
			return "��������û��������ô���˵ã��ǵ�ɡ����ô�Ҳ�Ե����Ը�����";
		}
		else if(!checkNo2.equals(checkNo)){
			return "��֤�벻��" ;
		}
		else{		
			poll.setName(name);
			poll.setPrice(price);
			poll.setDeal(deal);
			pollDAOBean.save(poll);
			session.setAttribute("checkNO", null);
			return "��ϲ��������ȥ�ˣ��Ǻǡ���";
		}
	}

}
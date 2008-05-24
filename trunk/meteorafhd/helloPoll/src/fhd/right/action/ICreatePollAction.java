package fhd.right.action;

import javax.servlet.http.HttpSession;

public interface ICreatePollAction {
	public String create(String name,long price,boolean deal,
			String checkNo,HttpSession session);
}

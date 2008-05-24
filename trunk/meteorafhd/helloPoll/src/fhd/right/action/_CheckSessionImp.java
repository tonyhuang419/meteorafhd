package fhd.right.action;

import javax.servlet.http.HttpSession;

public class _CheckSessionImp implements _ICheckSession {

	@Override
	public boolean checkSessionName(HttpSession session) {
		if(session.getAttribute("username").equals("")){
			return false;
		}
		return true;
	}
}

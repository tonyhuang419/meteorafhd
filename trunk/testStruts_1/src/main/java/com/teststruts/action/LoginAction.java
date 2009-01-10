package com.teststruts.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.teststruts.form.LoginForm;
import com.teststruts.form.Stu;

public class LoginAction extends DispatchAction {

	private List<Stu> list;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("re");
		LoginForm loginForm = (LoginForm) form;
		
		this.generateData(loginForm,request);
		
		if(isCancelled(request)){   
			//action被取消时要做的事情写在这里
			System.out.println("cancel");
			return mapping.getInputForward();
		}else{
			if(loginForm.getUserName().equals("fhd")){
				return mapping.findForward("s");
			}
			else{
				loginForm.setUserName("");
				loginForm.setPassWord("");
				return mapping.getInputForward();
			}

		}
	}
	
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.getInputForward();
	}
	
	private void generateData(LoginForm loginForm,HttpServletRequest request){
		Stu s1 = new Stu();
		s1.setAge(12);
		s1.setName("aname");
		
		Stu s2 = new Stu();
		s2.setAge(21);
		s2.setName("bname");
		s2.setObj(list);
		
		list = new ArrayList<Stu>();
		list.add(s1);
		list.add(s2);
		
		request.setAttribute("str", "str");
		request.setAttribute("s1", s1);
		request.setAttribute("s2", s2);
		request.setAttribute("list", list);
	}


}







package com.exam.action.client;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.exam.action.BaseAction;


@Results( {
	@Result(name = "menu", value = "/WEB-INF/jsp/client/menu.jsp")
})
public class MenuAction extends BaseAction{
	private static final long serialVersionUID = 5511361054517335846L;

	public String menu(){
		return "menu";
	}
	

}

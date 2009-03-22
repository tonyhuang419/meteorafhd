package com.baoz.yx.action.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.Gonggao;
import com.baoz.yx.service.IGonggaoService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;


/**
 * 公告
 * @author 
 */

@Results({
	@Result(name = "success", type =ServletRedirectResult.class, value = "/system/gongGaoQuery.action" ),
	@Result(name = "addOrUpdateSuccess", value = "/WEB-INF/jsp/manage/system/reportManage/addOrUpdateResult.jsp" ),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/manage/system/reportManage/reportManageForm.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/manage/system/reportManage/reportManageForm.jsp")
})

public class GonggaoAction extends DispatchAction implements ServletRequestAware{

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;

	@Autowired
	@Qualifier("gongaoService")
	private IGonggaoService gongaoService;

	
	private ServletRequest request;
	private List<Gonggao> gonggaoList;
	private PageInfo info;	
	private Gonggao gg;
	private String comeForm;
	private List<String> messages = null;

	@Override
	public String doDefault() throws Exception {
		logger.info("公告新增");
		comeForm = "new";
		return "enterSave";	
	} 

	public String savePro() throws Exception{
		//	logger.info("================================看看是否");
		this.logger.info("新增公告");
		gg.setIs_active("1");
		Employee user=UserUtils.getUser(); 
		gg.setById(user.getId());
		gg.setUpdateBy(new Date());
		service.save(gg);	
		return "addOrUpdateSuccess";
	}

	public String enterUpdate() throws Exception {
		String idss = request.getParameter("id");
		Long id = Long.valueOf(idss);
		Gonggao o = (Gonggao) service.uniqueResult("from Gonggao obj where obj.id='" + id + "'");
		this.gg = o;
		comeForm = "mod";
		return "enterUpdate";

	}

	public String updatePro() throws Exception {
		Employee user=UserUtils.getUser(); 
		gg.setById(user.getId());
//		gg.setUpdateBy(new Date());
		gg.setIs_active("1");
		service.update(gg);
		return "addOrUpdateSuccess";
	}

	@SuppressWarnings("unchecked")
	public String delete() {
		logger.info("删除类型");	
		messages = gongaoService.delGongGaos(gonggaoList);
		ActionContext.getContext().getSession().put("messages", messages);
		return SUCCESS;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public void setIds(Long[] ids) {
		if (gonggaoList == null)
			gonggaoList = new ArrayList<Gonggao>();
		for (Long id : ids) {
			Gonggao type_manage;
			type_manage = (Gonggao)service.load(Gonggao.class, id);
			gonggaoList.add(type_manage);
		}
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public Gonggao getGonggao() {
		return gg;
	}

	public void setGonggao(Gonggao gg) {
		this.gg = gg;
	}
	public List<Gonggao> getGonggaoList(){
		return gonggaoList;
	}
	public void setGonggaoList(List<Gonggao> gonggaoList) {
		this.gonggaoList = gonggaoList;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
	}

	public String getComeForm() {
		return comeForm;
	}

	public void setComeForm(String comeForm) {
		this.comeForm = comeForm;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}

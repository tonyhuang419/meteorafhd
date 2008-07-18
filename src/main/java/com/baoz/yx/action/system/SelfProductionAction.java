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
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.SelfProduction;
import com.baoz.yx.utils.UserUtils;
/**
 * 自有产品查询操作
 * @author 
 * 
 */
@Results({
	@Result(name = "success", type =ServletRedirectResult.class, value = "/system/selectSelfProduct.action" ),
	@Result(name = "addOrUpdateSuccess", value = "/WEB-INF/jsp/manage/system/selfproductManage/addOrUpdateResult.jsp" ),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/manage/system/selfproductManage/selfproductManageForm.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/manage/system/selfproductManage/selfproductManageUpdate.jsp")})
public class SelfProductionAction extends DispatchAction implements ServletRequestAware  {
	
	private List<SelfProduction> selfproduction;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	private ServletRequest request;
	private SelfProduction sp;
	
	@Override
	public String doDefault() throws Exception {
		
		logger.info("新增自有产品");
//		logger.info("=============state");
		return "enterSave";	
		} 
	
	
	public String savePro() throws Exception{
		this.logger.info("新增自有产品");
//		Date d = new Date();
//		sp.setRegisterDate(d);
		sp.setIs_active("1");
		Employee user=UserUtils.getUser(); 
		sp.setById(user.getId());
	    sp.setUpdateBy(new Date());
		service.save(sp);
		return "addOrUpdateSuccess";
	}
	
	public String enterUpdate() throws Exception {
//		logger.info("=============sssstate");
		String idss = request.getParameter("id");
		Long id = Long.valueOf(idss);
		SelfProduction o = (SelfProduction) service
				.uniqueResult("from SelfProduction obj where obj.id='" + id + "'");
		this.sp = o;

		return "enterUpdate";

	}
	
	public String updatePro() throws Exception {
		SelfProduction o = (SelfProduction) service
				.uniqueResult("from SelfProduction obj where obj.id='" + sp.getId() + "'");
//		Date d = new Date();
		o.setRegisterDate(sp.getRegisterDate());
		o.setPid(sp.getPid());
		o.setRegisterName(sp.getRegisterName());
		o.setValidateTime(sp.getValidateTime());
		o.setCertificateNum(sp.getCertificateNum());
		Employee user=UserUtils.getUser(); 
		o.setById(user.getId());
	    o.setUpdateBy(new Date());
		service.update(o);
		return "addOrUpdateSuccess";
	}
	
	public String delete() {
		 logger.info("删除类型");	
		 if (selfproduction != null && selfproduction.size() > 0){
			Employee user=UserUtils.getUser(); 
			for (SelfProduction delSelf:selfproduction) 
			{
				if (delSelf.getId() != null)
				{
				SelfProduction toDel = (SelfProduction) service.load(SelfProduction.class, delSelf.getId());
				toDel.setIs_active("0");
				toDel.setById(user.getId());
				toDel.setUpdateBy(new Date());
				service.saveOrUpdate(toDel);
				}
			}
		 logger.info("类型删除->成功");
		 }
		 return SUCCESS;
	}

	public void setIds(Long[] ids) {
		if (selfproduction == null)
			selfproduction = new ArrayList<SelfProduction>();
		for (Long id : ids) {
			SelfProduction type_manage = new SelfProduction();
			type_manage.setId(id);
			selfproduction.add(type_manage);
		}
	}
	
	public List<SelfProduction> getSelfProduction() {
		return selfproduction;
	}

	public void setSelfProduction(List<SelfProduction> selfproduction) {
		this.selfproduction = selfproduction;
	}
	
	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public SelfProduction getSp() {
		return sp;
	}

	public void setSp(SelfProduction sp) {
		this.sp = sp;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
		
	}
}

package com.baoz.yx.action.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
import com.baoz.yx.entity.YXChargeMan;
import com.baoz.yx.entity.YXChargemanDepartment;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.opensymphony.xwork2.ActionContext;


/**
 * 部门负责人
 * 
 * @author 
 */

@Results({
	@Result(name = "success", type =ServletRedirectResult.class, value = "/system/chargemanQuery.action" ),
	@Result(name = "addOrUpdateSuccess", value = "/WEB-INF/jsp/manage/system/chargemanManage/addOrUpdateResult.jsp" ),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/manage/system/chargemanManage/chargemanForm.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/manage/system/chargemanManage/chargemanUpdate.jsp")
	})

public class ChargemanAction extends DispatchAction implements ServletRequestAware{

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	
	private ServletRequest request;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	private PageInfo info;	
	private YXChargeMan cm;
	private YXChargemanDepartment cd;
	
	private List<YXChargeMan> chargeManList;
	private List<YXTypeManage>	departmentList;
	
	private String sessionCacheKey = "chargemanDepartmentSet";
	private Set<String>	relatedDeparment;
	private String departmentid;
	private String deleteDepartmentid;
	
	public String getDeleteDepartmentid() {
		return deleteDepartmentid;
	}

	public void setDeleteDepartmentid(String deleteDepartmentid) {
		this.deleteDepartmentid = deleteDepartmentid;
	}

	@Override	
	public String doDefault() throws Exception {
		logger.info("部门负责人新增");
		departmentList=typeManageService.getYXTypeManage(1018L);
		clearSessionCacheSet();
		return "enterSave";	
	} 

	public String savePro() throws Exception{
		this.logger.info("新增部门负责人");
		cm.setIs_active("1");
		service.save(cm);
		for (String departmentId : getSessionCacheSet()) {
			YXChargemanDepartment chargeDepartment = new YXChargemanDepartment();
			chargeDepartment.setDepartmentid(departmentId);
			chargeDepartment.setChargemanid(cm.getId());
			service.save(chargeDepartment);
		}
		clearSessionCacheSet();
		return SUCCESS;
	}
	
	public String saveDepartment() throws Exception
	{
		logger.info("新增部门");
		getSessionCacheSet().add(departmentid);	
		relatedDeparment = getSessionCacheSet();
		departmentList=typeManageService.getYXTypeManage(1018L);
		return "enterSave";	
	}
	
	public String delDepartment() throws Exception
	{
		logger.info("删除部门");
		getSessionCacheSet().remove(deleteDepartmentid);	
		relatedDeparment = getSessionCacheSet();
		departmentList=typeManageService.getYXTypeManage(1018L);
		return "enterSave";	
	}
	
	public String enterUpdate() throws Exception {
		String idss = request.getParameter("id");
		Long id = Long.valueOf(idss);
		YXChargeMan o = (YXChargeMan) service
				.uniqueResult("from YXChargeMan obj where obj.id='" + id + "'");
		this.cm = o;	
		List<String> d = new ArrayList<String>();
		d = (List<String>) service.list("select obj.departmentid from YXChargemanDepartment obj where obj.chargemanid='" + id + "'");
		clearSessionCacheSet();
		relatedDeparment = null ;
		for(String dpid:d){
			getSessionCacheSet().add(dpid);	
		}
		relatedDeparment = getSessionCacheSet();
		departmentList=typeManageService.getYXTypeManage(1018L);
		return "enterUpdate";

	}
	
	public String updateDepartment() throws Exception
	{
		logger.info("新增部门");
		getSessionCacheSet().add(departmentid);	
		relatedDeparment = getSessionCacheSet();
		departmentList=typeManageService.getYXTypeManage(1018L);
		return "enterUpdate";	
	}
	
	public String updateDelDepartment() throws Exception
	{
		logger.info("删除部门");
		getSessionCacheSet().remove(deleteDepartmentid);	
		relatedDeparment = getSessionCacheSet();
		departmentList=typeManageService.getYXTypeManage(1018L);
		return "enterUpdate";	
	}
	
	public String updatePro() throws Exception {
		YXChargeMan o = (YXChargeMan) service
		.uniqueResult("from YXChargeMan obj where obj.id='" + cm.getId() + "'");
		o.setName(cm.getName());
		o.setPhone(cm.getPhone());
        o.setMobilephone(cm.getMobilephone());
        service.update(o);
		service.executeUpdate("delete from YXChargemanDepartment where chargemanid = ?", o.getId());
        for (String departmentId : getSessionCacheSet()) {
			YXChargemanDepartment chargeDepartment = new YXChargemanDepartment();
			chargeDepartment.setDepartmentid(departmentId);
			chargeDepartment.setChargemanid(o.getId());
			service.save(chargeDepartment);
		}
		clearSessionCacheSet();
        return SUCCESS;
	}
	
	public String delete() {
		 logger.info("删除部门负责人");	
		 if (chargeManList != null && chargeManList.size() > 0){
			for (YXChargeMan delCm:chargeManList) 
			{
				if (delCm.getId() != null)
				{
					YXChargeMan toDel = (YXChargeMan) service.load(YXChargeMan.class, delCm.getId());
				    toDel.setIs_active("0");
				    service.saveOrUpdate(toDel);
				    service.executeUpdate("delete from YXChargemanDepartment where chargemanid = ?", delCm.getId());
				}
			}
		 logger.info("删除部门负责人->成功");
		 }
		 return SUCCESS;
	}
	
	private Set<String> getSessionCacheSet(){
		Set<String> departmentSet = (Set<String>) ActionContext.getContext().getSession().get(sessionCacheKey);
		if(departmentSet == null){
			departmentSet = new TreeSet<String>();
			ActionContext.getContext().getSession().put(sessionCacheKey, departmentSet);
		}
		return departmentSet;
	}
	
	private void clearSessionCacheSet(){
		ActionContext.getContext().getSession().remove(sessionCacheKey);
	}
	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public void setIds(Long[] ids) {
		if (chargeManList == null)
			chargeManList = new ArrayList<YXChargeMan>();
		for (Long id : ids) {
			YXChargeMan charge_man = new YXChargeMan();
			charge_man.setId(id);
			chargeManList.add(charge_man);
		}
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

	public YXChargeMan getCm() {
		return cm;
	}

	public void setCm(YXChargeMan cm) {
		this.cm = cm;
	}

	public YXChargemanDepartment getCd() {
		return cd;
	}

	public void setCd(YXChargemanDepartment cd) {
		this.cd = cd;
	}

	public List<YXChargeMan> getChargeManList() {
		return chargeManList;
	}

	public void setChargeManList(List<YXChargeMan> chargeManList) {
		this.chargeManList = chargeManList;
	}
	public List<YXTypeManage> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<YXTypeManage> departmentList) {
		this.departmentList = departmentList;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
	}

	public Set<String> getRelatedDeparment() {
		return relatedDeparment;
	}

	public void setRelatedDeparment(Set<String> relatedDeparment) {
		this.relatedDeparment = relatedDeparment;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

}

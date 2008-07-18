package com.baoz.yx.action.system;

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
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.SupplierInfo;
//import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;

/**
 * 供应商信息新增，修改，删除操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/supplyQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/supply/supplyAddForm.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/supply/supplyModifyForm.jsp") })
public class SupplyAction extends DispatchAction implements ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private ServletRequest request;
	private String[] ids;
	private SupplierInfo cc;
	private List<YXTypeManage> supplierTypeList; // 供应商类型
	
	private List<YXTypeManage> supplyArea; // 供应商地域

	
	

	/**
	 * 新增客户
	 */
	public String doDefault() throws Exception {
		logger.info("新供应商");
		// 查询出供应商地域
		
		supplierTypeList = typeManageService.getYXTypeManage(1013L);// 显示所有供应商类型
		supplyArea = typeManageService.getYXTypeManage(1005L);// 显示所有供应商地域
		
		return ENTER_SAVE;
	}

	/**
	 * 保存供应商信息
	 * 
	 * @return QUERY_LIST
	 * @throws Exception
	 */
	public String saveExployee() throws Exception {
		logger.info("保存");
		cc.setIs_active("1");
		service.save(cc);
		logger.info("保存完成");
		return "success";

	}

	/**
	 * 修改客户信息
	 * 
	 * @return ENTER_UPDATE
	 * @throws Exception
	 */
	public String enterUpdate() throws Exception {
		supplierTypeList = typeManageService.getYXTypeManage(1013L);// 显示所有供应商类型
		supplyArea = typeManageService.getYXTypeManage(1005L);// 显示所有客户地域
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
//		System.out.print( "--------------idsss"+idss);
		SupplierInfo o = (SupplierInfo) service
				.uniqueResult("from SupplierInfo obj where obj.id='" + id + "'");
		this.cc = o;
		return ENTER_UPDATE;

	}

	/**
	 * 执行对客户信息的更新
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateExp() throws Exception {

		if (cc.getSupplierid() != null && !"".equals(cc.getSupplierid())) {
			Long byid = 1l;
			cc.setById(byid);// 先这样固定设置，等做了用户登入后，在取当前登入用户的id
			
			cc.setIs_active("1");
			
			Date d = new Date();
			
			cc.setUpdateBy(d);
			service.saveOrUpdate(cc);
			return "success";
		} else {
			service.saveOrUpdate(cc);
			return "success";
		}

	}

	// 删除客户信息
	public String del() {
		String idss = request.getParameter("ids");
		String hql=" update SupplierInfo obj set obj.is_active=0 ";
		int a = systemService.deleteChose(idss, hql);
		if (a > 0) {
			return "success";
		} else {
			logger.info("删除操作不成功！");
			return "success";
		}
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	


	public List<YXTypeManage> getSupplyArea() {
		return supplyArea;
	}

	public void setSupplyArea(List<YXTypeManage> supplyArea) {
		this.supplyArea = supplyArea;
	}

	public SupplierInfo getCc() {
		return cc;
	}

	public void setCc(SupplierInfo cc) {
		this.cc = cc;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public List<YXTypeManage> getSupplierTypeList() {
		return supplierTypeList;
	}

	public void setSupplierTypeList(List<YXTypeManage> supplierTypeList) {
		this.supplierTypeList = supplierTypeList;
	}
	
}

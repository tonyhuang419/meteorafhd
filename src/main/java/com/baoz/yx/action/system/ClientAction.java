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
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;

/**
 * 客户信息新增，修改，删除操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/clientQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/client/clientAddForm.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/client/clientModifyForm.jsp") })
public class ClientAction extends DispatchAction implements ServletRequestAware {
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
	private YXClientCode cc;

	private List<YXTypeManage> clientNature; // 客户性质
	private List<YXTypeManage> businessType; // 行业类型
	private List<YXTypeManage> clientArea; // 客户地域
	private List<YXTypeManage> businessArea; // 行业市场
	
	



	/**
	 * 新增客户
	 */
	public String doDefault() throws Exception {
		logger.info("新增客户");
		// 查询出客户性质//行业类型//客户地域
		
		clientNature = typeManageService.getYXTypeManage(1001L);// 显示所有客户性质
		businessType = typeManageService.getYXTypeManage(1002L);// 显示所有行业类型
		clientArea = typeManageService.getYXTypeManage(1005L);// 显示所有客户地域
		businessArea = typeManageService.getYXTypeManage(1016L);// 显示所有行业市场
		return ENTER_SAVE;
	}

	/**
	 * 保存客户信息
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
		clientNature = typeManageService.getYXTypeManage(1001L);// 显示所有客户性质
		businessType = typeManageService.getYXTypeManage(1002L);// 显示所有行业类型
		clientArea = typeManageService.getYXTypeManage(1005L);// 显示所有客户地域
		businessArea = typeManageService.getYXTypeManage(1016L);// 显示所有行业市场
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
//		System.out.print( "--------------idsss"+idss);
		YXClientCode o = (YXClientCode) service
				.uniqueResult("from YXClientCode obj where obj.id='" + id + "'");
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

		if (cc.getId() != null && !"".equals(cc.getId())) {
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
		String hql=" update YXClientCode obj set obj.is_active=0 ";
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

	

	public List<YXTypeManage> getClientNature() {
		return clientNature;
	}

	public void setClientNature(List<YXTypeManage> clientNature) {
		this.clientNature = clientNature;
	}

	public List<YXTypeManage> getBusinessType() {
		return businessType;
	}

	public void setBusinessType(List<YXTypeManage> businessType) {
		this.businessType = businessType;
	}

	public List<YXTypeManage> getClientArea() {
		return clientArea;
	}

	public void setClientArea(List<YXTypeManage> clientArea) {
		this.clientArea = clientArea;
	}

	public YXClientCode getCc() {
		return cc;
	}

	public void setCc(YXClientCode cc) {
		this.cc = cc;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public List<YXTypeManage> getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(List<YXTypeManage> businessArea) {
		this.businessArea = businessArea;
	}
}

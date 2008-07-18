package com.baoz.yx.action.system;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ClientLinkMan;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.entity.YXTypeManage;
/**
 * 联系人信息新增，修改，删除操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/clientLinkManQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/linkMan/clientLinkManAddForm.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/linkMan/clientLinkManModifyForm.jsp") })
public class ClientLinkManAction extends DispatchAction implements ServletRequestAware {
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
	private YXLinkMan cc;
	private YXClientCode yc;
	private String namex;
	private List<YXTypeManage> linkmanNature; // 联系人性质
	private Long nameid;
	
	

	/**
	 * 新增客户联系人
	 */
	public String doDefault() throws Exception {
		logger.info("新增客户联系人");
		// 查询出新增客户联系人
		linkmanNature= typeManageService.getYXTypeManage(1003L);// 显示联系人性质
		
		// 从客户处添加联系人
		
		String idss = request.getParameter("idsss");
		System.out.println( "-------------->idsss"+idss);
		if(StringUtils.isNotBlank(idss)){
			Long id = Long.valueOf(idss);
		
			YXClientCode m = (YXClientCode) service
			.uniqueResult("from YXClientCode obj where obj.id='" + id + "'");
			this.yc = m;
			namex=yc.getName();
			nameid=yc.getId();
		}
		
		return ENTER_SAVE;
	}

	/**
	 * 保存供应商信息
	 * 
	 * @return QUERY_LIST
	 * @throws Exception
	 */
	public String saveLinkMan() throws Exception {
		logger.info("保存");
		cc.setIs_active("1");
		Long uid = UserUtils.getUser().getId();
		if (uid != null && !"".equals(uid))
			cc.setById(uid);
	
		cc.setUpdateBy(new Date());
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

		linkmanNature= typeManageService.getYXTypeManage(1003L);// 显示联系人性质
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
		YXLinkMan o = (YXLinkMan) service
				.uniqueResult("from YXLinkMan obj where obj.id='" + id + "'");
		this.cc = o;
		Long cid= cc.getClientId();
		
		YXClientCode m = (YXClientCode) service
		.uniqueResult("from YXClientCode obj where obj.id='" + cid + "'");
		this.yc = m;
		namex=yc.getName();
		
		Long uid = UserUtils.getUser().getId();
		if (uid != null && !"".equals(uid))
			cc.setById(uid);
		
		cc.setUpdateBy(new Date());
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
		String hql=" update YXLinkMan obj set obj.is_active=0 ";
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

	






	public YXLinkMan getCc() {
		return cc;
	}

	public void setCc(YXLinkMan cc) {
		this.cc = cc;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public List<YXTypeManage> getLinkmanNature() {
		return linkmanNature;
	}

	public void setLinkmanNature(List<YXTypeManage> linkmanNature) {
		this.linkmanNature = linkmanNature;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public YXClientCode getYc() {
		return yc;
	}

	public void setYc(YXClientCode yc) {
		this.yc = yc;
	}

	public String getNamex() {
		return namex;
	}

	public void setNamex(String namex) {
		this.namex = namex;
	}

	public Long getNameid() {
		return nameid;
	}

	public void setNameid(Long nameid) {
		this.nameid = nameid;
	}
}


package com.baoz.yx.action.system;

import java.util.Date;

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

import com.baoz.yx.entity.ProjectInfo;
import com.baoz.yx.service.ISystemService;

/**
 * 工程经济组成表新增，修改操作
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/projectInfo.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/projectInfo/addProjectInfo.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/projectInfo/updateProjectInfo.jsp")})
public class ProjectInfoAction extends DispatchAction implements ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;
    private ProjectInfo  		pi;
    private ISystemService 		systemService;
	private ServletRequest		request;
	private String[] 			idsss;

	public String[] getIdsss() {
		return idsss;
	}

	public void setIdsss(String[] idsss) {
		this.idsss = idsss;
	}

	public String doDefault() throws Exception {
		logger.info("新增项目信息");
		return ENTER_SAVE;
	}

	/**
	 * 保存项目信息内容
	 */
	public String savePI() throws Exception {
		logger.info("保存");
		service.save(pi);
		return "save";
	}

	
	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");// 怎么会出现空指针异常
		Long id = Long.valueOf(idss);
		ProjectInfo o = (ProjectInfo) service
				.uniqueResult("from ProjectInfo obj where obj.id='" + id + "'");
		this.pi = o;
		return "enterUpdate";

	}

	/**
	 * 执行对申购类型更新
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updatePI() throws Exception {
		Long Id = pi.getId();
		if (Id != null && !"".equals(Id)) {
			Long byid = 1l;
			pi.setById(byid);// 先这样固定设置，等做了用户登入后，在取当前登入用户的id
			Date d = new Date();
			pi.setUpdateBy(d);
			service.update(pi);
			return "success";
		} else {
			service.save(pi);
			return "success";
		}

	}

	// 删除联系人性质
	public String del() {
		String idss = request.getParameter("ids");
		String hql=" update ProjectInfo obj set obj.is_active=0 ";
		int a = systemService.deleteChose(idss, hql);
		if (a > 0) {
			return "success";
		} else {
			logger.info("删除操作不成功！");
			return "success";
		}
	}
	

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public ProjectInfo getPi() {
		return pi;
	}

	public void setPi(ProjectInfo pi) {
		this.pi = pi;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

}

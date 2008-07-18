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
import com.baoz.yx.entity.Employee;
import com.baoz.yx.service.ISystemService;

/**
 * 员工信息新增，修改，删除操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/exployeeQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/exployee/exployeeForm.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/exployee/exployeeForm.jsp") })
public class ExployeeAction extends DispatchAction implements
		ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	private ServletRequest request;
	private String[] ids;
	private Employee exp;
	private List<Object> dutyList; // 职责代码表
	private List<Object> groupList; // 组别代码表

	/**
	 * 新增员工
	 */
	public String doDefault() throws Exception {
		logger.info("新增员工");
		dutyList = service.list("from YXDutyCode dc where dc.id not in(0)");
		groupList = service.list("from YXGroupCode gc where gc.id not in(0)");
		if (dutyList.size() > 0 && groupList.size() > 0) {

			return ENTER_SAVE;
		} else {
			return "success";
		}

	}

	/**
	 * 保存员工
	 * 
	 * @return QUERY_LIST
	 * @throws Exception
	 */
	public String saveExployee() throws Exception {
		logger.info("保存");
		// 从职责代码表中查询出所有职责//从组别代码表中查询出组别
        //exp.setIsactive("1");
		service.save(exp);
		return "success";

	}

	/**
	 * 修改
	 * 
	 * @return ENTER_UPDATE
	 * @throws Exception
	 */
	public String enterUpdate() throws Exception {
		dutyList = service.list("from YXDutyCode dc where dc.id not in(0)");
		groupList = service.list("from YXGroupCode gc where gc.id not in(0)");
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
		Employee o = (Employee) service
				.uniqueResult("from Employee obj where obj.id='" + id + "'");
		this.exp = o;
		return ENTER_UPDATE;

	}

	/**
	 * 执行对客户性质更新
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateExp() throws Exception {
		Long expId = exp.getId();
		if (expId != null && !"".equals(expId)) {
			Long byid = 1l;
			exp.setById(byid);// 先这样固定设置，等做了用户登入后，在取当前登入用户的id
			Date d = new Date();
			exp.setUpdateBy(d);
			service.update(exp);
			return "success";
		} else {
			service.save(exp);
			return "success";
		}

	}

	// 删除所选的员工信息
	public String delExployees() {
		String idss = request.getParameter("ids");
		String hql=" update Employee obj set obj.is_active=0 ";
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

	public Employee getExp() {
		return exp;
	}

	public void setExp(Employee exp) {
		this.exp = exp;
	}

	public List<Object> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<Object> dutyList) {
		this.dutyList = dutyList;
	}

	public List<Object> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Object> groupList) {
		this.groupList = groupList;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
}

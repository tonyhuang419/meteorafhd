package com.baoz.yx.action.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.service.IYXTypeManageService;

@Results( { @Result(name = "success", type = ServletRedirectResult.class, value = "/system/userQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/user/userForm.jsp"),
		@Result(name = "rolelist", value = "/WEB-INF/jsp/system/user/roleList.jsp"),
		@Result(name = "authoritylist", value = "/WEB-INF/jsp/system/user/authorityList.jsp"),
		@Result(name = "view", value = "/WEB-INF/jsp/system/user/userForm.jsp"),
		@Result(name = "select", value = "/WEB-INF/jsp/system/user/clientList.jsp"),
		@Result(name = "changePWD", value = "/WEB-INF/jsp/system/user/changePassword.jsp") })
@SuppressWarnings("unchecked")
@ParentPackage("yx")
public class UserAction extends DispatchAction {
	public Employee user;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	public List<YXOEmployeeClient> userclientList;
	public List<YXTypeManage> typeManageList;
	public List<String> clientNameList;
	public List<String> typeNameList;
	public List<YXOEmployeeClient> newExployeeClientList;
	public YXOEmployeeClient deleteExployeeClient;
	public String clientName;
	public String typeName;
	public PageInfo info;
	public String password;
	public String param;

	public String doDefault() {
		if (user != null && user.getId() != null) {
			logger.info(user.getId());
			user = (Employee) commonService.load(Employee.class, user.getId());
			logger.info("修改用户(name=" + user.getName() + ")");
			List list = commonService
					.list(
							"SELECT ec,tm.typeName,tm1.typeName FROM YXOEmployeeClient ec,YXTypeManage tm,YXTypeManage tm1 WHERE ec.is_active!=0 and tm.is_active!=0 and tm1.is_active!=0 and ec.cli.clientNID=tm.id and tm.typeBig=1001 and ec.cli.businessID=tm1.id and tm1.typeBig=1002 and ec.exp.id=?",
							user.getId());
			if(list!=null&&list.size()>1){
				if(userclientList==null) userclientList=new ArrayList();
				if(clientNameList==null) clientNameList=new ArrayList();
				if(typeNameList==null) typeNameList=new ArrayList();
				for(int i=0;i<list.size();i++){
					Object[] object=(Object[]) list.get(i);
					userclientList.add((YXOEmployeeClient) object[0]);	
					clientNameList.add((String) object[1]);
					typeNameList.add((String) object[2]);
				}
				logger.info(userclientList.size());
			} 
			
		}
		typeManageList = typeManageService.getYXTypeManage(1015L); // 得到职责
		return "view";
	}

	/**
	 * 新增员工
	 * 
	 * @return
	 */

	public String saveorUpdate() {
		userService.saveOrUpdate(user, password, newExployeeClientList);
		return SUCCESS;
	}

	/**
	 * 删除关联客户
	 * 
	 * @return
	 */

	public String deleteExployeeClient() {
		// logger.info(deleteExployeeClient.getId());
		// logger.info(user.getId());
		userService.deleteExployeeClient(deleteExployeeClient);
		// logger.info("after delete user.id->"+user.getId());
		return doDefault();
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 * @throws Exception
	 */

	public String delete() {
		logger.info("删除用户id->" + user.getId());
		userService.deleteUser(user);
		return SUCCESS;
	}

	/**
	 * 进入修改密码页面
	 */
	public String doChangePWD() {
		if (user != null && user.getId() != null) {
			logger.info(user.getId());
			user = (Employee) commonService.load(Employee.class, user.getId());
		}
		return "changePWD";
	}

	/**
	 * 原则关联客户
	 * 
	 * @return
	 * @throws Exception
	 */

	public String doSelect() throws Exception {
		this.logger.info("客户信息表");
		StringBuffer str = new StringBuffer();
		String oql = null;
		if (user != null && user.getId() != null) {
			oql = "select d,tm,tm2 from YXClientCode d,YXTypeManage tm,YXTypeManage tm2 where d.id not in(SELECT ec.cli.id FROM YXOEmployeeClient as ec WHERE ec.is_active!=0 AND ec.exp.id="
					+ user.getId()
					+ ") and d.is_active!=0 and tm.is_active!=0 and tm2.is_active!=0 and d.clientNID=tm.id and tm.typeBig=1001 and d.businessID=tm2.id and tm2.typeBig=1002";
		} else {
			oql = "select d,tm,tm2 from YXClientCode d,YXTypeManage tm,YXTypeManage tm2 where d.is_active!=0 and tm.is_active!=0 and tm2.is_active!=0 and d.clientNID=tm.id and tm.typeBig=1001 and d.businessID=tm2.id and tm2.typeBig=1002";
		}
		str.append(oql);
		if (clientName != null) {
			str.append(" and d.name like'%");
			str.append(clientName);
			str.append("%'");
		}
		if (typeName != null) {
			str.append(" and tm.typeName like'%");
			str.append(typeName);
			str.append("%'");
		}
		info = queryService.listQueryResult(str.toString(), info);
		return "select";
	}

	public String isExistUsername() {
		logger.info("isExistUsername");
		String oql = "FROM Employee as e WHERE e.is_active!=0 AND e.username=?";
		Employee employee = (Employee) commonService.uniqueResult(oql, param);
		if (employee != null)
			this.renderText("1");
		else
			this.renderText("0");
		return null;
	}
	
	public String isExistUserCode() {
		logger.info("isExistUserCode");
		String oql = "FROM Employee as e WHERE e.is_active!=0 AND e.userCode=?";
		Employee employee = (Employee) commonService.uniqueResult(oql, param);
		if (employee != null)
			this.renderText("1");
		else
			this.renderText("0");
		return null;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getTypeManageList() {
		return typeManageList;
	}

	public void setUserclientList(List<YXOEmployeeClient> userclientList) {
		this.userclientList = userclientList;
	}

	public List<YXOEmployeeClient> getUserclientList() {
		return userclientList;
	}

	public void setId(Long id) {
		YXClientCode client = new YXClientCode();
		client.setId(id);
		deleteExployeeClient.setCli(client);
	}

	public void setNewid(Long[] newid) {
		if (newExployeeClientList == null)
			newExployeeClientList = new ArrayList<YXOEmployeeClient>();
		YXOEmployeeClient eclient;
		YXClientCode client;
		for (int i = 0; i < newid.length; i++) {
			logger.info(i);
			client = new YXClientCode();
			eclient = new YXOEmployeeClient();
			client.setId(newid[i]);
			eclient.setCli(client);
			newExployeeClientList.add(eclient);
		}
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public void setDeleteExployeeClient(YXOEmployeeClient deleteExployeeClient) {
		this.deleteExployeeClient = deleteExployeeClient;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
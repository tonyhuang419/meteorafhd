package com.baoz.yx.action.personnelManager;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.MD5;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

/**
 *	新增员工管理信息
 *  
 */
@Results( {	
		@Result(name = "success", type = ServletRedirectResult.class, value = "/personnelManager/selectPerQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/personnelManager/addPersonnelManager.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/personnelManager/updatePersonnelManager.jsp"),
		@Result(name = "shwoChangePwd", value = "/WEB-INF/jsp/personnelManager/changePwd.jsp"),
		@Result(name = "showInfo", value = "/WEB-INF/jsp/personnelManager/showInfo.jsp"),
		@Result(name = "self", value = "/WEB-INF/jsp/personnelManager/alterSuccess.jsp"),
		@Result(name = "changeError", value = "/WEB-INF/jsp/personnelManager/changeError.jsp"),
		@Result(name = "alterSave", value = "/WEB-INF/jsp/personnelManager/alterSave.jsp"),
		@Result(name = "workidSave", value = "/WEB-INF/jsp/personnelManager/alterSavewordid.jsp"),
		@Result(name = "changeSelfPwd", value = "/WEB-INF/jsp/personnelManager/changeSelfPwd.jsp")})
public class PersonnelManagerAction extends DispatchAction implements ServletRequestAware  {

	@Autowired
	@Qualifier("queryService")
	private IQueryService 			queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService			commonService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService 			systemService;
	@Autowired
	@Qualifier("userService")
	private IUserService 			userService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	public List<YXOEmployeeClient> 	newExployeeClientList;
	private	YXOEmployeeClient		yec;
	private String 					password;
	private PageInfo 				info;
	private PageInfo				empCliInfo;
	private ServletRequest			request;
	private Employee				per;
	private List<YXTypeManage>		dutyList;
	private List<Employee>		dutyList1;        //~~~~~~~~~~~~~~
	private List<Employee>		dutyList2;        //~~~~~~~~~~~~~~
	private List<Object>			empCli;
	private Long                    cliId;
	private Long 					clientId;
	private String					isRelationLinkMan;
	private Long 					empId;
	private Long					isSelectId;
	private String 					firstPassword;
	private String 					changeState;

	

	@Override
	public String doDefault() throws Exception {
		this.logger.info("新增员工管理信息");
		dutyList=commonService.list(" from OrganizationTree gc where gc.is_active = 1");
		return ENTER_SAVE;
	}
	
	//新增客户管理
	public String savePer() throws Exception
	{	
		System.out.print(per.getUsername()+"  ............用户名");
		System.out.print(per.getWorkId()+"  ............工号");
		String hql="from Employee emp where emp.username= '"+per.getUsername()+"' and emp.is_active = '1' ";
		String hql1="from Employee emp where emp.workId= '"+per.getWorkId()+"' and emp.is_active = '1' ";
		dutyList1=commonService.list(hql);
		dutyList2=commonService.list(hql1);
		System.out.print(dutyList1.size()+"  ............数据库查出的用户名de大小");
		System.out.print(dutyList2.size()+"  ............数据库查出的工号de大小");
		if(dutyList1.size()<1&&dutyList2.size()<1)
		{
			System.out.print("进到里面来了");
			logger.info(password);
			per.setPassword(MD5.toMD5(this.password));
			userService.saveOrUpdate(per, password, newExployeeClientList);
			logger.info("插入新增信息>>成功");
			prepareUpdateData(per.getId());
			if("true".equals(isRelationLinkMan)){
				return "enterUpdate";
			}else{
				return "success";
			}
		}
		else if(dutyList2.size()>0)
		{
			return "workidSave";
		}
		else if(dutyList1.size()>0)
		{
			return "alterSave";
		}
		return null;
	}
	
	
	/**
	 * 执行对员工管理删除
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String del() throws Exception {
		logger.info("删除员工信息");
		String ids = request.getParameter("ids");	
		logger.info(ids);
		String hql=" update Employee obj set obj.is_active='0' ";
		int a = systemService.deleteChose(ids, hql);
		if (a > 0) {
			String empIds[] = ids.split(",");
			for (String empId : empIds) {
				commonService.executeUpdate("delete from RoleEmployee re where re.userId = ?",Long.parseLong(empId));
				commonService.executeUpdate("delete from AuthorityEmployee ae where ae.userId = ?",Long.parseLong(empId));
			}
			return "success";
		} else {
			logger.info("删除操作不成功！");
			return "success";
		}
	}
	/**
	 * 关联客户刷新父页面
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String refreshPage() throws Exception {
		Employee pagePer = per;
		prepareUpdateData(pagePer.getId());
		per = pagePer;
		return "enterUpdate";
	}
	
	/**
	 * 获取员工管理更新页面
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String enterUpdate() throws Exception {
		
		String ids = request.getParameter("ids");
		Long id = Long.valueOf(ids);
		logger.info(id);	
		prepareUpdateData(id);
		logger.info(per); 		
		return "enterUpdate";
		
	}

	private void prepareUpdateData(Long id){
		dutyList=commonService.list("from OrganizationTree gc where gc.is_active = 1");
		info=queryService.listQueryResult("select count(*) from Employee e where e.is_active=1 and e.id='"+id+"'"," select e,(select ot from OrganizationTree ot where ot.id=e.position ) from Employee e where e.is_active=1 and e.id='"+id+"'", info);
		per = (Employee)((Object[])((List)info.getResult()).get(0))[0];
		
		logger.info("++++++修改信息查询完毕>>输出关联客户信息开始+++++++");
		empCliInfo=queryService.listQueryResult(" from YXOEmployeeClient empCli where empCli.exp = '"+id+"'",empCliInfo);
	}
	
	/**
	 * 执行对客户管理修改功能
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updatePer() throws Exception {
		System.out.print(per.getUsername()+"  ............用户名");
		String hql="from Employee emp where emp.username= '"+per.getUsername()+"' and emp.is_active = '1' and emp.id <> "+per.getId();
		dutyList1=commonService.list(hql);
		System.out.print(dutyList1.size()+"  ............数据库查出的用户名de大小");
		if(dutyList1.size()<1)
		{
			System.out.print("222222222222222222222222进到里面来了");
			logger.info("修改"+per.getId()+"用户信息");	
			Employee toUpdate = (Employee) commonService.load(Employee.class, per.getId());
			toUpdate.setUsername(per.getUsername());
			toUpdate.setWorkId(per.getWorkId());
			toUpdate.setName(per.getName());
			toUpdate.setCallPhone(per.getCallPhone());		
			toUpdate.setEmail(per.getEmail());
			toUpdate.setPhone(per.getPhone());
			toUpdate.setOtherPhone(per.getOtherPhone());
			toUpdate.setPosition(per.getPosition());
			toUpdate.setById(UserUtils.getUser().getId());
			toUpdate.setUpdateBy(new Date());
			commonService.update(toUpdate);
		    return "success";
		    }
		else
		{
			return "alterSave";
		}
	}
	
	/**
	 * 进入修改客户密码页面
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String showChangePwd() throws Exception
	{
		logger.info("进入重置密码页面");
		String ids = request.getParameter("ids");
		Long id = Long.valueOf(ids);
		logger.info(id);
		per = (Employee) commonService.load(Employee.class, id);
		return "shwoChangePwd";
	}
	/**
	 * 进入修改登陆人自己密码页面
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String alterSelfPwd() throws Exception
	{
		logger.info("修改登陆人密码设置");
		Long uid = new UserUtils().getUser().getId();
		per = (Employee) commonService.load(Employee.class, uid);
		return "changeSelfPwd";
	}
	
	/**
	 * 修改客户密码
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String changePwd()
	{	
		logger.info("重置密码");	
		logger.info(password);
		logger.info(empId);
		systemService.updatePwd(MD5.toMD5(password), empId);
		changeState = "1";
		logger.info("修改密码成功!");	
		logger.info(isSelectId);		
		return "self";
	}
	/**
	 * 修改用户自己密码
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String changeSlefPwd()
	{	
		logger.info("重置用户密码");	
		logger.info(firstPassword);
		String firstPwd = MD5.toMD5(firstPassword);
		String pwd = (String)commonService.uniqueResult("select emp.password from Employee emp where emp.id =" + empId);
		if(StringUtils.equals(firstPwd, pwd)){
			systemService.updatePwd(MD5.toMD5(password), empId);
			logger.info("修改密码成功!");	
			return "self";
		}
		return "changeError";
	}
	/**
	 *  删除关联客户关系
	 *  
	 *  @exception
	 *  
	 */
	public String delClient() throws Exception
	{
		logger.info("删除关联");
		systemService.deleteAmount("YXOEmployeeClient", cliId);
		return refreshPage();
	}
	
	/**
	 *  显示员工信息
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String showInfo()
	{
		logger.info("显示用户信息");
		prepareUpdateData(clientId);
		return "showInfo";
	}
	
	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}



	public List<YXTypeManage> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<YXTypeManage> dutyList) {
		this.dutyList = dutyList;
	}

	public Employee getPer() {
		return per;
	}

	public void setPer(Employee per) {
		this.per = per;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Object> getEmpCli() {
		return empCli;
	}

	public void setEmpCli(List<Object> empCli) {
		this.empCli = empCli;
	}

	public YXOEmployeeClient getYec() {
		return yec;
	}

	public void setYec(YXOEmployeeClient yec) {
		this.yec = yec;
	}

	public PageInfo getEmpCliInfo() {
		return empCliInfo;
	}

	public void setEmpCliInfo(PageInfo empCliInfo) {
		this.empCliInfo = empCliInfo;
	}

	public List<YXOEmployeeClient> getNewExployeeClientList() {
		return newExployeeClientList;
	}

	public void setNewExployeeClientList(
			List<YXOEmployeeClient> newExployeeClientList) {
		this.newExployeeClientList = newExployeeClientList;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public Long getCliId() {
		return cliId;
	}

	public void setCliId(Long cliId) {
		this.cliId = cliId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getIsRelationLinkMan() {
		return isRelationLinkMan;
	}

	public void setIsRelationLinkMan(String isRelationLinkMan) {
		this.isRelationLinkMan = isRelationLinkMan;
	}

	public Long getIsSelectId() {
		return isSelectId;
	}

	public void setIsSelectId(Long isSelectId) {
		this.isSelectId = isSelectId;
	}
	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getFirstPassword() {
		return firstPassword;
	}

	public void setFirstPassword(String firstPassword) {
		this.firstPassword = firstPassword;
	}

	public List<Employee> getDutyList1() {
		return dutyList1;
	}

	public void setDutyList1(List<Employee> dutyList1) {
		this.dutyList1 = dutyList1;
	}

	public List<Employee> getDutyList2() {
		return dutyList2;
	}

	public void setDutyList2(List<Employee> dutyList2) {
		this.dutyList2 = dutyList2;
	}

	public String getChangeState() {
		return changeState;
	}

	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}




}


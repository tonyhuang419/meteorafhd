package com.baoz.yx.action.assistance;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
@Results({	
	@Result(name = "succ", value = "/WEB-INF/jsp/assistance/assistanceMList.jsp"),
	@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/assistanceMQueryLeft.jsp")})
public class AssistanceMLeftQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	private List<Object> listSup; // 查询显示所有的供应商
	private String supplier;
	private PageInfo info;
	private List list;
	private String cState;   //外协合同状态
	private String mState;   //付款状态
	private String min;		 //最小金额
	private String max;		 //最大金额
	private String sDate;
	private String eDate;
	private String contractName;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("");
		listExp = service.list("from Employee d where d.id not in(0) and d.is_active!=0");
		groupList = UserUtils.getUserDetail().getDepartments();
		return "queryList";
	}
	
	public String query() throws Exception {
		this.logger.info("查询外协合同");
		List<AssistanceContract> ac = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("select ac, si.supplierName from AssistanceContract ac, SupplierInfo si where ac.is_active='1' and ac.supplyId=si.supplierid  ");
		if(supplier!=null && !"".equals(supplier)){
			sb.append("and exists (select s.supplierid from SupplierInfo s where s.supplierName like '%"+supplier+"%' and s.supplierid = ac.supplyId ) ");
			/*List<Long> idList = assistanceService.queryId(supplier);
			for(Long id : idList){
				sb.append("and ac.supplierId=").append(id);
				AssistanceContract a = (AssistanceContract)service.uniqueResult(sb.toString());
				ac.add(a);
			}
			list = assistanceService.queryAssistanceContract(idList);
		}else{
			info = queryService.listQueryResult(sb.toString(), info);
			List queryList = (List)info.getResult();
			list = assistanceService.queryAssistanceContract(queryList);*/
		}
		if(cState!=null &&!"".equals(cState)){
			sb.append(" and ac.assistanceContractType=").append(cState);
		}
		if(mState!=null && !"".equals(mState)){
			sb.append(" and ac.assistanceType=").append(mState);
		}
		if(min!=null && !"".equals(min)){
			sb.append(" and ").append(min).append("<ac.contractMoney ");
		}
		if(max!=null && !"".equals(max)){
			sb.append("and ac.contractMoney<").append(max);
		}
		if(sDate!=null && !"".equals(sDate)){
			sb.append("and to_date('").append(sDate).append("', 'yyyy-MM-dd')").append(" <= ac.contractDate ");
		}
		if(eDate!=null && !"".equals(eDate)){
			sb.append("and ac.contractDate <= to_date('").append(eDate).append("', 'yyyy-MM-dd')");
		}
		if(contractName!=null && !"".equals(contractName)){
			sb.append("and ac.assistanceName like '%").append(contractName).append("%'");
		}
		info = queryService.listQueryResult(sb.toString(), info);
		//List queryList = (List)info.getResult();
		//list = assistanceService.queryAssistanceContract(queryList);
		/**StringBuffer sb = new StringBuffer();
		sb.append("from SupplierInfo s where s.is_active='1'");
		if(supplier!=null && !"".equals(supplier)){
			sb.append("and s.supplierName like '%").append(supplier).append("%'");
		}
		info = queryService.listQueryResult(sb.toString(), info);
		System.out.println("*******************"+info);
		List queryList = (List)info.getResult();
		list = assistanceService.queryAssisContract(queryList);*/
		return "succ";
	}
	
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public List<Object> getListSup() {
		return listSup;
	}
	public void setListSup(List<Object> listSup) {
		this.listSup = listSup;
	}

	public String getMState() {
		return mState;
	}

	public void setMState(String state) {
		mState = state;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}

	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getCState() {
		return cState;
	}

	public void setCState(String state) {
		cState = state;
	}

	public String getSDate() {
		return sDate;
	}

	public void setSDate(String date) {
		sDate = date;
	}

	public String getEDate() {
		return eDate;
	}

	public void setEDate(String date) {
		eDate = date;
	}
	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public List<Department> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}	
}

package com.baoz.yx.action.contract;

import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.service.IYXTypeManageService;


	@Result(name = "success", value = "/WEB-INF/jsp/contract/saveMianPage.jsp")
	
public class ContractSmallAction extends DispatchAction{
		@Autowired
		@Qualifier("commonService")
		private ICommonService service;
		@Autowired
		@Qualifier("queryService")
		private IQueryService queryService;
		@Autowired
		@Qualifier("yxTypeManageService")
		private IYXTypeManageService 	typeManageService;
		private List<ContractOtherInfo> listSup; // 查询显示所有的合同
		private Long contract;
		private String time;
		private String  num;
	public ICommonService getService() {
			return service;
		}
		public void setService(ICommonService service) {
			this.service = service;
		}
		public IQueryService getQueryService() {
			return queryService;
		}
		public void setQueryService(IQueryService queryService) {
			this.queryService = queryService;
		}
		public IYXTypeManageService getTypeManageService() {
			return typeManageService;
		}
		public void setTypeManageService(IYXTypeManageService typeManageService) {
			this.typeManageService = typeManageService;
		}

		
		//保存
	public String doDefault() throws Exception {
		System.out.print(num+"hahahahahahahahahahahahahahahahahahahahahahahahaha");
		if(num.equals("1"))
		{
			logger.info("====================state1111111111");
			System.out.print(this.getTime()+"~~~~~~~~~~~~~~ 111时间");
			System.out.print(this.getContract()+"~~~~~~~~~~~~~~111合同系统号 ++++");
			Date times=DateUtil.parse(this.getTime(), "yyyy-M-d");
			String s1hql="from ContractOtherInfo c where c.contractMainSid="+this.getContract();
			listSup = service.list(s1hql);
			
		listSup.get(0).setPerativeReport(times);
		service.saveOrUpdate(listSup.get(0));
		}
		else if(num.equals("2"))
		{
			logger.info("====================state222222222");
			System.out.print(this.getTime()+"~~~~~~~~~~~~~~ 222时间");
			System.out.print(this.getContract()+"~~~~~~~~~~~~~~222合同系统号 ++++");
			Date times=DateUtil.parse(this.getTime(), "yyyy-M-d");
			String s1hql="from ContractOtherInfo c where c.contractMainSid="+this.getContract();
			listSup = service.list(s1hql);
			
			listSup.get(0).setRecivedThing(times); 
			service.saveOrUpdate(listSup.get(0));

		}
		else
		{
			logger.info("====================state333333333");
			System.out.print(this.getTime()+"~~~~~~~~~~~~~~ 333时间");
			System.out.print(this.getContract()+"~~~~~~~~~~~~~~333合同系统号 ++++");
			Date times=DateUtil.parse(this.getTime(), "yyyy-M-d");
			String s1hql="from ContractOtherInfo c where c.contractMainSid="+this.getContract();
			listSup = service.list(s1hql);
			listSup.get(0).setFinallyReport(times); 
			service.saveOrUpdate(listSup.get(0));

		}
		
		return SUCCESS;
		

	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getContract() {
		return contract;
	}
	public void setContract(Long contract) {
		this.contract = contract;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}

}

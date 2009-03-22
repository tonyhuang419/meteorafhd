package com.baoz.yx.action.harvestMeansManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IHarvestService;
/**
 * 单个收款信息确认的action
 * @author xusheng
 *
 */
public class ConformOneReveInfoAction extends DispatchAction {

	private Long reveId;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Autowired
	@Qualifier("harvestService")
	private IHarvestService harvestService;
	
	public String confirmOneReveInfo()throws Exception{
		harvestService.confirmOneReveInfo(reveId);
		return null;
	}

	public Long getReveId() {
		return reveId;
	}

	public void setReveId(Long reveId) {
		this.reveId = reveId;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IHarvestService getHarvestService() {
		return harvestService;
	}

	public void setHarvestService(IHarvestService harvestService) {
		this.harvestService = harvestService;
	}
}

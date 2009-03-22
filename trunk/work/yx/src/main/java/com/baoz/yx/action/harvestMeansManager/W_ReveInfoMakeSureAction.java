package com.baoz.yx.action.harvestMeansManager;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

@Results({
	@Result(name="query",value="/WEB-INF/jsp/harvestMeansManager/w_reveInfoMakeSure.jsp"),
	@Result(name = "updateSuc", value = "/WEB-INF/jsp/assistance/showSuc.jsp")
})
public class W_ReveInfoMakeSureAction extends DispatchAction{

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	private PageInfo info;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	@Autowired
	@Qualifier("harvestService")
	private IHarvestService harvestService;

	private Long conId;

	private Long itemId;

	private List reveMakeSureList;//收款确认查询的未收款的list

	private Boolean flag;
	private String sign;   //4 确认成功

	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public List getReveMakeSureList() {
		return reveMakeSureList;
	}
	public void setReveMakeSureList(List reveMakeSureList) {
		this.reveMakeSureList = reveMakeSureList;
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
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	@Override
	public String doDefault() throws Exception {
		String hql="select mainInfo," +
		"(select itemInfo from ContractItemMaininfo itemInfo where reve.billSid=itemInfo.conItemMinfoSid)," +
		" reve , e.name " +
		" from ContractMainInfo mainInfo, ReveInfo reve , Employee e  " +
		" where reve.conMainInfoSid=mainInfo.conMainInfoSid " +
		" and e.id = mainInfo.saleMan  and reve.hasSure='0' and reve.byId="+UserUtils.getUser().getId();
		reveMakeSureList=commonService.list(hql);
		return "query";
	}
	public String confirmReveInfo()throws Exception{
		String hql="select reve from " +
		"ReveInfo reve where" +
		" reve.hasSure='0' and reve.byId="+UserUtils.getUser().getId();
		List<ReveInfo> list=commonService.list(hql);
		flag=harvestService.confirmReveInfo(list);
		if(flag){
			sign = "4";
			return "updateSuc";
		}else{
			return this.doDefault();
		}
	}



	public IHarvestService getHarvestService() {
		return harvestService;
	}
	public void setHarvestService(IHarvestService harvestService) {
		this.harvestService = harvestService;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Long getConId() {
		return conId;
	}
	public void setConId(Long conId) {
		this.conId = conId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}


}

package com.baoz.yx.action.purchase;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.ISystemService;

/**
 *行业类型查询操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/purchase/purchaseVerifyList.jsp")

public class PurchaseVerifyQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	private PageInfo info;
    private List list;
	
  //查询条件  销售员,审购类型（2种）,客户,审购日期
    private Long expName;
    private String verifyType;
    private Long cliName;
    private String sTime;
    private String eTime;
    
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("");
		StringBuffer str = new StringBuffer();
		str.append("from ApplyMessage am where  am.affirmState='0' ");
		if (expName!=null&&!"".equals(expName)) {
			str.append(" and am.sellmanId=").append(expName);
		}
		if (verifyType!=null&&!"".equals(verifyType)) {
			str.append(" and am.applyType= ").append(verifyType);
		}
//		if (sTime != null && !sTime.equals("")) {
//			str.append(" and am.applyDate>='").append(sTime).append("'");
//		}
//		if (eTime != null && !eTime.equals("")) {
//			str.append(" and am.applyDate<='").append(eTime).append("'");
//		}
		str.append(" order by am.id");
		
		
		
		info = queryService.listQueryResult(str.toString(), info);
		List querylist = (List) info.getResult();
        list = systemService.queryVerityPurchase(querylist);
        logger.info("======================看看我的size=="+list.size());
		return "queryList";
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
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

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
	



	public String getVerifyType() {
		return verifyType;
	}

	public Long getExpName() {
		return expName;
	}

	public void setExpName(Long expName) {
		this.expName = expName;
	}

	public Long getCliName() {
		return cliName;
	}

	public void setCliName(Long cliName) {
		this.cliName = cliName;
	}

	public String getSTime() {
		return sTime;
	}

	public void setSTime(String time) {
		sTime = time;
	}

	public String getETime() {
		return eTime;
	}

	public void setETime(String time) {
		eTime = time;
	}public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

}


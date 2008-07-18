package com.baoz.yx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.service.IApplyBillService;

@Service("applyBillService")
@Transactional
public class ApplyBillService implements IApplyBillService {
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;


	@SuppressWarnings("unchecked")
	public List<ApplyBill> findApplyBillsByContractNo(Long contractMainInfo) {
		String hql="from ApplyBill a where a.contractMainInfo="+contractMainInfo;
		List<ApplyBill> bills=(List<ApplyBill>)commonDao.list(hql);
		return bills;
	}
	public ICommonDao getCommonDao() {
		return commonDao;
	}
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}


	/**	
	 * 根据 合同主体号，获取未出库申购清单 
	 */
	public PageInfo loadNotOutPurchase(Long conSid,String aid,PageInfo info){
		//不是分页查询...默认搜索
		if(aid==null || "".equals(aid)){
			info = queryService.listQueryResult(
					"select a,c from ApplyMessage a, YXClientCode c where" +
					" a.mainId = ? and a.customerId = c.id and " +
					" a.outState = 0 and a.ticketApplyId is null ", info, conSid);
		}
		//是分页查询...条件搜索
		else{
			info = queryService.listQueryResult(
					"select a,c from ApplyMessage a, YXClientCode c where" +
					" a.mainId = ? and a.customerId = c.id and " +
					" a.applyId  like '%" + aid + "%' and" +
					" a.outState = 0 and a.ticketApplyId is null ", info, conSid);
		}
		return info;
	}
}

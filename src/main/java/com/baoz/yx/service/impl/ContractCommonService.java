package com.baoz.yx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.IQueryService;
import com.baoz.yx.entity.YXChargeMan;
import com.baoz.yx.entity.YXChargemanDepartment;
import com.baoz.yx.service.IContractCommonService;

@Service("contractCommonService")
@Transactional
public class ContractCommonService implements IContractCommonService {

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public void addChargeManAndDepartment(String departmentCode,
			String chargeManName) {
		// TODO Auto-generated method stub

		/**
		 * 通过输入负责人姓名查询是否存在
		 */
		List chargeManList = commonDao.list(
				"from YXChargeMan man where man.name=? and man.is_active='1'",
				chargeManName);
		if (chargeManList != null && chargeManList.size() > 0) {
			/**
			 * No1. 如果存在获取负责人的id，然后把这个id和部门code作为查询条件，
			 * 看看是否有关联，如果没有关联重新添加一个关联，如果有关联什么也不做
			 */
			YXChargeMan chargeMan = (YXChargeMan) chargeManList.get(0);
			List chargeDepartMentList = commonDao
					.list(
							"from YXChargemanDepartment depart where depart.chargemanid=? and depart.departmentid=?",
							chargeMan.getId(), departmentCode);
			if(chargeDepartMentList==null||chargeDepartMentList.size()==0)
			{
				YXChargemanDepartment departMent=new YXChargemanDepartment();
				departMent.setChargemanid(chargeMan.getId());
				departMent.setDepartmentid(departmentCode);
				commonDao.save(departMent);
			}

		} else {
			/**
			 * No2. 添加一个姓名，然后添加一个关联
			 */
			YXChargeMan chargeMan=new YXChargeMan();
			chargeMan.setName(chargeManName);
			chargeMan.setIs_active("1");
			commonDao.save(chargeMan);
			YXChargemanDepartment departMent=new YXChargemanDepartment();
			departMent.setChargemanid(chargeMan.getId());
			departMent.setDepartmentid(departmentCode);
			commonDao.save(departMent);
		}

	}

}

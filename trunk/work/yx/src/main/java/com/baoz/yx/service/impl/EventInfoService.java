package com.baoz.yx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.IQueryService;
import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.tools.TaxChange;

@Service("eventInfoService")
@Transactional
public class EventInfoService implements IEventInfoService {
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService contractCommonService;

	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}

	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

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

	public List<ContractMaininfoPart> getMainInfoPartByConId(Long conId) {
		List<ContractMaininfoPart> list = new ArrayList<ContractMaininfoPart>();
		List<ContractMaininfoPart> tempList = new ArrayList<ContractMaininfoPart>();
		List<ContractItemInfo> tempItemInfoList = new ArrayList<ContractItemInfo>();

		tempList = commonDao.list(
				"from ContractMaininfoPart part where part.contractmainid=?",
				conId);
		if (tempList != null && tempList.size() > 0) {
			for (ContractMaininfoPart contractMaininfoPart : tempList) {
				List<ContractItemInfo> itemInfoList = new ArrayList<ContractItemInfo>();
				tempItemInfoList = commonDao
						.list(
								"from ContractItemInfo info where info.mainInfoPartId=?",
								contractMaininfoPart.getId());
				for (ContractItemInfo contractItemInfo : tempItemInfoList) {
					ContractItemMaininfo itemMainInfo = (ContractItemMaininfo) commonDao
							.load(ContractItemMaininfo.class, contractItemInfo
									.getContractItemMaininfo());
					contractItemInfo.setItemMainInfo(itemMainInfo);
					itemInfoList.add(contractItemInfo);
				}
				contractMaininfoPart.setItemInfoList(itemInfoList);
				list.add(contractMaininfoPart);
			}
		}
		return list;
	}

	public List<ContractItemMaininfo> getItemMainInfoByConId(Long conId) {
		List<ContractItemMaininfo> list = new ArrayList<ContractItemMaininfo>();
		list = commonDao.list(
				"from ContractItemMaininfo info where info.contractMainInfo=?",
				conId);
		return list;
	}
	
	public void saveEventInfo(ContractItemMaininfo itemMainInfo,
			ContractItemInfo itemInfo,String operator) {
		// 在添加的时候判断如果项目选择的负责部门在同一个合同号下面存在的话就不用重新添加项目了。只添加项目费用信息。此时需要把项目id从数据库中查出来
		
		ContractMaininfoPart inforPart = (ContractMaininfoPart) commonDao.load(
				ContractMaininfoPart.class, itemInfo.getMainInfoPartId());
		ContractMainInfo contract = (ContractMainInfo)commonDao.load(ContractMainInfo.class, inforPart.getContractmainid());
		Long mainId = contract.getConMainInfoSid();
		if("2".equals(contract.getStandard())){///如果不含税。项目组成的不含税金额等于基准金额
			itemInfo.setConItemAmountWithNoTax(itemInfo.getConItemAmountWithTax());
		}else if("1".equals(contract.getStandard())){////如果含税，项目组成的不含税金额等于基准金额*票据类型
			itemInfo.setConItemAmountWithNoTax(TaxChange.TaxToNoTaxBigDecimal(itemInfo.getConItemAmountWithTax(), inforPart.getTicketType()));
		}
		List list = commonDao
				.list(
						"from ContractItemMaininfo mainInfo where mainInfo.contractMainInfo=? and mainInfo.itemResDept=?",
						mainId, itemMainInfo
								.getItemResDept());
		if (list == null || list.size() == 0) {
			commonDao.save(itemMainInfo);
			itemInfo.setContractItemMaininfo(itemMainInfo.getConItemMinfoSid());
		} else {
			ContractItemMaininfo mainInfo = (ContractItemMaininfo) list.get(0);
			mainInfo.setItemResDeptP(itemMainInfo.getItemResDeptP());
			commonDao.update(mainInfo);
			////
			itemInfo.setContractItemMaininfo(mainInfo.getConItemMinfoSid());
		}

		itemInfo.setItemcontrent(inforPart.getMoneytype());
		
		//SeeBeanFields.travBean(itemInfo);
		
		commonDao.saveOrUpdate(itemInfo);
		
		//SeeBeanFields.travBean(itemInfo);
		/**如果是修改呢。判断合同是否是导入的。如果是导入的呢。就不重新生成计划了**/	
		if(StringUtils.equals(operator,"update")){
				if(!Boolean.TRUE.equals(itemMainInfo.getImportFromFile())){
					this.contractService.GeneratePlanByEventId(itemMainInfo
							.getContractMainInfo(), itemInfo.getContractItemMaininfo(),
							inforPart.getMoneytype(), itemInfo.getConItemAmountWithTax()
									.doubleValue());
				}
			/**如果是新增的话还要生成**/
			}else if(StringUtils.equals(operator,"add")){
				this.contractService.GeneratePlanByEventId(itemMainInfo
						.getContractMainInfo(), itemInfo.getContractItemMaininfo(),
						inforPart.getMoneytype(), itemInfo.getConItemAmountWithTax()
								.doubleValue());
			}
		
		contractCommonService.addChargeManAndDepartment(itemMainInfo.getItemResDept(), itemMainInfo.getItemResDeptP());
	}

	public ContractItemInfo selectContractItemInfoById(Long id) {
		// 通过费用id load出费用信息的对象
		ContractItemInfo itemInfo = (ContractItemInfo) commonDao.load(
				ContractItemInfo.class, id);
		// 通过费用信息的对象查找费用对应的项目信息对象的id
		Long itemMaininfoId = itemInfo.getContractItemMaininfo();
		// load项目信息的对象
		ContractItemMaininfo itemMaininfo = (ContractItemMaininfo) commonDao
				.load(ContractItemMaininfo.class, itemMaininfoId);
		// 把项目信息放在费用信息中去
		itemInfo.setItemMainInfo(itemMaininfo);
		return itemInfo;
	}

	public void updateContractItemInfo(ContractItemInfo itemInfo) {
		// 修改信息
		ContractItemInfo item = (ContractItemInfo) commonDao.load(
				ContractItemInfo.class, itemInfo.getConItemInfoSid());
		ContractItemMaininfo itemMainInfo=(ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class, item.getContractItemMaininfo());
		itemMainInfo.setItemResDeptP(itemInfo.getItemMainInfo().getItemResDeptP());
		item.setConItemAmountWithTax(itemInfo.getConItemAmountWithTax());
		
		commonDao.update(item);
		commonDao.update(itemMainInfo);
		contractCommonService.addChargeManAndDepartment(itemMainInfo.getItemResDept(), itemMainInfo.getItemResDeptP());
		////
	}

	public List getFeeMoneyByPartId(Long conid) {
		List list = new ArrayList();
		list = commonDao
				.list(
						"select sum(itemInfo.conItemAmountWithTax),itemInfo.mainInfoPartId from ContractItemInfo itemInfo where itemInfo.mainInfoPartId in(select part.id from ContractMaininfoPart part where part.contractmainid=?) group by itemInfo.mainInfoPartId",
						conid);
		return list;
	}

	public Double getOneFeeMoney(Long itemInfoId) {

		ContractItemInfo itemInfo = (ContractItemInfo) commonDao.load(
				ContractItemInfo.class, itemInfoId);
		ContractMaininfoPart mainInfoPart = (ContractMaininfoPart) commonDao
				.load(ContractMaininfoPart.class, itemInfo.getMainInfoPartId());
		Double sumTemp = mainInfoPart.getMoney();
		List list = new ArrayList();
		list = commonDao
				.list(
						"select sum(itemInfo.conItemAmountWithTax) from ContractItemInfo itemInfo where itemInfo.mainInfoPartId=?",
						itemInfo.getMainInfoPartId());
		Double opValue = 0.0;
		if (list != null && list.size() > 0) {
			BigDecimal returnValue = (BigDecimal) list.get(0);
			opValue = returnValue.doubleValue();
		}
		sumTemp -= opValue;
		return sumTemp;
	}

	public void saveContractBeforeSell(ContractBeforeSell cbs) {
		this.commonDao.saveOrUpdate(cbs);
		//添加信息的同时判断是否添加项目负责部门和负责人之间的关联
		this.contractCommonService.addChargeManAndDepartment(cbs.getDutyDepartmentId(),cbs.getProjectManId());
	}

	public List selectDepartmentListByDepartCode(String code) {
		return this.commonDao.list("select c from YXChargemanDepartment cd , YXChargeMan c where c.is_active = '1' and cd.chargemanid = c.id and cd.departmentid = ? ", code);
	}

}

package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistanceService;
@Service("assistanceService")
@Transactional
public class AssistanceService implements IAssistanceService {
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	private AssistanceContract ac; 
	private PageInfo info;
	public List listA(String hql) {
		List l = null;
		if (hql != null) {
			l = commonDao.list(hql);
		}
		return l;
	}

	public List queryAssistanceContract(List list) {
		List<AssistanceContract> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (AssistanceContract ac : dlist) {
				Object[] ob = new Object[3];
				ob[0] = ac;
				// 得到供应商id,通过供应商id查询出供应商名称
				if (ac.getSupplyId() != null) {
					String chql = "from SupplierInfo d where d.supplierid ='" + ac.getSupplyId() +"'";
					SupplierInfo s = (SupplierInfo) commonDao
							.uniqueResult(chql);
						ob[1] = s.getSupplierName();					
				} else
					ob[1] = null;
				Long cId = ac.getContractId();
				if(cId != null){
					String conHql = "from ContractMainInfo cmi where cmi.conMainInfoSid = "+cId;
					ContractMainInfo cmi = (ContractMainInfo)commonDao.uniqueResult(conHql);
					ob[2] = cmi;
				}
				alist.add(ob);				
			}
		}
		return alist;
	}

	public List queryAssisContract(List list) {
		List<SupplierInfo> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (SupplierInfo s : dlist) {
				Object[] ob = new Object[2];
				ob[0] = s.getSupplierName();
				Long sid = s.getSupplierid(); // 得到供应商id,通过供应商id查询出供应商名称				
				if (sid != null && !"".equals(sid)) {
					String chql = "from AssistanceContract d where d.supplyId =" + sid;
					AssistanceContract ac = (AssistanceContract) commonDao
							.uniqueResult(chql);
					if (ac != null)
						ob[1] = ac;
					else
						ob[1] = null;
				} else
					ob[1] = null;		
				alist.add(ob);
			}
		}		
		return alist;
	}
	
	public List queryId(String supplier) {
		List<Long> idList = new ArrayList();
		String hql = "from SupplierInfo s where s.supplierName like '%"+supplier+"%'";
		List<SupplierInfo> si = commonService.list(hql);
		for(SupplierInfo o:si){
			Long id = o.getSupplierid();
			idList.add(id);
		}
		return idList;
	}
	
	public List getSupply(List list) {
		List<AssistanceTicket> aList = list;
		List l = new ArrayList();
		if(aList!=null){
			for(AssistanceTicket at : aList){
				Object[] ob = new Object[2];
				ob[0] = at;
				Long sid = at.getCustomerId();
				String hql = "from SupplierInfo s where s.supplierid = "+sid;
				SupplierInfo s = (SupplierInfo)commonDao.uniqueResult(hql);
				ob[1] = s.getSupplierName();
				l.add(ob);
			}
		}
		return l;
	}
	
	public int updateState(String[] id) {
		int a = 0;
		for(String s : id){
			String hql = "update AssistanceContract ac set ac.assistanceContractType = '1' where ac.id = "+Long.parseLong(s);
			int i = commonDao.executeUpdate(hql);
			a += i;
		}
		return a;
	}

	public int updateState2(String[] id) {
		int a = 0;
		for(String s : id){
			String hql = "update AssistanceContract ac set ac.assistanceContractType = '2' where ac.id = "+Long.parseLong(s);
			int i = commonDao.executeUpdate(hql);
			a += i;
		}
		return a;
	}
	
	public int back(String[] id) {
		int a = 0;
		for(String s : id){
			String hql = "update AssistanceContract ac set ac.assistanceContractType = '0' where ac.id = "+Long.parseLong(s);
			int i = commonDao.executeUpdate(hql);
			a += i;
		}
		return a;
	}

	public List queryTicket(List list){
		List<AssistanceTicket> tList = list;
		List l = new ArrayList();
		for(AssistanceTicket at : tList){
			Object[] ob = new Object[2];
			ob[0] = at;
			Long supplyId = at.getCustomerId();
			String hql = "from SupplierInfo si where si.supplierid = "+supplyId;
			SupplierInfo si = (SupplierInfo)commonDao.uniqueResult(hql);
			ob[1] = si.getSupplierName();
			l.add(ob);
		}
		return l;
	}
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public AssistanceContract getAc() {
		return ac;
	}

	public void setAc(AssistanceContract ac) {
		this.ac = ac;
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

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	


}

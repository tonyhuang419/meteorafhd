package com.baoz.yx.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.ITestService;
import com.baoz.yx.tools.NumberToTime;

@Service("testService")
@Transactional
public class TestService implements ITestService {

	
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void updateContractIsActiveDate(List<ContractMainInfo> mainInfoList){
		for (ContractMainInfo contract : mainInfoList) {
			updateOneContractIsActiveDate(contract);
		}
	}
	public void updateOneContractIsActiveDate(ContractMainInfo contract){
		if(contract.getConId()!=null && contract.getConId().length()==11){
			String conId = contract.getConId();
			String year_str = StringUtils.substring(conId, 5, 7);
			Date activeDate = contract.getActiveDate();
			String activeDate_str = NumberToTime.getDateFormat(activeDate, "yyyy-MM-dd");
			String[] activeDate_arr = StringUtils.split(activeDate_str, "-");
			Long year_compare = Long.valueOf("20"+year_str);
			Long year = Long.valueOf(activeDate_arr[0]);
			Date conSignDate = contract.getConSignDate();
			String conSignDate_str = NumberToTime.getDateFormat(conSignDate, "yyyy-MM-dd");
			String[] conSignDate_arr = StringUtils.split(conSignDate_str, "-");
			Long year_Sign = Long.valueOf(conSignDate_arr[0]);
			if(year_compare.longValue()!= year){
				if(year_compare.longValue()== year_Sign){
					contract.setActiveDate(contract.getConSignDate());
				}else{
					Date resultDate = NumberToTime.getStringFormatDate(year_compare+"-1-1","yyyy-MM-dd");
					contract.setActiveDate(resultDate);
				}
				commonDao.update(contract);
			}
		}
	}
}


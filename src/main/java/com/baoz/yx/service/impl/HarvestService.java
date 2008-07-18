package com.baoz.yx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.service.IYXTypeManageService;

@Service("harvestService")
@Transactional
public class HarvestService implements IHarvestService {

	
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;

	public void delreve(Long id) {
		ReveInfo ri=(ReveInfo) commonDao.uniqueResult(" from ReveInfo where id=?", id);
		ri.setIs_active("0");
        commonDao.update(ri);
	}

}

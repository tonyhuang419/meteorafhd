package com.baoz.yx.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.entity.ContractMaterialSet;
import com.baoz.yx.service.IMaterialService;

@Service("materialService")
@Transactional
public class MaterialService implements IMaterialService{
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;
	
	public void deleteMaterial(Long[] mid) {
		String hql=" delete MaterialManager mm where mm.id in ("+StringUtils.join(mid,",")+") ";
		commonDao.executeUpdate(hql);
	}

	public void deleteMaterialSet(String[] mid) {
		// TODO Auto-generated method stub
		for (String id : mid) {
			ContractMaterialSet materialSet=(ContractMaterialSet)commonDao.load(ContractMaterialSet.class, Long.valueOf(id));
			commonDao.delete(materialSet);
		}
		
	}
	
}

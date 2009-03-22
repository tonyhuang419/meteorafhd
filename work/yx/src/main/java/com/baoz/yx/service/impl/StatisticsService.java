package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.OrganizationTree;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.vo.Department;

@Service("statisticsService")
@Transactional
public class StatisticsService implements IStatisticsService {

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public List<Department> getDepartment() {
		// TODO Auto-generated method stub
		List<Department> departList=new ArrayList<Department>();
		// 取出子部门
		List<OrganizationTree> subOrganizationTreeList = commonDao.list("from OrganizationTree as ot where ot.organizationCode like ? and ot.is_active = '1' order by ot.organizationCode","10__");
	
		departList.add(new Department(-1L,"10", "整体"));
		for (int i = 0; i < subOrganizationTreeList.size(); i++) {
			String myDepartmentCode = subOrganizationTreeList.get(i).getOrganizationCode();
			String myDepartmentName = subOrganizationTreeList.get(i).getOrganizationName();
			departList.add(new Department(subOrganizationTreeList.get(i).getId(),myDepartmentCode, myDepartmentName));
		
		}
		return departList;
	}

	public List<Employee> getEmployee() {
		// TODO Auto-generated method stub
		return commonDao.list("from Employee d where d.id not in(0) and d.is_active!=0");
	}

}

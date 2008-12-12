package com.exam.service.impl.manage;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.entity.Employee;
import com.exam.service.ICommonService;
import com.exam.service.manage.IRegisterMService;

@Service("registerMService")
@Transactional
public class  RegisterMService implements IRegisterMService{
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	public boolean saveNewEmployeer(Employee  emp) {
		emp.setPassword( DigestUtils.md5Hex(emp.getPassword()));
		commonService.save(emp);
		return true;
	}
	
	public boolean uniqueEmployeeJobNum(String jobNum){
		Long x = (Long)commonService.uniqueResult(" select e.jobNum from Employee e where e.jobNum = ? ", jobNum);
		if(x!=null){
			return false;
		}
		else{
			return true;
		}
	}

}

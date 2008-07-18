package com.baoz.yx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PropertyUtils;
import com.baoz.yx.entity.SysItem;
import com.baoz.yx.service.IHrService;

@Service("hrService")
@Transactional
public class HrService implements IHrService {
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;

	public String getMainProjectCode(String proectlevel) {
		StringBuffer sb = new StringBuffer();
		String ym = DateUtil.format(new Date(), "yyMM");
		sb.append("P").append(proectlevel).append(ym);
		String id = "P" + ym.substring(0, ym.length() - 2);
		Object obj = commonDao.uniqueSQLResult("select tableid from sysid where id=?", id);
		if (obj == null) {
			addCode(id);
			sb.append(getCode("main_project_code","1"));
		} else {
			sb.append(getCode("main_project_code",obj.toString()));
		}
		setCode(id);
		addCode(sb.toString());
		return sb.toString();
	}
	public String getProjectCode(String mainprojectcode) {
		StringBuffer sb = new StringBuffer();
		sb.append(mainprojectcode);
		Object obj = commonDao.uniqueSQLResult("select tableid from sysid where id=?", mainprojectcode);
		if (obj == null) {
			addCode(mainprojectcode);
			sb.append(getCode("project_code","1"));
		} else {
			sb.append(getCode("project_code",obj.toString()));
		}
		setCode(mainprojectcode);
		addCode(sb.toString());
		return sb.toString();
	}

	public String getCode(String projperty,String idxcode,String projectcode) {
		StringBuffer sb = new StringBuffer();
		sb.append(idxcode);
		String code=PropertyUtils.getValue(projectcode);
		Object obj = commonDao.uniqueSQLResult("select tableid from sysid where id=?", code);
		if (obj == null) {
			addCode(code);
			sb.append(getCode(projperty,"1"));
		} else {
			sb.append(getCode(projperty,obj.toString()));
		}
		setCode(code);
		return sb.toString();
	}

	public List<SysItem> getSysItems(String itemid) {

		return null;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public String getCode(String projperty,String code) {
		String str=PropertyUtils.getValue(projperty);
		return str.substring(0, str.length() - code.length()) + code;
	}

	public void setCode(String id) {
		commonDao.executeSQLUpdate("update sysid set tableid=tableid+1 where id=?", id);
	}

	public void addCode(String id) {
		commonDao.executeSQLUpdate("INSERT INTO sysid(id,tableid) VALUES(?,1)", id);
	}
}

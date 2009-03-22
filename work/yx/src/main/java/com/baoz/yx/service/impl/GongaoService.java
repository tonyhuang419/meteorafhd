package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.Gonggao;
import com.baoz.yx.service.IFirstPageService;
import com.baoz.yx.service.IGonggaoService;
import com.baoz.yx.utils.UserUtils;

@Service("gongaoService")
@Transactional
public class GongaoService implements IGonggaoService {


	@Autowired
	@Qualifier("commonService")
	private ICommonService 			service;

	@Autowired
	@Qualifier("firstPageService")
	private IFirstPageService firstPageService;


	/**
	 *删除公告
	 */
	public List<String>   delGongGaos( List<Gonggao>  gonggaoList ){
		Boolean isAdmin = firstPageService.isAdmin();
		Employee user=UserUtils.getUser(); 
		List<String> messages = new ArrayList<String>();
		int mark = 1;

		if ( isAdmin ){
			mark = 1;
		}
		else{
			for (Gonggao delSelf:gonggaoList) {
				Gonggao toDel = (Gonggao) service.load(Gonggao.class, delSelf.getId());
				if( toDel.getDelLevel() ){
					messages.add("公告编号"+toDel.getId()+"只有系统管理员才能删除");
					mark=0;
					break;
				}
			}
		}
		if(mark==1){
			for (Gonggao delSelf:gonggaoList) 
			{
				if (delSelf.getId() != null)
				{
					Gonggao toDel = (Gonggao) service.load(Gonggao.class, delSelf.getId());
					toDel.setIs_active("0");
					toDel.setById(user.getId());
					//toDel.setUpdateBy(new Date());
					service.saveOrUpdate(toDel);
				}
			}
		}
		return messages;

	}


}

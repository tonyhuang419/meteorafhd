package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.TypeManageUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.utils.YxConstants;

@Service("yxTypeManageService")
@Transactional
public class YXTypeManageService implements IYXTypeManageService {
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao yxCommonDao;
	/**
	 * 在本线程缓存类型
	 */
	private ThreadLocal<Map<Long,List<YXTypeManage>>> threadTypeManage= new ThreadLocal<Map<Long,List<YXTypeManage>>>();
	
	protected Log logger = LogFactory.getLog(this.getClass());
	/**
	 * 获得类型map，没有就创建一个map
	 * @return
	 */
	private Map<Long,List<YXTypeManage>> getTypeManageMap(){
		Map<Long,List<YXTypeManage>> map = threadTypeManage.get();
		if(map == null){
			map = new HashMap<Long, List<YXTypeManage>>();
			threadTypeManage.set(map);
		}
		return map;
	}
	/**
	 * 取得在数组ids中的所有YXTypeManage形成一个List
	 */
	public List<YXTypeManage> loadAll(List<YXTypeManage> typeManageList) {
		if (typeManageList == null || typeManageList.size() < 1)
			return null;
		for (int i = 0; i < typeManageList.size(); ++i) {
			if (typeManageList.get(i).getId() != null)
				typeManageList.set(i, (YXTypeManage) yxCommonDao.load(YXTypeManage.class, typeManageList.get(i)
						.getId()));
		}
		return typeManageList;
	}

	/**
	 * 得到typeManageList对应的类型种类名称
	 */
	public List<String> getAllTypeBig(List<YXTypeManage> typeManageList) {
		if (typeManageList == null || typeManageList.size() < 1)
			return null;
		List<String> typeBigList = new ArrayList<String>();
		for (YXTypeManage typeManage : typeManageList) {
			if (YxConstants.typeBigMap.containsKey(typeManage.getTypeBig())) {
				typeBigList.add(YxConstants.typeBigMap.get(typeManage.getTypeBig()));
			}
		}
		return typeBigList;
	}

	public Map<Long, String> getTypeBigMap() {
		return YxConstants.typeBigMap;
	}

	public String getTypeBigName(Long typeBig) {
		return YxConstants.typeBigMap.get(typeBig);
	}

	/**
	 * 将所有ids对应的YXTypeManage置为无效
	 */

	public void deleteAll(List<YXTypeManage> typeManageList) {
		Employee user=UserUtils.getUser();
		typeManageList = this.loadAll(typeManageList);
		if (typeManageList == null || typeManageList.size() < 1)
			return;
		for (YXTypeManage typeManage : typeManageList) {
			typeManage.setIs_active("0");
			typeManage.setById(user.getId());
			typeManage.setUpdateBy(new Date());
			yxCommonDao.saveOrUpdate(typeManage);
		}
	}
	
	/**
	 * 得到所有typeBig的YXTypeManage
	 */
	public List<YXTypeManage> getYXTypeManage(Long typeBig) {	
		// 先从线程缓存中取
		List<YXTypeManage> typeList = getTypeManageMap().get(typeBig);
		if(typeList == null){
			// 线程缓存中没有，去数据库中查
			// 不分是否active,getYXTypeManage(Long typeBig, String typeSmall)不active的也需要
			typeList = yxCommonDao.list("FROM YXTypeManage tm WHERE tm.typeBig=? order by orderNum", typeBig);
			// 放进线程缓存
			getTypeManageMap().put(typeBig, typeList);
		}
		// 只取active的typeManage
		List<YXTypeManage> typeActivedList = new ArrayList<YXTypeManage>();
		for (YXTypeManage typeManage : typeList) {
			if(StringUtils.equals(typeManage.getIs_active(), "1")){
				typeActivedList.add(typeManage);
			}
		}
		return typeActivedList;
	}

	public YXTypeManage getYXTypeManage(Long typeBig, String typeSmall) {
		// 如果编号没有，直接返回
		if(StringUtils.isEmpty(typeSmall)){
			return null;
		}
		// 获得类别列表
		List<YXTypeManage> typeList = getYXTypeManage(typeBig);
		// 取出等于samll的类别
		for (YXTypeManage typeManage : typeList) {
			if(StringUtils.equals(typeManage.getTypeSmall(), typeSmall)){
				return typeManage;
			}
		}
		return null;
	}

	public int getHarvestMonth(String typeSmall) {
		YXTypeManage tm = (YXTypeManage) yxCommonDao.uniqueResult("FROM YXTypeManage tm WHERE tm.typeBig=? and tm.typeSmall = ?", 1012L ,typeSmall);
		return TypeManageUtils.getHarvestMonth(tm);
	}
	public void setYxCommonDao(IYXCommonDao commonDao) {
		this.yxCommonDao = commonDao;
	}
}

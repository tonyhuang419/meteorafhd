package com.baoz.yx.tools.compare;


import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.baoz.yx.tools.compare.beans.ClassInfo;
import com.baoz.yx.tools.compare.beans.CompareObject;
import com.baoz.yx.tools.compare.beans.ComparePropertyObject;
import com.baoz.yx.tools.compare.beans.CompareResult;
import com.baoz.yx.tools.compare.beans.PropertyCompareResult;
import com.baoz.yx.tools.compare.beans.PropertyInfo;

/**
 * 类BeanComparor.java的实现描述：属性比较工具类
 * @author xurong Jun 13, 2008 6:22:21 PM
 */
public class BeanCompareUtils {
	private static Logger logger = Logger.getLogger(BeanCompareUtils.class);
	/**
	 * 比较两个bean的属性值
	 * @param srcObj
	 * @param targetObj
	 * @return
	 */
	public static CompareResult compareObject(CompareObject srcObj,CompareObject targetObj){
		return compareObject(srcObj,targetObj,false,null);	
	}
	
	/**
	 * 比较两个属性，只比较includeProperties中定义的属性
	 * @param srcObj
	 * @param targetObj
	 * @param includeProperties
	 * @return
	 */
	public static CompareResult compareObjectIncludeProperties(CompareObject srcObj,CompareObject targetObj,String[] includeProperties){
		return compareObject(srcObj,targetObj,true,includeProperties);		
	}
	/**
	 * 比较两个属性，只比较不在excludeProperties中定义的属性
	 * @param srcObj
	 * @param targetObj
	 * @param excludeProperties
	 * @return
	 */
	public static CompareResult compareObjectExcludeProperties(CompareObject srcObj,CompareObject targetObj,String[] excludeProperties){
		return compareObject(srcObj,targetObj,false,excludeProperties);		
	}
	/**
	 * 比较两个bean的属性值
	 * @param srcObj
	 * @param targetObj
	 * @param includeOrExclude true是比较包含在属性集里的属性，false是比较不在属性集里的属性
	 * @param properties
	 * @return
	 */
	public static CompareResult compareObject(CompareObject srcObj,CompareObject targetObj,boolean includeOrExclude,String[] properties){
		// 参数不能为null
		if(srcObj == null || targetObj == null){
			throw new IllegalArgumentException("srcObj and targetObj must be not null");
		}
		if(srcObj.getTargetObject() == null || targetObj.getTargetObject() == null){
			throw new IllegalArgumentException("Object to be compared must be not null");
		}
		// 创建结果
		CompareResult result = new CompareResult(srcObj,targetObj);
		// 根据src的属性进行比较
		ClassInfo srcClassInfo = srcObj.getClassInfo();
		PropertyInfo[] srcPropInfos = srcClassInfo.getPropertyInfos();
		// 包装bean，用反射取值
		BeanWrapper srcBean = new BeanWrapperImpl(false);
		srcBean.setWrappedInstance(srcObj.getTargetObject());
		///////////////////
		BeanWrapper targetBean = new BeanWrapperImpl(false);
		targetBean.setWrappedInstance(targetObj.getTargetObject());
		/////////////////////
		for (PropertyInfo srcPropertyInfo : srcPropInfos) {
			// 如果是要包含在属性集合里，不在集合里，跳过
			if(includeOrExclude == true && !isInclude(srcPropertyInfo.getPropertyName(),properties)){
				continue;
			}
			// 如果是要排除在属性集合外，在集合里，跳过
			if(includeOrExclude == false && isInclude(srcPropertyInfo.getPropertyName(),properties)){
				continue;
			}
			//
			PropertyInfo targetPropInfo = targetObj.getClassInfo().getPropertyInfo(srcPropertyInfo.getPropertyName());
			// 属性名不匹配不比较
			if(targetPropInfo == null){
				logger.warn("there is not property ["+srcPropertyInfo.getPropertyName()+"] in target object");
				continue;
			}
			// 类型不匹配不比较
			if(!srcPropertyInfo.getPropertyType().equals(targetPropInfo.getPropertyType())){
				logger.warn("type is not match, property name ["+srcPropertyInfo.getPropertyName()+"] , source type ["
						+srcPropertyInfo.getPropertyType()+"] ,target type ["+targetPropInfo.getPropertyType()+"]");
				continue;
			}
			// 获得值
			Object srcValue = srcBean.getPropertyValue(srcPropertyInfo.getPropertyName());
			Object targetValue = targetBean.getPropertyValue(srcPropertyInfo.getPropertyName());
			/////////////////////////////
			PropertyCompareResult propResult = new PropertyCompareResult();
			propResult.setSourcePropertyObject(new ComparePropertyObject(srcPropertyInfo,srcValue));
			propResult.setTargetPropertyObject(new ComparePropertyObject(targetPropInfo,targetValue));
			propResult.setResult(ValueCompareUtils.compareValue(srcValue, targetValue));
			result.addPropertyCompareResult(propResult);
		}
		return result;		
	}
	/**
	 * 属性名字是否在集合里
	 * @param propName
	 * @param props
	 * @return
	 */
	private static boolean isInclude(String propName,String[] props){
		if(props == null){
			return false;
		}
		for (String prop : props) {
			if(propName.equals(prop)){
				return true;
			}
		}
		return false;
	}
}

package com.baoz.yx.tools;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.opensymphony.xwork2.ActionContext;

/**
 * 类ActionPropertySessionHoldTool.java的实现描述：保存和恢复对象属性到session中
 * 
 * @author xurong Jun 26, 2008 8:54:07 PM
 */
public class ObjectPropertySessionHoldTool {
	private Object holdObject;
	private String[] holdPropertys;
	private String storeKey;
	private BeanWrapper beanWrapper = new BeanWrapperImpl(false);

	/**
	 * @param storeKey 在session中的key
	 * @param holdObject 包含属性的对象
	 * @param holdPropertys 保存到session的属性
	 */
	public ObjectPropertySessionHoldTool(String storeKey, Object holdObject,
			String[] holdPropertys) {
		this.holdObject = holdObject;
		this.holdPropertys = holdPropertys;
		this.storeKey = storeKey;
	}

	/**
	 * 将属性存入session
	 */
	public void storeToSession() {
		if (holdObject != null) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			beanWrapper.setWrappedInstance(holdObject);
			for (String property : holdPropertys) {
				map.put(property, beanWrapper.getPropertyValue(property));
			}
			ActionContext.getContext().getSession().put(storeKey, map);
		}
	}

	/**
	 * 从session把属性设回对象
	 */
	public void setBackToObject() {
		Map<Object, Object> map = (Map<Object, Object>) ActionContext
				.getContext().getSession().get(storeKey);
		if (map != null) {
			beanWrapper.setWrappedInstance(holdObject);
			for (String property : holdPropertys) {
				beanWrapper.setPropertyValue(property, map.get(property));
			}
		}
	}

	/**
	 * 清除session中的属性值
	 */
	public void clearStore() {
		ActionContext.getContext().getSession().remove(storeKey);
	}
}

/**
 *
 */
package com.baoz.yx.tools.append.dynamic;

import java.util.ArrayList;
import java.util.List;

import com.baoz.yx.tools.append.QLStringAppender;
import com.baoz.yx.tools.append.utils.AppenderUtils;


/**
 * <b>描述：</b> 常量appender，没有条件判断
 * @author xurong
 * Jun 21, 2008
 */
public class ConstantAppender implements QLStringAppender {
	private String appendString;
	private Object parameter;
	
	public ConstantAppender() {
	}
	/**
	 * @param appenderString 要append的字符串
	 */
	public ConstantAppender(String appenderString) {
		this.appendString = appenderString;
	}
	
	public ConstantAppender(String appendString, Object parameter) {
		this.appendString = appendString;
		this.parameter = parameter;
	}
	
	/**
	 * 子追加器
	 */
	private List<QLStringAppender> childrenList = new ArrayList<QLStringAppender>();

	/*
	 * (non-Javadoc)
	 *
	 * @see com.raddle.dynamic.append.QLStringAppender#addChild(com.raddle.dynamic.append.QLStringAppender)
	 */
	public QLStringAppender append(QLStringAppender childAppender) {
		childrenList.add(childAppender);
		return this;
	}

	/* (non-Javadoc)
	 * @see com.baoz.yx.tools.append.QLStringAppender#append(java.lang.String)
	 */
	public QLStringAppender append(String string) {
		return append(new ConstantAppender(string));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.raddle.dynamic.append.QLStringAppender#getString()
	 */
	public String getString() {
		StringBuilder builder = new StringBuilder();
		// 追加自己的字符串
		if (appendString != null) {
			builder.append(appendString);
		}
		// 追加子字符串
		AppenderUtils.appendChildrenString(builder,AppenderUtils.getAppendChildren(childrenList));
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see com.baoz.yx.tools.append.QLStringAppender#getParameters()
	 */
	public List<Object> getParameters() {
		List<Object> paramList = new ArrayList<Object>();
		if(parameter != null){
			paramList.add(parameter);
		}
		// 追加子参数
		paramList.addAll(AppenderUtils.getParameters(AppenderUtils.getAppendChildren(childrenList)));
		return paramList;
	}

	/**
	 * @return the appendString
	 */
	public String getAppendString() {
		return appendString;
	}

	/**
	 * @param appendString
	 *            the appendString to set
	 */
	public void setAppendString(String appendString) {
		this.appendString = appendString;
	}
	
	public Object getParameter() {
		return parameter;
	}
	
	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}
}

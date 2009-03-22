/**
 *
 */
package com.baoz.yx.tools.append.dynamic;

import com.baoz.yx.tools.append.utils.JudgeUtils;

/**
 * <b>描述：</b>条件不为空就追加
 *
 * @author xurong Jun 21, 2008
 */
public class IsNotEmptyAppender extends SingleConditionAppender {
	/**
	 * @param appendString  要追加的字符串
	 * @param conditionValue 用于判断的条件
	 */
	public IsNotEmptyAppender(String appendString, Object conditionValue) {
		this.conditionValue = conditionValue;
		this.appendString = appendString;
	}
	/**
	 *
	 * @param prepend 加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString 要追加的字符串
	 * @param conditionValue 用于判断的条件
	 */
	public IsNotEmptyAppender(String prepend, String appendString,
			Object conditionValue) {
		this.conditionValue = conditionValue;
		this.appendString = appendString;
		this.prepend = prepend;
	}
	/**
	 *
	 * @param appendString 要追加的字符串
	 * @param conditionValue 用于判断的条件
	 * @param parameter 字符串中用到的参数
	 */
	public IsNotEmptyAppender(String appendString, Object conditionValue,Object parameter) {
		this.conditionValue = conditionValue;
		this.appendString = appendString;
		this.parameter = parameter;
	}
	/**
	 *
	 * @param prepend 加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString 要追加的字符串
	 * @param conditionValue 用于判断的条件
	 * @param parameter 字符串中用到的参数
	 */
	public IsNotEmptyAppender(String prepend, String appendString,
			Object conditionValue,Object parameter) {
		this.conditionValue = conditionValue;
		this.appendString = appendString;
		this.prepend = prepend;
		this.parameter = parameter;
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see com.baoz.yx.tools.append.dynamic.simple.AbstractConditionStringAppender#isAppend()
	 */
	@Override
	public boolean isAppend() {
		return JudgeUtils.isNotEmpty(conditionValue);
	}
}

/**
 *
 */
package tools.append.dynamic;

import tools.append.utils.JudgeUtils;

/**
 * <b>描述：</b>条件为空时就追加
 *
 * @author xurong Jun 21, 2008
 */
public class IsEmptyAppender extends SingleConditionAppender {
	/**
	 * @param appendString  要追加的字符串
	 * @param conditionValue 用于判断的条件
	 */
	public IsEmptyAppender(String appendString, Object conditionValue) {
		this.conditionValue = conditionValue;
		this.appendString = appendString;
	}
	/**
	 *
	 * @param prepend 加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString 要追加的字符串
	 * @param conditionValue 用于判断的条件
	 */
	public IsEmptyAppender(String prepend, String appendString,
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
	public IsEmptyAppender(String appendString, Object conditionValue,Object parameter) {
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
	public IsEmptyAppender(String prepend, String appendString,
			Object conditionValue,Object parameter) {
		this.conditionValue = conditionValue;
		this.appendString = appendString;
		this.prepend = prepend;
		this.parameter = parameter;
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see tools.append.dynamic.simple.AbstractConditionStringAppender#isAppend()
	 */
	@Override
	public boolean isAppend() {
		return JudgeUtils.isEmpty(conditionValue);
	}
}

/**
 *
 */
package com.baoz.yx.tools.append.dynamic;

import java.util.ArrayList;
import java.util.List;

import com.baoz.yx.tools.append.utils.AppenderUtils;


/**
 * <b>描述：</b>单条件字符串追加
 * @author xurong
 * Jun 21, 2008
 */
public abstract class SingleConditionAppender extends
		AbstractConditionAppender {
	protected Object conditionValue;
	protected Object parameter;

	public List<Object> getParameters() {
		List<Object> paramList = new ArrayList<Object>();
		if(parameter != null){
			paramList.add(parameter);
		}
		// 追加子参数
		paramList.addAll(AppenderUtils.getParameters(getAppendChildren()));
		return paramList;
	}

}

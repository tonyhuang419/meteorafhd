/**
 *
 */
package com.baoz.yx.tools.append.utils;

import java.util.ArrayList;
import java.util.List;

import com.baoz.yx.tools.append.QLStringAppender;
import com.baoz.yx.tools.append.dynamic.AbstractConditionAppender;

/**
 * <b>描述：</b>
 * @author xurong
 * Jun 21, 2008
 */
public class AppenderUtils {
	/**
	 * 获得需要append的子追加器
	 *
	 * @return
	 */
	public static List<QLStringAppender> getAppendChildren(List<QLStringAppender> childrenList) {
		List<QLStringAppender> list = new ArrayList<QLStringAppender>();
		for (QLStringAppender appender : childrenList) {
			// 如果是有条件的，判断条件
			if (appender instanceof AbstractConditionAppender) {
				AbstractConditionAppender subAppend = (AbstractConditionAppender) appender;
				if (subAppend.isAppend()) {
					list.add(appender);
				}
			} else {
				// 如果是没有条件的，判断内容是不是空
				String subString = appender.getString();
				if (subString != null && subString.length()>0) {
					list.add(appender);
				}
			}
		}
		return list;
	}
	/**
	 * 追加子串
	 * @param builder
	 * @param childrenList
	 */
	public static void appendChildrenString(StringBuilder builder,List<QLStringAppender> childrenList) {
		for (int i = 0; i < childrenList.size(); i++) {
			QLStringAppender appender = childrenList.get(i);
			// 有prepend
			if (appender instanceof AbstractConditionAppender) {
				AbstractConditionAppender subAppend = (AbstractConditionAppender) appender;
				// 如果有prepend
				if(subAppend.getPrepend() !=null && subAppend.getPrepend().trim().length()>0){
					// 如果前面有兄弟
					if(i>0){
						builder.append(subAppend.getPrepend());
					}
				}
				builder.append(subAppend.getString());
			} else {
				// 无prepend
				builder.append(appender.getString());
			}
		}
	}
	/**
	 * 从列表中获得参数
	 * @param appendChilrenList
	 * @return
	 */
	public static List<Object> getParameters(List<QLStringAppender> appendChilrenList) {
		List<Object> paramList = new ArrayList<Object>();
		for (int i = 0; i < appendChilrenList.size(); i++) {
			QLStringAppender appender = appendChilrenList.get(i);
			// 如果是有参数的
			if (appender instanceof QLStringAppender) {
				QLStringAppender subAppend = (QLStringAppender) appender;
				paramList.addAll(subAppend.getParameters());
			}
		}
		return paramList;
	}
}

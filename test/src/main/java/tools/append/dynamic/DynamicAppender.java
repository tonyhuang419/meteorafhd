/**
 *
 */
package tools.append.dynamic;

import java.util.ArrayList;
import java.util.List;

import tools.append.QLStringAppender;
import tools.append.utils.AppenderUtils;

/**
 * <b>描述：</b> 如果有child需要append，才append
 * @author xurong
 * Jun 21, 2008
 */
public class DynamicAppender implements QLStringAppender {


	private String appendString;

	/**
	 * 子追加器
	 */
	private List<QLStringAppender> childrenList = new ArrayList<QLStringAppender>();
	/**
	 * @param appendString 要追加的字符串
	 */
	public DynamicAppender(String appendString) {
		this.appendString = appendString;
	}

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
	 * @see tools.append.QLStringAppender#append(java.lang.String)
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

		// 获得要追加的child列表
		List<QLStringAppender> appendChilrenList = AppenderUtils.getAppendChildren(childrenList);
		// 追加自己的字符串
		if (appendChilrenList.size() > 0) {
			// 追加子字符串
			StringBuilder subBuilder = new StringBuilder();
			AppenderUtils.appendChildrenString(subBuilder,appendChilrenList);
			if(subBuilder.length() > 0 && subBuilder.toString().trim().length() > 0){
				if(appendString != null ){
					builder.append(appendString);
				}
				builder.append(subBuilder);
			}
		}
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see tools.append.QLStringAppender#getParameters()
	 */
	public List<Object> getParameters() {
		List<Object> paramList = new ArrayList<Object>();
		// 追加子参数
		List<QLStringAppender> appendChilrenList = AppenderUtils.getAppendChildren(childrenList);
		paramList.addAll(AppenderUtils.getParameters(appendChilrenList));
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
}

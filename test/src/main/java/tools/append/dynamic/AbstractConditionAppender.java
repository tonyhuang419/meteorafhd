/**
 *
 */
package tools.append.dynamic;

import java.util.ArrayList;
import java.util.List;

import tools.append.QLStringAppender;
import tools.append.utils.AppenderUtils;

/**
 * <b>描述：</b>根据条件判断是否追加
 *
 * @author xurong Jun 21, 2008
 */
public abstract class AbstractConditionAppender implements QLStringAppender {

	protected String appendString;
	protected String prepend;
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
		// 追加自己的字符串
		if (isAppend() && appendString != null) {
			builder.append(appendString);
		}
		// 追加子字符串
		List<QLStringAppender> appendChilrenList = getAppendChildren();
		AppenderUtils.appendChildrenString(builder,appendChilrenList);
		return builder.toString();
	}

	/**
	 * 获得需要append的子追加器
	 *
	 * @return
	 */
	protected List<QLStringAppender> getAppendChildren() {
		return AppenderUtils.getAppendChildren(childrenList);
	}

	/**
	 * 是否追加本字符串
	 *
	 * @return
	 */
	abstract public boolean isAppend();

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

	/**
	 * @return the prepend
	 */
	public String getPrepend() {
		return prepend;
	}

	/**
	 * @param prepend
	 *            the prepend to set
	 */
	public void setPrepend(String prepend) {
		this.prepend = prepend;
	}

}

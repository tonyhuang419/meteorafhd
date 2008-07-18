package com.baoz.yx.tools.append.helper;

import java.util.List;

import com.baoz.yx.tools.append.QLStringAppender;
import com.baoz.yx.tools.append.dynamic.ConstantAppender;
import com.baoz.yx.tools.append.dynamic.IsEmptyAppender;
import com.baoz.yx.tools.append.dynamic.IsNotEmptyAppender;
import com.baoz.yx.tools.append.dynamic.IsNotNullAppender;
import com.baoz.yx.tools.append.dynamic.IsNullAppender;

/**
 * 类StringAppender.java的实现描述：字符串生成类
 *
 * @author xurong Jun 21, 2008 5:27:38 PM
 */
public class StringAppender implements QLStringAppender {
	private QLStringAppender appender = new ConstantAppender();

	/*
	 * (non-Javadoc)
	 *
	 * @see com.baoz.yx.tools.append.QLStringAppender#append(com.baoz.yx.tools.append.QLStringAppender)
	 */
	public QLStringAppender append(QLStringAppender childAppender) {
		return appender.append(childAppender);
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.baoz.yx.tools.append.QLStringAppender#append(java.lang.String)
	 */
	public QLStringAppender append(String string) {
		return appender.append(string);
	}
	
	public QLStringAppender appendWithParameter(String string, Object parameter ) {
		return appender.append(new ConstantAppender(string,parameter));
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see com.baoz.yx.tools.append.QLStringAppender#getParameters()
	 */
	public List<Object> getParameters() {
		return appender.getParameters();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.baoz.yx.tools.append.QLStringAppender#getString()
	 */
	public String getString() {
		return appender.getString();
	}

	/**
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 */
	public StringAppender appendIfEmpty(String appendString, Object conditionValue) {
		appender.append(new IsEmptyAppender(appendString, conditionValue));
		return this;
	}

	/**
	 *
	 * @param prepend
	 *            加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 */
	public StringAppender appendIfEmpty(String prepend, String appendString, Object conditionValue) {
		appender.append(new IsEmptyAppender(prepend, appendString, conditionValue));
		return this;
	}

	/**
	 *
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 * @param parameter
	 *            字符串中用到的参数
	 */
	public StringAppender appendIfEmpty(String appendString, Object conditionValue, Object parameter) {
		appender.append(new IsEmptyAppender(appendString, conditionValue, parameter));
		return this;
	}

	/**
	 *
	 * @param prepend
	 *            加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 * @param parameter
	 *            字符串中用到的参数
	 */
	public StringAppender appendIfEmpty(String prepend, String appendString, Object conditionValue, Object parameter) {
		appender.append(new IsEmptyAppender(prepend, appendString, conditionValue, parameter));
		return this;
	}

	/**
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 */
	public StringAppender appendIfNotEmpty(String appendString, Object conditionValue) {
		appender.append(new IsNotEmptyAppender(appendString, conditionValue));
		return this;
	}

	/**
	 *
	 * @param prepend
	 *            加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 */
	public StringAppender appendIfNotEmpty(String prepend, String appendString, Object conditionValue) {
		appender.append(new IsNotEmptyAppender(prepend, appendString, conditionValue));
		return this;
	}

	/**
	 *
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 * @param parameter
	 *            字符串中用到的参数
	 */
	public StringAppender appendIfNotEmpty(String appendString, Object conditionValue, Object parameter) {
		appender.append(new IsNotEmptyAppender(appendString, conditionValue, parameter));
		return this;
	}

	/**
	 *
	 * @param prepend
	 *            加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 * @param parameter
	 *            字符串中用到的参数
	 */
	public StringAppender appendIfNotEmpty(String prepend, String appendString, Object conditionValue, Object parameter) {
		appender.append(new IsNotEmptyAppender(prepend, appendString, conditionValue, parameter));
		return this;
	}

	/**
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 */
	public StringAppender appendIfNull(String appendString, Object conditionValue) {
		appender.append(new IsNullAppender(appendString, conditionValue));
		return this;
	}

	/**
	 *
	 * @param prepend
	 *            加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 */
	public StringAppender appendIfNull(String prepend, String appendString, Object conditionValue) {
		appender.append(new IsNullAppender(prepend, appendString, conditionValue));
		return this;
	}

	/**
	 *
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 * @param parameter
	 *            字符串中用到的参数
	 */
	public StringAppender appendIfNull(String appendString, Object conditionValue, Object parameter) {
		appender.append(new IsNullAppender(appendString, conditionValue, parameter));
		return this;
	}

	/**
	 *
	 * @param prepend
	 *            加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 * @param parameter
	 *            字符串中用到的参数
	 */
	public StringAppender appendIfNull(String prepend, String appendString, Object conditionValue, Object parameter) {
		appender.append(new IsNullAppender(prepend, appendString, conditionValue, parameter));
		return this;
	}

	/**
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 */
	public StringAppender appendIfNotNull(String appendString, Object conditionValue) {
		appender.append(new IsNotNullAppender(appendString, conditionValue));
		return this;
	}

	/**
	 *
	 * @param prepend
	 *            加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 */
	public StringAppender appendIfNotNull(String prepend, String appendString, Object conditionValue) {
		appender.append(new IsNotNullAppender(prepend, appendString, conditionValue));
		return this;
	}

	/**
	 *
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 * @param parameter
	 *            字符串中用到的参数
	 */
	public StringAppender appendIfNotNull(String appendString, Object conditionValue, Object parameter) {
		appender.append(new IsNotNullAppender(appendString, conditionValue, parameter));
		return this;
	}

	/**
	 *
	 * @param prepend
	 *            加在appendString前面的字符串，前面有兄弟节点时才加
	 * @param appendString
	 *            要追加的字符串
	 * @param conditionValue
	 *            用于判断的条件
	 * @param parameter
	 *            字符串中用到的参数
	 */
	public StringAppender appendIfNotNull(String prepend, String appendString, Object conditionValue, Object parameter) {
		appender.append(new IsNotNullAppender(prepend, appendString, conditionValue, parameter));
		return this;
	}

	@Override
	public String toString() {
		return getString();
	}
	
}

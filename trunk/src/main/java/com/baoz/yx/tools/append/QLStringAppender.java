/**
 *
 */
package com.baoz.yx.tools.append;

import java.util.List;

/**
 * <b>描述：</b> 生成sql或hql
 * @author xurong
 * Jun 21, 2008
 */
public interface QLStringAppender{
	/**
	 * 获得最终生成的字符串
	 * @return
	 */
	public String getString();
	/**
	 * 增加子追加器，子追加器生成的字符串，将被加载主追加器的后面
	 * @param child 子追加器
	 * @return 返回自己
	 */
	public QLStringAppender append(QLStringAppender childAppender);
	/**
	 * 追加字符串
	 * @param string
	 * @return
	 */
	public QLStringAppender append(String string);
	/**
	 * 获得sql或hql要用的参数
	 * @return
	 */
	public List<Object> getParameters();
}

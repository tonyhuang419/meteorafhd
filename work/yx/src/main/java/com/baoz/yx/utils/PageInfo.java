/**
 * 
 */
package com.baoz.yx.utils;

/**
 * @author FHDone
 *
 */
public class PageInfo extends com.baoz.core.utils.PageInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8550737273100315439L;

	@Override
	public void setTotalCount(int totalCount) {
		super.setTotalCount(totalCount);
		setCurPage(Math.min(getCurPage(), getTotalPageCount()));
	}
	
}

package formBean;

import org.apache.struts.action.ActionForm;

public class StrideFormBean extends ActionForm{
	
	/** 页号 */
	private int page;
	
	/** 第一个表单的文本框 */
	private String text1;
	
	/** 第二个表单的文本框 */
	private String text2;

	/**
	 * @return 返回 page。
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page 要设置的 page。
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return 返回 text1。
	 */
	public String getText1() {
		return text1;
	}

	/**
	 * @param text1 要设置的 text1。
	 */
	public void setText1(String text1) {
		this.text1 = text1;
	}

	/**
	 * @return 返回 text2。
	 */
	public String getText2() {
		return text2;
	}

	/**
	 * @param text2 要设置的 text2。
	 */
	public void setText2(String text2) {
		this.text2 = text2;
	}
	
	
}

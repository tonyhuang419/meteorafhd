package formBean;

import org.apache.struts.action.ActionForm;

public class StrideFormBean extends ActionForm{
	
	/** ҳ�� */
	private int page;
	
	/** ��һ�������ı��� */
	private String text1;
	
	/** �ڶ��������ı��� */
	private String text2;

	/**
	 * @return ���� page��
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page Ҫ���õ� page��
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return ���� text1��
	 */
	public String getText1() {
		return text1;
	}

	/**
	 * @param text1 Ҫ���õ� text1��
	 */
	public void setText1(String text1) {
		this.text1 = text1;
	}

	/**
	 * @return ���� text2��
	 */
	public String getText2() {
		return text2;
	}

	/**
	 * @param text2 Ҫ���õ� text2��
	 */
	public void setText2(String text2) {
		this.text2 = text2;
	}
	
	
}

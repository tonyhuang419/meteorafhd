package com.exam.action.demo;

import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.exam.tools.cewolf.CewolfAdapter;

import de.laures.cewolf.DatasetProduceException;


@Results( {
	@Result(name = "cewolfDemo", value = "/WEB-INF/jsp/demo/cewolfOne.jsp")
})
public class CewolfOneAction extends CewolfAdapter {
	private static final long serialVersionUID = 3221028806051525125L;
	
	private final String[] categories =    {"mon", "tue", "wen", "thu", "fri", "sat", "sun"};
	private final String[] seriesNames =    {"cewolfset.jsp", "tutorial.jsp", "testpage.jsp", "performancetest.jsp"};

	public String cewolfDemo(){
		return "cewolfDemo";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object produceDataset(Map params) throws DatasetProduceException {
		logger.debug("producing data.");
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int series = 0; series < seriesNames.length; series ++) {
			int lastY = (int)(Math.random() * 1000 + 1000);
			for (int i = 0; i < categories.length; i++) {
				final int y = lastY + (int)(Math.random() * 200 - 100);
				lastY = y;
				dataset.addValue(y, seriesNames[series], categories[i]);
			}
		}
		return dataset;
	}
	

	/**
	 * Returns a unique ID for this DatasetProducer
	 */
	public String getProducerId() {
		return "chartOne";
	}
	
	/**
	 * Returns a link target for a special data item.
	 */
	public String generateLink(Object data, int series, Object category) {
		return seriesNames[series];
	}
	
	/**
	 * @see org.jfree.chart.tooltips.CategoryToolTipGenerator#generateToolTip(CategoryDataset, int, int)
	 */
	public String generateToolTip(CategoryDataset arg0, int series, int arg2) {
		return seriesNames[series];
	}



}

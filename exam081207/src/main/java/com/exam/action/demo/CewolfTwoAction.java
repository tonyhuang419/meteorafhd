package com.exam.action.demo;

import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.exam.tools.cewolf.CewolfAdapter;

import de.laures.cewolf.DatasetProduceException;


@Results( {
	@Result(name = "cewolfDemo", value = "/WEB-INF/jsp/demo/cewolfTwo.jsp")
})
public class CewolfTwoAction extends CewolfAdapter {
	private static final long serialVersionUID = 3221028806051525125L;

	private final String[] seriesNames =    {"cewolfset.jsp", "tutorial.jsp", "testpage.jsp", "performancetest.jsp"};

	public String cewolfDemo(){
		return "cewolfDemo";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object produceDataset(Map params) throws DatasetProduceException {
		TimeSeries ts = new TimeSeries("Cewolf Release Schedule", Month.class);
		ts.add(new Month(7, 2002), 0.1);
		ts.add(new Month(8, 2002), 0.4);
		ts.add(new Month(9, 2002), 0.9);
		ts.add(new Month(10, 2002), 1.0);
		return new TimeSeriesCollection(ts);
	}


	/**
	 * Returns a unique ID for this DatasetProducer
	 */
	public String getProducerId() {
		return "chartTwo";
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

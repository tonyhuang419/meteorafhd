package com.exam.action.demo;

import java.awt.Color;
import java.awt.GradientPaint;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ChartResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.exam.action.BaseAction;

@ParentPackage(value = "jfreechart-default")
@Results( {
	@Result(name = "demoChart", type=ChartResult.class , value = "",
			params={"width","800","height","600"})
})
public class ChartAction extends BaseAction{
	private static final long serialVersionUID = -9009128063312892982L;
	private JFreeChart chart;

	public String demoChartOne()  {
		// chart creation logic...
		XYSeries dataSeries = new XYSeries(new Integer(1)); //pass a key for this serie
		for (int i = 0; i <= 100; i++) {
			dataSeries.add(i, RandomUtils.nextInt());
		}
		XYSeriesCollection xyDataset = new XYSeriesCollection(dataSeries);

		ValueAxis xAxis = new NumberAxis("Raw Marks");
		ValueAxis yAxis = new NumberAxis("Moderated Marks");

		// set my chart variable
		chart =
			new JFreeChart(
					"Moderation Function 标题",
					JFreeChart.DEFAULT_TITLE_FONT,
					new XYPlot(
							xyDataset,
							xAxis,
							yAxis,
							new StandardXYItemRenderer(StandardXYItemRenderer.LINES)),
							false);
		chart.setBackgroundPaint(java.awt.Color.white);

		return "demoChart";
	}


	public String demoChartTwo(){

		chart = ChartFactory.createBarChart(
				"Bar Chart Demo", //图形标题名称
				"Category",		//domain 轴 Lable   这里先简单理解为横坐标Lable好了
				"Value", //range 轴 Lable   这里也先简单理解为纵坐标Lable好了
				createDataset() , //  dataset
				//				PlotOrientation.VERTICAL, //垂直显示
				PlotOrientation.HORIZONTAL,
				true, // legend
				true,  // tooltips
				false); //URLs
		chart.setBackgroundPaint(Color.white);   //设定背景色为白色
		CategoryPlot categoryplot = chart.getCategoryPlot(); //获得 plot：CategoryPlot！！
		categoryplot.setBackgroundPaint(Color.lightGray); //设定图表数据显示部分背景色
		categoryplot.setDomainGridlinePaint(Color.white); //横坐标网格线白色
		categoryplot.setDomainGridlinesVisible(true); //可见
		categoryplot.setRangeGridlinePaint(Color.white); //纵坐标网格线白色
		//下面两行使纵坐标的最小单位格为整数
		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		BarRenderer barrenderer = (BarRenderer)categoryplot.getRenderer(); //获得renderer 注意这里是下嗍造型到BarRenderer！！
		barrenderer.setDrawBarOutline(false); // Bar的外轮廓线不画
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.blue, 
				0.0F, 0.0F, new Color(0, 0, 64));   //设定特定颜色
		GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F, Color.green, 
				0.0F, 0.0F, new Color(0, 64, 0));
		GradientPaint gradientpaint2 = new GradientPaint(0.0F, 0.0F, Color.red,
				0.0F, 0.0F, new Color(64, 0, 0));
		barrenderer.setSeriesPaint(0, gradientpaint); //给series1 Bar设定上面定义的颜色
		barrenderer.setSeriesPaint(1, gradientpaint1); //给series2 Bar 设定上面定义的颜色
		barrenderer.setSeriesPaint(2, gradientpaint2); //给series3 Bar 设定上面定义的颜色
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();  //横轴上的 Lable 45度倾斜
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);        
		//		return jfreechart;
//		ChartFrame chartFrame = new ChartFrame("公司人员信息", chart);
//		chartFrame.pack();
//		chartFrame.setVisible(true); 
		return "demoChart";
	}
	
	
	
	private  DefaultCategoryDataset createDataset()
	{
		String series1 = "First";
		String series2 = "Second";
		String series3 = "Third";
		String category1 = "Category 1";
		String category2 = "Category 2";
		String category3 = "Category 3";
		String category4 = "Category 4";
		String category5 = "Category 5";
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		defaultcategorydataset.addValue(1.0D, series1, category1);
		defaultcategorydataset.addValue(4D, series1, category2);
		defaultcategorydataset.addValue(3D, series1, category3);
		defaultcategorydataset.addValue(5D, series1, category4);
		defaultcategorydataset.addValue(5D, series1, category5);

		defaultcategorydataset.addValue(5D, series2, category1);
		defaultcategorydataset.addValue(7D, series2, category2);
		defaultcategorydataset.addValue(6D, series2, category3);
		defaultcategorydataset.addValue(8D, series2, category4);
		defaultcategorydataset.addValue(4D, series2, category5);

		defaultcategorydataset.addValue(4D, series3, category1);
		defaultcategorydataset.addValue(3D, series3, category2);
		defaultcategorydataset.addValue(2D, series3, category3);
		defaultcategorydataset.addValue(3D, series3, category4);
		defaultcategorydataset.addValue(6D, series3, category5);
		return defaultcategorydataset;
	}



	public JFreeChart getChart() {
		return chart;
	}


}

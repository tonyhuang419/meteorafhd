package jFreeChart;

import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class CategoryDataset {

	public static void main(String[] args)   
	{  
		DefaultCategoryDataset  categorydataset = createDataset();
		
		JFreeChart jfreechart = ChartFactory.createBarChart(
				"Bar Chart Demo", //图形标题名称
				"Category",		//domain 轴 Lable   这里先简单理解为横坐标Lable好了
				"Value", //range 轴 Lable   这里也先简单理解为纵坐标Lable好了
				categorydataset, //  dataset
				PlotOrientation.VERTICAL, //垂直显示
				true, // legend
				true,  // tooltips
				false); //URLs
		jfreechart.setBackgroundPaint(Color.white);   //设定背景色为白色
		CategoryPlot categoryplot = jfreechart.getCategoryPlot(); //获得 plot：CategoryPlot！！
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
		ChartFrame chartFrame = new ChartFrame("公司人员信息", jfreechart);
		chartFrame.pack();
		chartFrame.setVisible(true); 
	}


	private static DefaultCategoryDataset createDataset()
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

}

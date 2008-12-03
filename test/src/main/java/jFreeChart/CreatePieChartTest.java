package jFreeChart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class CreatePieChartTest {
	public static void main(String[] args) {

		// 准备饼图数据集
		DefaultPieDataset dpd = new DefaultPieDataset();
		dpd.setValue("Chinese", 108);
		dpd.setValue("Math", 110);
		dpd.setValue("English", 74);
		dpd.setValue("Science Department", 226);

		/**
		 * 利用chart工厂产生JFreeChart对象
		 * createPieChart四个参数饼图标题,数据集,是否产生图注,鼠标移上去是否产生相应的提示信息、locale - the locale (null not permitted).
		 */
		JFreeChart jfreechart = ChartFactory.createPieChart("bulktree high-tech achievement", dpd,
				true, true, false);
		//	     产生3d饼图   
		//	     JFreeChart jfreechart = ChartFactory.createPieChart3D("bulktree high-tech achievement", dpd,
		//	            true, true, false);
		ChartFrame frame = new ChartFrame("BULKTREE HIGH-TECH ACHIEVEMENT", jfreechart);
		frame.pack();
		frame.setVisible(true);
	}
}

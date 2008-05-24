package statistics.dept;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Statistics {
	Dept apm = new APMDept(1000,500); //总包工程市场部
	Dept auto = new AutoEDept(5100,6153); //自动化工程事业部
	Dept bis = new BISDept(5100,3131); //商务智能软件事业部
	Dept cob = new CoBussDept(6100,5685); //协同商务软件事业部
	Dept erp = new ERPDept(7541,2356); //ERP软件事业部
	Dept eap = new ExcavateAppDept(8400,3654); //采掘应用事业部
	Dept ice = new ICEDept(50310,43452); //智能化工程事业部
	Dept lad = new LandAppDept(8100,6512); //石化应用事业部
	Dept md = new MEIDept(8524,6532); //MES软件事业部
	Dept mes = new MESDept(7854,5641); //机电一体化产品事业部
	Dept ss = new SysServerDept(7415,6524); //系统服务事业部
	
	Dept[] dept = {apm,auto,bis,cob,erp,eap,ice,lad,md,mes,ss};
	Dept zj = new SumDept(dept); //总计
		
	List<Dept> listX = new ArrayList<Dept>();
	double sumTarget = 0;
	double sumAccomplish = 0;
	double sumBlance = 0;
	
	public void genercList(){
		listX.add(apm);
		listX.add(auto);
		listX.add(bis);
		listX.add(cob);
		listX.add(erp);
		listX.add(eap);
		listX.add(ice);
		listX.add(lad);
		listX.add(md);
		listX.add(mes);
		listX.add(ss);
		listX.add(zj);	
	}
	
//	public void getListXIterator(){
//		NumberFormat nf  =  NumberFormat.getPercentInstance();
//		nf.setMinimumFractionDigits( 2 );
//		
//		genercList();
//		Iterator iterator =listX.iterator();
//		while(iterator.hasNext()){
//			Dept dept = (Dept)iterator.next();
//		
//			System.out.print(dept.getTarget()+"\t\t");
//			sumTarget += dept.getTarget();		
//			
//			System.out.print(dept.getAccomplish()+"\t\t");
//			sumAccomplish += dept.getAccomplish();
//			
//			System.out.print(dept.getPrecentage()+"\t\t");
//			
//			System.out.println(dept.getBalance());
//			sumBlance += dept.getBalance();
//		}
//		System.out.print(sumTarget+"\t");
//		System.out.print(sumAccomplish+"\t\t");
//		System.out.print(nf.format(sumAccomplish / sumTarget)+"\t\t");
//		System.out.print(sumBlance);
//		
//	}
	
	public Iterator getListXIterator2(){
		genercList();
		return listX.iterator();			
	}
	
	
//	public static void main(String args[]){
//		new Statistics().getListXIteratoe();		
//	}
}
package statistics.dept;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Statistics {
	Dept apm = new APMDept(1000,500); //�ܰ������г���
	Dept auto = new AutoEDept(5100,6153); //�Զ���������ҵ��
	Dept bis = new BISDept(5100,3131); //�������������ҵ��
	Dept cob = new CoBussDept(6100,5685); //Эͬ���������ҵ��
	Dept erp = new ERPDept(7541,2356); //ERP�����ҵ��
	Dept eap = new ExcavateAppDept(8400,3654); //�ɾ�Ӧ����ҵ��
	Dept ice = new ICEDept(50310,43452); //���ܻ�������ҵ��
	Dept lad = new LandAppDept(8100,6512); //ʯ��Ӧ����ҵ��
	Dept md = new MEIDept(8524,6532); //MES�����ҵ��
	Dept mes = new MESDept(7854,5641); //����һ�廯��Ʒ��ҵ��
	Dept ss = new SysServerDept(7415,6524); //ϵͳ������ҵ��
	
	Dept[] dept = {apm,auto,bis,cob,erp,eap,ice,lad,md,mes,ss};
	Dept zj = new SumDept(dept); //�ܼƮ�
		
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
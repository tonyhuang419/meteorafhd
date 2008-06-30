import java.util.Date;



public class Test {
	public Stu getStu(){
		return new Stu();
	}
	public static void main(String[] args) {
//		Stu s = null;
//		try{
//		System.out.println(s.getClass().getName());
//		}catch(Exception e){
//		e.printStackTrace();
//		}
//		s = new Test().getStu();
//		try{
//		System.out.println(s.getClass().getName());
//		}catch(Exception e){
//		e.printStackTrace();
//		}

//		BigDecimal a = new BigDecimal(100.00);
//		System.out.println(a.add(new BigDecimal(-1)));

//		String s="";
//		System.out.println(s.trim());

//		String str = "1,2,3,4,5,6,7,8,9";
//		String a[] = str.split(",");
//		for(int i=0;i<a.length;i++){
//			System.out.println(a[i]);
//		}
		
		Date now = new Date();
		System.out.println(now);
	}

}

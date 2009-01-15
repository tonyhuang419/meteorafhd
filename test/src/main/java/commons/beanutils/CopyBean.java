package commons.beanutils;
import org.apache.commons.beanutils.BeanUtils;
public class CopyBean {
	public void copybean(){
		Address a1 = new Address();
		a1.setAddr("address");
		a1.setCity("mars");
		a1.setCountry("milky way");
		a1.setZipCode(null);
		Address a2 =  new Address();
		try{
			/**
			 * BeanUtils的copyProperties，null也会一概copy噢^^
			 */
			BeanUtils.copyProperties(a2, a1);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(a2.getAddr());
		System.out.println(a2.getCity());
		System.out.println(a2.getCountry());
		System.out.println(a2.getZipCode());
	}
	
	
	public void copybeanB(){
		Address a1 = new Address();
		a1.setAddr("address");
		a1.setCity("mars");
		a1.setCountry("milky way");
		a1.setZipCode(null);
		
		AddressX a2 =  new AddressX();
		try{
			BeanUtils.copyProperties(a2, a1);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(a2.getAddr());
		System.out.println(a2.getCity());
		System.out.println(a2.getCountry());
		System.out.println(a2.getZipCode());
		System.out.println(a2.getNum());
		
	}

	public static void main(String[] args) throws Exception {
		new CopyBean().copybeanB();
	}
}
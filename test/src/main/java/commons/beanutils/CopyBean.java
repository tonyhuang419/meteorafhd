package commons.beanutils;
import java.util.HashMap;

import org.apache.commons.beanutils.BeanUtils;
public class CopyBean {
	public void copybean(){
		Address a1 = new Address();
		a1.setAddr("address");
		a1.setCity("mars");
		a1.setCountry("milky way");
		a1.setZipCode("-----");
		Address a2 =  new Address();
		HashMap map = new HashMap();
		try{
			BeanUtils.copyProperties(a2, a1);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(a2.getAddr());
		System.out.println(a2.getCity());
		System.out.println(a2.getCountry());
		System.out.println(a2.getZipCode());
	}
	public static void main(String[] args) throws Exception {
		new CopyBean().copybean();
	}
}
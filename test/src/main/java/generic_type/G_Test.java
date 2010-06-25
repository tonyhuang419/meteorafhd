package generic_type;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于泛型
 * 推荐阅读:“这可不是泛型”-Bruce Eckel眼中的Java泛型
 * http://space.itpub.net/12476590/viewspace-133982
 */
public class G_Test {

	/**
	 * 这里对泛型的使用毫无意义，而且语法上让我觉得混乱，
	 * 还不如使用下面的多态
	 */
	public <T extends G_Base>  void sayHelloByGeneric ( T base){
		base.sayHello();
	}
	
	public void sayHelloByExtends ( G_Base base){
		base.sayHello();
	}
	
	public static void main(String[] args) {

		G_Test test = new G_Test();
		
		test.sayHelloByGeneric(new G_Lover());
		test.sayHelloByGeneric(new G_Parent());
		
		test.sayHelloByExtends(new G_Lover());
		test.sayHelloByExtends(new G_Parent());
		
		List l = new ArrayList<G_Base>();
		
	}

}

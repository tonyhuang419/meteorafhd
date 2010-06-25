package generic_type;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.MethodUtils;

/**
 * 关于泛型
 * 推荐阅读:“这可不是泛型”-Bruce Eckel眼中的Java泛型
 * http://space.itpub.net/12476590/viewspace-133982
 */
public class G_Test {

	/**
	 * 这里对泛型的使用毫无意义，而且语法上让我觉得混乱，
	 * 还不如使用多态
	 */
	public <T extends G_Base>  void sayHelloByGeneric ( T base){
		base.sayHello();
	}
	
	/**
	 * 如果一定要用的话，因该是这样，不过性能上的就差很多了
	 */
	public <T>  void sayHelloByGenericX ( T base){
		try {
			MethodUtils.invokeMethod(base, "sayHello", new Object[] {});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
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
		
		test.sayHelloByGenericX(new G_Lover());
		test.sayHelloByGenericX(new G_Parent());
		
	}

}

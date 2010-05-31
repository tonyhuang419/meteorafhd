package jibx;

import java.io.FileInputStream;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

public class JibxTest {
	public static void main(String[] args) { 
		try{ 
			IBindingFactory bfact = BindingDirectory.getFactory(Customer.class); 
			IUnmarshallingContext uctx = bfact.createUnmarshallingContext(); 
			Customer customer = (Customer)uctx.unmarshalDocument(new FileInputStream("src/main/java/jibx/data.xml"), null); 
			Person person = customer.person; 

			System.out.println("cust-num:" + person.customerNumber); 
			System.out.println("first-name:" + person.firstName); 
			System.out.println("last-name:" + person.lastName); 
			System.out.println("street:" + customer.street); 
		}catch(Exception e){ 
			System.out.println(e.toString()); 
		} 
	}
}

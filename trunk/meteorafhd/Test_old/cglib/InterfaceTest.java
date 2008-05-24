// π”√Mixin

package cglib;

import net.sf.cglib.proxy.Mixin;public class InterfaceTest {
	public static void main(String[] args) {
		Class[] interfaces = new Class[] { 
				InterfaceA.class, InterfaceB.class };		
		Object[] delegates = new Object[] {
				new InterfaceAImpl(),
				new InterfaceBImpl()
		};							
		Object obj = Mixin.create(interfaces, delegates);		
		InterfaceA myA = (InterfaceA)obj;	
		myA.methodA();		
		InterfaceB myB = (InterfaceB)obj;	
		myB.methodB();	
	}
}
package instrumentationDemo;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class HelloTest {
	public static void main(String[] args) throws Exception {
		ClassPool cp = ClassPool.getDefault();
		CtClass cc = cp.get("instrumentationDemo.Hello");
		CtMethod m = cc.getDeclaredMethod("say");
		m.insertBefore("{ System.out.println(\"Hello.say():\"); }");
		Class c = cc.toClass();
		Hello h = (Hello)c.newInstance();
		h.say();
	}
}

package instrumentationDemo;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
 
public class SleepingClassFileTransformer implements ClassFileTransformer {
 
    public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined,
        ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
 
        byte[] byteCode = classfileBuffer;
 
        if (className.equals("instrumentationDemo/Sleeping")) {
 
            try {
                ClassPool cp = ClassPool.getDefault();
                CtClass cc = cp.get("instrumentationDemo.Sleeping");
                CtMethod m = cc.getDeclaredMethod("randomSleep");
                m.addLocalVariable("elapsedTime", CtClass.longType);
                m.insertBefore("elapsedTime = System.currentTimeMillis();");
                m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
                        + "System.out.println(\"Method Executed in ms: \" + elapsedTime);}");
                byteCode = cc.toBytecode();
                cc.detach();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
 
        return byteCode;
    }
}
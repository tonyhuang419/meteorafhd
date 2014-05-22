package instrumentationDemo;

import java.lang.instrument.Instrumentation;

public class SleepingAgent {
 
    public static void premain(String agentArgs, Instrumentation inst) {
         
        // registers the transformer
        inst.addTransformer(new SleepingClassFileTransformer());
    }
}

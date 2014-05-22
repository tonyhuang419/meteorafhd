package instrumentationDemo;

import org.junit.Test;

public class AgentTest {
 
	//JUNIT
	//vm args: -javaagent:D:\dev\project\test\target\test-0.0.1.jar
    @Test
    public void shouldInstantiateSleepingInstance() throws InterruptedException {
 
        Sleeping sleeping = new Sleeping();
        sleeping.randomSleep();
    }
}

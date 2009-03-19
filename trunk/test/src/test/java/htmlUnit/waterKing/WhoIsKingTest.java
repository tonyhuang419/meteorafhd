package htmlUnit.waterKing;

import org.junit.Test;

public class WhoIsKingTest {
	
	@Test 
	public void testUserThread(){
		new UserThread(new User("非法_用户","happyamiga",100)).getT().start();
		new UserThread(new User("MS佳菲猫","lzhouwen",80)).getT().start();
	}
}

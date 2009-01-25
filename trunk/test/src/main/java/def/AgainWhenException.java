package def;

public class AgainWhenException {


	public void testA() throws Exception{
		int maxRetryCount = 3;
		int retryCount = 0;
		while(true){
			try{
				int i = 1/0;
			}
			catch( Exception e){
				System.out.println(retryCount);
				if( retryCount++ >= maxRetryCount){
					throw e;
				}
				else{
					e.printStackTrace();
					continue;
				}
			}
		}
	}

	public static void main ( String[] args ) throws Exception{
		AgainWhenException awe  = new AgainWhenException();
		awe.testA();
	}
}

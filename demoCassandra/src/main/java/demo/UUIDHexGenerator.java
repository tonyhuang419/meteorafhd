package demo;
import java.net.InetAddress;


public class UUIDHexGenerator {
	private static final int IP;  
	private static UUIDHexGenerator instance = null;  
	private static short counter = (short) 0;  
	private static final int JVM = (int) ( System.currentTimeMillis() >>> 8 );  
	private String sep = "";  
	static {  
		int ipadd;  
		try {  
			ipadd = toInt( InetAddress.getLocalHost().getAddress() );  
		}  
		catch (Exception e) {  
			ipadd = 0;  
		}  
		IP = ipadd;  
	}  
	protected String format(int intval) {  
		String formatted = Integer.toHexString(intval);  
		StringBuffer buf = new StringBuffer("00000000");  
		buf.replace( 8-formatted.length(), 8, formatted );  
		return buf.toString();  
	}  

	protected String format(short shortval) {  
		String formatted = Integer.toHexString(shortval);  
		StringBuffer buf = new StringBuffer("0000");  
		buf.replace( 4-formatted.length(), 4, formatted );  
		return buf.toString();  
	}  

	public static int toInt( byte[] bytes ) {  
		int result = 0;  
		for (int i=0; i<4; i++) {  
			result = ( result << 8 ) - Byte.MIN_VALUE + (int) bytes[i];  
		}  
		return result;  
	}  
	protected int getJVM() {  
		return JVM;  
	}  


	protected short getCount() {  
		synchronized(UUIDHexGenerator.class) {  
			if (counter<0) counter=0;  
			return counter++;  
		}  
	}  
	/** 
	 * @author zhangguang 
	 *  
	 * @return UUID. 一組 UUID，係由一串 16 位元組（亦稱 16 字節，或 128 位元）的16進位數字所構成 
	 */  
	public String generate() {  
		return new StringBuffer(36)  
		.append( format( getIP() ) ).append(sep)  
		.append( format( getJVM() ) ).append(sep)  
		.append( format( getHiTime() ) ).append(sep)  
		.append( format( getLoTime() ) ).append(sep)  
		.append( format( getCount() ) )  
		.toString();  
	}  





	protected int getIP() {  
		return IP;  
	}  

	protected short getHiTime() {  
		return (short) ( System.currentTimeMillis() >>> 32 );  
	}  
	protected int getLoTime() {  
		return (int) System.currentTimeMillis();  
	}  

	public static UUIDHexGenerator getInstance()  
	{  
		if (instance == null)  
			instance = new UUIDHexGenerator();  
		return instance;  
	}  




	public static void main( String[] args ) throws Exception {  

		for ( int i=0; i<10; i++) {  
			String id = getInstance().generate();  
			System.out.println(id);  
			String id2 = getInstance().generate();  
			System.out.println(id2);  
		}  

	}  
}

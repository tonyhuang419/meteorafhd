package breakthroughOneInstance;

import java.io.InputStream;

public class OnlyOneLoader extends   ClassLoader {
	public IOnlyOne createNewOne() throws Exception{
		InputStream is = getClass().getResourceAsStream("OnlyOne.class");
		byte[] b = new byte[is.available()];
		is.read(b);
		Class clz = defineClass(null,b, 0, b.length);
		Object o = clz.newInstance();
		return (IOnlyOne) o;
	}
}

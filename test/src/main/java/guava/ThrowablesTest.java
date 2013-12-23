package guava;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Test;

import com.google.common.base.Throwables;

public class ThrowablesTest {
	@Test
	public void testThrowables(){
		try {
			throw new Exception();
		} catch (Throwable t) {
			String ss = Throwables.getStackTraceAsString(t);
			System.out.println("ss:"+ss);
			Throwables.propagate(t);
		}
	}

	@Test
	public void call() throws IOException {
		try {
			throw new IOException();
		} catch (Throwable t) {
			Throwables.propagateIfInstanceOf(t, IOException.class);
			throw Throwables.propagate(t);
		}
	}
	@Test
	public Void testPropagateIfPossible() throws Exception {
		try {
			throw new Exception();
		} catch (Throwable t) {
			Throwables.propagateIfPossible(t, Exception.class);
			Throwables.propagate(t);
		}

		return null;
	}

}

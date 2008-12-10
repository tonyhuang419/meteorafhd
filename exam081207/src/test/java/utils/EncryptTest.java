package utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class EncryptTest {
	
	
	@Test
	public void encrypt(){
		System.out.println(DigestUtils.md5Hex("aaaaa"));
		System.out.println(DigestUtils.md5Hex("aaaaa"));
	}
}

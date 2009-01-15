package com.exam.authentication;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.commons.codec.digest.DigestUtils;

public class XMd5PasswordEncoder  extends Md5PasswordEncoder {
	
	@Override
    public String encodePassword(String rawPass, Object salt)
    {
//        String saltedPass = mergePasswordAndSalt(rawPass, salt, false);
//        MessageDigest messageDigest = getMessageDigest();
//        byte digest[] = messageDigest.digest(saltedPass.getBytes());
//        if(getEncodeHashAsBase64())
//            return new String(Base64.encodeBase64(digest));
//        else
//            return new String(Hex.encodeHex(digest));
		return DigestUtils.md5Hex(rawPass);
    }

}

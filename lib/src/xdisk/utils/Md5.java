package xdisk.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {

	public static String md5(String s) throws NoSuchAlgorithmException{
		String md5="";
		MessageDigest m;
		m = MessageDigest.getInstance("MD5");
		m.update(s.getBytes(),0,s.length());
		md5 = new BigInteger(1,m.digest()).toString(16);
		//System.out.println("MD5: "+md5);
		return md5;
	}
}

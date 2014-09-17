package com.hadrion.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class DigestUtil {
	
	public static String md5(String valor){
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return (new HexBinaryAdapter()).marshal(md5.digest(valor.getBytes()));
	}
}

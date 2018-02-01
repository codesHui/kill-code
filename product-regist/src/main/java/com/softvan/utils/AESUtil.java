package com.softvan.utils;

import java.nio.charset.Charset;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.StringUtils;
/**
 * 对称加密工具类。
 */
public final class AESUtil {
	public final static String algorithm = "AES",charsetName = "UTF-8";
	/**
	 * 创建密钥。
	 * @param password
	 * 密码。
	 * @return
	 * 密钥对象。
	 */
	private  static Key creatKey(String password){
			if(StringUtils.isEmpty(password)) throw new RuntimeException("password  密码为空！");
			
			if(password.length() < 16)  
				return creatKey(MD5Util.MD5(password)); 
			
			password = password.substring(0, 16);
			
			return new SecretKeySpec(password.getBytes(Charset.forName(charsetName)), algorithm);
	}
	/**
	 * 加密。
	 * @param content
	 * 需要加密的内容。
	 * @param password
	 * 密码。
	 * @return
	 * 加密后的字节数组。
	 */
	public  static byte[] encrypt(String content,String password){
		if(StringUtils.isEmpty(content) || StringUtils.isEmpty(password)) return null;
		try {
			Cipher cipher = Cipher.getInstance(algorithm);//创建密码器。
			cipher.init(Cipher.ENCRYPT_MODE, creatKey(password));//初始化。
			return  cipher.doFinal(content.getBytes(Charset.forName(charsetName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  解密。
	 * @param encrypts
	 * 密文字节数组。
	 * @param password
	 * 密码。
	 * @return
	 * 解密后的明文。
	 */
	public static String decrypt(byte[] encrypts, String password){
		if(encrypts == null || encrypts.length == 0 || StringUtils.isEmpty(password)) return null;
		try {
			Cipher cipher = Cipher.getInstance(algorithm);//创建密码器。
			cipher.init(Cipher.DECRYPT_MODE, creatKey(password));//初始化。
			
			return  new String(cipher.doFinal(encrypts), Charset.forName(charsetName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
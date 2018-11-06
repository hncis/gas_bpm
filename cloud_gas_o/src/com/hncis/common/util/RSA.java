package com.hncis.common.util;

import java.security.PrivateKey;

import javax.crypto.Cipher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RSA {
    private transient static Log logger = LogFactory.getLog(RSA.class.getClass());
    
 public static String decryptRsa(PrivateKey privateKey, String securedValue) {
	 String decryptedValue = "";
	 try{
		Cipher cipher = Cipher.getInstance("RSA");
	   /**
		* 암호화 된 값은 byte 배열이다.
		* 이를 문자열 폼으로 전송하기 위해 16진 문자열(hex)로 변경한다. 
		* 서버측에서도 값을 받을 때 hex 문자열을 받아서 이를 다시 byte 배열로 바꾼 뒤에 복호화 과정을 수행한다.
		*/
		byte[] encryptedBytes = hexToByteArray(securedValue);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
	 }catch(Exception e)
	 {
//		 logger.info("decryptRsa Exception Error : "+e.getMessage());
		 logger.error("messege", e);
	 }
		return decryptedValue;
} 
/** 
 * 16진 문자열을 byte 배열로 변환한다. 
 */
 public static byte[] hexToByteArray(String hex) {
	if (hex == null || hex.length() % 2 != 0) {
		return new byte[]{};
	}
 
	byte[] bytes = new byte[hex.length() / 2];
	for (int i = 0; i < hex.length(); i += 2) {
		byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
		bytes[(int) Math.floor(i / 2)] = value;
	}
	return bytes;
}

 
}

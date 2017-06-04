package AES;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
 
public class AES_Example {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		try { 
			String en[][] = new String[10][];
			String de[][] = new String[10][];
			double timestamp [] = new double[10];
			
			for(int i=0; i<10; i++) {
				long rStart = System.currentTimeMillis();
				en[i] = new String[i+1];
				de[i] = new String[i+1];
				
				for(int j=0; j<=i; j++) {
					String originalText = "20164149" + i + "이동원";
					String key = "key";
					en[i][j] = Encrypt(originalText, key);
					de[i][j] = Decrypt(en[i][j], key);
				}
				long rEnd = System.currentTimeMillis();
				timestamp[i] = ((rEnd - rStart) / 1000.0);
			}
			
			System.out.println("평문: ");
			for(int i=0; i<en.length; i++) {
				for(int j=0; j<=i; j++) {
					System.out.println( (i+1) + "회-" + (j+1) + " " + de[i][j]);
				}
			}
			System.out.println();
			
			System.out.println("암호문: ");
			for(int i=0; i<en.length; i++) {
				for(int j=0; j<=i; j++) {
					System.out.println( (i+1) + "회-" + (j+1) + " " + en[i][j]);
				}
			}
			System.out.println();
			
			System.out.println("실행시간: ");
			for(int i=0; i<en.length; i++) {
				System.out.println( (i+1) + "회 " + timestamp[i]);
			}
			System.out.println();
			
        } catch (Exception ex) {
                   System.out.println("오류=>\n" + ex);
        }
		
		long end = System.currentTimeMillis();

		System.out.println("총 실행 시간 : " + ( end - start )/1000.0);
       
	}
	
	public static String Decrypt(String text, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyBytes= new byte[16];
		byte[] b= key.getBytes("UTF-8");
		int len= b.length;
		if (len > keyBytes.length) len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
 
		BASE64Decoder decoder = new BASE64Decoder();
		byte [] results = cipher.doFinal(decoder.decodeBuffer(text));
		return new String(results,"UTF-8");
	}
 
	public static String Encrypt(String text, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyBytes= new byte[16];
		byte[] b= key.getBytes("UTF-8");
		int len= b.length;
		if (len > keyBytes.length) len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);
		 
		byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(results);
	}
}
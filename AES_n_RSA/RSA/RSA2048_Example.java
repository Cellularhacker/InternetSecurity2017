package RSA;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
 
import javax.crypto.Cipher;
 
public class RSA2048_Example {
    public static void main(String[] args) {
    	long start = System.currentTimeMillis();
    	
        try {
        	String en[][] = new String[10][];
			String de[][] = new String[10][];
			double timestamp [] = new double[10];
			
			//공개키 및 개인키 생성
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            Key publicKey = keyPair.getPublic(); // 공개키
            Key privateKey = keyPair.getPrivate(); // 개인키
            
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
			
            
            // Round 진행
			for(int i=0; i<10; i++) {
				long rStart = System.currentTimeMillis();
				en[i] = new String[i+1];
				de[i] = new String[i+1];
				
				for(int j=0; j<=i; j++) {
					String originalText = "20164149" + i + "이동원";
					
					// 암호화(Encryption)
		            Cipher cipher = Cipher.getInstance("RSA");
		            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		            byte[] arrCipherData = cipher.doFinal(originalText.getBytes()); // 암호화된 데이터(byte 배열)
		            String strCipher = new String(arrCipherData);
		           
					en[i][j] = strCipher;
					
					
					// 복호화(Decryption)
		            cipher.init(Cipher.DECRYPT_MODE, privateKey);
		            byte[] arrData = cipher.doFinal(arrCipherData);
		            String strResult = new String(arrData);

					de[i][j] = strResult;
				}
				long rEnd = System.currentTimeMillis();
				timestamp[i] = ((rEnd - rStart) / 1000.0);
			}
			
			// Printing Results
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
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        long end = System.currentTimeMillis();

		System.out.println("총 실행 시간 : " + ( end - start )/1000.0);
    }
}
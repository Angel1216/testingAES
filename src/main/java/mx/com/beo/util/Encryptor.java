package mx.com.beo.util;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encryptor {
    public static String encrypt(String key, String initVector, String value) {
        try {
        	
        	byte[] byteskey = key.getBytes(StandardCharsets.US_ASCII);
    		Rfc2898DeriveBytesAES pdb = new Rfc2898DeriveBytesAES(key, byteskey);
    	
    		byte[] bytesIv = initVector.getBytes(StandardCharsets.US_ASCII);
    		Rfc2898DeriveBytesAES pdbIv = new Rfc2898DeriveBytesAES(initVector, bytesIv);
    		
    		
//            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            
    		IvParameterSpec iv = new IvParameterSpec(pdbIv.getBytes(16));
            SecretKeySpec skeySpec = new SecretKeySpec(pdb.getBytes(16), "AES");
            

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

//    public static void main(String[] args) {
//        String key = "Bar12345Bar12345"; // 128 bit key  .- Bar12345Bar12345
//        String initVector = "RandomInitVector"; // 16 bytes IV  .- RandomInitVector
//
////        System.out.println(decrypt(key, initVector,
////                encrypt(key, initVector, "PlanetaTierraAML")));
//        
//        encrypt(key, initVector, "PlanetaTierraAML");
//    }
}

package com.pg.springb.front.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.service.FileUploadService;

//import com.esotericsoftware.minlog.Log;
/*** Encryption and Decryption of String data; PBE(Password Based Encryption and Decryption)
* @author Sanjiv
*/
public class CryptoUtil 
{
	private static final Logger log = LoggerFactory.getLogger(CryptoUtil.class);

    static Cipher ecipher;
    static Cipher dcipher;
    private static String secretKey="FRONTIER_KEY";  
    // 8-byte Salt
    static byte[] salt = {
        (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };
    // Iteration count
    static int iterationCount = 19;
    
    public CryptoUtil() {

    }

    /**
     *
     * @param secretKey Key used to encrypt data
     * @param plainText Text input to be encrypted
     * @return Returns encrypted text
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.io.UnsupportedEncodingException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     *
     */
    public static String encrypt( String plainText){
    	 String encStr = "h56Y75tUD";//Default password
    	  try {
    		//Key generation for enc and desc
    	        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
    	        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
    	        // Prepare the parameter to the ciphers
    	        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

    	        //Enc process
    	        ecipher = Cipher.getInstance(key.getAlgorithm());
    	        ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
    	        String charSet = "UTF-8";
    	        byte[] in = plainText.getBytes(charSet);
    	        byte[] out = ecipher.doFinal(in);
    	        encStr = new String(Base64.getEncoder().encode(out));
    	  }catch(Exception ex){
    		  log.error(ex.getMessage());
    	  }
        
        return encStr;
    }

    /**
     * @param secretKey Key used to decrypt data
     * @param encryptedText encrypted text input to decrypt
     * @return Returns plain text after decryption
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.io.UnsupportedEncodingException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     */
    public String decrypt(String encryptedText)
            throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            UnsupportedEncodingException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        //Key generation for enc and desc
        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
        // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
        //Decryption process; same key will be used for decr
        dcipher = Cipher.getInstance(key.getAlgorithm());
        dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        byte[] enc = Base64.getDecoder().decode(encryptedText);
        byte[] utf8 = dcipher.doFinal(enc);
        String charSet = "UTF-8";
        String plainStr = new String(utf8, charSet);
        return plainStr;
    }    
    public static void main(String[] args) throws Exception {
        CryptoUtil cryptoUtil=new CryptoUtil();        
        String plain="CAXTON123";
        String enc=cryptoUtil.encrypt(plain);
        System.out.println("Original text: "+plain);
        System.out.println("Encrypted text: "+enc);
        String plainAfter=cryptoUtil.decrypt(enc);
        System.out.println("Original text after decryption: "+plainAfter);
    }
}
package com.jecp.sysmanage.util;


import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtil {
	public static final String KEY_ALGORITHM = "RSA";  
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";  
  
    private static final String PUBLIC_KEY = "RSAPublicKey";  
    private static final String PRIVATE_KEY = "RSAPrivateKey";  
    //I6000许可KEY
    private static final String pub="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKw1fu5cSRGtHf4sSomHS3QOGWyhVB6InYT1aZ/bzW81AK2O4doS1459/ApIcEx3vRMDi3ffhwiqEAzyGb9lhNUCAwEAAQ==";
    private static final String pri="MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEArDV+7lxJEa0d/ixKiYdLdA4ZbKFUHoidhPVpn9vNbzUArY7h2hLXjn38CkhwTHe9EwOLd9+HCKoQDPIZv2WE1QIDAQABAkBKoCQRRwyjDFIjAy6Jyod4E2QRHxhE4w7fvzm2fJnZxnashS7aG/gkW5M5brvh8H/6QlOjozU77kycR6UsudoBAiEA70Tm/1oGZHAifuELWVYVxtuZdg5kcs0Xy7prEkDmwC0CIQC4QCpzTw8Gg5HwEEUeT9XB6O9d51Pp+4DiAnGrujaYSQIhAJpNPQr/649qIVThnIgIVBLiG51euriuOuJPXi1vXWAhAiA8H+Q6YJAcpg6Xche7/n1Ym7SQxg4iCopSNk31pfxu0QIhAJFVfSbTYBdXm4DNMCAOc2jatKzqLjA0ZfECjcRixO9F";
    public static byte[] decryptBASE64(String privateKey){  
        byte[] output = null;  
        output = Base64.getDecoder().decode(privateKey);       
        return output;    
    }  
      
      
    public static String encryptBASE64( byte[] keyBytes){  
         String s = Base64.getEncoder().encodeToString(keyBytes);  
         return s;  
    }  
      
    /** 
     * 用私钥对信息生成数字签名 
     *  
     * @param data 
     *            加密数据 
     * @param privateKey 
     *            私钥 
     *  
     * @return 
     * @throws Exception 
     */  
    private static String sign(byte[] data, String privateKey) throws Exception {  
        // 解密由base64编码的私钥  
        byte[] keyBytes = decryptBASE64(privateKey);  
  
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
  
        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initSign(priKey);  
        signature.update(data);  
          
        return encryptBASE64(signature.sign());  
    }  
  
    /** 
     * 校验数字签名 
     *  
     * @param data 加密数据 
     * @param publicKey  公钥 
     * @param sign 数字签名 
     * @return 校验成功返回true 失败返回false 
     * @throws Exception 
     *  
     */  
    private static boolean verify(byte[] data, String publicKey, String sign)  
            throws Exception {  
  
        // 解密由base64编码的公钥  
        byte[] keyBytes = decryptBASE64(publicKey);  
  
        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
  
        // 取公钥匙对象  
        PublicKey pubKey = keyFactory.generatePublic(keySpec);  
  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initVerify(pubKey);  
        signature.update(data);  
  
        // 验证签名是否正常  
        return signature.verify(decryptBASE64(sign));  
    }  
  
    /** 
     * 解密<br> 
     * 用私钥解密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    private static byte[] decryptByPrivateKey(byte[] data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);  
  
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
  
        return cipher.doFinal(data);  
    }  
  
    /** 
     * 解密<br> 
     * 用公钥解密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    private static byte[] decryptByPublicKey(byte[] data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);  
  
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicKey = keyFactory.generatePublic(x509KeySpec);  
  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);  
  
        return cipher.doFinal(data);  
    }  
  
    /** 
     * 加密<br> 
     * 用公钥加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    private static byte[] encryptByPublicKey(byte[] data, String key)  
            throws Exception {  
        // 对公钥解密  
        byte[] keyBytes = decryptBASE64(key);  
  
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicKey = keyFactory.generatePublic(x509KeySpec);  
  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
  
        return cipher.doFinal(data);  
    }  
  
    /** 
     * 加密<br> 
     * 用私钥加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    private static byte[] encryptByPrivateKey(byte[] data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);  
  
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
  
        return cipher.doFinal(data);  
    }  
  
    /** 
     * 取得私钥 
     *  
     * @param keyMap 
     * @return 
     * @throws Exception 
     */  
    private static String getPrivateKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PRIVATE_KEY);  
  
        return encryptBASE64(key.getEncoded());  
    }  
  
    /** 
     * 取得公钥 
     *  
     * @param keyMap 
     * @return 
     * @throws Exception 
     */  
    private static String getPublicKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PUBLIC_KEY);  
  
        return encryptBASE64(key.getEncoded());  
    }  
  
    /** 
     * 初始化密钥 
     *  
     * @return 
     * @throws Exception 
     */  
    private static Map<String, Object> initKey() throws Exception {  
        KeyPairGenerator keyPairGen = KeyPairGenerator  
                .getInstance(KEY_ALGORITHM);  
        keyPairGen.initialize(512);  
  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
  
        // 公钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
  
        // 私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
  
        Map<String, Object> keyMap = new HashMap<String, Object>(2);  
  
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        return keyMap;  
    }  
//    /**
//     * 加密
//     * @param en
//     * @return
//     * @throws Exception
//     */
//    public static String encode(String en) throws Exception{
//    	byte[] data = en.getBytes(); 
//    	byte[] encodedData = encryptByPublicKey(data, publicKey);      	
//    	return Base64.getEncoder().encodeToString(encodedData);
//    }
//    /**
//     * 解密
//     * @param de
//     * @return
//     * @throws Exception
//     */
//    public static String decode(String de) throws Exception{
//    	byte[] decodedData = decryptByPrivateKey(decryptBASE64(de),privateKey); 
//    	return new String(decodedData);
//    }
    /**
     * 加密
     * @param en
     * @return
     * @throws Exception
     */
    public static String encode1(String en) throws Exception{
    	byte[] data = en.getBytes(); 
    	byte[] encodedData = encryptByPublicKey(data, pub);      	
    	return Base64.getEncoder().encodeToString(encodedData);
    }
    /**
     * 解密
     * @param de
     * @return
     * @throws Exception
     */
    public static String decode1(String de) throws Exception{    	
    	byte[] decodedData = decryptByPrivateKey(decryptBASE64(de),pri); 
    	return new String(decodedData);
    }
    public static void main(String[] args) throws Exception {  
    	System.out.println(RSAUtil.encode1("Abc@1234"));
    	System.out.println(RSAUtil.decode1("JARUM2Ev5Hti9qj4/oY3UMowEei2s+l6RFj8AEqIrVBXpToQbiI9QEczsRUjK3aXoLNUwtNTRpdud/KuCrdnDw=="));
    } 
}

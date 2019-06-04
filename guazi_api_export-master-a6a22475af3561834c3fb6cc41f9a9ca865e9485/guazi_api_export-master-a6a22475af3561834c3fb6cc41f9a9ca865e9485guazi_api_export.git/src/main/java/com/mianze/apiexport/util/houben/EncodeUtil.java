package com.mianze.apiexport.util.houben;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/*import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;*/

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
public class EncodeUtil {
    /** 
     * ECB����,��ҪIV 
     * @param key ��Կ
     * @param data ����
     * @return Base64���������
     * @throws Exception 
     */  
    public static byte[] des3EncodeECB(byte[] key, byte[] data)
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, deskey);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
    /** 
     * ECB����,��ҪIV 
     * @param key ��Կ
     * @param data Base64���������
     * @return ����
     * @throws Exception 
     */  
    public static byte[] ees3DecodeECB(byte[] key, byte[] data)
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");  
        cipher.init(Cipher.DECRYPT_MODE, deskey);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
    /** 
     * CBC���� 
     * @param key ��Կ 
     * @param keyiv IV 
     * @param data ���� 
     * @return Base64��������� 
     * @throws Exception 
     */  
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(keyiv);  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
    /** 
     * CBC���� 
     * @param key ��Կ 
     * @param keyiv IV 
     * @param data Base64��������� 
     * @return ���� 
     * @throws Exception 
     */  
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(keyiv);  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }
}

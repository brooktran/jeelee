package com.test.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DES {
    
    /**
     * 加密String明文输入,经过BASE64编码String密文输出
     * 
     * @param text,keystr,ivstr
     * @return
     */
    public static String encrypt(String text, String keystr,String ivstr) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes= new byte[16];
        byte[] ivBytes = new byte[16];
        
        byte[] b= keystr.getBytes("UTF-8");
        byte[] v = ivstr.getBytes("UTF-8");
        
        
        int len= b.length; 
        int len2 = v.length;
        
        if (len > keyBytes.length) len = keyBytes.length;
        if (len2> ivBytes.length) len2 = ivBytes.length;
        
        System.arraycopy(b, 0, keyBytes, 0, len);
        System.arraycopy(v,0,ivBytes,0,len2);
        
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);
        BASE64Encoder decoder = new BASE64Encoder();
        byte [] results = cipher.doFinal(text.getBytes("UTF8"));
        return decoder.encode(results);
    }
    
    /**
     * 解密 以BASE64形式String密文输入,String明文输出
     * 
     * @param text,keystr,ivstr
     * @return
     */
    public static String decrypt(String text, String keystr,String ivstr) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes= new byte[16];
        byte[] ivBytes = new byte[16];
        
        byte[] b= keystr.getBytes("UTF-8");
        byte[] v = ivstr.getBytes("UTF-8");
        
        int len= b.length; 
        int len2 = v.length;
        
        if (len > keyBytes.length) len = keyBytes.length;
        if (len2> ivBytes.length) len2 = ivBytes.length;
        
        System.arraycopy(b, 0, keyBytes, 0, len);
        System.arraycopy(v,0,ivBytes,0,len2);
        
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
        BASE64Decoder decoder = new BASE64Decoder();
        byte [] results = cipher.doFinal(decoder.decodeBuffer(text));
        return new String(results,"UTF-8");
    }
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String text = "Account =aqtest201,Password =test4321,NewPassword =test4321;";
        String key = "ITMSYNlCKMTRUDFJ";
        String ivStr = "12345678";
        String strenc = DES.encrypt(text,key,ivStr);//加密
        System.out.println("strenc is :" + strenc);
        String strDes = DES.decrypt(strenc,key,ivStr);//解密
        System.out.println("strDes is :" + strDes);
        strenc = java.net.URLEncoder.encode(strenc);
        System.out.println("URLEncoder.encode(data) :" +strenc);
        strenc = java.net.URLDecoder.decode(strenc);
        System.out.println("URLDecoder.decoder(data) :" +strenc);

    }

}

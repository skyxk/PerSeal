package com.clt.perseal.Util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SimpleDesede {
    // ��������㷨
    private static final String Algorithm = "DESede";
    // 加密
    public static byte[] encryptMode(byte[] src,String sPwd) {
    	int iLen = src.length;
    	if((iLen % 8) != 0)
    		iLen = (8 - src.length % 8) + iLen;
    	byte[] bSrc = new byte[iLen];
    	System.arraycopy(src, 0, bSrc, 0, src.length);
    	for(int i = src.length;i<iLen;i++)
    		bSrc[i] = 0;
        try {// ������Կ
            SecretKey deskey = new SecretKeySpec(build3Deskey(sPwd), Algorithm);
            // ʵ����cipher
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, deskey);      
            return cipher.doFinal(bSrc);
        } catch (Exception e) {
            return null;
        }

    }

    // 解密
    public static byte[] decryptMode(byte[] src,String sPwd) {
        SecretKey deskey;
        try {
            deskey = new SecretKeySpec(build3Deskey(sPwd),Algorithm);
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            byte[] b = cipher.doFinal(src);
            int iLen = b.length;
            for(int i = b.length -1;i>0;i--)
            {
            	if(b[i] == 0)
            		iLen--;
            	else
            		break;
            }
            byte[] c = new byte[iLen];
            System.arraycopy(b, 0, c, 0, iLen);
            return c;
        } catch (Exception e) {
            return null;
        }
    }



    // �����ַ���������Կ24λ���ֽ�����
    public static byte[] build3Deskey(String keyStr) throws Exception {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes();
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }
    
//

    // 3des加密 base64
    public static String encryptToDB(String sealItem) {
        String item ="";
        return Base64Utils.ESSGetBase64Encode(encryptMode(sealItem.getBytes(),"123456"));
    }

    // 3des解密 base64
    public static String decryptFromDB(String sealItem) {
        String item ="";

        item = new String(decryptMode(Base64Utils.ESSGetBase64Decode(sealItem), "123456"));
        return item;
    }
}

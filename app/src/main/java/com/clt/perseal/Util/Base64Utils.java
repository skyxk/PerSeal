package com.clt.perseal.Util;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by clt_abc on 2017/5/31.
 */

public class Base64Utils {
    // 加密
    public static String getBase64(String str) {
        String result = null;
        if( str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 解密
    public static String getFromBase64(String str) {
        String result = null;
        if (str != null) {
            try {
                result = new String(Base64.decode(str, Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 将传入数据BASE64编码
     * @param bMsg  ?  编码的数 ?
     * @return String
     */
    public static String ESSGetBase64Encode(byte[] bMsg) {
        BASE64Encoder ben = new BASE64Encoder();
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        String sBase64File = ben.encode(bMsg);
        Matcher m = p.matcher(sBase64File);
        sBase64File = m.replaceAll("");
        return sBase64File;

    }

    /**
     * 将传入数据BASE64解码
     * @param sEncMsg  ?  解码码的数据
     * @return byte[]
     */
    public static byte[] ESSGetBase64Decode(String sEncMsg) {
        byte[] date= null;
        try
        {
            BASE64Decoder bdr = new BASE64Decoder();
            date = bdr.decodeBuffer(sEncMsg);
            return date;
        }catch(IOException e)
        {
//            throw(new MuticaCryptException(e.getMessage()));
        }
        return date;
    }

}

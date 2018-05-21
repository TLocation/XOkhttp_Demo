package com.loction.xokhttp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lenovo on 2018/5/10.
 */

public class MD5Utils {
    public static String encrypt(String s){
        char hexDigits[] = {'0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final byte[] encrypt2(String string) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(string.getBytes("UTF-8"));
            return digest;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String getMD5(String content) {
        String s = makeMD5(content);
        String s1 = null;
        if (s != null) {
            s1 = s.substring(0, 16);
        }
        String s2 = null;
        if (s != null) {
            s2 = s.substring(16, 32);
        }
        s1 = makeMD5(s1);
        s2 = makeMD5(s2);
        s = s1 + s2;
        for (int i = 0; i < 100; i++) {
            s = makeMD5(s);
        }
        return s;
    }


    private static String makeMD5(String content) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(content.getBytes());
            return getHashString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getHashString(MessageDigest messageDigest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : messageDigest.digest()) {
            builder.append(Integer.toHexString(  ( b >> 4 ) & 0xf )  );
            builder.append(Integer.toHexString(  b & 0xf )           );
        }
        return builder.toString();
    }
}

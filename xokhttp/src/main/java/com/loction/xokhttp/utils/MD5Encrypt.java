package com.loction.xokhttp.utils;

import android.util.Log;

/**
 * Created by lenovo on 2018/5/13.
 */

public class MD5Encrypt {
    private static final String TAG = MD5Encrypt.class.getSimpleName();
    private static  char i1;

    public static  String encopt(String data, String key) {
        String builder = new String();
        key = MD5Utils.encrypt(key);
        Log.e(TAG, "key===》" + key);
        int index = 0;
        int dataLength = data.length();
        int keyLength = key.length();
        String s2 = new String();
        Log.e(TAG, "keyLength===》" + keyLength);
        for (int i = 0; i < dataLength; i++) {
            if (index == keyLength) {
                index = 0;
            }

            builder+=key.charAt(index);
            Log.e(TAG, "append===》" + builder);
            index++;
        }

        Log.e(TAG, "str===》" + builder.toString());
        String str = builder.toString();
        for (int i = 0; i < dataLength; i++) {
            int integer = Integer.valueOf(data.charAt(i));
            int integer1 = Integer.valueOf(str.charAt(i)) % 256;
            i1 = (char) (integer + integer1);
            Log.e(TAG, "integer===》" + integer);
            Log.e(TAG, "integer1===》" + integer1);
            Log.e(TAG, "integer2===》" + i1);
            Log.e(TAG, "datachar===》" + data.charAt(i));
            Log.e(TAG, "strchar===》" + str.charAt(i));
            Log.e(TAG, "jiajai===》" + i1);
            s2 += i1;
            Log.e(TAG, "strin===》" + s2);

        }

        String rouse = "";
        rouse = Base64Utils.getBase64(s2);
        Log.e(TAG, "rose===>" + rouse);
        return rouse;

    }

    public static  String ss(String data, String key) {
        String builder = new String();
        String encod = new String();
        key = MD5Utils.encrypt(key);
        Log.e(TAG, "Key====>>>" + key);
        int index = 0;

        Log.e(TAG, "解密data===ss>" + data);
        String getData = Base64Utils.getFromBase64(data);
        Log.e(TAG, "解密data===ss>" + getData);
        int dataLength = getData.length();
        Log.e(TAG, "解密data===>" + getData.length());

        int keyLength = key.length();
        for (int i = 0; i < dataLength; i++) {

            if (index == keyLength) {
                index = 0;
            }
            Log.e(TAG, "key.substring===>" + key.charAt(index));
            builder+=key.charAt(index);
            Log.e(TAG, "builder===>" + builder.toString());
            index++;

        }
        String string = builder.toString();
        Log.e(TAG, "ss: ====>>"+string );
        for (int i = 0; i < dataLength; i++) {
            if (Integer.valueOf(getData.charAt(i)) < Integer.valueOf(string.charAt(i))) {
                int c = Integer.valueOf(getData.charAt(i)) + 256;
                int c1 = Integer.valueOf(string.charAt(i));
                char c2 = (char) (c - c1);
                Log.e(TAG, "c===>" + c);
                Log.e(TAG, "c1===>" + c1);
                Log.e(TAG, "c2===>" + c2);
                encod += c2;
                Log.e(TAG, "encod.append===c>" + (encod += c2));
            } else {
                int s = Integer.valueOf(getData.charAt(i));
                int s1 = Integer.valueOf(string.charAt(i));
                char s2 = (char) (s - s1);
                Log.e(TAG, "s===>" + s);
                Log.e(TAG, "s1===>" + s1);
                Log.e(TAG, "s2===>" + s2);
                encod += s2;
                Log.e(TAG, "encod.append===s>" + encod.length());
                Log.e(TAG, "encod.append===s>" + encod.toString());
            }
        }
        Log.e(TAG, "encod.append===s>" + encod.toString());
        return encod.toString();

    }
//

}

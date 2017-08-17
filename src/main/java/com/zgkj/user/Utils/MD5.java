package com.zgkj.user.Utils;

import com.zgkj.user.pojo.User;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 韩明泽 on 2017/8/4.
 * md5工具类
 */
public class MD5 {
    /**利用MD5进行加密*/
    public static  String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5= MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    public static boolean checkpassword(String newpasswd, String oldpasswd, User user) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        if(EncoderByMd5(newpasswd).equals(oldpasswd)&&user!=null){
            return true;
        }
        else {
            return false;
        }
    }
}

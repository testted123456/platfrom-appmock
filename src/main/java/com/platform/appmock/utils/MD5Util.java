package com.platform.appmock.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {
	public static Logger logger = LoggerFactory.getLogger(MD5Util.class);
	
	/**
	 * 
	 * @Title: toMD5
	 * @Description: TODO
	 * @param str
	 * @return md5Str
	 */
	public static String toMD5(String str) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
			return null;
		}
		md5.update(str.getBytes());
		byte[] md5Bytes = md5.digest();
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 
	 * @Title: equalsMD5
	 * @Description: TODO
	 * @param str
	 * @param md5
	 * @return result
	 */
	public static boolean equalsMD5(String str, String md5) {
		if (str.isEmpty() || md5.isEmpty()) {
			return false;
		}
		return toMD5(str).toUpperCase().equals(md5.toUpperCase());
	}
	
	/**
     * 
     * @方法名称:md5
     * @方法作用：
     * @方法条件：
     * @方法流程：
     * @作者:madman MD5工具算法类
     * @param s
     *            参数
     * @return 返回结果
     */
    public static final String md5(String s) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes("UTF-8");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}

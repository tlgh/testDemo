/**
 * 
 */
package pers.ksy.common;

import java.security.MessageDigest;

/**
 * simple introduction
 * 
 * <p>
 * detailed comment
 * 
 * @author jiaen 2015年4月16日
 * @see
 * @since 1.2
 */
public class MD5Util {
    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
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

    /*
     * public static void main(String[] args) {
     * System.out.println(MD5Util.MD5(MD5Util
     * .MD5(MD5Util.MD5("123"))));//7EF2C31A293BBB57C2AF7C08A22DEDC9
     * System.out.println(MD5Util.MD5(MD5Util.MD5(MD5Util.MD5("123456"))));//
     * CF814721358D09942B255746542AD2A4 }
     */

}

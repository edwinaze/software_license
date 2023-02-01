package javaproject.authen;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;

public class client {
    private static final String publickay = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3ukIUGAP9N6t6Q6pY5WH8Yeq3w+Gc+Xq+doIUkwspfXWfTu10bd8H/n8uzPDgn4EhL959AIwHzpIuWvSTNoBCBKprjtHqfEERGZp8UIqt7PCnBAgsZtyXnuk2Tiz6/CQlIQWQk8Ku1of07T646n+P66X6VdIVwePIc2gxmLnCGgO7UnFk3b4DYBhZrmHBzYTgqW3v4bp+t4rOo6f5jQQnnSUdi+lw96/HurvJqQe0POcvM0bOYVJJE/uzNWlQyFEKRErt9tPJHifXQ5T1e0vErgXqAvZIl0GK+RqHXm1Bnkcv5x6Dxs42tqKFB2Y9Kfx5vc2TNl1SIt3xvOWyHeVZwIDAQAB";

    /**
     * @param authencode 待解密密文
     * @return 解密后的原文(String)
     */
    public static String decodeAthuncode(String authencode) throws Exception{
        byte[] code = Base64.getDecoder().decode(authencode);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publickay));

        Key key = keyFactory.generatePublic(x509EncodedKeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(code));
    }
    /**
     * @param date 解密后原文中过期时间
     * @return 是否过期
     */
    public static boolean isExpired(String date) {
        int yy,mm,dd;
        yy = Integer.parseInt(date.substring(0,4));
        mm = Integer.parseInt(date.substring(4,6));
        dd = Integer.parseInt(date.substring(6,8));
        Calendar c = Calendar.getInstance();
        // && c.get(Calendar.MONTH)+1 >= mm && c.get(Calendar.DATE) >= dd
        if(c.get(Calendar.YEAR) > yy)
            return true;
        else if(c.get(Calendar.YEAR) == yy)
        {
            if(c.get(Calendar.MONTH)+1 > mm)
                return true;
            else if(c.get(Calendar.MONTH)+1 == mm) {
                if(c.get(Calendar.DATE) > dd)
                    return true;
            }
        }
        return false;
    }
    /**
     * @param deCodeMachineCode 解密后原文
     * @param machineCode 该机机器码
     * @return 是否有效
     */
    public static boolean isValid(String deCodeMachineCode, String machineCode)
    {
        System.out.println(deCodeMachineCode);
        System.out.println(machineCode);
        if(deCodeMachineCode.equals(machineCode))
            return false;
        return true;
    }
    /**
     * @param deCode 解密后原文
     * @return 前面的机器码
     */
    public static String getMachineCode(String deCode)
    {
        return deCode.substring(0,deCode.length()-8);
    }
    /**
     * @param deCode 解密后原文
     * @return 注册码的过期日期
     */
    public static String getDate(String deCode)
    {
        return deCode.substring(deCode.length()-8);
    }

    public static Date stringToDate(String date) throws ParseException {
        DateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd");
        Date startDate = simpleFormat.parse(date);
        return startDate;
    }

    public static Date getNow()
    {
        return new Date();
    }

    public static int getTimeDistance(Date beginDate , Date endDate ) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        long beginTime = beginCalendar.getTime().getTime();
        long endTime = endCalendar.getTime().getTime();
        int betweenDays = (int)((endTime - beginTime) / (1000 * 60 * 60 *24));//先算出两时间的毫秒数之差大于一天的天数

        endCalendar.add(Calendar.DAY_OF_MONTH, -betweenDays);//使endCalendar减去这些天数，将问题转换为两时间的毫秒数之差不足一天的情况
        endCalendar.add(Calendar.DAY_OF_MONTH, -1);//再使endCalendar减去1天
        if(beginCalendar.get(Calendar.DAY_OF_MONTH)==endCalendar.get(Calendar.DAY_OF_MONTH))//比较两日期的DAY_OF_MONTH是否相等
            return betweenDays + 1;	//相等说明确实跨天了
        else
            return betweenDays + 0;	//不相等说明确实未跨天
    }
}

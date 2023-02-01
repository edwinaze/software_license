package javaproject.authen;
import javaproject.machine.Machine;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Scanner;

public class Server {
    private static final String publickay = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3ukIUGAP9N6t6Q6pY5WH8Yeq3w+Gc+Xq+doIUkwspfXWfTu10bd8H/n8uzPDgn4EhL959AIwHzpIuWvSTNoBCBKprjtHqfEERGZp8UIqt7PCnBAgsZtyXnuk2Tiz6/CQlIQWQk8Ku1of07T646n+P66X6VdIVwePIc2gxmLnCGgO7UnFk3b4DYBhZrmHBzYTgqW3v4bp+t4rOo6f5jQQnnSUdi+lw96/HurvJqQe0POcvM0bOYVJJE/uzNWlQyFEKRErt9tPJHifXQ5T1e0vErgXqAvZIl0GK+RqHXm1Bnkcv5x6Dxs42tqKFB2Y9Kfx5vc2TNl1SIt3xvOWyHeVZwIDAQAB";
    private static final String privatekay = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDe6QhQYA/03q3pDqljlYfxh6rfD4Zz5er52ghSTCyl9dZ9O7XRt3wf+fy7M8OCfgSEv3n0AjAfOki5a9JM2gEIEqmuO0ep8QREZmnxQiq3s8KcECCxm3Jee6TZOLPr8JCUhBZCTwq7Wh/TtPrjqf4/rpfpV0hXB48hzaDGYucIaA7tScWTdvgNgGFmuYcHNhOCpbe/hun63is6jp/mNBCedJR2L6XD3r8e6u8mpB7Q85y8zRs5hUkkT+7M1aVDIUQpESu3208keJ9dDlPV7S8SuBeoC9kiXQYr5GodebUGeRy/nHoPGzja2ooUHZj0p/Hm9zZM2XVIi3fG85bId5VnAgMBAAECggEAWd3+LZVE6ZZa68Iu9UiEKZJmB47VSGVxsbA+uZsfRvtRMWUAvN9p6eRZiUlpsXLHtJMTghXdu8E+L47yFmyvjMclZGpeznsuGrtlS9A+s5gDxfJUk/YVClVz1OVg2CbAyzdjV/FQ5M6FVU859M4EhD0J3peDQR75jAnAHyshdIfqgdR95ve23yVu20/MyIoznIGrPVOTCaBgYFi7V9snws+ZsTGV6XSb53lmNqsUcE7RDFk0noBfxoCxEDY7IllCEI1FMtwMFFCSlHeah4ELHvzafpMlKKzMybiUAxm5PVriH5zK9W46LBVLlva6FTxfmDhCNkqPRW4Sll565Pi1fQKBgQDnYLeim4/7uwK66V2jeBq/BXEhbaY2mBx63xZn+FvLJ8Jpn/FU+qRcdDKoo9Y1Cs6Ixi3e1w3cVNBMclRKUuXHteE6TsOGbJxoEzcWky/QYd1/ZCdtX0zyLBJEwhWDEY+SKixqU6H6Kvsdin48MmMFjMv7W8w1a4+k2riTVUbnhQKBgQD2oaPHNVWzD0KcI64eg4YLSTw+eBBvby8FO0HYq4hjexJp7KqFracaY/Arf0UUAAZhKNVNHtnw37asIDt5Z6p0vARPh2rW5Qi5SVNSkOGmQQrLFIzR2Qw3pmqMTAC6hgPFF159V7uArSLKW/lkjxZL09dFKv6iBL6MzbCcs90e+wKBgQDbBzRu6uIPA0YfWAB7WZvADk+8vEam0bHMItjcDUAf/YBNasoSjr3+g0TdXVLOnW9MDIapt1x4oZUZePUP8o9wUV1nS95Npw0cfGweo7CUi8AY8t9b6uY1LcERc8TXcU0+qUuoT4k6/2r92PeM1onkXBdQ1+JlA3BUN3SYjBZ13QKBgQDsissR3M19Fz3Dk1UEXxDI8kydfXoF5pUqZ1MYBCyjfP6EKUDDRXHnlcJbdY1DSWLU/NzXQqe0plwFakSghBSwMUPRIwgNY02ip/kKT3qcgfVgIS2OdqWJGktGk/5+5tnmxisR/HsRRvz/KIdhMvsnbt4gsMPXkMVRNlYVdUy20QKBgQCVBJrNhovc8x5xQm+fBRT2Xhdrc6iLLwzltpp7ZEQwkHzQyplCf9wQVJgJ/LwXVTyKLt6ZoZLzDbytu+swrSHzWO23utE70ZLr4/9H+6QUxQu1AcwIyULQGHGtdg3jX13wo6SjbwJMpYL1d8n6y58Vu/k+9SSJ6dXBEpGLQoBIvA==";
    public static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;
    private String orignal_data;

    /**
     * @param orignal_data 待加密原文
     * @return 加密后的Base64码(String)
     */
    public String getAuthencode(String orignal_data) throws Exception{
        //byte[] data = Base64.getDecoder().decode(orignal_data);
        byte[] data = orignal_data.getBytes(StandardCharsets.UTF_8);
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // base64 解码privatekey

        // 密钥材料转换
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privatekay));

        // 产生私钥
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }
    /**用于调试
     * @param authencode 待解密密文
     * @return 解密后的原文(String)
     */
    private static String decodeAthuncode(String authencode) throws Exception{
        byte[] code = Base64.getDecoder().decode(authencode);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publickay));

        Key key = keyFactory.generatePublic(x509EncodedKeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(code));
    }

    /**用于调试
     * @param day 注册码时长
     * @return 待加密原文
     */
    public String getOrignal_data(int day)
    {
        Machine machine = new Machine();
        String orignal_data = machine.getMachineCode();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,day);
        String y,m,d;
        y = String.valueOf(c.get(Calendar.YEAR));
        m = String.valueOf(c.get(Calendar.MONTH) + 1);
        d = String.valueOf(c.get(Calendar.DATE));
        if(m.length() == 1) m = "0" + m;
        if(d.length() == 1) d = "0" + d;

        orignal_data += y + m + d;
        return orignal_data;
    }
    /**
     * @param year 注册过期年份
     * @param month 注册过期月份
     * @param day 注册过期天
     * @param machine 机器码
     * @return 待加密原文
     */
    public String getOrignal_data(int year, int month, int day, String machine)
    {
        String orignal_data = machine;
        if(year == -1 &&  month == -1 &&  day == -1)
            orignal_data += "99999999";
        else {
            String y = Integer.toString(year);
            String m = Integer.toString(month);
            String d = Integer.toString(day);
            if(m.length() == 1) m = "0" + m;
            if(d.length() == 1) d = "0" + d;

            orignal_data += y + m + d;
        }
        return orignal_data;
    }


    public static void main(String[] args) throws Exception
    {
        Server server = new Server();
        System.out.print("输入注册码天数：");
        Scanner sc = new Scanner(System.in);
        int day = sc.nextInt();

        String orignal_data = server.getOrignal_data(day);
        System.out.println(orignal_data);
        String pass = server.getAuthencode(orignal_data);
        System.out.println(pass);
        System.out.println(decodeAthuncode(pass));
    }

}

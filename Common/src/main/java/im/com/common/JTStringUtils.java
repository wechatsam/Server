package im.com.common;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JTStringUtils {

    public static String stringToMD5(String account)
    {
        byte[] digest = null;
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(account.getBytes("utf-8"));
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        String content = new BigInteger(1, digest).toString(16);
        return content;
    }
}

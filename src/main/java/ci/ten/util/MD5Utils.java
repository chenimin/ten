package ci.ten.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Random;

public class MD5Utils {

    public final static String passwordEncode(String password,String salt){
        //随机生成salt
        String encodePassword = (new SimpleHash("MD5", password, ByteSource.Util.bytes(salt), 1024)).toString();
        return encodePassword;
    }

}

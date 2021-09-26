package Utils;

import Common.Constant;

import javax.servlet.http.Cookie;

public class CookieUtils {
    public static Cookie setCookie(Constant key, String value, int expiry){
        Cookie cookie = new Cookie(key.name(), value); //key 为cookie内容名字，value为cookie 类型
        cookie.setMaxAge(expiry);       //cookie过期时间
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        return cookie;
    }
}

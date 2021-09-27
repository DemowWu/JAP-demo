package com.jap.bladeAdapter;


import javax.servlet.http.Cookie;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com) and hq.W
 * @program JAP-demo
 * @description -BladeCookieAdapter
 */
public class BladeCookieAdapter extends Cookie {
    public BladeCookieAdapter(String name,String value){
        super(name,value);
    }

    public static BladeCookieAdapter convert(com.blade.mvc.http.Cookie cookie){
        BladeCookieAdapter res = new BladeCookieAdapter(cookie.name(),cookie.value());
        res.setPath(cookie.path());
        res.setDomain(cookie.domain());
        res.setHttpOnly(cookie.httpOnly());
        res.setSecure(cookie.secure());
        res.setMaxAge((int) cookie.maxAge());
        return res;
    }
}

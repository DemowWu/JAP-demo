package com.jap.actAdapter;

import javax.servlet.http.Cookie;

/**
 * @author hq.W
 * @program JAP-demo
 * @description ActCookieAdapter
 */
public class ActCookieAdapter extends Cookie {

    public ActCookieAdapter(String name,String value){
        super(name,value);
    }
    public static ActCookieAdapter convert(org.osgl.http.H.Cookie cookie){
        ActCookieAdapter res = new ActCookieAdapter(cookie.name(),cookie.value());
        res.setPath(cookie.path());
        res.setDomain(cookie.domain());
        res.setHttpOnly(cookie.httpOnly());
        res.setSecure(cookie.secure());
        res.setMaxAge((int) cookie.maxAge());
        return res;
    }
}

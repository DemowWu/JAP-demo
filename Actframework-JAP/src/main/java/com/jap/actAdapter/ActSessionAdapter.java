package com.jap.actAdapter;

import org.osgl.http.H;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * @author hq.W
 * @program JAP-demo
 * @description ActSessionAdapter
 */
public class ActSessionAdapter implements HttpSession{

    private  H.Session actSession;

    public ActSessionAdapter(H.Session actSession){
        this.actSession = actSession;
    }
    @Override
    public long getCreationTime() {
        return actSession.expiry();
    }

    @Override
    public String getId() {
        return actSession.id();
    }

    @Override
    public Object getAttribute(String s) {
        return actSession.get(s);
    }

    @Override
    public Object getValue(String s) {
        return actSession.get(s);
    }

    @Override
    public void setAttribute(String s, Object o) {
        String s1 = s.replace(":", ".");
        actSession.put(s1, o);
    }

    @Override
    public void putValue(String s, Object o) {
        actSession.put(s,o);
    }
    @Override
    public void removeAttribute(String s) {
        actSession.remove(s);
    }

    @Override
    public void removeValue(String s) {
        actSession.remove(s);
    }

    @Override
    public void invalidate() {
        Set<String> keySet = actSession.keySet();
        if(null == keySet || keySet.isEmpty()){
            return;
        }
        for(String s : keySet){
            actSession.remove(s);
        }
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int i) {

    }

    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }


    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public boolean isNew() {
        return false;
    }
}

package com.jap.bladeAdapter;

import com.blade.mvc.http.Session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com) and hq.W
 * @program JAP-demo
 * @description BladeSessionAdapter
 */
public class
BladeSessionAdapter implements HttpSession {
    private final Session bladeSession;

    public BladeSessionAdapter(Session bladeSession){
        this.bladeSession = bladeSession;
    }

    @Override
    public long getCreationTime() {
        return bladeSession.created();
    }

    @Override
    public String getId() {
        return bladeSession.id();
    }

    @Override
    public Object getAttribute(String name) {
        return bladeSession.attribute(name);
    }

    @Override
    public Object getValue(String name) {
        return bladeSession.attribute(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        bladeSession.attribute(name, value);
    }

    @Override
    public void putValue(String name, Object value) {
        bladeSession.attribute(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        bladeSession.removeAttribute(name);
    }

    @Override
    public void removeValue(String name) {
        bladeSession.removeAttribute(name);
    }

    @Override
    public void invalidate() {
        Map<String, Object> map = bladeSession.attributes();
        if (null == map || map.isEmpty()) {
            return;
        }
        for (String s : map.keySet()) {
            bladeSession.removeAttribute(s);
        }
    }

    /**
     * Returns the last time the client sent a request associated with this session, as the number of milliseconds since
     * midnight January 1, 1970 GMT, and marked by the time the container received the request.
     *
     * <p>
     * Actions that your application takes, such as getting or setting a value associated with the session, do not
     * affect the access time.
     *
     * @return a <code>long</code> representing the last time the client sent a request associated with this session,
     * expressed in milliseconds since 1/1/1970 GMT
     * @throws IllegalStateException if this method is called on an invalidated session
     */
    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    /**
     * Returns the ServletContext to which this session belongs.
     *
     * @return The ServletContext object for the web application
     * @since Servlet 2.3
     */
    @Override
    public ServletContext getServletContext() {
        return null;
    }

    /**
     * Returns the maximum time interval, in seconds, that the servlet container will keep this session open between
     * client accesses. After this interval, the servlet container will invalidate the session. The maximum time
     * interval can be set with the <code>setMaxInactiveInterval</code> method.
     *
     * <p>
     * A return value of zero or less indicates that the session will never timeout.
     *
     * @return an integer specifying the number of seconds this session remains open between client requests
     * @see #setMaxInactiveInterval
     */
    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    /**
     * Specifies the time, in seconds, between client requests before the servlet container will invalidate this
     * session.
     *
     * <p>
     * An <tt>interval</tt> value of zero or less indicates that the session should never timeout.
     *
     * @param interval An integer specifying the number of seconds
     */
    @Override
    public void setMaxInactiveInterval(int interval) {

    }

    /**
     * @return the {@link HttpSessionContext} for this session.
     * @deprecated As of Version 2.1, this method is deprecated and has no replacement. It will be removed in a future
     * version of Jakarta Servlets.
     */
    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    /**
     * Returns an <code>Enumeration</code> of <code>String</code> objects containing the names of all the objects bound
     * to this session.
     *
     * @return an <code>Enumeration</code> of <code>String</code> objects specifying the names of all the objects bound
     * to this session
     * @throws IllegalStateException if this method is called on an invalidated session
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    /**
     * @return an array of <code>String</code> objects specifying the names of all the objects bound to this session
     * @throws IllegalStateException if this method is called on an invalidated session
     * @deprecated As of Version 2.2, this method is replaced by {@link #getAttributeNames}
     */
    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    /**
     * Returns <code>true</code> if the client does not yet know about the session or if the client chooses not to join
     * the session. For example, if the server used only cookie-based sessions, and the client had disabled the use of
     * cookies, then a session would be new on each request.
     *
     * @return <code>true</code> if the server has created a session, but the client has not yet joined
     * @throws IllegalStateException if this method is called on an already invalidated session
     */
    @Override
    public boolean isNew() {
        return false;
    }
}

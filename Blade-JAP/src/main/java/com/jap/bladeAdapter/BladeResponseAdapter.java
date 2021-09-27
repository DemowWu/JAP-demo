package com.jap.bladeAdapter;

import cn.hutool.core.collection.CollectionUtil;
import com.blade.mvc.http.HttpResponse;
import com.blade.mvc.http.Response;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author hq.W
 * @program JAP-demo
 * @description BladeResponseAdapter
 */
public class BladeResponseAdapter implements HttpServletResponse {

    private final HttpResponse response;

    public BladeResponseAdapter(HttpResponse response){
        this.response = response;
    }

    @Override
    public void addCookie(Cookie cookie) {
        response.cookie(cookie.getName(),cookie.getValue());
    }

    @Override
    public boolean containsHeader(String s) {
        Map<String, String> headers = response.headers();
        return  CollectionUtil.isEmpty(headers);
    }

    @Override
    public String encodeURL(String s) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String s) {
        return null;
    }

    @Override
    public String encodeUrl(String s) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return null;
    }

    @Override
    public void sendError(int i, String s) throws IOException {
        this.sendError(i);
    }

    @Override
    public void sendError(int i) throws IOException {
        response.status(i);
    }

    @Override
    public void sendRedirect(String s) throws IOException {
        response.redirect(s);
    }

    @Override
    public void setDateHeader(String s, long l) {

    }

    @Override
    public void addDateHeader(String s, long l) {
    }

    @Override
    public void setHeader(String s, String s1) {
        response.header(s,s1);
    }

    @Override
    public void addHeader(String s, String s1) {
        response.header(s,s1);
    }

    @Override
    public void setIntHeader(String s, int i) {

    }

    @Override
    public void addIntHeader(String s, int i) {
    }

    @Override
    public void setStatus(int i) {
        response.status(i);
    }

    @Override
    public void setStatus(int i, String s) {
        this.setStatus(i);
    }

    @Override
    public int getStatus() {
        return response.statusCode();
    }

    @Override
    public String getHeader(String s) {
        Map<String, String> headers = response.headers();
        return headers.get(s);
    }

    @Override
    public Collection<String> getHeaders(String s) {
        Map<String, String> headers = response.headers();
        return (Collection<String>) headers;
    }

    @Override
    public Collection<String> getHeaderNames() {
        Map<String, String> headers = response.headers();
        Set<String> keySet = headers.keySet();
        return keySet;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}

package com.jap.utils;

import com.blade.mvc.http.HttpRequest;
import com.blade.mvc.http.HttpResponse;
import com.jap.bladeAdapter.BladeRequestAdapter;
import com.jap.bladeAdapter.BladeResponseAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hq.W
 * @program JAP-demo
 * @description HttpUtils
 */
public class HttpUtils {

    public static HttpServletRequest toHttpServletReq(HttpRequest request){
        return new BladeRequestAdapter(request);
    }
    public static HttpServletResponse toHttpServletRes(HttpResponse response){

        return new BladeResponseAdapter(response);
    }
}

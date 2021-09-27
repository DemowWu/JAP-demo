package com.jap.utils;

import act.app.ActionContext;
import com.jap.actAdapter.ActRequestAdapter;
import com.jap.actAdapter.ActResponseAdapter;
import org.osgl.http.H;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hq.W
 * @program JAP-demo
 * @description HttpUtils
 */
public class HttpUtils {

    public static HttpServletRequest toHttpServletReq(H.Session session,H.Request request){
        return new ActRequestAdapter(session,request);
    }
    public static HttpServletRequest toHttpServletConReq(ActionContext context){
        return new ActRequestAdapter(context);
    }
    public static HttpServletResponse toHttpServletRes(H.Response response){
        return new ActResponseAdapter(response);
    }
    public static HttpServletResponse toHttpServletConRes(ActionContext context){
        return new ActResponseAdapter(context);
    }
}

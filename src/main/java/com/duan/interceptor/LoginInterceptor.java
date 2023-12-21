package com.duan.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 配置拦截器：若没有登录则将请求重定向到登录界面
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("uid") == null) {
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}

package com.example.mybatis.controller;

import com.example.mybatis.service.LoginRequired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * Created by LJ on 2018/6/28.
 * 编写拦截器,使用 @Commponent 让 Spring 管理其生命周期
 */
@Component
@Controller
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登陆
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // System.out.println("methodAnnotation===" + methodAnnotation);
        // 有@LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 执行认证
            HttpSession session = request.getSession();
            String hasSession = (String)session.getAttribute("name");
            // System.out.println("hasSession == " + hasSession);
            if (hasSession == null) {
                response.sendRedirect(request.getContextPath()+"/");
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) throws Exception {
        System.out.println("---------------视图渲染之后的操作-------------------------0");
    }
}

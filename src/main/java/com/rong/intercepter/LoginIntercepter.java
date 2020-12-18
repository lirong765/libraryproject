package com.rong.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginIntercepter implements HandlerInterceptor {

    /**
     * 在请求处理的方法之前执行
     * 如果返回true执行下一个拦截器
     * 如果返回false就不执行下一个拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //调用时间：Controller方法处理之前
        HttpSession session = request.getSession();
        if(session.getAttribute("user")!=null){
            return true;
        } else {
            //没有登录，跳转到登录页面进行登录
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //在请求处理方法执行之后执行
        //调用时间：Controller方法处理完之后，DispatcherServlet进行视图的渲染之前，也就是说在这个方法中你可以对ModelAndView进行操作
        //System.out.println("------------处理后------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //调用前提：preHandle返回true
        //调用时间：DispatcherServlet进行视图的渲染之后
        //多用于清理资源
        //System.out.println("执行后，返回前执行...");
    }
}

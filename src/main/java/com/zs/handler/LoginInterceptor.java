package com.zs.handler;

import com.mysql.cj.log.Log;
import com.zs.pojo.User;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Created by zs on 2022/2/22.
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println(request.getRequestURL());

        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser != null) {
            return true;
        }
        request.setAttribute("prompt","请登录");
        request.getRequestDispatcher("/admin").forward(request, response);
        return false;
    }


}

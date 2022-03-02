package com.zs.config;

import com.github.pagehelper.PageInterceptor;
import com.zs.handler.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置类
 * @Created by zs on 2022/2/22.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/admin", "/admin/login", "/admin/login.do",
                                     "/admin/loginOut", "/admin/validate", "/admin/register.do",
                                     "/res/**", "/error");
    }
}

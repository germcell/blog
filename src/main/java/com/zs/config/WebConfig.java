package com.zs.config;

import com.github.pagehelper.PageInterceptor;
import com.zs.handler.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置类
 * @Created by zs on 2022/2/22.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /* 登录拦截器 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/admin", "/admin/login", "/admin/login.do",
                                     "/admin/loginOut", "/admin/validate", "/admin/register.do",
                                     "/res/**", "/error","/blog/**");
    }

    /* 实时显示上传后的文件，而不需重启服务器 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/images/category/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + Const.CATEGORY_PICTURE_SAVE_DIR);
        registry.addResourceHandler("/res/images/blogPicture/**")
                .addResourceLocations("file:"+System.getProperty("user.dir") + Const.BLOG_FIRST_PICTURE_SAVE_DIR);
    }
}

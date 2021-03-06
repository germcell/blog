package com.zs.config;

import com.github.pagehelper.PageInterceptor;
import com.zs.handler.LoginInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;

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

   /* 定义一个单例的map由IoC管理。用于处理editormd上传图片请求时，保存上传用户的ip，图片路径 */
    @Bean("imageMap")
    public HashMap<String,List<String>> imageMap() {
        return new HashMap<String,List<String>>();
    }
}

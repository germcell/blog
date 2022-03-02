package com.zs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Created by zs on 2022/2/22.
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @RequestMapping("/index")
    public String blogPage() {
        return "admin/blogindex";
    }

}

package com.zs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/1")
    public String t1 () {
//        int a = 1/0;
//        String blog = null;
//        if (blog == null) {
//            throw new CustomNotFoundException("博客找不到了");
//        }
        return "admi/blogindex";
    }

    @RequestMapping("/2")
    public String t2 () {
        return "admi/index";
    }

}

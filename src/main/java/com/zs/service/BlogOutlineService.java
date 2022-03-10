package com.zs.service;

import com.zs.pojo.BlogOutline;

import java.util.List;

/**
 * @Created by zs on 2022/3/10.
 */
public interface BlogOutlineService {

    List<BlogOutline> listBlogOutlines();

    BlogOutline getBlogOutlineById(Long bid);
}

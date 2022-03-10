package com.zs.service.impl;

import com.zs.handler.RedisUtils;
import com.zs.mapper.BlogOutlineMapper;
import com.zs.pojo.BlogOutline;
import com.zs.service.BlogOutlineService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created by zs on 2022/3/10.
 */
@Service
public class BlogOutlineServiceImpl implements BlogOutlineService {

    @Resource
    private BlogOutlineMapper blogOutlineMapper;

    // TODO 将热门文章的概要对象存储到redis
    @Override
    public List<BlogOutline> listBlogOutlines() {
        return  blogOutlineMapper.listSortByViewsBlogOutline();
    }

    @Override
    public BlogOutline getBlogOutlineById(Long bid) {
        return blogOutlineMapper.getBlogOutlineById(bid);
    }
}

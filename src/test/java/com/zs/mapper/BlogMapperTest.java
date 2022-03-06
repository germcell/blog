package com.zs.mapper;

import com.zs.pojo.Blog;
import com.zs.pojo.RequestResult;
import com.zs.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Created by zs on 2022/3/3.
 */
@Slf4j
@SpringBootTest
class BlogMapperTest {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogService blogService;

    @Test
    void listBlogs() {
        List<Blog> blogs = blogMapper.listBlogs();
//        assertEquals(1, blogs.size());
        log.info("结果:{}", blogs);
    }

    @Test
    void listConditionBlogs() {
        List<Blog> aSuper = blogMapper.listConditionBlogs(null, null, true);
        assertEquals(2, aSuper.size());
        log.info("结果:{}", aSuper);
    }

    @Test
    void getBlog() {
        Blog blog = new Blog();
        blog.setBid(1L);
        Blog blog1 = blogMapper.getBlog(blog);
        assertNotNull(blog1);
        log.info("结果:{}", blog1);
    }

    @Test
    void updateBlogById() throws Exception {
        Blog b1 = new Blog();
        b1.setBid(38L);
        b1.setTitle("测试updateBlogById的title");
        b1.setContent("6666");
        b1.setCid(4);
        b1.setViews(1000L);
        b1.setIsAppreciate(true);
        b1.setIsComment(false);
        b1.setIsPublish(false);

        RequestResult requestResult = blogService.updateBlog(b1, 38L);
        assertEquals("博客编辑成功", requestResult.getMessage());
    }

}
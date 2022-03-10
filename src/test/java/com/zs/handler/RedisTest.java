package com.zs.handler;

import com.zs.pojo.Blog;
import com.zs.pojo.BlogOutline;
import com.zs.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Created by zs on 2022/3/8.
 */
@SpringBootTest
public class RedisTest {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void redisTest() throws Exception {
        Blog blog = new Blog();
        blog.setBid(1L);

        Blog blog2 = new Blog();
        blog2.setBid(2L);

        User user = new User();
        user.setUid(1);
        user.setMail("111@qq.com");
        blog.setUser(user);

        List<Blog> list = new ArrayList<>();
        list.add(blog);
        list.add(blog2);

        Iterator<Blog> iterator = list.iterator();
        while (iterator.hasNext()) {
            Blog next = iterator.next();
            redisTemplate.opsForList().leftPush("list", next);
        }

        List<Blog> blogs = new LinkedList<>();

        List<Object> list1 = redisTemplate.opsForList().range("list", 0, -1);
        Iterator<Object> iterator1 = list1.iterator();
        while (iterator1.hasNext()) {
            Object next = iterator1.next();
//            blogs.add((Blog) next);

            System.out.println(next.getClass());
        }

        System.out.println(blogs);

    }


}

package com.zs.service.impl;

import com.zs.mapper.CommentMapper;
import com.zs.pojo.Comment;
import com.zs.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Created by zs on 2022/3/11.
 */
@SpringBootTest
class CommentServiceImplTest {

    @Resource
    private CommentService commentService;

    @Test
    void postComments() {
        Comment comment = new Comment(45,"测试","111@qq.com","111","/fs",new Date(), 2332L, 23L, null, null, null);
        int result = commentService.postComments(comment);
        assertEquals(1, result);
    }

    @Test
    void listCommentsByBid() {
        List<Comment> comments = commentService.listCommentsByBid(44L);
        Iterator<Comment> iterator = comments.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
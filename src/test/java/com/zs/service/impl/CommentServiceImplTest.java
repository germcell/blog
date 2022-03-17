package com.zs.service.impl;

import com.zs.mapper.CommentMapper;
import com.zs.pojo.Comment;
import com.zs.pojo.RequestResult;
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
//        Comment comment = new Comment(45,"测试","111@qq.com","111","/fs",new Date(), 2332L, 23L, null, null, null);
//        int result = commentService.postComments(comment);
//        assertEquals(1, result);
    }

    @Test
    void listCommentsByBid() {
        List<Comment> comments = commentService.listCommentsByBid(44L);
        Iterator<Comment> iterator = comments.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    void deleteComments() {
        // 单例留言删除测试
        //  Long[] ids = {47L};
        //  RequestResult requestResult = commentService.deleteComments(ids, 1L);
        //  System.out.println(requestResult);

        // 批量留言删除测试
        Long[] ids = {47L, 48L};
        RequestResult requestResult = commentService.deleteComments(ids);
        System.out.println(requestResult);
    }

    @Test
    void passComments() {
        Comment comment = new Comment();
        comment.setIsPass(true);
        comment.setPassContent("此留言违反了社区规范，已被屏蔽");
        Long[] comIds = {};
        RequestResult requestResult = commentService.passComments(comIds, comment);
        assertEquals(7004, requestResult.getCode());
    }
}
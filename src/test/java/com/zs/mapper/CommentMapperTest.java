package com.zs.mapper;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.zs.pojo.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Created by zs on 2022/3/11.
 */
@SpringBootTest
class CommentMapperTest {

    @Resource
    private CommentMapper commentMapper;

    @Test
    void insert() {
        Comment comment = new Comment(45,"测试","111@qq.com","111","/fs",new Date(), 2332L, 23l, null, null, null);
        commentMapper.insert(comment);
    }

    @Test
    void listCommentsByBid() {
        List<Comment> comments = commentMapper.listCommentsByBid(1L);
        comments.stream().forEach(c -> System.out.println(c));

    }
}
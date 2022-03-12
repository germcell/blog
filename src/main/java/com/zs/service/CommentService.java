package com.zs.service;

import com.zs.pojo.Comment;

import java.util.List;

/**
 * @Created by zs on 2022/3/11.
 */
public interface CommentService {

    int postComments(Comment comment);

    List<Comment> listCommentsByBid(Long bid);
}

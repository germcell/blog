package com.zs.service.impl;

import com.zs.handler.RandomUtils;
import com.zs.mapper.CommentMapper;
import com.zs.pojo.Comment;
import com.zs.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Created by zs on 2022/3/11.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Value("${postAvatar}")
    private String postAvatar;

    @Value("${replyAvatar}")
    private String replyAvatar;

    /**
     * 发布、回复评论
     * @param comment
     * @return
     */
    @Transactional
    @Override
    public int postComments(Comment comment) {
        comment.setAvatar(RandomUtils.generateAvatar());
        comment.setReplyTime(new Date());
        int rows = commentMapper.insert(comment);
        return rows;
    }

    /**
     * 查询评论
     * 流程 : 先查询父评论、再根据父评论的id作为子评论的com_parent_id查询子评论
     * @param bid
     * @return
     * FIXME 实现多级评论
     * TODO 评论时间处理，(今天、昨天、刚刚...)
     */
    @Override
    public List<Comment> listCommentsByBid(Long bid) {
        // 查询所有父评论(查询 com_parent_id == -1 和 指定 bid 的记录)
        List<Comment> comments = commentMapper.listCommentsByBid(bid);
        // 再查询父评论的所有子评论
        Iterator<Comment> commentsIterator = comments.iterator();
        while (commentsIterator.hasNext()) {
            Comment next = commentsIterator.next();
            List<Comment> listChildComments = commentMapper.listCommentsByComId(next.getComId());
            if (listChildComments.size() != 0) {
                next.setListChildComments(listChildComments);
            }
        }
        return comments;
    }
}

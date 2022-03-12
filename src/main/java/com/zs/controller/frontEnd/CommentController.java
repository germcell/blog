package com.zs.controller.frontEnd;

import com.zs.pojo.Comment;
import com.zs.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created by zs on 2022/3/11.
 */
@Controller
@RequestMapping("/blog")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 发表评论
     * @param comment
     * @param model
     * @return
     */
    @PostMapping("/comment/post")
    public String post(Comment comment, Model model) {
        commentService.postComments(comment);
        return "redirect:/blog/comment/load/" + comment.getBid();
    }

    /**
     * 加载评论
     * @param bid
     * @param model
     * @return
     * FIXME 评论太多时可分页加载评论
     */
    @GetMapping("/comment/load/{bid}")
    public String loadComment(@PathVariable("bid") Long bid, Model model) {
        List<Comment> listComments = commentService.listCommentsByBid(bid);
        model.addAttribute("comments", listComments);
        return "blog :: commentList";
    }

}

package com.zs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 博客实体类
 * @Created by zs on 2022/2/22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Blog {

    private Long bId; // id
    private String title; // 博客标题
    private String content; // 博客内容
    private String firstPicture; // 博客首图
    private Integer cId; // 分类id
    private long views; // 浏览次数
    private Boolean isAppreciate; // 是否开启赞赏
    private Boolean isComment; // 是否开启评论
    private Boolean isPublish; // 是否发布/草稿
    private Date writeTime; // 创建时间
    private Date updateTime; // 最近更新时间
    private Integer uid;
    /* 一个用户对应多篇博客 */
    private User user;
    /* 一个分类对应多篇博客 */
    private Category category;
    /* 一篇博客对应多个评论 */
    private List<Comment> listComments;

}

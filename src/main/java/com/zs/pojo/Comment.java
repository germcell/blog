package com.zs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 评论实体类
 * @Created by zs on 2022/2/22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

  private long comId;
  private String nickname;
  private String mail;
  private String content;
  private String avatar;
  private Date replyTime;
  /* 和博客类构成多对一关系：多个评论 --> 一篇博客 */
  private Blog blog;
  /* 和本身构成一对多关系：一个子评论 --> 多个父评论，暂时仅支持两级评论 */
  private List<Comment> listComments;
}

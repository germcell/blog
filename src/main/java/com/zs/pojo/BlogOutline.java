package com.zs.pojo;

/**
 * 一篇博客在首页展示时的部分内容，因为博客的内容是markdown格式，
 * 会有一些特殊符号，而内容太长的话又会将布局撑开
 */
public class BlogOutline {

  private Long did;
  private String outline;
  private Long views;
  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getViews() {
    return views;
  }

  public void setViews(Long views) {
    this.views = views;
  }

  public long getDid() {
    return did;
  }

  public void setDid(long did) {
    this.did = did;
  }


  public String getOutline() {
    return outline;
  }

  public void setOutline(String outline) {
    this.outline = outline;
  }

  public BlogOutline() {}
  public BlogOutline(Long did, String outline, Long views, String title) {
    this.did = did;
    this.outline = outline;
    this.views = views;
    this.title = title;
  }

  @Override
  public String toString() {
    return "BlogOutline{" +
            "did=" + did +
            ", outline='" + outline + '\'' +
            ", views=" + views +
            ", title='" + title + '\'' +
            '}';
  }
}

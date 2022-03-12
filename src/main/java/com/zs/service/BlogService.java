package com.zs.service;

import com.github.pagehelper.PageInfo;
import com.zs.pojo.*;

import java.util.List;

/**
 * @Created by zs on 2022/3/3.
 */
public interface BlogService {

    /**
     * 分页查询博客
     * @param currentPage
     * @param rows
     * @return
     */
    PageInfo<Blog> listPageBlogs(Integer currentPage, Integer rows);

    /**
     * 条件分页查询博客
     * @param currentPage 页码
     * @param rows 每页行数
     * @param title 博客标题
     * @param categoryId  博客所属分类id
     * @param isPublish  是否发布
     * @return
     */
    PageInfo<Blog> pageConditionBlog(Integer currentPage, Integer rows, String title, Integer categoryId, Boolean isPublish);

    /**
     * 按博客id删除博客
     * @param bid
     * @return
     */
    RequestResult deleteBlogById(Integer bid);

    /**
     * 保存一条博客
     * @param blog 博客内容
     * @param loginUser 发布作者
     * @return
     * TODO 后端发布博客时，对应还需创建一个概要对象，并插入数据库
     */
    RequestResult insertBlog(Blog blog, User loginUser);

    /**
     * 查询博客
     * @param bid
     * @return
     */
    Blog getBlogById(Long bid);

    /**
     * 按照博客id编辑博客
     * @param blog
     * @param bid
     * @return
     */
    RequestResult updateBlog(Blog blog, Long bid) throws Exception;

    /**
     * 查询浏览量前 10 的文章概要
     * @return
     */
    List<BlogOutline> listRecommendBlog();

    /**
     * 查询博客，并将内容由 Markdown 转为 HTML
     * @param bid
     * @return
     */
    Blog getBlogByIdAndConvert(Long bid);

    /**
     * 分页查询指定分类的博客
     * @param cid
     * @return
     */
    PageInfo<Blog> listPageBlogsByCid(Integer currentPage, Integer rows, Integer cid);

}

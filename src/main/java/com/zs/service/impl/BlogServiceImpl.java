package com.zs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.log.Log;
import com.zs.config.Const;
import com.zs.handler.CompareUtils;
import com.zs.handler.UniversalException;
import com.zs.mapper.BlogMapper;
import com.zs.mapper.CategoryMapper;
import com.zs.pojo.Blog;
import com.zs.pojo.Category;
import com.zs.pojo.RequestResult;
import com.zs.pojo.User;
import com.zs.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Created by zs on 2022/3/3.
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageInfo<Blog> listPageBlogs(Integer currentPage, Integer rows) {
        PageHelper.startPage(currentPage, rows);
        List<Blog> listBlogs = blogMapper.listBlogs();
        PageInfo<Blog> pageInfo = new PageInfo<>(listBlogs);
        return pageInfo;
    }

    /**
     * 条件分页查询博客
     * @param currentPage 页码
     * @param rows 每页行数
     * @param title 博客标题
     * @param categoryId  博客所属分类id
     * @param isPublish  是否发布
     * @return
     */
    @Override
    public PageInfo<Blog> pageConditionBlog(Integer currentPage, Integer rows,
                                            String title, Integer categoryId, Boolean isPublish) {
        List<Blog> listConditionBlogs = null;
        PageHelper.startPage(currentPage, rows);
        if (categoryId == 0) {
            listConditionBlogs = blogMapper.listConditionBlogs(title, null, isPublish);
        } else {
            listConditionBlogs = blogMapper.listConditionBlogs(title, categoryId, isPublish);
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(listConditionBlogs);
        return pageInfo;
    }

    /**
     * 按博客id删除博客
     * @param bid
     * @return
     */
    @Transactional
    @Override
    public RequestResult deleteBlogById(Integer bid) {
        RequestResult requestResult = new RequestResult();
        int rows = blogMapper.deleteBlogById(bid);
        if (rows == 1) {
            // TODO 评论功能上线后，此处删除博客的同时还需删除博客所对应的评论
            requestResult.setCode(Const.DELETE_BLOG_SUCCESS);
            requestResult.setMessage("删除成功");
        } else {
            requestResult.setCode(Const.DELETE_BLOG_FAILED);
            requestResult.setMessage("删除失败 : database异常");
        }
        return requestResult;
    }

    /**
     * 保存一条博客
     * @param blog 博客内容
     * @param loginUser 发布作者
     * @return
     */
    @Transactional
    @Override
    public RequestResult insertBlog(Blog blog, User loginUser) {
        RequestResult requestResult = new RequestResult();
        blog.setViews(0L);
        int rows = blogMapper.insertBlog(blog, loginUser);
        if (rows == 1 && blog.getIsPublish()) {
            requestResult.setCode(Const.EDIT_BLOG_SUCCESS);
            requestResult.setMessage("博客发布成功");
        } else {
            requestResult.setCode(Const.EDIT_BLOG_SUCCESS);
            requestResult.setMessage("博客已保存至草稿");
        }
        return requestResult;
    }

    /**
     * 查询博客
     * @param bid
     * @return
     */
    @Override
    public Blog getBlogById(Long bid) {
        Blog blog = new Blog();
        blog.setBid(bid);
        return blogMapper.getBlog(blog);
    }

    /**
     * 按照博客id编辑博客
     *      需先根据bid查询博客记录，比较记录是否全部相等，
     *      务必重写Blog实体类的 hashCode() and equals()方法，
     *      重写方法时有目的的选择比较的属性，不必比较全部属性
     * @param blog
     * @param bid
     * @return
     */
    @Transactional
    @Override
    public RequestResult updateBlog(Blog blog, Long bid) throws Exception {
        RequestResult requestResult = new RequestResult();
        /* 更行博客内容 */
        Blog tmp = new Blog();
        tmp.setBid(bid);
        Blog blogById = blogMapper.getBlog(tmp);
        // 数据库数据和表单数据比较，返回的 needUpdateData 里面只包括需要修改的属性项，其余为 null
        Blog needUpdateData = CompareUtils.compareBlog(blogById, blog);
        if (needUpdateData.getBid() == null && needUpdateData.getTitle() == null &&
            needUpdateData.getContent() == null && needUpdateData.getCid() == null &&
            needUpdateData.getIsAppreciate() == null && needUpdateData.getIsComment() == null &&
            needUpdateData.getIsPublish() == null && needUpdateData.getCrTipId() == null) {
            /* 博客内容无修改、只更新博客首图 */
            if (!(blog.getFirstPicture() == null || "".equals(blog.getFirstPicture()))) {
                Blog updatePicture = new Blog();
                updatePicture.setFirstPicture(blog.getFirstPicture());
                blogMapper.updateBlogById(updatePicture, bid);
                requestResult.setCode(Const.EDIT_BLOG_SUCCESS);
                requestResult.setMessage("图片更新成功");
            } else {
                requestResult.setCode(Const.EDIT_BLOG_FAILED_CONTENT_IS_NULL);
                requestResult.setMessage("编辑失败 : 无更改项");
            }
        } else {
            /* 博客内容修改 */
            needUpdateData.setFirstPicture(blog.getFirstPicture());
            blogMapper.updateBlogById(needUpdateData, bid);
            requestResult.setCode(Const.EDIT_BLOG_SUCCESS);
            requestResult.setMessage("博客编辑成功");
        }
        return requestResult;
    }

}
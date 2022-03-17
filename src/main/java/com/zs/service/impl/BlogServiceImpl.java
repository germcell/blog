package com.zs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zs.config.Const;
import com.zs.handler.CompareUtils;
import com.zs.handler.MarkdownUtils;
import com.zs.handler.UniversalException;
import com.zs.mapper.BlogMapper;
import com.zs.mapper.BlogOutlineMapper;
import com.zs.mapper.CategoryMapper;
import com.zs.pojo.*;
import com.zs.service.BlogService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created by zs on 2022/3/3.
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Resource
    private BlogOutlineMapper blogOutlineMapper;

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
     * 分为两个步骤: 一是保存博客，返回生成主键
     *            二是解析部分博客内容，生成博客概要对象，并插入数据库
     * @param blog 博客内容
     * @param loginUser 发布作者
     * @return
     */
    @Transactional
    @Override
    public RequestResult insertBlog(Blog blog, User loginUser) {
        RequestResult requestResult = new RequestResult();
        blog.setViews(0L);
        // 保存博客
        int rows = blogMapper.insertBlog(blog, loginUser);
        // 保存博客概要信息
        BlogOutline blogOutline = new BlogOutline();
        blogOutline.setDid(blog.getBid());
        blogOutline.setViews(blog.getViews());
        blogOutline.setTitle(blog.getTitle());
        blogOutline.setOutline(MarkdownUtils.wordParse(blog.getContent()));
        blogOutlineMapper.insert(blogOutline);

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
        /* 获取原博客内容 */
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

    /**
     * 查询浏览量前 10 的文章概要
     * @return
     */
    @Override
    public List<BlogOutline> listRecommendBlog() {
        return blogOutlineMapper.listSortByViewsBlogOutline();
    }

    /**
     * 查询博客，并将内容由 Markdown 转为 HTML
     * @param bid
     * @return
     */
    @Override
    public Blog getBlogByIdAndConvert(Long bid) {
        Blog blog = blogMapper.getBlogView(bid);
        String htmlData = MarkdownUtils.markdownToHtmlExtensions(blog.getContent());
        blog.setContent(htmlData);
        return blog;
    }

    /**
     * 分页查询指定分类的博客
     * @param cid
     * @return
     */
    @Override
    public PageInfo<Blog> listPageBlogsByCid(Integer currentPage, Integer rows, Integer cid) {
        PageHelper.startPage(currentPage, rows);
        List<Blog> blogsByCid = blogMapper.listBlogs(cid);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogsByCid);
        return pageInfo;
    }

    /**
     * 查询博客基本信息(留言管理显示)
     * @param bid
     * @return
     */
    @Override
    public Blog getBlogBaseMsg(Long bid) {
        return blogMapper.getBlogBaseMsg(bid);
    }
}

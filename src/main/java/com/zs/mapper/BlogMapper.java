package com.zs.mapper;

import com.zs.pojo.Blog;
import com.zs.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Created by zs on 2022/3/3.
 */
@Mapper
public interface BlogMapper {

    /**
     * 查询所有记录
     * @return
     */
    List<Blog> listBlogs();

    /**
     * 条件查询
     * @param title 博客标题(模糊查询)
     * @param cid 博客分类id
     * @param isPublish 是否发布
     * @return
     */
    List<Blog> listConditionBlogs(@Param("title") String title,
                                  @Param("cid") Integer cid,
                                  @Param("isPublish") Boolean isPublish);

    /**
     * 根据博客id删除博客
     * @param bid
     * @return
     */
    int deleteBlogById(@Param("bid") Integer bid);

    /**
     * 插入一条记录
     * @param blog 博客内容
     * @param user 发布作者
     * @return
     */
    @Transactional
    int insertBlog(@Param("blog") Blog blog,
                   @Param("user") User user);

    /**
     * 动态条件查询，但至少具备一个条件，否则使用 listBlogs
     * @param blog
     * @return
     */
    Blog getBlog(@Param("blog") Blog blog);

    /**
     * 根据博客id修改记录
     * @param blog 条件，里面只包含了需要修改项的数据，其余为null
     * @param bid
     * @return
     */
    @Transactional
    int updateBlogById(@Param("blog") Blog blog,
                       @Param("bid") Long bid);
}
package com.zs.mapper;

import com.zs.pojo.BlogOutline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Created by zs on 2022/3/7.
 */
@Mapper
public interface BlogOutlineMapper {
    /**
     * 查询博客对应的概要信息
     * @param blogOutline
     * @return
     */
    int insert(@Param("blogOutline") BlogOutline blogOutline);

    /**
     * 浏览量降序
     * @return
     */
    List<BlogOutline> listSortByViewsBlogOutline();

    /**
     * 查询
     * @param bid
     * @return
     */
    BlogOutline getBlogOutlineById(@Param("bid") Long bid);

    /**
     * 删除
     * @param bid
     * @return
     */
    int deleteByBid(@Param("bid") Integer bid);

    /**
     * 条件更新
     * @param bo
     * @return
     */
    int updateByCondition(@Param("bo") BlogOutline bo);
}

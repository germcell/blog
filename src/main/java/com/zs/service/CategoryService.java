package com.zs.service;

import com.github.pagehelper.PageInfo;
import com.zs.pojo.Category;
import com.zs.pojo.RequestResult;

import java.util.List;

/**
 * @Created by zs on 2022/2/24.
 */
public interface CategoryService {

    /**
     * 分页
     * @param currentPage 需查询的页数
     * @param rows 行数
     * @return
     */
    PageInfo<Category> pageCategory(int currentPage, int rows);

    /**
     * 新建分类
     * @param category
     * @return
     */
    int addCategory(Category category) throws Exception;

    /**
     * 条件分页
     * @param condition
     * @param currentPage
     * @param rows
     * @return
     */
    PageInfo<Category> conditionPageCategory(String condition, Integer currentPage, int rows);

    /**
     * 删除分类
     * @param cid 被删除id
     * @return
     */
    RequestResult deleteCategoryById(Integer cid);

    /**
     * 修改分类
     * @param category
     * @return
     */
    RequestResult updateCategory(Category category);

    /**
     * 查询所有分类
     */
    List<Category> listCategories();

    /**
     * 查询前 6 个分类，按分类下所存在博客数量排序
     * @return
     */
    List<Category> listSortCategories();
}

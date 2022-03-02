package com.zs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.log.Log;
import com.zs.config.Const;
import com.zs.mapper.CategoryMapper;
import com.zs.pojo.Category;
import com.zs.pojo.RequestResult;
import com.zs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Created by zs on 2022/2/24.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 分页查询分类
     * @param currentPage 需查询的页数
     * @param rows 行数
     * @return
     */
    @Override
    public PageInfo<Category> pageCategory(int currentPage, int rows) {
        // 计算查询索引
//        int start = currentPage * rows - rows;
        PageHelper.startPage(currentPage, rows);
        PageInfo<Category> info = new PageInfo<>(categoryMapper.listCategories());
        return info;
    }

    /**
     * 新建分类
     * @param category
     * @return
     */
    @Override
    public int addCategory(Category category) throws Exception{
        return categoryMapper.insertCategory(category);
    }

    /**
     * 条件分页
     * @param condition
     * @param currentPage
     * @param rows
     * @return
     */
    @Override
    public PageInfo<Category> conditionPageCategory(String condition, Integer currentPage, int rows) {
        PageHelper.startPage(currentPage, rows);
        List<Category> list =  categoryMapper.listConditionCategories(condition);
        PageInfo<Category> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 删除分类
     * @param cid 被删除id
     * @return
     */
    @Transactional
    @Override
    public RequestResult deleteCategoryById(Integer cid) {
        RequestResult requestResult = new RequestResult();
        int rows = categoryMapper.deleteCategoryById(cid);
        requestResult.setCode(Const.DELETE_CATEGORY_SUCCESS);
        requestResult.setMessage("删除成功");
        return requestResult;
    }

    /**
     * 修改分类信息
     * @param category
     * @return
     */
    @Transactional
    @Override
    public RequestResult updateCategory(Category category) {
        RequestResult requestResult = new RequestResult();
        Category tempCondition = new Category();
        // 是否上传了文件
        if(category.getPicture() == null) {
            tempCondition.setCid(category.getCid());
            Category getCategoryById = categoryMapper.getCategory(tempCondition);
            // 是否更改了分类信息 (name、description)
            if (getCategoryById.getName().equals(category.getName())
                    && getCategoryById.getDescription().equals(category.getDescription())) {
                requestResult.setCode(Const.CATEGORY_ADD_FAILED);
                requestResult.setMessage("编辑失败:无更改项");
            } else {
                categoryMapper.updateCategoryById(category);
                requestResult.setCode(Const.CATEGORY_ADD_SUCCESS);
                requestResult.setMessage("编辑成功");
            }
        } else {
            tempCondition.setName(category.getName());
            Category getCategoryByName = categoryMapper.getCategory(tempCondition);
            // 是否已存在更改后的同名分类，并且 getCategoryByName != category， 通过cid判断
            if (getCategoryByName != null && getCategoryByName.getCid() != category.getCid()) {
                requestResult.setCode(Const.CATEGORY_ADD_FAILED);
                requestResult.setMessage("编辑失败:该分类已存在");
            } else {
                categoryMapper.updateCategoryById(category);
                requestResult.setCode(Const.CATEGORY_ADD_SUCCESS);
                requestResult.setMessage("编辑成功");
            }
        }
        return requestResult;
    }
}

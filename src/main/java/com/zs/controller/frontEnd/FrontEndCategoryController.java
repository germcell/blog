package com.zs.controller.frontEnd;

import com.zs.config.Const;
import com.zs.handler.RandomUtils;
import com.zs.pojo.BlogOutline;
import com.zs.pojo.Category;
import com.zs.service.BlogOutlineService;
import com.zs.service.BlogService;
import com.zs.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created by zs on 2022/3/12.
 */
@Controller
@RequestMapping("/blog")
public class FrontEndCategoryController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private BlogService blogService;

    @Resource
    private BlogOutlineService blogOutlineService;

    @GetMapping("/category/{currentPage}")
    public String page(@PathVariable("currentPage") Integer currentPage, Model model) {
        // 初始化一个搜索条件(博客标题)，选取浏览量前5的任意一篇博客
        List<BlogOutline> listBlogOutlines = blogOutlineService.listBlogOutlines();
        BlogOutline initSearchCondition = RandomUtils.generateBlogOutline(listBlogOutlines);
        model.addAttribute("initSearch", initSearchCondition);
        // 分类数据、对应的博客数据渲染
        List<Category> listCategories = categoryService.listCategories();
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageInfo", blogService.listPageBlogsByCid(currentPage,
                                                                                    Const.BLOG_PAGE_ROWS,
                                                                                    listCategories.get(0).getCid()));
        return "category";
    }





}

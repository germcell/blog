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
import org.springframework.util.StringUtils;
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

    /**
     * 跳转到分类页面，并返回指定分类下的博客(分页显示)
     * @param currentPage 当前页
     * @param cid 分类id
     * @param model
     * @return
     */
    @GetMapping({"/category/{currentPage}/", "/category/{currentPage}/{cid}"})
    public String page(@PathVariable("currentPage") Integer currentPage,
                       @PathVariable(value = "cid", required = false) Integer cid,
                       Model model) {
        // 初始化一个搜索条件(博客标题)，选取浏览量前5的任意一篇博客
        List<BlogOutline> listBlogOutlines = blogOutlineService.listBlogOutlines();
        BlogOutline initSearchCondition = RandomUtils.generateBlogOutline(listBlogOutlines);
        model.addAttribute("initSearch", initSearchCondition);
        // 分类数据、对应的博客数据渲染
        List<Category> listCategories = categoryService.listSortCategories();
        model.addAttribute("listCategories", listCategories);
        if (cid != null) {
            model.addAttribute("pageInfo", blogService.listPageBlogsByCid(currentPage,
                    Const.BLOG_PAGE_ROWS, cid));
        } else {
            model.addAttribute("pageInfo", blogService.listPageBlogsByCid(currentPage,
                    Const.BLOG_PAGE_ROWS, listCategories.get(0).getCid()));
        }
        return "category";
    }

}

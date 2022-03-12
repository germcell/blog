package com.zs.controller.frontEnd;

import com.github.pagehelper.PageInfo;
import com.zs.config.Const;
import com.zs.handler.RandomUtils;
import com.zs.handler.RedisUtils;
import com.zs.pojo.Blog;
import com.zs.pojo.BlogOutline;
import com.zs.service.BlogOutlineService;
import com.zs.service.BlogService;
import com.zs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Created by zs on 2022/3/6.
 */
@Controller
@RequestMapping("/blog")
public class IndexController {

    @Resource
    private BlogService blogService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private BlogOutlineService blogOutlineService;

    public static Map<String, Long> StatisticalVisitsMap = new HashMap<>();

    /* 生成搜索条件时，用于中转blog对象的集合 */
    private List<BlogOutline> listBlogOutlines = new LinkedList<>();
    private List<Blog> listBlogs = new LinkedList<>();

    /*
       TODO 防止用户重复请求，
       reqCache 第一次会存储搜索条件，如果第二次搜索条件与第一次相同时，则不处理搜索请求
    */
    private Map<String, String> reqCache = new HashMap<>();

    /**
     * 入口url
     * @return
     */
    @GetMapping()
    public String index() {
        return "redirect:/blog/index/";
    }

    /**
     * 首页分页显示博客(局部刷新)
     * @param currentPage
     * @param model
     * @mapping /index/                 第一次访问 index.html，只能使用此请求路径
     * @mapping /index/{currentPage}    在 index.html 中做分页操作，局部刷新，使用此请求路径
     * @return
     */
    @GetMapping({"/index/{currentPage}", "/index/"})
    public String pageBlog(@PathVariable(value = "currentPage", required = false) Integer currentPage, Model model) {
        /* 分支1: currentPage为null时，表示第一次访问，将转发并刷新整个页面 */
        if (null == currentPage) {
            currentPage = 1;
            PageInfo<Blog> pageInfo = blogService.pageConditionBlog(currentPage, Const.BLOG_PAGE_ROWS,
                                                                    null, 0, true);
            // 初始化一个搜索条件(博客标题)，选取浏览量前50的任意一篇博客
            List<BlogOutline> listBlogOutlines = blogOutlineService.listBlogOutlines();
            BlogOutline initSearchCondition = RandomUtils.generateBlogOutline(listBlogOutlines);
            // 渲染页面数据
            model.addAttribute("initSearch", initSearchCondition);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("categories", categoryService.listSortCategories());
            model.addAttribute("recommendBlogs", blogService.listRecommendBlog());
            return "index";
        } else {
            /* 分支2: 反之，表示在进行翻页操作，只做局部刷新 */
            PageInfo<Blog> pageInfo = blogService.pageConditionBlog(currentPage, Const.BLOG_PAGE_ROWS,
                                                                    null, 0, true);
            model.addAttribute("pageInfo", pageInfo);
            return "index :: blogPageList";
        }
    }

    /**
     * 条件分页
     * @param currentPage
     * @param search
     * @param model
     * @return
     * FIXME 可将pageBlog、conditionPageBlog两个方法整合在一起，只需改变请求参数
     */
    @GetMapping({"/condition/page/{currentPage}", "/condition/page/"})
    public String conditionPageBlog(@PathVariable(value = "currentPage", required = false) Integer currentPage,
                                    @RequestParam(value = "search", required = false) String search,
                                    Model model) {

        if (null == currentPage) {
            currentPage = 1;
        }
        if ("".equals(search) || null == search) {
            return "redirect:/blog";
        } else {
            PageInfo<Blog> pageInfo = blogService.pageConditionBlog(currentPage, Const.BLOG_PAGE_ROWS, search,
                    0, true);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("search", search);
            return "search";
        }
    }

    /**
     * 浏览文章
     * @param bid
     * @param model
     * @return
     * TODO 浏览量计数
     */
    @GetMapping("/view/{bid}")
    public String view(@PathVariable("bid") Long bid, Model model, HttpServletRequest request) {
        // StatisticalVisitsMap 先进行点击量计数，由定时任务定时将浏览量存入数据库(未限制相同ip)
//        String remoteAddr = request.getRemoteAddr();
//        BlogOutline blogOutlineById = blogOutlineService.getBlogOutlineById(bid);
//        StatisticalVisitsMap.put(remoteAddr, blogOutlineById.getViews() + 1);
//        blogOutlineService.updateViews();

        if (bid != null || Objects.equals("", bid)) {
            Blog blog = blogService.getBlogByIdAndConvert(bid);
            if (blog != null) {
                // 初始化一个搜索条件(博客标题)，选取浏览量前5的任意一篇博客
                List<BlogOutline> listBlogOutlines = blogOutlineService.listBlogOutlines();
                BlogOutline initSearchCondition = RandomUtils.generateBlogOutline(listBlogOutlines);
                // blog.html渲染数据
                model.addAttribute("initSearch", initSearchCondition);
                model.addAttribute("blog", blog);
                return "blog";
            } else {
                return "error/404";
            }
        } else {
            return "error/404";
        }
    }
}

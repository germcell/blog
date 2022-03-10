package com.zs.config;

import com.zs.pojo.User;

/**
 * 常量
 * @Created by zs on 2022/2/23.
 */
public class Const {

    /**
     * 登录用户session存储的键的名称
     */
    public static final User LOGIN_USER_SESSION = null;

    /**
     * 博客首图访问路径
     */
    public static final String BLOG_FIRST_PICTURE_ACCESS_DIR = "/res/images/blogPicture/";

    /**
     * 博客首图存储路径
     */
    public static final String BLOG_FIRST_PICTURE_SAVE_DIR = "/src/main/resources/static/images/blogPicture/";

    /**
     * 分类图片访问路径
     */
    public static final String CATEGORY_PICTURE_ACCESS_DIR = "/res/images/category/";

    /**
     * 分类图片上传存储目录 (写死)
     */
    public static final String CATEGORY_PICTURE_SAVE_DIR = "/src/main/resources/static/images/category/";

    /**
     * 验证码session存储的键的名称 (未考虑并发情况)
     */
    public static final String REGISTER_VALIDATE_CODE_SESSION = "validateCode";

    /**
     *  验证码生成失败
     */
    public static final int GENERATE_CODE_FAILED = 4414;

    /**
     *  注册失败，邮箱已被注册使用
     */
    public static final int REGISTER_FAILED_USED = 4415;

    /**
     * 验证码生成成功
     */
    public static final int GENERATE_CODE_SUCCESS = 4416;

    /**
     * 验证码验证失败
     */
    public static final int VALIDATE_CODE_CHECK_FAILED = 4417;

    /**
     * 注册成功
     */
    public static final int REGISTER_SUCCESS = 4418;

    /**
     * 注册失败，数据库持久失败
     */
    public static final int REGISTER_FAILED_DATABASE = 4419;

    /**
     * 验证码发送失败，qq邮箱发送失败
     */
    public static final int SEND_VALIDATE_CODE_FAILED = 4420;

    /**
     * 分类每页条数
     */
    public static final int CATEGORY_PAGE_ROWS = 5;

    /**
     * 新建/编辑分类成功
     */
    public static final int CATEGORY_ADD_SUCCESS = 4421;

    /**
     * 新建/编辑分类失败
     */
    public static final int CATEGORY_ADD_FAILED = 4422;

    /**
     * 图片支持的格式
     */
    public static final String PICTURE_SUPPORT_FORMAT = ".jpg.jpeg.png";

    /**
     * 删除分类成功
     */
    public static final int DELETE_CATEGORY_SUCCESS = 4423;

    /**
     * 删除分类失败
     */
    public static final int DELETE_CATEGORY_FAILED = 4424;

    /**
     * 博客每页条数
     */
    public static final Integer BLOG_PAGE_ROWS = 5;

    /**
     * 删除博客成功
     */
    public static final int DELETE_BLOG_SUCCESS = 5050;

    /**
     * 删除博客失败
     */
    public static final int DELETE_BLOG_FAILED = 5051;

    /**
     * 发布/保存博客成功
     */
    public static final int EDIT_BLOG_SUCCESS = 5052;

    /**
     * 发布/保存博客失败
     */
    public static final int EDIT_BLOG_FAILED = 5053;

    /**
     * 发布/保存博客失败 (更改项)
     */
    public static final int EDIT_BLOG_FAILED_CONTENT_IS_NULL = 5054;

    /**
     * 发布/保存博客失败 (上传文件处理异常)
     */
    public static final int EDIT_BLOG_FAILED_UPLOAD_EXCEPTION = 5055;

    /**
     * 博客首图文件大小
     */
    public static final Long BLOG_FIRST_PICTURE_SIZE = 1024 * 1024 * 3L;

    /**
     * 博客显示概要的字数
     */
    public static final Integer BLOG_OUTLINE_NUM = 135;

}

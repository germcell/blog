package com.zs;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zs.mapper.CategoryMapper;
import com.zs.pojo.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableScheduling
@SpringBootTest
class BlogApplicationTests {

    @Autowired
    CategoryMapper categoryMapper;

    @Test
    void contextLoads() {
//        log.info("datasource:{}", dataSource.getClass());

        String s = DigestUtils.md5Digest("323".getBytes(StandardCharsets.UTF_8)).toString();
        System.out.println("加密:" + new String(s.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void page() {
        PageHelper.startPage(0,1);
        List<Category> categories = categoryMapper.listCategories();
        PageInfo<Category> info = new PageInfo<>(categories);
        System.out.println(info);
        System.out.println(info.getList());
    }

    @Test
    void task() {
    }



}

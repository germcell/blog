package com.zs.handler;

import com.zs.config.Const;
import com.zs.service.ViewsAndLikesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务
 * @Created by zs on 2022/3/10.
 */
@Component
public class ScheduledTask {

    @Resource
    private ViewsAndLikesService viewsAndLikesService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private HashMap<String, List<String>> imageMap;

    /**
     * “0 0/5 14,18 * * ?” 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     */
    // @Scheduled(cron = "0 0/5 13,18 * * ?")

    /**
     * 每隔一小时执行一次
     */
    // @Scheduled(cron = "0 0 * * * ?")

    /**
     * 每隔10分钟执行一次，同步redis中存储的博客点击（浏览）量
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void viewsSyncTask() {
        try {
            int rows = viewsAndLikesService.saveView();
            logger.info("定时任务执行-->同步浏览量: 影响数据行:{} {}", rows, new Date());
        } catch (Exception e) {
            throw new UniversalException("定时任务执行失败-->同步浏览量");
        }
    }

    // TODO 定时任务多线程任务异常

    /**
     *  每隔 1h 执行一次，清除imageMap集合、删除未使用的文件
     */
    @Scheduled(cron = "0 0/59 * * * ?")
    public void discardFileTask() {
        // 获取集合数据
        Set<Map.Entry<String, List<String>>> entries = imageMap.entrySet();
        Integer users = imageMap.size();
        Integer rows = 0;
        if (entries.size() != 0) {
            // 删除文件
            Iterator<Map.Entry<String, List<String>>> itSet = entries.iterator();
            while (itSet.hasNext()) {
                Map.Entry<String, List<String>> dirs = itSet.next();
                List<String> value = dirs.getValue();
                rows = value.size();
                if (value.size() != 0) {
                    // 清除集合
                    imageMap.remove(dirs.getKey());
                    Iterator<String> itList = value.iterator();
                    while (itList.hasNext()) {
                        String delDir = itList.next();
                        File file = new File(System.getProperty("user.dir") + Const.BLOG_FIRST_PICTURE_SAVE_DIR + delDir);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
            }
            logger.info("定时任务执行-->清除imageMap集合:影响{}用户,影响{}条数 {}", users, rows, new Date());
            return;
        } else {
            logger.info("定时任务执行-->清除imageMap集合:影响{}用户,{}条数 {}", users, rows, new Date());
            return;
        }
    }
}

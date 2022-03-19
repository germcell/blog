package com.zs.handler;

import com.zs.service.ViewsAndLikesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
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

    /**
     * “0 0/5 14,18 * * ?” 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     */
    // @Scheduled(cron = "0 0/5 13,18 * * ?")

    /**
     * 每隔一小时执行一次
     */
    // @Scheduled(cron = "0 0 * * * ?")

    /**
     * 每隔10分钟执行一次
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void viewsSyncTask() {
        int rows = viewsAndLikesService.saveView();
        logger.info("定时任务执行-->同步浏览量: 影响数据行:{} 时间:{}", rows, new Date());
    }
}

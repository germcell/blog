package com.zs.handler;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务
 * @Created by zs on 2022/3/10.
 */
@Component
public class ScheduledTask {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0/60 * * * * ?")
    public void viewsStatisticsTask() {

    }
}

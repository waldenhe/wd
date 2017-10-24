package com.stylefeng.guns.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.stylefeng.guns.modular.school.bean.BackUpDataBaseBean;

/**
 * 定时任务配置类
 */
@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {

    
    @Autowired
    BackUpDataBaseBean backUpDataBaseBean; 

    @Scheduled(cron = "0 0 21 * * ? ") // 每日凌晨执行一次
    public void scheduler() {
        backUpDataBaseBean.backUp();
    }

}

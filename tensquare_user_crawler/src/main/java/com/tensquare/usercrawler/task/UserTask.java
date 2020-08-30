package com.tensquare.usercrawler.task;

import com.tensquare.usercrawler.pipeline.UserPipeline;
import com.tensquare.usercrawler.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 用户爬取任务类
 */
@Component
public class UserTask {

    @Autowired
    private UserProcessor userProcessor;

    @Autowired
    private UserPipeline userPipeline;

    @Autowired
    private RedisScheduler redisScheduler;

    @Scheduled(cron = "0 19 20 * * ?")
    public void userTask(){
        System.out.println("爬取用户数据");
        Spider spider=Spider.create(userProcessor);
        spider.addUrl("https://blog.csdn.net");
        spider.addPipeline(userPipeline);
        spider.setScheduler( redisScheduler );
        spider.start();

    }


}

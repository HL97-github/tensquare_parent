package com.tensquare.articlecrawler.task;

import com.tensquare.articlecrawler.pipeline.ArticleDbPipeline;
import com.tensquare.articlecrawler.processor.ArticleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@Component
public class ArticleTask {

    @Autowired
    private ArticleProcessor articleProcessor;

    @Autowired
    private ArticleDbPipeline articleDbPipeline;

    @Autowired
    private RedisScheduler redisScheduler;

    /**
     * 爬取AI文章
     */
    @Scheduled(cron = "0 14 18 * * ?")
    public void aiTask(){
        System.out.println("爬取AI文章");
        Spider spider =Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/ai");
        articleDbPipeline.setChannelId("ai");
        spider.addPipeline(articleDbPipeline);
        spider.setScheduler(redisScheduler);

        spider.start();
    }


}

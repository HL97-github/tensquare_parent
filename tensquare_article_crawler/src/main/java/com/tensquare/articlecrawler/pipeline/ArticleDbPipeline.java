package com.tensquare.articlecrawler.pipeline;

import com.tensquare.articlecrawler.dao.ArticleDao;
import com.tensquare.articlecrawler.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.IdWorker;

@Component
public class ArticleDbPipeline implements Pipeline {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;


    private String channelId;//频道ID

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String title=resultItems.get("title");//取出标题
        String content=resultItems.get("content");//内容
        Article article=new Article();
        article.setId(idWorker.nextId()+"");
        article.setChannelid(channelId);
        article.setTitle(title);
        article.setContent(content);

        articleDao.save(article);
    }
}

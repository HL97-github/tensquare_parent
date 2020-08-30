package com.tensquare.usercrawler.pipeline;

import com.tensquare.usercrawler.dao.UserDao;
import com.tensquare.usercrawler.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.DownloadUtil;
import util.IdWorker;

import java.io.IOException;

@Component
public class UserPipeline implements Pipeline {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;

    @Override
    public void process(ResultItems resultItems, Task task) {

        //将昵称和头像文件名称存入用户表
        User user=new User();
        user.setId(idWorker.nextId()+"");
        user.setNickname( resultItems.get("nickname") );
        String image= resultItems.get("images");
        String fileName= image.substring( image.lastIndexOf("/")+1 );
        user.setAvatar(fileName);
        userDao.save(user);
        //将头像下载到本地
        try {
            DownloadUtil.download(image, fileName,"e:/userimg" );
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

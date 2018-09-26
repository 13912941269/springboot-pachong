package com.pachong.common;

import com.pachong.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuartzService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private static final String URLONE="https://www.booktxt.net/xiaoshuodaquan/";

    private static final String URLTWO="https://www.booktxt.net/xuanhuanxiaoshuo/";

    private static final String URLTHREE="https://www.booktxt.net/xiuzhenxiaoshuo/";

    private static final String URLFOUR="https://www.booktxt.net/dushixiaoshuo/";

    private static final String URLFIVE="https://www.booktxt.net/chuanyuexiaoshuo/";

    private static final String URLSIX="https://www.booktxt.net/wangyouxiaoshuo/";

    private static final String URLSEVEN="https://www.booktxt.net/kehuanxiaoshuo/";

    @Autowired
    private BookService bookService;

    @Scheduled(cron="0 55 23 * * ?")
    public void timerToNow(){
        logger.info("定时任务启动");
        bookService.insertBookIn(URLTWO);
        logger.info("玄幻小说更新完成");

        bookService.insertBookIn(URLTHREE);
        logger.info("修真小说更新完成");

        bookService.insertBookIn(URLFOUR);
        logger.info("都市小说更新完成");

        bookService.insertBookIn(URLFIVE);
        logger.info("穿越小说更新完成");

        bookService.insertBookIn(URLSIX);
        logger.info("网游小说更新完成");

        bookService.insertBookIn(URLSEVEN);
        logger.info("科幻小说更新完成");
    }
}

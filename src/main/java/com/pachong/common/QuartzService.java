package com.pachong.common;

import com.pachong.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuartzService {

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
        System.out.println("11");
        //bookService.insertBookIn(URLONE);
        bookService.insertBookIn(URLTWO);
        System.out.println("22");
        //bookService.insertBookIn(URLTHREE);
        //bookService.insertBookIn(URLFOUR);
        //bookService.insertBookIn(URLFIVE);
        //bookService.insertBookIn(URLSIX);
        //bookService.insertBookIn(URLSEVEN);
    }
}

package com.pachong.controller;

import com.pachong.dbmapper.PCBookMapper;
import com.pachong.dbmapper.PCChapterMapper;
import com.pachong.dbmapper.PCColumMapper;
import com.pachong.dbmapper.PCNovelMapper;
import com.pachong.model.PCBook;
import com.pachong.model.PCChapter;
import com.pachong.model.PCNovel;
import com.pachong.service.BookService;
import com.pachong.util.EncrypDES;
import com.pachong.util.PaChongUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-07-17.
 */
@RestController
public class TestController {

    private static final String URLONE="https://www.booktxt.net/xiaoshuodaquan/";

    private static final String URLTWO="https://www.booktxt.net/xuanhuanxiaoshuo/";

    private static final String URLTHREE="https://www.booktxt.net/xiuzhenxiaoshuo/";

    private static final String URLFOUR="https://www.booktxt.net/dushixiaoshuo/";

    private static final String URLFIVE="https://www.booktxt.net/chuanyuexiaoshuo/";

    private static final String URLSIX="https://www.booktxt.net/wangyouxiaoshuo/";

    private static final String URLSEVEN="https://www.booktxt.net/kehuanxiaoshuo/";


    @Autowired
    private BookService bookService;

    @Autowired
    private PCBookMapper bookDao;

    @Autowired
    private PCColumMapper columDao;

    @Autowired
    private PCChapterMapper chapterDao;

    @Autowired
    private PCNovelMapper novelDao;


    /**
     * 书籍爬虫
     * @return
     */
    @RequestMapping("startindex")
    public void startIndex(){
        bookService.insertBookIn(URLTWO);
    }






    /**
     * 根据条件查询书籍
     * @return
     */
    @RequestMapping("findbook")
    public List<PCBook> findBook(Integer columId){
        Map map=new HashMap();
        if(columId!=null){
            map.put("columId",columId);
        }
        List<PCBook> list = bookDao.selectByParam(map);
        return list;
    }


    /**
     * 根据书籍查询章节
     * @param bookId
     * @return
     */
    @RequestMapping("findchapter")
    public List<PCChapter> findChapter(Integer bookId){
        List<PCChapter> list = chapterDao.selectByBookId(bookId);
        return list;
    }


    /**
     * 根据章节查询小说
     * @param chapterId
     * @return
     */
    @RequestMapping("findnovel")
    public List<PCNovel> findNovel(Integer chapterId){
        List<PCNovel> list = novelDao.selectByChapterId(chapterId);
        return list;
    }
}

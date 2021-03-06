package com.pachong.controller;

import com.pachong.dbmapper.PCBookMapper;
import com.pachong.dbmapper.PCChapterMapper;
import com.pachong.dbmapper.PCColumMapper;
import com.pachong.model.PCBook;
import com.pachong.model.PCChapter;
import com.pachong.service.BookService;
import com.pachong.util.TxtExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


    /**
     * 书籍爬虫
     * @return
     */
    @RequestMapping("startindex")
    public void startIndex(){
        bookService.insertBookIn(URLFOUR);
    }




    /**
     * 文件夹转换
     * @return
     */
    @RequestMapping("exchangetext")
    public void exChangeText(){

    }


    /**
     * 转换text
     * @return
     */
    /*@RequestMapping("exchangetext")
    public void exChangeText(){
        bookService.exChangeText(1);
    }*/



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
    public Map findNovel(Integer chapterId){
        PCChapter pcChapter = chapterDao.selectByPrimaryKey(chapterId);
        Map paramMap=new HashMap();
        String chapterDetail = TxtExport.readToString("chapter_file_" + chapterId);
        paramMap.put("chapterDetail",chapterDetail);
        paramMap.put("chapterTitle",pcChapter.getChapterTitle());
        return paramMap;
    }


    /**
     * 章节-下一章
     * @param nowChapterId
     * @return
     */
    @RequestMapping("findnextnovel")
    public Map findNextNovel(Integer nowChapterId,Integer bookId){
        Map map=new HashMap();
        map.put("chapterId",nowChapterId);
        map.put("bookId",bookId);
        Integer chapterId = chapterDao.selectByNextChapterId(map);
        Map parammap=new HashMap();
        parammap.put("chapterId",chapterId);
        return parammap;
    }


    /**
     * 章节-上一章
     * @param nowChapterId
     * @return
     */
    @RequestMapping("findprenovel")
    public Map findPreNovel(Integer nowChapterId,Integer bookId){
        Map map=new HashMap();
        map.put("chapterId",nowChapterId);
        map.put("bookId",bookId);
        Integer chapterId = chapterDao.selectByPreChapterId(map);
        Map parammap=new HashMap();
        parammap.put("chapterId",chapterId);
        return parammap;
    }
}

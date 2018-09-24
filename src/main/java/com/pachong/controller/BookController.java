package com.pachong.controller;


import com.pachong.dbmapper.PCBookMapper;
import com.pachong.dbmapper.PCColumMapper;
import com.pachong.model.PCBook;
import com.pachong.model.PCColum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 2018-07-17.
 */
@Controller
public class BookController {
    @Autowired
    private PCBookMapper bookDao;

    @Autowired
    private PCColumMapper columDao;
    /**
     * 前往小说列表页
     * @return
     */
    @RequestMapping("booklist")
    public ModelAndView bookList(){
        ModelAndView mv=new ModelAndView();
        List<PCColum> listColum = columDao.selectAllColum();
        mv.addObject("listColum",listColum);
        mv.addObject("listSize",listColum.size());
        mv.setViewName("html/booklist");
        return mv;
    }



    /**
     * 前往小说章节页
     * @return
     */
    @RequestMapping("chapterlist")
    public ModelAndView chapterlist(Integer bookId){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("html/chapterlist");
        PCBook pcBook = bookDao.selectByPrimaryKey(bookId);
        mv.addObject("pcBook",pcBook);
        return mv;
    }

    /**
     * 前往小说页
     * @return
     */
    @RequestMapping("read")
    public ModelAndView read(Integer bookId,Integer chapterId){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("html/booknovel");
        mv.addObject("bookId",bookId);
        mv.addObject("chapterId",chapterId);
        return mv;
    }

}

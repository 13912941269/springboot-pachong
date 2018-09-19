package com.pachong.controller;

import com.pachong.dbmapper.PCBookMapper;
import com.pachong.dbmapper.PCChapterMapper;
import com.pachong.dbmapper.PCColumMapper;
import com.pachong.dbmapper.PCNovelMapper;
import com.pachong.model.PCBook;
import com.pachong.model.PCChapter;
import com.pachong.model.PCColum;
import com.pachong.model.PCNovel;
import com.pachong.util.EncrypDES;
import com.pachong.util.PaChongUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-07-17.
 */
@Controller
public class TestController {

    @Autowired
    PCBookMapper pcbookdao;

    @Autowired
    PCChapterMapper pcchapterdao;

    @Autowired
    PCColumMapper pccolumdao;

    @Autowired
    PCNovelMapper pcnoveldao;

    /**
     * 书籍爬虫
     * @return
     */
    @RequestMapping("startindex")
    public ModelAndView startIndex(){
        ModelAndView mv = new ModelAndView();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PaChongUtil pachong=new PaChongUtil();
        List list = pachong.bookIndex("https://www.booktxt.net/xiaoshuodaquan/");
        //EncrypDES des = new EncrypDES();
        for(Object object:list){
            Map map=(Map)object;
            Integer columId;
            PCColum colum = pccolumdao.selectByColumName((String) map.get("colum"));
            if(colum==null){
                PCColum pcColum=new PCColum();
                pcColum.setColumName((String) map.get("colum"));
                pcColum.setOrderVal(0);
                try {
                    pcColum.setAddtime(format.parse(format.format(new Date())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                pccolumdao.insertSelective(pcColum);
                columId=pcColum.getColumId();
            }else{
                columId=colum.getColumId();
            }

            List booklist=(List)map.get("bookList");
            for(Object param:booklist){
                Map paramMap=(Map)param;
                String booklinkurl=(String)paramMap.get("bookLink");
                /*try {
                    booklinkurl=des.aesDecrypt(booklinkurl);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                Map mapzj = pachong.zhangJie(booklinkurl);
                String auth=(String)mapzj.get("auth");
                String desc=(String)mapzj.get("desc");
                String titimg=(String)mapzj.get("titimg");

                PCBook pcBookCheck = pcbookdao.selectByBookName((String) paramMap.get("bookName"));
                if(pcBookCheck==null){
                    pcBookCheck=new PCBook();
                    pcBookCheck.setBookName((String)paramMap.get("bookName"));
                    pcBookCheck.setColumId(columId);
                    pcBookCheck.setBookAuth(auth);
                    pcBookCheck.setBookDesc(desc);
                    pcBookCheck.setBookImg(titimg);
                    pcbookdao.insertSelective(pcBookCheck);
                }


                List zjList=(List)mapzj.get("listart");
                for(Object objectzj:zjList){
                    Map zjparam=(Map)objectzj;

                    Map checkChapter=new HashMap();
                    checkChapter.put("chapterName",(String)zjparam.get("linktext"));
                    checkChapter.put("bookId",pcBookCheck.getBookId());
                    PCChapter pcChapter = pcchapterdao.selectByChapterNameAndBookId(checkChapter);
                    if(pcChapter==null){
                        pcChapter=new PCChapter();
                        pcChapter.setBookId(pcBookCheck.getBookId());
                        pcChapter.setChapterTitle((String)zjparam.get("linktext"));
                        pcchapterdao.insertSelective(pcChapter);
                        String novellinkurl=(String)zjparam.get("link");
                        /*try {
                            novellinkurl=des.aesDecrypt(novellinkurl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                        String detail = pachong.detail(novellinkurl);
                        PCNovel pcNovel=new PCNovel();
                        pcNovel.setChapterId(pcChapter.getChapterId());
                        pcNovel.setVovelDetail(detail);
                        pcnoveldao.insertSelective(pcNovel);
                    }
                }

            }
        }
        mv.setViewName("html/index");
        return mv;
    }




    /**
     * 书籍首页
     * @return
     */
    @RequestMapping("bookindex")
    public ModelAndView bookIndex(){
        ModelAndView mv = new ModelAndView();
        PaChongUtil pachong=new PaChongUtil();
        List list = pachong.bookIndex("https://www.booktxt.net/xiaoshuodaquan/");
        mv.addObject("list",list);
        mv.setViewName("html/index");
        return mv;
    }



    /**
     * 章节
     * @return
     */
    @RequestMapping("zhangjie")
    public ModelAndView zhangJie(String param){
        EncrypDES des = new EncrypDES();
        String linkurl=param;
        try {
            linkurl=des.aesDecrypt(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        param=linkurl;
        ModelAndView mv = new ModelAndView();
        PaChongUtil pachong=new PaChongUtil();
        Map map = pachong.zhangJie(param);
        mv.addObject("title",map.get("title"));
        mv.addObject("auth",map.get("auth"));
        mv.addObject("desc",map.get("desc"));
        mv.addObject("titimg",map.get("titimg"));
        mv.addObject("listart",map.get("listart"));
        mv.setViewName("/pachong/zhangjie");
        return mv;
    }



    /**
     * 详情
     * @return
     */
    @RequestMapping("bookdetail")
    public ModelAndView bookDetail(String param){
        EncrypDES des = new EncrypDES();
        String linkurl=param;
        try {
            linkurl=des.aesDecrypt(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        param=linkurl;
        ModelAndView mv = new ModelAndView();
        PaChongUtil pachong=new PaChongUtil();
        String detail = pachong.detail(param);
        mv.addObject("detail",detail);
        mv.setViewName("/pachong/detail");
        return mv;
    }
}

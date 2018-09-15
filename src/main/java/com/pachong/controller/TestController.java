package com.pachong.controller;

import com.pachong.util.EncrypDES;
import com.pachong.util.PaChongUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-07-17.
 */
@Controller
public class TestController {

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
        mv.setViewName("/pachong/index");
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

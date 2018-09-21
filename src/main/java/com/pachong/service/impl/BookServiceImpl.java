package com.pachong.service.impl;

import com.pachong.dbmapper.PCBookMapper;
import com.pachong.dbmapper.PCChapterMapper;
import com.pachong.dbmapper.PCColumMapper;
import com.pachong.dbmapper.PCNovelMapper;
import com.pachong.model.PCBook;
import com.pachong.model.PCChapter;
import com.pachong.model.PCColum;
import com.pachong.model.PCNovel;
import com.pachong.service.BookService;
import com.pachong.util.PaChongUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    PCBookMapper pcbookdao;

    @Autowired
    PCChapterMapper pcchapterdao;

    @Autowired
    PCColumMapper pccolumdao;

    @Autowired
    PCNovelMapper pcnoveldao;

    @Override
    public Map insertBookIn(String url) {
        PaChongUtil pachong=new PaChongUtil();
        List list;
        if(url.contains("xiaoshuodaquan")){
            list = pachong.bookIndex(url);
        }else{
            list = pachong.bookIndexColum(url);
        }

        for(Object object:list){
            Map map=(Map)object;
            String columName=(String) map.get("colum");
            String[] columSplit = columName.split("-");
            Integer columId=0;
            Map columparam=new HashMap();
            for(String columStr:columSplit){
                columparam.put("columName",columStr);
                columparam.put("parentId",columId);
                PCColum colum = pccolumdao.selectByColumName(columparam);
                if(colum==null){
                    PCColum pcColum=new PCColum();
                    pcColum.setColumName(columStr);
                    pcColum.setParentId(columId);
                    pcColum.setOrderVal(0);
                    pccolumdao.insertSelective(pcColum);
                    columId=pcColum.getColumId();
                }else{
                    columId=colum.getColumId();
                }
            }


            List booklist=(List)map.get("bookList");
            for(Object param:booklist){
                Map paramMap=(Map)param;
                String booklinkurl=(String)paramMap.get("bookLink");
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
                    checkChapter.put("chapterTitle",(String)zjparam.get("linktext"));
                    checkChapter.put("bookId",pcBookCheck.getBookId());
                    PCChapter pcChapter = pcchapterdao.selectByChapterNameAndBookId(checkChapter);
                    if(pcChapter==null){
                        pcChapter=new PCChapter();
                        pcChapter.setBookId(pcBookCheck.getBookId());
                        pcChapter.setChapterTitle((String)zjparam.get("linktext"));
                        pcchapterdao.insertSelective(pcChapter);
                        String novellinkurl=(String)zjparam.get("link");
                        String detail = pachong.detail(novellinkurl);
                        PCNovel pcNovel=new PCNovel();
                        pcNovel.setChapterId(pcChapter.getChapterId());
                        pcNovel.setVovelDetail(detail);
                        pcnoveldao.insertSelective(pcNovel);
                    }
                }

            }
        }
        return null;
    }
}

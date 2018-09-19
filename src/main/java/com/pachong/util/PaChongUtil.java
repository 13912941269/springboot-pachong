package com.pachong.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.net.util.URLUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaChongUtil {

    /**
     * 首页
     * @return
     */
    public List bookIndex(String url){
        String str = OKHttpUtil.httpGetHtml(url);
        List list = findShuJi(str);
        return list;
    }


    /**
     * 章节
     * @return
     */
    public Map zhangJie(String url){
        String str = OKHttpUtil.httpGetHtml(url);
        Map paramMap = findZhangJie(str,url);
        return paramMap;
    }


    /**
     * 详情
     * @return
     */
    public String detail(String url){
        String str = OKHttpUtil.httpGetHtml(url);
        String detail = findDetail(str);
        return detail;
    }



    /**
     * 顶点中文网书籍页
     * @param str
     * @return
     */
    private List findShuJi(String str) {
        //EncrypDES des = new EncrypDES();
        List listart=new ArrayList();
        if(StringUtils.isNotEmpty(str)){
            //转化html
            Document doc = Jsoup.parse(str);
            //获取maininfo节点
            Element main = doc.getElementById("main");
            Document  maindoc= Jsoup.parse(main.toString());
            //获取maininfo下的info节点
            Elements novelistinfo = maindoc.getElementsByClass("novellist");
            for(Element element:novelistinfo){
                Map mapBook=new HashMap();
                List listBook=new ArrayList();
                Document noveldoc = Jsoup.parse(element.toString());
                Elements h2ele = noveldoc.getElementsByTag("h2");
                String columstr=h2ele.eq(0).text();

                Elements ulele = noveldoc.getElementsByTag("ul");
                Document uldoc=Jsoup.parse(ulele.eq(0).toString());
                Elements liele = uldoc.getElementsByTag("li");
                for(Element elementli:liele){
                    Map bookparam=new HashMap();
                    Document lidoc=Jsoup.parse(elementli.toString());
                    Elements aele = lidoc.getElementsByTag("a");
                    bookparam.put("bookName",aele.eq(0).text());
                    String linkurl=aele.eq(0).attr("href");
                    /*try {
                        linkurl=URLEncoder.encode(des.aesEncrypt(linkurl),"UTF-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    bookparam.put("bookLink",linkurl);
                    listBook.add(bookparam);
                }
                mapBook.put("colum",columstr);
                mapBook.put("bookList",listBook);
                listart.add(mapBook);
            }

        }
        return listart;
    }






    /**
     * 顶点中文网章节页
     * @param str
     * @return
     */
    private Map findZhangJie(String str,String urlstr) {
        String rootPath="";
        if(urlstr.contains("https")){
            rootPath="https://"+urlstr.split("//")[1].split("/")[0];
        }
        //EncrypDES des = new EncrypDES();
        Map paramMap=new HashMap();
        if(StringUtils.isNotEmpty(str)){
            //标题
            String title="";
            //作者
            String auth="";
            //简介
            String desc="";
            //封面
            String titimg="";
            //章节
            List listart=new ArrayList();
            //转化html
            Document doc = Jsoup.parse(str);
            //获取maininfo节点
            Element maininfo = doc.getElementById("maininfo");
            Document  maininfodoc= Jsoup.parse(maininfo.toString());
            //获取maininfo下的info节点
            Element info = maininfodoc.getElementById("info");
            Document  infodoc= Jsoup.parse(info.toString());
            //获取info下的h1节点
            Elements h1 = infodoc.getElementsByTag("h1");
            title=h1.eq(0).text();
            Elements p = infodoc.getElementsByTag("p");
            auth=p.eq(0).text();
            //获取maininfo节点----简介
            Element intro = doc.getElementById("intro");
            Document  introdoc= Jsoup.parse(intro.toString());
            Elements pdetail = introdoc.getElementsByTag("p");
            desc=pdetail.eq(0).text();
            //获取titleimg
            Element fmimg = doc.getElementById("fmimg");
            Document  fmimgdoc= Jsoup.parse(fmimg.toString());
            Elements img = fmimgdoc.getElementsByTag("img");
            titimg=img.eq(0).attr("src");
            if(!titimg.contains(rootPath)){
                titimg=rootPath+titimg;
            }
            //获取章节list
            Element listzj=doc.getElementById("list");
            Document listzjdoc = Jsoup.parse(listzj.toString());
            Elements dl = listzjdoc.getElementsByTag("dl");
            String dlstr = dl.eq(0).toString();
            Document dldoc=Jsoup.parse(dlstr.toString());
            Elements dd = dldoc.getElementsByTag("dd");
            for(Element element:dd){
                Map linkMap=new HashMap();
                Document dddoc = Jsoup.parse(element.toString());
                Elements aele = dddoc.getElementsByTag("a");
                String link = aele.eq(0).attr("href");
                String linktext = aele.eq(0).text();
                if(!link.contains(rootPath)){
                    link=urlstr+link;
                }

                String linkurl=link;
                /*try {
                    linkurl= URLEncoder.encode(des.aesEncrypt(link),"UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    linkurl=link;
                }*/
                linkMap.put("link",linkurl);
                linkMap.put("linktext",linktext);
                listart.add(linkMap);
            }
            paramMap.put("title",title);
            paramMap.put("auth",auth);
            paramMap.put("desc",desc);
            paramMap.put("titimg",titimg);
            paramMap.put("listart",listart);
        }
        return paramMap;
    }





    /**
     * 顶点中文网详情页
     * @param str
     * @return
     */
    private String findDetail(String str) {
        String contentDetail="";
        if(StringUtils.isNotEmpty(str)){
            Document doc = Jsoup.parse(str);
            //获取maininfo节点
            Element content = doc.getElementById("content");
            if(content!=null){
                contentDetail=content.text();
            }

        }
        return contentDetail;
    }
}

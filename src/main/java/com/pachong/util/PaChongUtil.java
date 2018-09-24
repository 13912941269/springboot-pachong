package com.pachong.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaChongUtil {


    /**
     * 首页（分类小说）
     * @return
     */
    public List bookIndexColum(String url){
        String str = OKHttpUtil.httpGetHtml(url);
        String columName="";
        if(url.contains("xuanhuanxiaoshuo")){
            columName="玄幻小说";
        }else if(url.contains("xiuzhenxiaoshuo")){
            columName="修真小说";
        }else if(url.contains("dushixiaoshuo")){
            columName="都市小说";
        }else if(url.contains("chuanyuexiaoshuo")){
            columName="穿越小说";
        }else if(url.contains("wangyouxiaoshuo")){
            columName="网游小说";
        }else if(url.contains("kehuanxiaoshuo")){
            columName="科幻小说";
        }
        List list = findShuJiColum(str,columName);
        return list;
    }

    /**
     * 首页（小说大全）
     * @return
     */
    public List bookIndex(String url){
        String str = OKHttpUtil.httpGetHtml(url);
        List list = findShuJi(str,"全部小说");
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
     * 顶点中文网书籍页（分类小说）
     * @param str
     * @return
     */
    private List findShuJiColum(String str,String columName) {
        List listart=new ArrayList();
        if(StringUtils.isNotEmpty(str)){
            Document doc = Jsoup.parse(str);
            Element newscontentele = doc.getElementById("newscontent");
            Document  newscontenteledoc= Jsoup.parse(newscontentele.toString());
            Elements divele = newscontenteledoc.getElementsByTag("div");
            //List listBook=new ArrayList();
            for(Element element:divele){
                if(!element.attr("class").equals("clear")){
                    Map mapBook=new HashMap();
                    List listBook=new ArrayList();
                    Document noveldoc = Jsoup.parse(element.toString());

                    //Elements h2ele = noveldoc.getElementsByTag("h2");
                    //String newColum=columName+"-"+h2ele.eq(0).text();
                    mapBook.put("colum",columName);

                    Elements ulele=noveldoc.getElementsByTag("ul");
                    Document  uldoc= Jsoup.parse(ulele.eq(0).toString());
                    Elements liele=uldoc.getElementsByTag("li");
                    for(Element elementli:liele){
                        Map bookparam=new HashMap();
                        Document  eledoc=Jsoup.parse(elementli.toString());
                        Elements s2ele = eledoc.getElementsByClass("s2");
                        Document  s2doc=Jsoup.parse(s2ele.eq(0).toString());
                        Elements  aele=s2doc.getElementsByTag("a");
                        bookparam.put("bookName",aele.eq(0).text());
                        String linkurl=aele.eq(0).attr("href");
                        bookparam.put("bookLink",linkurl);
                        listBook.add(bookparam);
                    }
                    mapBook.put("bookList",listBook);
                    listart.add(mapBook);
                }
            }
        }
        return listart;
    }





    /**
     * 顶点中文网书籍页（小说大全）
     * @param str
     * @return
     */
    private List findShuJi(String str,String columName) {
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
                    bookparam.put("bookLink",linkurl);
                    listBook.add(bookparam);
                }
                mapBook.put("colum",columName+"-"+columstr);
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
            dlstr=dlstr.replace("dt","dd");
            Document dldoc=Jsoup.parse(dlstr);
            Elements dd = dldoc.getElementsByTag("dd");
            int type=0;
            for(Element element:dd){
                if(element.text().contains("正文")){
                    type=1;
                    continue;
                }
                if(type==1){
                    Map linkMap=new HashMap();
                    Document dddoc = Jsoup.parse(element.toString());
                    Elements aele = dddoc.getElementsByTag("a");
                    String link = aele.eq(0).attr("href");
                    String linktext = aele.eq(0).text();
                    if(!link.contains(rootPath)){
                        link=urlstr+link;
                    }
                    String linkurl=link;
                    linkMap.put("link",linkurl);
                    linkMap.put("linktext",linktext);
                    listart.add(linkMap);
                }
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
            Element content = doc.getElementById("content");
            if(content!=null){
                contentDetail=content.text();
            }
        }
        return contentDetail;
    }





}

package com.pachong.util;


import com.alibaba.fastjson.JSONObject;
import com.pachong.model.PCChapter;
import com.pachong.model.PCNovel;
import info.monitorenter.cpdetector.io.*;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShiWei on 2018-06-12.
 */
public class OKHttpUtil {

    private static OkHttpClient client = OKHttpSingle.getInstance();


    /**
     * xmlpost
     * @throws IOException
     */
    public static JSONObject xmlPost(String xmlstr, String url){
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, xmlstr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/xml")
                .build();
        String jsonstr = null;
        JSONObject jsonObject =null;
        try {
            Response response = client.newCall(request).execute();
            jsonstr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(StringUtils.isNotEmpty(jsonstr)){
            jsonObject=JSONObject.parseObject(jsonstr);
        }
        return  jsonObject;
    }




    /**
     * httpGet
     * @throws IOException
     */
    public static JSONObject httpGet(String url){
        Request request = new Request.Builder().url(url).build();
        String jsonstr = null;
        JSONObject jsonObject =null;
        try {
            Response response = client.newCall(request).execute();
            jsonstr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(StringUtils.isNotEmpty(jsonstr)){
            jsonObject=JSONObject.parseObject(jsonstr);
        }
        return jsonObject;
    }



    /**
     * httpGethtml
     * @throws IOException
     */
    public static String httpGetHtml(String url){
        Request request = new Request.Builder().url(url)
                .addHeader("content-type", "text/plain")
                .build();
        String jsonstr = null;
        String line;
        try {
            Response response = client.newCall(request).execute();
            String encode=null;
            String header = response.header("Content-Type");
            if(StringUtils.isNotEmpty(header)) {
                if (header.contains("UTF-8")){
                    encode= "UTF-8";
                }
                if(header.contains("GB2312")){
                    encode= "GB2312";
                }
                if (header.contains("GBK")){
                    encode= "GBK";
                }
            }

            //如果相应头里面没有编码格式,用下面这种
            if(StringUtils.isEmpty(encode)&&(!url.contains("https"))){
                CodepageDetectorProxy codepageDetectorProxy = CodepageDetectorProxy.getInstance();
                codepageDetectorProxy.add(JChardetFacade.getInstance());
                codepageDetectorProxy.add(ASCIIDetector.getInstance());
                codepageDetectorProxy.add(UnicodeDetector.getInstance());
                codepageDetectorProxy.add(new ParsingDetector(false));
                codepageDetectorProxy.add(new ByteOrderMarkDetector());
                Charset charset = codepageDetectorProxy.detectCodepage(new URL(url));
                encode= charset.name();
            }
            if(StringUtils.isEmpty(encode)){
                encode="GBK";
            }
            InputStream inputStream = response.body().byteStream();
            BufferedReader in=new BufferedReader(new InputStreamReader(inputStream,encode));
            while ((line=in.readLine())!=null){
                jsonstr+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonstr;
    }



    /**
     * httpGet
     * @throws IOException
     */
    public static JSONObject httpPost(String url,String json){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();
        String jsonstr = null;
        JSONObject jsonObject =null;
        try {
            Response response = client.newCall(request).execute();
            jsonstr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(StringUtils.isNotEmpty(jsonstr)){
            jsonObject=JSONObject.parseObject(jsonstr);
        }
        return jsonObject;
    }

    public static void main(String[] args) throws IOException {
        PaChongUtil pachong=new PaChongUtil();
        Map mapzj = pachong.zhangJie("https://www.booktxt.net/2_2219/");
        List zjList=(List)mapzj.get("listart");
        //System.out.println(zjList.size());
        for(Object objectzj:zjList){
            Map zjparam=(Map)objectzj;
            System.out.println(zjparam.get("linktext"));
        }
    }
}

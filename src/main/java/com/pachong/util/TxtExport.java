package com.pachong.util;
import java.io.*;

public class TxtExport {
    private static String path = "/home/ubuntu/java/textfile/";
    public static Boolean writeText(String fileName,String context){
        Boolean flag=true;
        File file = new File(path+fileName+".txt");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(context);
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }


    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(path+fileName+".txt");
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


}

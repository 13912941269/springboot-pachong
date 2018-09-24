package com.pachong.util;
import java.io.*;

public class TxtExport {
    private static String path = "/home/ubuntu/java/textfile/";
    public static Boolean writeText(String fileName,String context){
        Boolean flag=true;
        File file = new File(path+fileName+".txt");
        if(!file.exists()){
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
}

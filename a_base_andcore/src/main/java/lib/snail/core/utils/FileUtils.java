package lib.snail.core.utils;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class FileUtils {

    public static String getFileType(String end){
//        return "*/*";
        if(!Tools.isEmpty(end)){
            end = end.toLowerCase();
        }
        if(end.equals("doc")  ){
            return "application/msword";
        }else if(end.equals("docx")  ){
           return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
//            return "application/msword";
        }else if(end.equals("pdf")){
            return ("application/pdf");
        }else if(end.equals("htm") || end.equals("html")){
            return "text/x-component";
        }else if(end.equals("ppt")   ||end.equals("pps")){
           return  "application/vnd.ms-powerpoint";
        }else if(end.equals("pptx")){
           return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        }else if(end.equals("apk")){
           return  "application/vnd.android.package-archive";
        }else if(end.equals("txt")){
            return  "text/plain";
        }else if(end.equals("xls") ){
            return"application/vnd.ms-excel";
        }else if(end.equals("xlsx") ){
            return"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }else if(end.equals("zip")){
            return"application/x-zip-compressed";
        }else if( end.equals("rar")){
            return "application/octet-stream";
        }else{
            return "*/*";
        }
    }
}

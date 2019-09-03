package lib.snail.core.utils;


import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 * 时间处理工具
 * 2019-4-24 levent
 */
public class DateTimeUtils {

    public static String yyyyMMDDHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static String yyyyMMDDHHmm = "yyyy-MM-dd HH:mm";
    public static String yyyyMMDD = "yyyy-MM-dd";
    public static String  yearMonthDay= "yyyy年MM月dd日";
    public static String yyyyMM = "yyyy-MM";

    /**
     * 获取当前时间-默认格式
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     * 2019-4-24 levent
     */
    public static String getStringDate() {
        return getStringDate(yyyyMMDDHHmmss);
    }

    /****
     * 获取当前时间，指定时间格式
     * 2019-4-24 levent
     */
    public static String getStringDate(String formart){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(formart);
        String dateString = formatter.format(currentTime);
        formatter = null ;
        currentTime = null ;
        return dateString;
    }

    /****
     * 转换当前时间格式为日期
     * 2019-4-24 levent
     */
    public static  String converDateTimeByFormat(String dateStr ,String format ){
        if(format == ""){
            format = yyyyMMDDHHmmss ;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(yyyyMMDDHHmmss);
        try {
            Date date = formatter.parse(dateStr);
            formatter = new SimpleDateFormat(format);
            dateStr = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return dateStr ;

    }

    /****
     * 转换日期Date格式为String
     * 2019-7-12 levent
     */
    public static  String converDateToString(Date date ,String format ){
        if(format == ""){
            format = yyyyMMDDHHmmss ;
        }
        String str = "";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            str = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return str ;
    }

    /****
     * 转换日期String格式为Date
     * 2019-7-12 levent
     */
    public static  Date converStringToDate(String dateStr ,String format ){
        if(format == ""){
            format = yyyyMMDDHHmmss ;
        }
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            date = formatter.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return date ;
    }


    /****
     * 获取String格式日期的毫秒数
     * 2019-7-12 levent
     */
    public static  long  converStringToLong(String dateStr ,String format ){
        if(format == ""){
            format = yyyyMMDDHHmmss ;
        }
        long dtime = 0;
        SimpleDateFormat formatter = new SimpleDateFormat(yyyyMMDDHHmmss);
        try {
            Date date = formatter.parse(dateStr);
            dtime = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return dtime ;
    }

    /****
     * string 时间转换 Calendar
     */
    public static Calendar stringToCalendar(String curr) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            Date date =sdf.parse(curr);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }


    /****
     * 前一天、后一天
     */
    public static String preNextDay(String curr, String arg){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = stringToCalendar(curr);
        System.out.println("当前日期:"+sf.format(c.getTime()));
        if("pre".equals(arg)){
            //前一天
            c.add(Calendar.DAY_OF_MONTH, -1);
        }else{
            //后一天
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        String res = sf.format(c.getTime()) ;
        System.out.println("增加一天后日期:"+res);
        return res  ;
    }



    /***
     * 计算当前时间小于指定时间
     * return : 小于则true 否则false
     * 2019-4-30 levent
     */
    public static boolean   lessThanTime(String time){
        boolean isFlg = false ;
        SimpleDateFormat simpleFormat = new SimpleDateFormat(yyyyMMDDHHmmss);
        Date nowTime,startTime ;
        long nowLong , startLong;
        try {
            nowTime = simpleFormat.parse(getStringDate());
            startTime = simpleFormat.parse(time);
            nowLong = nowTime.getTime();
            startLong = startTime.getTime();
//            int minutes = (int) ((to3 - from3) / (1000 * 60));
            if(nowLong<startLong){
                isFlg = true ;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            simpleFormat = null ;
            nowTime = null;
            startTime = null;
            nowLong = 0 ;
            startLong = 0 ;
        }
        return isFlg ;
    }

    /****
     * 计算当前时间是否在两个时间段之间
     * start ： yyyy-MM-dd HH:mm:ss
     * end   : yyyy-MM-dd HH:mm:ss
     * return : flase 早于或晚于时间段  true 在时间段之间
     * 2019-4-24 levent
     */
    public static boolean betweenDate(String start,String end){
        boolean isFlg = false ;
        SimpleDateFormat simpleFormat = new SimpleDateFormat(yyyyMMDDHHmmss);
        Date nowTime,startTime ,endTime;
        long nowLong , startLong=0 , endLong ;
        try {
            nowTime = simpleFormat.parse(getStringDate());
            if(!TextUtils.isEmpty(start)){
                startTime = simpleFormat.parse(start);
                startLong = startTime.getTime();
            }

            endTime = simpleFormat.parse(end);
            nowLong = nowTime.getTime();
            endLong = endTime.getTime();
//            int minutes = (int) ((to3 - from3) / (1000 * 60));
            if(!TextUtils.isEmpty(start)){
                if((nowLong-startLong)>=0 && (nowLong-endLong)<=0){
                    isFlg = true ;
                }
            }else{
                if( (nowLong-endLong)<=0){
                    isFlg = true ;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            simpleFormat = null ;
            nowTime = null;
            startTime = null;
            endTime = null ;
            nowLong = 0 ;
            startLong = 0 ;
            endLong = 0 ;
        }

        return isFlg ;
    }

}


package lib.snail.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/***
 * 数据格式转换工具
 */
public class DataConver {

    /***
     * double保留两位小数
     */
    public static String doubleConverTwoDecimal(String s){

        try{
            DecimalFormat    df   = new DecimalFormat("######0.00");
            s = df.format(Double.parseDouble(s));
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

    /***
     * 将输入流 转换成字符串
     * 2019-3-27 levent
     */
    public static String  getStringByInputStream(InputStream in){
        String content = null ;
        try {
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));
            StringBuffer sb = new StringBuffer( );
            String line = null ;
            while ((line = buffRead.readLine())!=null){
                sb.append(line+"\n");
            }
            if(buffRead!=null){
                buffRead.close();
                buffRead = null ;
            }
            return sb.toString() ;
        } catch (Exception e) {
            LogUtil.e(e);
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    in = null ;
                }
            }
        }
        return content;
    }


}

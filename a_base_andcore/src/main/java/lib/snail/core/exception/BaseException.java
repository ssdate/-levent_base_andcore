package lib.snail.core.exception;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * exception Handler
 * 2019-3-28 工作日志
 */
public class BaseException  extends  Exception{

    static BaseException exception = null ;


    public static BaseException getInstance(){
        if(exception == null){
            exception = new BaseException();
        }
        return exception ;
    }

    public int getExceptionCode(Exception e) {
        int code = 0 ;
        if(e instanceof MyExctption){
            if(((MyExctption) e).getHttpCode() == 404 ){
                code = -404;
            }else  if(((MyExctption) e).getHttpCode() == 500 ){
                code = -500;
            }else  if(((MyExctption) e).getHttpCode() == 403 ){
                code = -403;
            }else  if(((MyExctption) e).getHttpCode() == 401 ){
                code = -401;
            }else {
                code = 0;
            }
        }else if(e instanceof SocketTimeoutException){
            code = 1 ;
        }if(e instanceof ProtocolException){
            code = 2 ;
        }if(e instanceof MalformedURLException){
            code = 3 ;
        }if(e instanceof IOException){
            code = 4 ;
        }if(e instanceof ArrayIndexOutOfBoundsException){
            code = 5 ;
        }if(e instanceof RuntimeException){
            code = 6 ;
        }if(e instanceof UnknownHostException){
            code = 7 ;
        }
        return code ;
    }




}

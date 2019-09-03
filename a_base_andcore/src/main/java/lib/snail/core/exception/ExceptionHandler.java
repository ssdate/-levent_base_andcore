package lib.snail.core.exception;

import lib.snail.core.exception.BaseException;

/**
 * exception handler
 * 2019-3-28 levent
 */
public class ExceptionHandler {

    /***
     * 根据code返回错误信息
     * @param errorCode
     * @return
     */
    public String  handlerException( int errorCode ) {
        String content = "";
        switch (errorCode) {
            case -401:
                content = "401:登录已过期,或您的帐号在别处登录,请您重新登录!";
                break;
            case -403:
                content = "403:服务器请求异常,请稍候再试!";
                break;
            case -404:
                content = "404:服务器请求异常,请稍候再试!";
                break;
            case -500:
                content = "500:服务器请求异常,请稍候再试!";
                break;
            case 0:
                content = "业务处理异常,请稍候尝试!";
                break;
            case 1:
                content = "服务器请求异常,请稍候再试!";
                break;
            case 2:
                content = "请求协议异常,请稍候重试!";
                break;
            case 3:
                content = "程序异常ExceptionC";
                break;
            case 4:
                content = "数据流处理异常,请稍候尝试";
                break;
            case 5:
                content = "数据处理异常,请稍候尝试";
                break;
            case 6:
                content = "业务处理异常,请稍候尝试";
                break;
            case 7:
                content = "网络请求异常,请检查您的网络";
                break;
            default:
                break;
        }
        return content;
    }

    /***
     * 根据异常返回错误信息
     * @return
     */
    public String  handlerByException( Exception e ) {
        String content = "";
        int errorCode  = BaseException.getInstance().getExceptionCode(e);
        return handlerException(errorCode);
    }

}

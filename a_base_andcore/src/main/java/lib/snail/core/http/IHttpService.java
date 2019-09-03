package lib.snail.core.http;

/***
 * http service interface
 * 2019-3-27 levent
 */
public interface IHttpService {

    //设置请求方式
    void setMethod (String m);
    //设置url
    void setUrl(String url);
    //设置请求参数
    void setRequestData(byte[] requestData);
    //执行请求
    void execute();

    //设置请求回调
    void setHttpCallBack(IHttpListener httpListener );

}

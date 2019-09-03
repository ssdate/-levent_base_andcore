package lib.snail.core.http;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

public class HttpTask<T> implements  Runnable {

    private IHttpListener iHttpListener ;
    private IHttpService iHttpService ;

    public HttpTask(T requestData,String url ,IHttpService httpService,IHttpListener httpListener){
        this.iHttpListener = httpListener ;
        this.iHttpService =  httpService ;
        iHttpService.setUrl(url);
        iHttpService.setHttpCallBack(iHttpListener);
        if(requestData != null){
            String requestContent = JSON.toJSONString(requestData);
            try {
                iHttpService.setRequestData(requestContent.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        iHttpService.execute();
    }
}

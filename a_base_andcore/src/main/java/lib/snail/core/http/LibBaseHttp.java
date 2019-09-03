package lib.snail.core.http;


import android.os.AsyncTask;

import java.util.HashMap;

import lib.snail.core.http.service.JsonHttpListener;
import lib.snail.core.http.service.JsonHttpService;
import lib.snail.core.http.service.UploadListener;
import lib.snail.core.http.service.UploadService;

/***
 * 统一http请求入口类
 */
public class LibBaseHttp {

    /***
     * httpJson请求
     * @param requestInfo  请求数据
     * @param url           请求地址
     * @param resObj        结果转换对象
     * @param dataListener 监听
     * @param <T>
     * @param <M>
     */
    public static<T,M> void sendJsonRequest(final T  requestInfo, final String url, final Class<M> resObj, final IDataListener dataListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                IHttpService httpService = new JsonHttpService() ;
                IHttpListener httpListener = new JsonHttpListener<M>(resObj,dataListener);
                HttpTask<T> httpTask = new HttpTask<T>(requestInfo,url,httpService,httpListener);
                ThreadPoolManager.getInstance().putQueue(httpTask);
            }
        }).start();
    }


    /***
     * httpJson请求
     * @param requestInfo  请求数据
     * @param url           请求地址
     * @param method        请求方式 ： POST \ GET
     * @param resObj        结果转换对象
     * @param dataListener 监听
     * @param <T>
     * @param <M>
     */
    public static<T,M> void sendJsonRequestMethod(T  requestInfo,String url,String method,Class<M> resObj,IDataListener dataListener){

        IHttpService httpService = new JsonHttpService() ;
        IHttpListener httpListener = new JsonHttpListener<M>(resObj,dataListener);
        httpService.setMethod(method);
        HttpTask<T> httpTask = new HttpTask<T>(requestInfo,url,httpService,httpListener);
        ThreadPoolManager.getInstance().putQueue(httpTask);

    }

    /****
     * http上传文件
     * @param paramsMap    携带参数
     * @param url           请求地址
     * @param resObj        结果转换对象
     * @param dataListener 监听
     * @param <T>
     * @param <M>
     * 2019-5-8 levent
     */
    public static<T,M> void uploadFile(HashMap<String, Object> paramsMap,String url ,Class<M> resObj,IDataListener dataListener ){
        IHttpService httpService = new UploadService(paramsMap) ;
        IHttpListener httpListener = new UploadListener(resObj,dataListener);
        HttpTask<T> httpTask = new HttpTask<T>(null,url,httpService,httpListener);
        ThreadPoolManager.getInstance().putQueue(httpTask);
    }


}

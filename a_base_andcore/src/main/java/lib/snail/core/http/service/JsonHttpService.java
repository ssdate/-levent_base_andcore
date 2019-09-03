package lib.snail.core.http.service;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lib.snail.core.exception.MyExctption;
import lib.snail.core.http.IHttpListener;
import lib.snail.core.http.IHttpService;
import lib.snail.core.utils.LogUtil;

public class JsonHttpService implements IHttpService {
    private static String TAG = "JsonHttpService";

    String httpUrl = "";
    byte requestData[] ;
    IHttpListener iHttpListener ;
    String method = "POST";


    @Override
    public void setMethod(String m) {
        this.method =  m ;
    }

    @Override
    public void setUrl(String url) {
        this.httpUrl = url ;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData ;
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        this.iHttpListener = httpListener ;
    }


    @Override
    public void execute() {
        httpUrlConnPost();
    }

    HttpURLConnection httpURLConnection ;
    public void httpUrlConnPost(){
        URL url  = null ;
        try {

            LogUtil.i(TAG,"********请求url："+this.httpUrl+"***********");
            url = new URL(this.httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(15000);  // 超时
            httpURLConnection.setReadTimeout(15000);  //响应超时
            httpURLConnection.setRequestMethod(method);
            if(method.equals("POST")){
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);   //不适用缓存
                httpURLConnection.setInstanceFollowRedirects(true);  //成员函数
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Type","application/json;charset=utf-8");
            }
            httpURLConnection.connect();

            if(method.equals("POST")) {
                //发送数据
                OutputStream outputStream = httpURLConnection.getOutputStream() ;
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                if(requestData != null){
                    bos.write(requestData);
                }
                bos.flush();
                outputStream.close();
                bos.close();
            }


            //写入数据

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = httpURLConnection.getInputStream() ;
                iHttpListener.onSuccess(in);
            }else if(httpURLConnection.getResponseCode() == 401){
                throw new MyExctption(401);
            }else if(httpURLConnection.getResponseCode() == 403){
                throw new MyExctption(403);
            }else if(httpURLConnection.getResponseCode() == 404){
                throw new MyExctption(404);
            }else if(httpURLConnection.getResponseCode() == 500){
                throw new MyExctption(500);
            }else {
                throw new MyExctption(0);
            }
        }  catch (Exception e) {
            LogUtil.e(e);
            iHttpListener.onFailure(e);
        }   finally {
            if(httpURLConnection!=null){
                try {
                    httpURLConnection.disconnect();  //释放连接
                } catch (Exception e) {
                    LogUtil.e(e);
                }
            }
        }
    }

}

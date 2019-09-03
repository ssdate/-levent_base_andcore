package lib.snail.core.http.service;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.util.HashMap;

import lib.snail.core.http.IDataListener;
import lib.snail.core.http.IHttpListener;
import lib.snail.core.utils.DataConver;
import lib.snail.core.utils.LogUtil;

/***
 * 上传服务监听
 * 2019-5-8 levent
 */
public class UploadListener<M> implements IHttpListener {
    private static String TAG = "UploadListener";
    HashMap<String, Object> paramsMap ;
    IDataListener dataListener ;
    Class<M> resClass;

    Handler handler = new Handler(Looper.getMainLooper()) ;

    /**
     * 监听构造函数
     * @param resData 返回 结果对象实例
     * @param iDataListener  监听
     */
    public UploadListener(Class<M> resData   , IDataListener iDataListener){
        this.resClass = resData ;
        this.dataListener = iDataListener ;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        if(inputStream != null){
            //inputstream 转 String
            String resContent = DataConver.getStringByInputStream(inputStream);
            LogUtil.i(TAG,"********上传请求结果："+resContent+"***********");
            //转对象--由于同一接口不同情况返回来的数据结构不一致无法同时转成同一对象，现改成json对象返回
            final M respObjData = JSON.parseObject(resContent, resClass);
//            final JSONObject jsonObject = JSON.parseObject(resContent);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(dataListener != null){
                        dataListener.onSuccess(respObjData);
                    }
                }
            });
        }
    }

    @Override
    public void onFailure(Exception e) {
        if(dataListener != null){
            dataListener.onFailure(e);
        }
    }

    @Override
    public void onLoading(String arg, String msg) {
        if(dataListener != null){
            boolean isF = "err".equals(arg)?false:true ;
            String resContent = "{\"flag\":"+isF+",\"type\":\""+arg+"\",\"message\":\""+msg+"\"}";
            LogUtil.i(TAG,"********上传进度："+resContent+"***********");
            //转对象--由于同一接口不同情况返回来的数据结构不一致无法同时转成同一对象，现改成json对象返回
            final M respObjData = JSON.parseObject(resContent, resClass);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(dataListener != null){
                        dataListener.onSuccess(respObjData);
                    }
                }
            });
        }
    }
}

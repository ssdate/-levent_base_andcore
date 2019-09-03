package lib.snail.core.http.service;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;

import lib.snail.core.http.IDataListener;
import lib.snail.core.http.IHttpListener;
import lib.snail.core.utils.DataConver;
import lib.snail.core.utils.LogUtil;

public class JsonHttpListener<M> implements IHttpListener {
    private static String TAG = "JsonHttpListener";

    Class<M> resClass;
    IDataListener dataListener ;

    /**
     * 获取主线程的Handle
     * 通过handle切换至主线程
     */
    Handler handler = new Handler(Looper.getMainLooper()) ;

    public JsonHttpListener(Class<M> resData , IDataListener iDataListener){
        this.resClass = resData ;
        this.dataListener = iDataListener ;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        if(inputStream != null){
            //inputstream 转 String
            String resContent = DataConver.getStringByInputStream(inputStream);
            LogUtil.i(TAG,"********请求结果："+resContent+"***********");
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
    public void onFailure(final Exception e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(dataListener != null){
                    dataListener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void onLoading(String arg, String msg) {

    }
}

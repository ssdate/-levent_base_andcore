package lib.snail.core.base.common;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.Map;

import lib.snail.core.utils.LogUtil;

/***
 * 全局继承父级application
 * base application
 * 2019-3-22 levent
 */
public abstract class BaseApplication extends Application {

    public static Context appContext ;

    /***
     * 是否开启日志
     * @param isDebug
     */
    public void isDebug(boolean isDebug){
        LogUtil.DEBUG = isDebug ;
        getAppContext();
    }

    public abstract void getAppContext() ;

    public static Context getBseContext(){
        return appContext ;
    }

    /****
     * 发送广播
     * map<String,Object>
     * Object : String 或者 序列化对象
     * @param className
     * 2019-5-7
     */
    public static void LiAC_SendBroad(String className, Map<String ,Object> map){
        if(appContext!=null){
            Intent intent = new Intent();
            intent.setAction(className);
            if(map != null){
                for(String k : map.keySet()){
                    if(map.get(k) instanceof String){
                        intent.putExtra(k, (String)map.get(k));
                    }else{
                        intent.putExtra(k, (Serializable) map.get(k));
                    }
                }
            }
            appContext.sendBroadcast(intent);
        }

    }

}

package lib.snail.core.mvp;


import android.content.Context;

import java.util.List;


/***
 * 定义数据处理逻辑接口
 * 2019-3-22 levent
 */
public interface ILibModel  {

    /****
     *
     */
    void setContext(Context _con);

    /***
     * load data  listener
     * @param onLoadListener
     */
    void loadData(OnLoadListener onLoadListener) ;
//    void callData(OnStaticCallBack onStaticCallBack);

    /**
     *
     */
    interface OnLoadListener{
        void onComplete(String arg, String res);
     }

//     interface  OnStaticCallBack<B>{
//         void callList(String arg, List<B> list);
//     }


}

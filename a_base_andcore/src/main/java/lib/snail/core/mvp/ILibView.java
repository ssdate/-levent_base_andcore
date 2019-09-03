package lib.snail.core.mvp;

import android.content.Context;

import java.util.List;

/***
 * 定义基础UI逻辑
 * 2019-3-22 levent
 */
public interface ILibView {

    /***
     * activity context
     */
    Context getContext();

    /***
     * initlialize ui
     */
//    void initView();

    /**
     * show loading
     */
    void showLoading();

    /***
     * hide loading
     */
    void hideLoading(String arg, String msg, List<?> callList);



}

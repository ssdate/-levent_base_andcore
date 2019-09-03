package lib.snail.core.base.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import lib.snail.core.mvp.ILibPresenterFather;
import lib.snail.core.ui.helper.statusBar.StatusBarUtil;

/***
 * mvp base fragment
 * 2019-4-23 levent
 * @param <V>
 * @param <T>
 */
public abstract class LibBaseFragment<V , T extends ILibPresenterFather<V>> extends Fragment {

    protected Context context;
    protected Activity activity ;
    public View view ;

    //表示层引用
    public T iLibPresenter ;

    protected abstract  T createPresenter();
    protected abstract   View setLayout(LayoutInflater inflater);
    protected  abstract  void init();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_home, null);
        if(view == null){
            view = setLayout(inflater);
            context = this.getContext() ;
            activity = getActivity();
            iLibPresenter = createPresenter();
            iLibPresenter.attachView((V) this);
            init();
            //状态栏
//        refStatusBar();
            fullScreen();
        }

        return view;
    }

    //toast
    public void toast(String msg){
        if(!TextUtils.isEmpty(msg)){
            try{
                Looper.prepare();
                Toast.makeText(BaseApplication.getBseContext(),msg,Toast.LENGTH_SHORT).show();
                Looper.loop();
            }catch (Exception e){
                e.printStackTrace();
                try{
                    Toast.makeText(BaseApplication.getBseContext(),msg,Toast.LENGTH_SHORT).show();
                }catch (Exception  e2){
                    e2.printStackTrace();
                }

            }
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(iLibPresenter != null){
//            iLibPresenter.detachView();
//        }
//    }

    /***
     * 沉浸式状态栏
     */
    public static boolean isShowStatusHeight = true ;
    public void refStatusBar(){

        // 当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this.getActivity(),isShowStatusHeight);
        if(isShowStatusHeight == false){
            isShowStatusHeight = true ;
        }
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this.getActivity());
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this.getActivity(), true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this.getActivity(),0x55000000);
        }

    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     */
    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //设置状态栏为透明，否则在部分手机上会呈现系统默认的浅灰色
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以考虑设置为透明色
                window.setNavigationBarColor(Color.TRANSPARENT);
                setAndroidNativeLightStatusBar(this.getActivity(),true);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    } //状态栏文字颜色
    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

}

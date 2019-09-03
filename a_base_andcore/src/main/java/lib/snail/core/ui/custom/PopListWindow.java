package lib.snail.core.ui.custom;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import java.util.List;

import lib.snail.core.R;
import lib.snail.core.db.entrty.PopObj;
import lib.snail.core.ui.custom.adapter.PopListAdapter;
import lib.snail.core.base.common.LibBaseCallback;


/***
 *  自定义重写pop--实现右上角pop效果
 *  2019-5-15 levent
 */
public class PopListWindow   {
    Activity context ;
    public LibBaseCallback call ;
    public void setI(LibBaseCallback call){
        this.call = call ;
    }

    private PopupWindow mPopupWindow;
//    private AnimUtil animUtil;

    NoScrollListView listV;
    PopListAdapter vp2;

    /***
     * 初始化
     * @param context
     */
    public PopListWindow(Activity context ) {
        this.context = context ;
//        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        setOutsideTouchable(true);
//        setFocusable(true);
//        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_list_win,null, false);
//
//
//        setContentView(contentView);
        mPopupWindow = new PopupWindow(context);
//        animUtil = new AnimUtil();
        buildPop();
    }

    public void setData(List<PopObj>li){
        vp2.setData(li);
    }


    private void buildPop() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_list_win,null, false);
        listV = contentView.findViewById(R.id.lay_menu_list);
        vp2 = new PopListAdapter(context);
        listV.setAdapter(vp2);
        // 设置布局文件
        mPopupWindow.setContentView(contentView);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(call!=null){
                    call.callback("1",i);
                }
            }
        });
    }

    public void show(View parentV){
        // 为了避免部分机型不显示，我们需要重新设置一下宽高
        mPopupWindow.setWidth(500);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        mPopupWindow.setAnimationStyle(R.style.pop_win);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        mPopupWindow.setOutsideTouchable(true);
        // 相对于 + 号正下面，同时可以设置偏移量
        mPopupWindow.showAsDropDown(parentV, -100, 0);
        // 设置pop关闭监听，用于改变背景透明度
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                toggleBright();
            }
        });
    }
    public void cencel(){
        mPopupWindow.dismiss();
    }


//    private float bgAlpha = 1f;
//    private boolean bright = false;
//
//    private static final long DURATION = 500;
//    private static final float START_ALPHA = 0.7f;
//    private static final float END_ALPHA = 1f;
//    private void toggleBright() {
//        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
//        animUtil.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
//        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
//            @Override
//            public void progress(float progress) {
//                // 此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
//                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
//                backgroundAlpha(bgAlpha);
//            }
//        });
//        animUtil.addEndListner(new AnimUtil.EndListener() {
//            @Override
//            public void endUpdate(Animator animator) {
//                // 在一次动画结束的时候，翻转状态
//                bright = !bright;
//            }
//        });
//        animUtil.startAnimator();
//    }
//
//    /**
//     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
//     */
//    private void backgroundAlpha(float bgAlpha) {
//        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
//        // 0.0-1.0
//        lp.alpha = bgAlpha;
//        context.getWindow().setAttributes(lp);
//        // everything behind this window will be dimmed.
//        // 此方法用来设置浮动层，防止部分手机变暗无效
//        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//    }



}

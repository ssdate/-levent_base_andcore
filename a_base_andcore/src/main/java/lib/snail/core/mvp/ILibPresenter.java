package lib.snail.core.mvp;


/**
 * presenter logic
 * 2019-3-22 levent
 * @param <T>
 */
public class ILibPresenter<T extends ILibView> extends ILibPresenterFather<T> {

    //view
//    ILibView iLibView;

    //model
    ILibModel iLibModel ;

    public ILibPresenter(ILibModel viewImpl){
        iLibModel = viewImpl ;
    }

    /***
     * 初始化UI接口
     * 2019-3-27 levent
     */
//    public void initView(){
//        if(viewReference.get() != null ){
//            viewReference.get().initView();
//        }
//    }

    /***
     *  网络请求动态数据
     * 2019-3-26 levent
     */
    public void fetch(){
        if(viewReference.get() != null ){
            viewReference.get().showLoading();
        }
        if(iLibModel != null){
            iLibModel.loadData(new ILibModel.OnLoadListener() {
                @Override
                public void onComplete(String arg, String resObj) {
                    if(viewReference.get() != null){
                        viewReference.get().hideLoading(arg,resObj,null);
                    }
                }
            });
        }
    }

    /***
     * 静态数据构造转换
     * 2019-4-19
     */
//    public void transfer(){
//        if(iLibModel != null){
//            iLibModel.callData(new ILibModel.OnStaticCallBack() {
//                @Override
//                public void callList(String arg, List callList) {
//                    if(viewReference.get() != null){
//                        viewReference.get().hideLoading(arg,"",callList);
//                    }
//                }
//            });
//        }
//    }


}

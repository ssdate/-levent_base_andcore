package lib.snail.core.mvp;

import java.lang.ref.WeakReference;

/***
 * presenter  father
 * 2019-3-22 levent
 */
public class ILibPresenterFather<T> {

    WeakReference<T>  viewReference ;

    /**
     * bind view
     * @param view
     */
    public void attachView(T view){
        viewReference = new WeakReference<T>(view);
    }

    /**
     * unbind view
     */
    public void detachView(){
        if(viewReference!=null){
            viewReference.clear();
        }
    }

}

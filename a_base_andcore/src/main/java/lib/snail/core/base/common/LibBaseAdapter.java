package lib.snail.core.base.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/***
 * 公共adapter实现
 * @param <T>
 */
public abstract class LibBaseAdapter<T,M> extends BaseAdapter {
    public Context context;
    public List<T> li = null;

    /***
     * 设置是否只创建一次视图及数据绑定,防止动态 构建视图重复执行
     * 2019-5-30 levent
     */
    public boolean isOnlyLoadFirst = false ;

    @Override
    public int getCount() {
        if(li!=null && li.size()>0)
            return li.size();
        else
            return 0;
    }
    @Override
    public Object getItem(int position) {
        return li.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract int getLayout();
    public abstract M createHolder();
    public abstract void findView(M vh, View convertView);
    public abstract void buildAdapter(int pos,M vh);
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        M vh = createHolder();
        if (convertView == null) {
            convertView = inflater.inflate(getLayout(), null);
            findView(vh,convertView);
            convertView.setTag(vh);
            if(isOnlyLoadFirst){
                buildAdapter(position,vh);
            }
        } else{
            vh = (M) convertView.getTag();
        }
        if(!isOnlyLoadFirst){
            buildAdapter(position,vh);
        }
        return convertView;
    }


    public void setData(List<T> listb){
        this.li = listb ;
        this.notifyDataSetChanged() ;
    }

    public List<T> getLi() {
        return li;
    }

}

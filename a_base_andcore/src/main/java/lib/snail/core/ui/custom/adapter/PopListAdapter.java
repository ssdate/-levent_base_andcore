package lib.snail.core.ui.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import lib.snail.core.R;
import lib.snail.core.db.entrty.PopObj;


/***
 * poplistwindow 数据adapter
 * 2019-5-15 levent
 */
public class PopListAdapter extends BaseAdapter{

	Context context;
	public List<PopObj> li = null;
	public String attName = "";

	public PopListAdapter(Context con ){
		this.context = con;
	}
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
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		 
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewHolder vh = new ViewHolder(); 
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.pop_list_win_item, null);
			vh.name = (TextView) convertView.findViewById(R.id.l_text);
			vh.lnum = (TextView) convertView.findViewById(R.id.l_num);
			vh.l_line = convertView.findViewById(R.id.l_line);
			vh.itemImg = convertView.findViewById(R.id.item_img);
			vh.imgLay = convertView.findViewById(R.id.lay_img);
			convertView.setTag(vh);
		} else{
			vh = (ViewHolder) convertView.getTag();
		}
		PopObj t =  li.get(position);
		vh.name.setText(t.getName() );
		if(position == li.size()-1){
			vh.l_line.setVisibility(View.GONE);
		}else{
			vh.l_line.setVisibility(View.VISIBLE);
		}
		if(t.getItemImg()==null){
			vh.imgLay.setVisibility(View.GONE);
		}else{
			vh.imgLay.setVisibility(View.VISIBLE);
			vh.itemImg.setBackground(t.getItemImg());
		}
		if(t.getNum()>0){
			vh.lnum.setVisibility(View.VISIBLE);
			vh.lnum.setText(t.getNum()+"");
		}else{
			vh.lnum.setVisibility(View.GONE);
		}
		return convertView;
	} 
	public class ViewHolder{
		public TextView name;
		public TextView lnum;
		public View l_line;
		public ImageView itemImg ;
		public LinearLayout imgLay ;

	}	
	
	public void setData(List<PopObj> listb){
		this.li = listb ;
		this.notifyDataSetChanged() ;
	}

}

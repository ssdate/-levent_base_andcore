package lib.snail.core.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class VideofullScreen extends VideoView {
	
	public VideofullScreen(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle); 
	}

	public VideofullScreen(Context context, AttributeSet attrs) {
		super(context, attrs); 
	}

	public VideofullScreen(Context context) {
		super(context); 
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {// 这里重写onMeasure的方法
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getDefaultSize(0, widthMeasureSpec);// 得到默认的大小（0，宽度测量规范）
		int height = getDefaultSize(0, heightMeasureSpec);// 得到默认的大小（0，高度度测量规范）
		setMeasuredDimension(width, height); // 设置测量尺寸,将高和宽放进去
	}
}
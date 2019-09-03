package lib.snail.core.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public   class CustomDialog extends Dialog {
	
	private Context context ; 


	public CustomDialog(Context context) {
		super(context);
		this.context = context ;
	}
	
	
	public CustomDialog(Context context,int theme) {
		super(context,theme);
		this.context = context ;
	}
	
	

	protected CustomDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.context = context ;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	 

}

package lib.snail.core.ui.dialog;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import lib.snail.core.ui.custom.LoadingDialog;

/***
 * dialogUtils
 * 2019-5-14 levent
 */
public class DialogUtils extends Activity{

	/******************
	 * 通用加载提示框
	 * 2019-5-14 levent
	 ****************/
	private static LoadingDialog loadingDialog;
	public static void  showLoadingDialog(Context context ,String msg ){
		try{
			if(loadingDialog == null){
				loadingDialog  = new LoadingDialog(context,msg);
			}
			loadingDialog.show();
		}catch (Exception e){

		}
	}

	public static void cencelLoadingDialog(){
		try{
			if(loadingDialog!=null){
				loadingDialog.dismiss();
				loadingDialog = null ;
			}
		}catch (Exception e){

		}

	}

	/******************************************
	 * 基于ProgressDialog 简单封装的全局通用dialog
	 * before
	 ****************************************/
//	private static ProgressDialog progressDialog; // 加载进度

	/**
	 * 显示正在加载提示 
	 * */
//	public static void ShowProgressDialog(Context context, String msg) {
//		// 加载网络
//		progressDialog = new ProgressDialog(context);
//		progressDialog.setMessage(msg);
//		progressDialog.setCancelable(true);
//		progressDialog.show();
//	}

	/** 加载完成，关闭提 */
//	public static void centelProgressdialog() {
//		if(progressDialog!=null && progressDialog.isShowing()){
//			try{
//				progressDialog.dismiss();
//			}catch(Exception e){
//
//			}
//
//		}
//	}

	/**
	 * 显示退出提示框
	 * */
	public static void showExitDialog(Context context, String title,
			String msg, int resId) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
				.setIcon(resId)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// context.finish();
						System.exit(0);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				}).show();
	}

	 
}

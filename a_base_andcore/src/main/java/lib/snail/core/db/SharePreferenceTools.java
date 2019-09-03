package lib.snail.core.db;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceTools {
	 
	public static SharedPreferences sys_Share_tool = null ;
	public static SharedPreferences.Editor editor = null ;
	
	
	/**
	 * 初始化共享信息
	 * */
	public static void initShare(Context context){
		if(sys_Share_tool==null)
		{
			sys_Share_tool  = context.getSharedPreferences("_libli_sharecore", 0);
			editor = sys_Share_tool.edit();
		}   
		
	}
	
	/**
	 * 编辑boole值
	 * */
	public static void editBooleanValue(String name,Context context,boolean b)
	{
		initShare(context);
		editor.putBoolean(name, b);	
		editor.commit(); 
	}
	/**
	 * 编辑String 值
	 * */
	public static void editStringValue(String name,Context context,String str)
	{
		initShare(context);
		editor.putString(name, str);
		editor.commit(); 
	}
	
	/**
	 * 获取boole值
	 * */
	public static boolean getBooleanValue(String name,Context context)
	{
		initShare(context);
		return sys_Share_tool.getBoolean(name, false); 
	}
	/**
	 * 编辑String 值
	 * */
	public static String getStringValue(String name,Context context)
	{
		initShare(context);
		return sys_Share_tool.getString(name,"");
	}
	
	/**
	 * 编辑Int 值
	 * */
	public static void editIntValue(String name,Context context,int str)
	{
		initShare(context);
		editor.putInt(name, str);
		editor.commit(); 
	}
	
	/**
	 * 获取Int 值
	 * */
	public static int getIntValue(String name,Context context)
	{
		initShare(context);
		return sys_Share_tool.getInt(name, 0);
	}
	
	/**
	 * 编辑Long值
	 * */
	public static void editLongValue(String name,Context context,long str)
	{
		initShare(context);
		editor.putLong(name, str);
		editor.commit(); 
	}
	
	/**
	 * 获取Long 值
	 * */
	public static long getLongValue(String name,Context context)
	{
		initShare(context);
		return sys_Share_tool.getLong(name, 0);
	}
	
	/**
	 * 删除配置
	 * */
	public static void delConfig(String name,Context context)
	{
		initShare(context);
		editor.remove(name);
		editor.commit(); 
	}

}

package com.karger.booknote;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import com.karger.booknote.util.Constant;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AppApplication extends Application{
	
	DisplayMetrics dm ;
	WindowManager wm;
	private int screenWidth = 0;
	private int screenHeight = 0;
	
	public final int MARGIN_TOP = 1;
	public final int MARGIN_BOTTON = 2;
	public final int MARGIN_LEFT = 3;
	public final int MARGIN_RIGHT = 4;
	
	private long STATE_TIME = 5*60*1000;//五分钟后锁屏
	
	private SharedPreferences preferences;
	private Editor editor;
	
	private String rootPath;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		dm = new DisplayMetrics();
		wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
		rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
		editor = preferences.edit();
		
	}
	
	/**
	 * 获取屏幕宽度
	 * @return 屏幕宽度
	 */
	public int getScreenWidth(){
		return screenWidth;
	}
	
	/**
	 * 获取屏幕高度
	 * @return 屏幕高度
	 */
	public int getScreenHeight(){
		return screenHeight;
	}
	
	/**
	 * 获取资源的ID值
	 * @param idName
	 * @return
	 */
	public int getResId(String idName){
		return getResources().getIdentifier(idName, "id",getPackageName());
	}
	/**
	 * 获取layout文件
	 * @param layout
	 * @return
	 */
	public int getResLayout(String layout){
		return getResources().getIdentifier(layout, "layout", getPackageName());
	}
	
	/**
	 * 获取color
	 * @param colorName
	 * @return
	 */
	public int getResColor(String colorName){
		return getResources().getIdentifier(colorName, "color", getPackageName());
	}
	
	
	/**
	 * 获取网络信息
	 * @return
	 * 
	 */
	public NetworkInfo getNetworkInfo(){
		ConnectivityManager cm = 
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}
	
	/**
	 * 设置View的大小
	 * @param view
	 * @param width
	 * @param height
	 */
	public void setViewLayout(View view,int width,int height){
		LayoutParams layout = view.getLayoutParams();
		layout.width = width;
		layout.height = height;
		view.setLayoutParams(layout);
	}
	
	/**
	 * 設置view之間的間距,前提view必須是在LinearLayout佈局或RelativeLayout布局中
	 * 
	 * @param view
	 * @param scale 比例（上下距離是高*比例，左右距離是寬*比例）
	 * @param direction 方向
	 */
	public void setViewLayoutMagin(View view, float scale, int direction) {

		if(view.getLayoutParams() instanceof LinearLayout.LayoutParams){
//			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view.getLayoutParams());
			LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) view.getLayoutParams();
			switch (direction) {
			case MARGIN_TOP:
				params.topMargin = (int) (scale * screenHeight);
				break;
			case MARGIN_BOTTON:
				params.bottomMargin = (int) (scale * screenHeight);
				break;
			case MARGIN_LEFT:
				params.leftMargin = (int) (scale * screenWidth);
				break;
			case MARGIN_RIGHT:
				params.rightMargin = (int) (scale * screenWidth);
				break;
			default:
				break;
			}
			view.setLayoutParams(params);
		}else if(view.getLayoutParams() instanceof RelativeLayout.LayoutParams){
//			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(view.getLayoutParams());
			RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) view.getLayoutParams();
			switch (direction) {
			case MARGIN_TOP:
				params.topMargin = (int) (scale * screenHeight);
				break;
			case MARGIN_BOTTON:
				params.bottomMargin = (int) (scale * screenHeight);
				break;
			case MARGIN_LEFT:
				params.leftMargin = (int) (scale * screenWidth);
				break;
			case MARGIN_RIGHT:
				params.rightMargin = (int) (scale * screenWidth);
				break;
			default:
				break;
			}
		
			view.setLayoutParams(params);
		}
	}
	
	/**
	 * 
	 * @param view
	 * @param left 左边距离的比例（相对于宽来说）
	 * @param top  上边距离的比例 （先对于高来说）
	 * @param right 右边距离的比例
	 * @param bottom 底部距离的比例
	 */
	public void setViewLayoutMagin(View view, float left, float top,float right, float bottom) {

		if(view.getLayoutParams() instanceof LinearLayout.LayoutParams){
			LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) view.getLayoutParams();
			if(left > 0)
				params.leftMargin = (int) (left *screenWidth);
			if(top > 0)
				params.topMargin = (int) (top * screenHeight);
			if(right > 0)
				params.rightMargin = (int) (right * screenWidth);
			if(bottom > 0)
				params.bottomMargin = (int) (bottom * screenHeight);
			view.setLayoutParams(params);
		}else if(view.getLayoutParams() instanceof RelativeLayout.LayoutParams){
			RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) view.getLayoutParams();
			if(left > 0)
				params.leftMargin = (int) (left *screenWidth);
			if(top > 0)
				params.topMargin = (int) (top * screenHeight);
			if(right > 0)
				params.rightMargin = (int) (right * screenWidth);
			if(bottom > 0)
				params.bottomMargin = (int) (bottom * screenHeight);
			view.setLayoutParams(params);
		}
	}
	
	/**
	 * 调用系统相机
	 * @return pic file dir:/storage/sdcard0/BookNote/20141230_071731.jpg
	 */ 
	public Intent getCameraIntent(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
//		String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+".jpg";
//		File file = new File(rootPath + File.separator + Constant.appPath,name);
//		if(!file.exists()){
//			try {
//				file.getParentFile().mkdirs();
//				file.createNewFile();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		Uri pictureUri = Uri.fromFile(file);
//		Log.e("app", "pic file dir:"+file.getAbsolutePath());
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
//		
		return intent;
	}
	
	/**
	 * 动用系统相册
	 * @return
	 */
	public Intent getPhotoIntent(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		return intent;
	}
	
	public String getRootPath(){
		return rootPath + File.separator + Constant.appPath + File.separator;
	}
	
	public void saveLongPreference(String key,Long value){
		editor.putLong(key, value).commit();
	}
	
	public void resetLockTime(){
		saveLongPreference(Constant.LOCK_TIME, System.currentTimeMillis());
	}
	
	public boolean canLockScreen(){
		long currentTime = System.currentTimeMillis();
		if(currentTime - 
				preferences.getLong(Constant.LOCK_TIME, currentTime)>=STATE_TIME){
			return true;
		}
		return false;
	}
	
	public String getStrPreference(String key){
		return preferences.getString(key, null);
	}
	
	public void saveStrPreference(String key,String value){
		editor.putString(key, value);
	}
	
	public void toast(String info){
		Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
	}
	
}

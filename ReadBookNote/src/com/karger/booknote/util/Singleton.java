package com.karger.booknote.util;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.karger.booknote.AppApplication;
import com.karger.booknote.entity.ImageFolder;
import com.karger.readbooknote.R;


public class Singleton {
	
	private ImageFolder mImageFolder;
	
	private Thread thread;
	
	private PopupWindow lockScreenWindow;//锁屏弹窗
	
	private View lockScreenWinParentView;
	
//	Handler handler;
	
	private boolean firstIn = false;
	
	private static class SingletonHolder{
		private static final Singleton instance = new Singleton();
	}
	
	private Singleton(){}
	
	public static Singleton getInstance(){
		return SingletonHolder.instance;
	}

	public ImageFolder getmImageFolder() {
		return mImageFolder;
	}

	public void setmImageFolder(ImageFolder mImageFolder) {
		this.mImageFolder = mImageFolder;
	}
	
	public void setLockScreenWinParentView(View lockScreenWinParentView){
		this.lockScreenWinParentView = lockScreenWinParentView;
	}
	
	public void initLockScreen(final AppApplication app){
		app.saveStrPreference(Constant.PASS_WORD_PREFERENCE, "123456");
		app.saveLongPreference(Constant.LOCK_TIME, System.currentTimeMillis());
		initLockScreenThread(app);
		createPopuWindow(app);
		
	}
	
	public void startLockScreenThread(){
		if(thread !=null && !thread.isAlive())
			thread.start();
	}
	
	private void initLockScreenThread(final AppApplication app){
		thread = new Thread(){
			Handler handler = new Handler(app.getMainLooper()){
				public void handleMessage(android.os.Message msg) {
					if(app.canLockScreen() || firstIn){
						if(lockScreenWindow != null){
							lockScreenWindow.showAtLocation(lockScreenWinParentView, Gravity.CENTER, 0, 0);
							firstIn = false;
						}
					}
					
				}
			};
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
						handler.sendEmptyMessage(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	private void createPopuWindow(final AppApplication app){
		
		View contentView = LayoutInflater.from(app).inflate(R.layout.lock_screen_window_layout, null);
		lockScreenWindow = new PopupWindow(contentView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,true);
		lockScreenWindow.setFocusable(true);
		lockScreenWindow.setTouchable(true);
		Button button = (Button) contentView.findViewById(R.id.bt_lock_screen_commit);
		final EditText editText = (EditText) contentView.findViewById(R.id.et_lock_screen_password);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pass = editText.getText().toString();
				if(pass.equals(app.getStrPreference(Constant.PASS_WORD_PREFERENCE))){
					lockScreenWindow.dismiss();
					app.refleshLockTime();
					editText.setText("");
				}else{
					app.toast("Password error");
				}
			}
		});
	}
	
	private OnTouchListener onTouchListener;
	public OnTouchListener getTouchLIstener(final AppApplication app){
		if(onTouchListener == null){
			onTouchListener = new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					app.saveLongPreference(Constant.LOCK_TIME, System.currentTimeMillis());
					if(event.getAction() == MotionEvent.ACTION_UP){
						v.performClick();
					}
					return false;
				}
			};
		}
		return onTouchListener;
	}

	public void setFirstIn(boolean firstIn) {
		this.firstIn = firstIn;
	}
	
}

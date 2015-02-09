package com.karger.booknote.ui;

import com.karger.booknote.AppApplication;
import com.karger.booknote.util.Constant;
import com.karger.booknote.util.Singleton;
import com.karger.readbooknote.R;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;

@SuppressLint("NewApi")
public  class BaseActivity extends FragmentActivity{
	protected AppApplication app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		app = (AppApplication) getApplication();
		initView();
		setLayout();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		app.saveLongPreference(Constant.LOCK_TIME, System.currentTimeMillis());
		Singleton.getInstance().startLockScreenThread();
	}
	
	protected void initView(){
		
	}
	
	protected void setLayout(){};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.search, menu);
		SearchManager sm = 
				(SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView sv = (SearchView) (menu.findItem(app.getResId("action_search")).getActionView());
		relateSearch(sv);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.e("BaseActivity", event.getAction()+"");
		if(event.getAction() == MotionEvent.ACTION_DOWN)
			app.saveLongPreference(Constant.LOCK_TIME, System.currentTimeMillis());
		return super.onTouchEvent(event);
	}
	
	
	protected void relateSearch(SearchView sv){
		
	}
	
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
	
}

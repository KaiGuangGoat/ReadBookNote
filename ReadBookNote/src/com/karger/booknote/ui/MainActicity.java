package com.karger.booknote.ui;

import com.karger.booknote.fragment.BaseFragment;
import com.karger.booknote.fragment.HomePageFragment;
import com.karger.booknote.fragment.MeinfoPageFragment;
import com.karger.booknote.util.Constant;
import com.karger.booknote.util.Factory;
import com.karger.booknote.util.Singleton;
import com.karger.readbooknote.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

@SuppressLint("InlinedApi")
public class MainActicity extends BaseActivity{
	
	private FragmentManager fm;
	private BaseFragment mainPage;
	private BaseFragment friendPage;
	private MeinfoPageFragment meinfoPage;
	
	private Button bt_main_page;
	private Button bt_friend_list;
	private Button bt_me_info;
	
	private int currentFragmentPage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		fm = getSupportFragmentManager();
		
		showPage(currentFragmentPage);
	}
	
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		Log.e("Main", "init");
		currentFragmentPage = 0;
		setContentView(app.getResLayout("main_layout"));
		
		bt_main_page = (Button) findViewById(app.getResId("bt_main_page"));
		bt_friend_list = (Button) findViewById(app.getResId("bt_my_friend"));
		bt_me_info = (Button) findViewById(app.getResId("bt_me_page"));
		
		bt_main_page.setOnClickListener(clickListener);
		bt_friend_list.setOnClickListener(clickListener);
		bt_me_info.setOnClickListener(clickListener);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Singleton.getInstance().setLockScreenWinParentView(bt_main_page);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode,resultCode,data);//添加这一句才会调用Fragment的onActivityResult
		Log.e("main", "requestCode:"+requestCode+"\nresultCode:"+resultCode);
		showPage(currentFragmentPage);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.e("BaseActivity", event.getAction()+"");
		return super.onTouchEvent(event);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch(view.getId()){
			case R.id.bt_main_page:
				showPage(0);
				break;
			case R.id.bt_my_friend:
				showPage(1);
				break;
			case R.id.bt_me_page:
				showPage(2);
				break;
			}
		}
	};
	
	protected void showPage(int index){
		currentFragmentPage = index;
		FragmentTransaction transaction = fm.beginTransaction();
		hideAllPage(transaction);
		clearFootState();
		switch(index){
			case 0:
				showMainPage(transaction);
				bt_main_page.setBackgroundResource(R.drawable.main_footer_me_press);
				break;
			case 1:
				showFriendPage(transaction);
				bt_friend_list.setBackgroundResource(R.drawable.main_footer_friend_press);
				break;
			case 2:
				showMeinfoPage(transaction);
				bt_me_info.setBackgroundResource(R.drawable.main_footer_me_press);
		}
		transaction.commitAllowingStateLoss();
	}
	
	private void hideAllPage(FragmentTransaction transaction){
		if(mainPage!=null){
			transaction.hide(mainPage);
		}
		if(friendPage!=null){
			transaction.hide(friendPage);
		}
		if(meinfoPage!=null){
			transaction.hide(meinfoPage);
		}
	}
	
	private void showMainPage(FragmentTransaction transaction){
		if(mainPage == null){
			mainPage = Factory.getInstance().getFragment(R.id.bt_main_page);
			transaction.add(app.getResId("frg_change_page"), mainPage);
		}else{
			transaction.show(mainPage);
		}
	}
	private void showFriendPage(FragmentTransaction transaction){
		if(friendPage == null){
			friendPage = Factory.getInstance().getFragment(R.id.bt_my_friend);
			transaction.add(app.getResId("frg_change_page"), friendPage);
		}else{
			transaction.show(friendPage);
		}
	}
	private void showMeinfoPage(FragmentTransaction transaction){
		if(meinfoPage == null){
			meinfoPage = (MeinfoPageFragment) Factory.getInstance().getFragment(R.id.bt_me_page);
			transaction.add(app.getResId("frg_change_page"), meinfoPage);
		}else{
			transaction.show(meinfoPage);
		}
	}
	
	private void clearFootState(){
		bt_main_page.setBackgroundResource(R.drawable.main_button);
		bt_friend_list.setBackgroundResource(R.drawable.friend_button);
		bt_me_info.setBackgroundResource(R.drawable.me_button);
	}
}

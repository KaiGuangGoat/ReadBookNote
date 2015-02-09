package com.karger.booknote.util;

import android.content.Context;
import android.view.View;

import com.karger.booknote.fragment.BaseFragment;
import com.karger.booknote.fragment.FriendPageFragment;
import com.karger.booknote.fragment.HomePageFragment;
import com.karger.booknote.fragment.MeinfoPageFragment;
import com.karger.readbooknote.R;

/**
 * ��������
 * @author Karger Wang
 *
 */
public class Factory {
	
	private static Factory instance = new Factory();
	
	private HomePageFragment homePage;
	private FriendPageFragment friendPage;
	private MeinfoPageFragment meinfoPage;
	
	public static Factory getInstance(){
		return instance;
	}
	
	public BaseFragment getFragment(int id){
		if(R.id.bt_main_page == id){
			if(homePage == null){
				homePage = new HomePageFragment(); 
			}
			return homePage;
		}else if(R.id.bt_my_friend == id){
			if(friendPage == null){
				friendPage = new FriendPageFragment();
			}
			return friendPage;
		}else if(R.id.bt_me_page == id){
			if(meinfoPage == null){
				meinfoPage = new MeinfoPageFragment();
			}
			return meinfoPage;
		}
		return null;
	}
	
	public View createView(Context context,int id){
		
		return null;
	}
	
	
	
}

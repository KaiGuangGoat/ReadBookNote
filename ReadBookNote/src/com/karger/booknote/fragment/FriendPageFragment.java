package com.karger.booknote.fragment;

import com.karger.readbooknote.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FriendPageFragment extends BaseFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(app.getResLayout("friend_layout"), container,false);
		
		return view;
	}
}

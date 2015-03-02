package com.karger.booknote.fragment;

import com.karger.booknote.AppApplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment{
	
	AppApplication app;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (AppApplication) getActivity().getApplication();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
		super.onPause();
	}
	
	
	
	protected void initView(View view,LayoutInflater inflater){
		
	}
}

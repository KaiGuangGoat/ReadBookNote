package com.karger.booknote.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.karger.booknote.util.Constant;
import com.karger.booknote.util.Singleton;
import com.karger.readbooknote.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomePageFragment extends BaseFragment{
	private ViewPager viewPage ;
	private List<View> views;
	private List<TextView> msgsState;
	private TextView tv_read,tv_note,tv_share;
	private TextView tv_line1,tv_line2,tv_line3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(app.getResLayout("home_layout"), container,false);
		viewPage = (ViewPager) view.findViewById(app.getResId("vp_change_home"));
		initView(view,inflater);
		viewPage.setAdapter(new MyAdapter());
		viewPage.setOnPageChangeListener(new PageChange());
		
		return view;
	}
	
	protected void initView(View view,LayoutInflater inflater){
		views = new ArrayList<View>();
		msgsState = new ArrayList<TextView>();
		views.add(inflater.inflate(R.layout.home_layout_read, null));
		views.add(inflater.inflate(R.layout.home_layout_notebook, null));
		views.add(inflater.inflate(R.layout.home_layout_share, null));
		
		tv_read = (TextView) view.findViewById(R.id.tv_reader_head_read);
		tv_note = (TextView) view.findViewById(R.id.tv_reader_head_note);
		tv_share = (TextView) view.findViewById(R.id.tv_reader_head_share);
		msgsState.add(tv_read);
		msgsState.add(tv_note);
		msgsState.add(tv_share);
		
		tv_line1 = (TextView) view.findViewById(R.id.tv_reader_head_line1);
		tv_line2 = (TextView) view.findViewById(R.id.tv_reader_head_line2);
		tv_line3 = (TextView) view.findViewById(R.id.tv_reader_head_line3);
		msgsState.add(tv_line1);
		msgsState.add(tv_line2);
		msgsState.add(tv_line3);
	}
	
	private void clearHeadState(){
		TextView tv = null;
		for(int i=0;i<msgsState.size();i++){
			tv = msgsState.get(i);
			if(i<=2){
				tv.setTextColor(Color.parseColor("#000000"));//black
			}else{
				tv.setBackgroundColor(Color.parseColor("#00000000"));//transparent
			}
		}
	}
	
	class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

		@Override
		public boolean isViewFromObject(View paramView, Object paramObject) {
			// TODO Auto-generated method stub
			return paramView == paramObject;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = views.get(position);
			view.setOnTouchListener(Singleton.getInstance().getTouchLIstener(app));
			viewPage.addView(view);
			return view;
		}
	}
	
	class PageChange implements OnPageChangeListener{
		int pageCounts;
		public PageChange() {
			pageCounts = views.size();
		}
		

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			clearHeadState();
			msgsState.get(position).setTextColor(Color.parseColor("#DC9956"));
			msgsState.get(position+pageCounts).setBackgroundColor(Color.parseColor("#DC9956"));
		}
		
	}
	
}

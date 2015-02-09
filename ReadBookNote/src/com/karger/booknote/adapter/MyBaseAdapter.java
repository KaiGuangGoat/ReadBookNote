package com.karger.booknote.adapter;

import java.util.List;

import com.karger.booknote.AppApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter extends BaseAdapter{
	
	protected List dataList;
	protected LayoutInflater inflater;
	protected AppApplication app;
	
	public MyBaseAdapter(List filePaths,Context context){
		inflater = LayoutInflater.from(context);
		this.dataList = filePaths;
		app = (AppApplication) context.getApplicationContext();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(dataList == null ){
			return 0;
		}
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseViewHolder baseViewHolder = null;
		if(convertView == null){
			convertView = getConvertView();
			baseViewHolder = initViewHolder(convertView);
			convertView.setTag(baseViewHolder);
		}else{
			baseViewHolder = (BaseViewHolder) convertView.getTag();
		}
		setContentView(position, baseViewHolder);
		return convertView;
	}
	
	
	protected abstract View getConvertView();
	
	protected abstract BaseViewHolder initViewHolder(View convertView);
	
	protected abstract void setContentView(int position, BaseViewHolder baseViewHolder);
	
	
	public class BaseViewHolder{
		
	}

}

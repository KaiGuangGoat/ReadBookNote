package com.karger.booknote.adapter;

import java.util.List;

import com.karger.booknote.AppApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter extends BaseAdapter{
	
	protected List dataList; //需要显示的数据
	protected LayoutInflater inflater;
	protected AppApplication app;
	
	public MyBaseAdapter(List dataList,Context context){
		inflater = LayoutInflater.from(context);
		this.dataList = dataList;
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
	
	/**
	 * 获取ConvertView
	 * @return
	 */
	protected abstract View getConvertView();
	/**
	 * 初始化ViewHolder,把ViewHolder相关的属性进行赋值在这个方法里面进行
	 * @param convertView
	 * @return
	 */
	protected abstract BaseViewHolder initViewHolder(View convertView);
	/**
	 * 这里主要对contentview的Item进行处理，相关的事件，逻辑都在这里进行
	 * @param position
	 * @param baseViewHolder
	 */
	protected abstract void setContentView(int position, BaseViewHolder baseViewHolder);
	
	//持有Item相关的类
	public static class BaseViewHolder{
		
	}

}

package com.karger.booknote.adapter;

import java.io.File;
import java.util.List;

import com.karger.booknote.AppApplication;
import com.karger.booknote.util.BitmapUtil;
import com.karger.booknote.util.ImageLoader;
import com.karger.readbooknote.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

public class PictureSelectorAdapter extends MyBaseAdapter{
	
	private int selectedNum = 0;
	public int MAX_SELECTED_NUM = 9;
	
	private CheckBoxListener listener;

	public PictureSelectorAdapter(List<File> filePaths, Context context) {
		super(filePaths, context);
	}
	
	public void setCheckBoxListener(CheckBoxListener listener){
		this.listener = listener;
	}
	
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView!=null){
			viewHolder = (ViewHolder) convertView.getTag();
		}else{
			convertView = 
					inflater.inflate(R.layout.picture_selector_layout_item, null,false);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_img_item);
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_select_picture_item);
			convertView.setTag(viewHolder);
		}
		
//		viewHolder.imageView.setImageBitmap(bitmap);
		clickImageView(viewHolder.imageView, viewHolder.checkBox);
		String path = ((File)dataList.get(position)).getAbsolutePath();
		checkBoxListener(viewHolder.imageView,viewHolder.checkBox,path);
		ImageLoader.getInstance().addTask(path, viewHolder.imageView);
		return convertView;
	}
	
	private void clickImageView(final ImageView imageView,final CheckBox checkBox){
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkBox.setChecked(!checkBox.isChecked());
			}
		});
	}
	
	private void checkBoxListener(final ImageView imageView,final CheckBox checkBox,final String path){
//		checkBox.setBackgroundColor(Color.parseColor("#77000000"));
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(listener == null){
					return ;
				}
				if(isChecked){
					selectedNum ++;
					if(selectedNum > MAX_SELECTED_NUM){
						selectedNum = MAX_SELECTED_NUM; 
						checkBox.setChecked(false);
						app.toast("最多只能够选"+MAX_SELECTED_NUM+"张");
						return;
					}
					listener.onChecked(path,selectedNum);
					imageView.setColorFilter(Color.parseColor("#77000000"));//点击图片变暗
				}else{
					selectedNum --;
					if(selectedNum <0){
						selectedNum = 0;
					}
					listener.onCheckedCancle(path,selectedNum);
					imageView.setColorFilter(null);//恢复
				}
				
			}
		});
	}
	
	
	
	public class ViewHolder extends BaseViewHolder{
		ImageView imageView;
		public CheckBox checkBox;
	}
	
	public interface CheckBoxListener{
		public void onChecked(String imgFilePath,int selectedNum);
		public void onCheckedCancle(String imgFilePath,int selectedNum);
	}

	@Override
	protected View getConvertView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BaseViewHolder initViewHolder(View convertView) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setContentView(int position, BaseViewHolder baseViewHolder) {
		// TODO Auto-generated method stub
		
	}

}

package com.karger.booknote.adapter;

import java.io.File;
import java.util.List;

import com.karger.booknote.entity.FolderPath;
import com.karger.booknote.util.ImageLoader;
import com.karger.readbooknote.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureSelectorMoreAdapter extends MyBaseAdapter {
	
	private PictureSelectorMoreListener listener;

	public PictureSelectorMoreAdapter(List<FolderPath> filePaths, Context context) {
		super(filePaths, context);
	}

	@Override
	protected View getConvertView() {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.picture_selector_popwindow_more_item, null);
	}

	@Override
	protected BaseViewHolder initViewHolder(View convertView) {
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_picture_select_more_item);
		viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_picture_select_more_item);
		return viewHolder;
	} 

	@Override
	protected void setContentView(int position, BaseViewHolder baseViewHolder) {
		ViewHolder viewHolder = (ViewHolder) baseViewHolder;
		FolderPath folder = (FolderPath) dataList.get(position);
		viewHolder.textView.setText(new File(folder.getParentPath()).getName());
		ImageLoader.getInstance().addTask(folder.getFirstPath(), viewHolder.imageView);
		final String folderPath = folder.getParentPath();
		viewHolder.imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null){
					listener.onSelected(folderPath);
				}
			}
		});
		viewHolder.textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null){
					listener.onSelected(folderPath);
				}
			}
		});
	}
	
	public class ViewHolder extends BaseViewHolder{
		ImageView imageView;
		TextView textView;
	}
	
	public void setListener(PictureSelectorMoreListener listener){
		this.listener = listener;
	}

	public interface PictureSelectorMoreListener{
		public void onSelected(String folderPath);
	}

}

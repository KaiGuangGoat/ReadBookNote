package com.karger.booknote.fragment;

import java.io.File;
import java.util.ArrayList;

import com.karger.booknote.ui.MainActicity;
import com.karger.booknote.ui.PictureSelectorActivity;
import com.karger.booknote.util.BitmapUtil;
import com.karger.booknote.util.Constant;
import com.karger.booknote.util.FileUtil;
import com.karger.booknote.util.UIHelper;
import com.karger.booknote.widget.CircleImageView;
import com.karger.readbooknote.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MeinfoPageFragment extends BaseFragment implements View.OnClickListener{
	private CircleImageView headImg;
	private TextView tv_userName,tv_userSign;
	private RelativeLayout ry_me_info_head;
	private ScrollView meinfoScrollView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(app.getResLayout("me_info_layout"), container,false);
		initView(view, inflater);
		
		return view;
	}
	
	@Override
	protected void initView(View view, LayoutInflater inflater) {
		headImg = (CircleImageView) view.findViewById(R.id.civ_head_portail);
		setHeadImg();
		headImg.setOnClickListener(this);
		
		tv_userName = (TextView) view.findViewById(R.id.tv_user_name);
		tv_userSign = (TextView) view.findViewById(R.id.tv_user_sign);
		
		ry_me_info_head = (RelativeLayout) view.findViewById(R.id.ry_me_info_header);
		meinfoScrollView = (ScrollView) view.findViewById(R.id.sl_me_info_layout);
		initScrollView();
	}
	
	private void initScrollView(){
		meinfoScrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.e("MeinfoPage", "touch");
				if(event.getAction() == MotionEvent.ACTION_UP)
					app.refleshLockTime();
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.civ_head_portail:
			dialog();
			break;
		}
		
	}
	
	private void dialog(){
		AlertDialog dialog = 
				new AlertDialog.Builder(getActivity())
											.setTitle("请进行操作")
											.setPositiveButton("拍一张", new OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
													startActivityForResult(app.getCameraIntent(), Constant.RESULT_CODE_CAMERA);
												}
											})
											.setNegativeButton("相册选一张", new OnClickListener() {
												
												@Override
												public void onClick(DialogInterface dialog, int which) {
													Intent intent = new Intent(getActivity(),PictureSelectorActivity.class);
													intent.putExtra(Constant.SELECT_IMGS_MAX_SIZE, 1);
													startActivityForResult(intent,Constant.RESULT_CODE_SELECTED_IMG_LIST);
//													startActivityForResult(app.getPhotoIntent(), Constant.RESULT_CODE_PHOTO);
												}
											})
											.create();
		dialog.show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		// TODO Auto-generated method stub
		super.onActivityResult(requestCode,resultCode,data);
		Log.e("Meinfo", "requestCode:"+requestCode+"\nresultCode:"+resultCode);
		
		if(resultCode == Activity.RESULT_OK){
			if(requestCode == Constant.RESULT_CODE_CAMERA){
				Bitmap bitmap = (Bitmap) data.getExtras().get("data");
				setAndSaveHeadImg(bitmap);
			}else if(requestCode == Constant.RESULT_CODE_SELECTED_IMG_LIST){
				
				ArrayList<String> imgList = data.getExtras().getStringArrayList(Constant.SELECTED_IMG_LIST);
				if(imgList != null && imgList.size() >0){
					setAndSaveHeadImg(imgList.get(0));
				}
			}
		}
	
		
	}
	
	
	private void setHeadImg(){
		String avatar = getAvatarPath();
		if(FileUtil.pathIsExist(avatar)){
			Bitmap bitmap = BitmapUtil.getBitmap(avatar);
			if(bitmap!=null)
				headImg.setImageBitmap(bitmap);
		}
	}
	
	private void setAndSaveHeadImg(String imgPath){
		setAndSaveHeadImg(BitmapUtil.getBitmap(headImg.getWidth(), headImg.getHeight(),imgPath));
	}
	
	private void setAndSaveHeadImg(Bitmap headBitmap){
		headBitmap = Bitmap.createScaledBitmap(headBitmap, headImg.getWidth(), headImg.getHeight(), true);
		BitmapUtil.saveBitmap(getAvatarPath(), headBitmap);
		headImg.setImageBitmap(headBitmap);
	}
	
	private String getAvatarPath(){
		return app.getRootPath() + Constant.avatarPath + File.separator + Constant.avatarPhoto;
	}
	
}

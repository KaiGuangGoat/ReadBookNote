package com.karger.booknote.ui;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.karger.booknote.adapter.PictureSelectorAdapter;
import com.karger.booknote.adapter.PictureSelectorAdapter.CheckBoxListener;
import com.karger.booknote.adapter.PictureSelectorAdapter.ViewHolder;
import com.karger.booknote.adapter.PictureSelectorMoreAdapter;
import com.karger.booknote.adapter.PictureSelectorMoreAdapter.PictureSelectorMoreListener;
import com.karger.booknote.entity.FolderPath;
import com.karger.booknote.entity.ImageFolder;
import com.karger.booknote.util.Constant;
import com.karger.booknote.util.FileUtil;
import com.karger.booknote.util.ImageLoader;
import com.karger.booknote.util.Singleton;
import com.karger.readbooknote.R;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

public class PictureSelectorActivity extends BaseActivity implements OnClickListener, CheckBoxListener,PictureSelectorMoreListener{
	static final String TAG = "PictureSelectorActivity";
	private Handler mHanHandler = null;
	private LinearLayout mainView;
	private GridView gridView;
	private Button sureButton;
	private Button allImgButton;
	private PopupWindow selectMorePopWin;
	private int select_img_max_size;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mHanHandler = new Handler(getMainLooper()){
			public void handleMessage(android.os.Message msg) {
				if(msg.what == Constant.EMPTY_MSG_0){
					adaptImgSelector(Singleton.getInstance().getmImageFolder().getMaxOfImgFolderPath());
				}
				if(msg.what == Constant.EMPTY_MSG_1){
					PictureSelectorAdapter adapter = new PictureSelectorAdapter(fileList, PictureSelectorActivity.this);
					if(select_img_max_size != 0){
						adapter.MAX_SELECTED_NUM = select_img_max_size;
					}
					adapter.setCheckBoxListener(PictureSelectorActivity.this);
					gridView.setAdapter(adapter);
					gridView.setOnScrollListener(new OnScrollListener() {
						
						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {
							// TODO Auto-generated method stub
							app.resetLockTime();
							switch(scrollState){
							case OnScrollListener.SCROLL_STATE_FLING:
								ImageLoader.getInstance().lock();
								break;
							case OnScrollListener.SCROLL_STATE_IDLE:
								ImageLoader.getInstance().unLock();
								break;
							case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
								ImageLoader.getInstance().lock();
								break;
							}
						}
						
						@Override
						public void onScroll(AbsListView view, int firstVisibleItem,
								int visibleItemCount, int totalItemCount) {
							// TODO Auto-generated method stub
							
						}
						
						
					});
				}
			}
		};
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void initView() {
		super.initView();
		setContentView(R.layout.picture_selector_layout);
		select_img_max_size = getIntent().getIntExtra(Constant.SELECT_IMGS_MAX_SIZE, 0);
		mainView = (LinearLayout) findViewById(R.id.ly_select_picture_main_layout);
		gridView = (GridView) findViewById(R.id.gv_show_picture);
		sureButton = (Button) findViewById(R.id.bt_select_sure);
		allImgButton = (Button) findViewById(R.id.bt_all_image);
		ImageLoader.getInstance().init(this);
		
		sureButton.setOnClickListener(this);
		allImgButton.setOnClickListener(this);
		
		runGetAllImgFolderPath();
		Singleton.getInstance().setLockScreenWinParentView(mainView);
	}
	
	private void runGetAllImgFolderPath(){
		new Thread(){
			public void run() {
				getAllImgFolderPath();
				mHanHandler.sendEmptyMessage(Constant.EMPTY_MSG_0);
			}
		}.start();
	}
	
	private void getAllImgFolderPath(){
		
		if(Singleton.getInstance().getmImageFolder()!=null
				&&Singleton.getInstance().getmImageFolder().hasLoad()){
			return;
		}
		
		int totalImgs = 0;
		int maxOfFolderImgsCount = 0;
		String maxOfImgFolderPath = null;
		
		ContentResolver resolver = getContentResolver();
		Uri imgUri = Media.EXTERNAL_CONTENT_URI;
		Cursor mCursor =  resolver.query(imgUri, null, 
				Media.MIME_TYPE + "= ? or "+ Media.MIME_TYPE + "= ? ", 
				new String[]{"image/jpeg","image/png"}, null);
		
		ImageFolder imageFolder = new ImageFolder();
		List<FolderPath> folderPaths = new ArrayList<FolderPath>();
		List<String> flags = new ArrayList<String>();
		while(mCursor.moveToNext()){
			String imgPath = mCursor.getString(mCursor.getColumnIndex(Media.DATA));
			String folderPath = new File(imgPath).getParent();
			if(flags == null || flags.contains(folderPath)){
				continue;
			}
			File parentFile = new File(folderPath);
			flags.add(folderPath);
			String[] fileList = parentFile.list(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String filename) {
					if(filename.endsWith(".jpg")
							||filename.endsWith(".jpeg")
							||filename.endsWith(".png")){
						return true;
					}
					return false;
				}
			});
			FolderPath folderPathEntity = new FolderPath(folderPath, folderPath + File.separator +fileList[0]);
			folderPaths.add(folderPathEntity);
			int size = fileList.length;
			totalImgs += size;
			if(size > maxOfFolderImgsCount){
				maxOfFolderImgsCount = size;
				maxOfImgFolderPath = parentFile.getAbsolutePath();
			}
		}
		imageFolder.setMaxOfFolderImgsCount(maxOfFolderImgsCount);
		imageFolder.setMaxOfImgFolderPath(maxOfImgFolderPath);
		imageFolder.setTotalImgs(totalImgs);
		imageFolder.setFolderPaths(folderPaths);
		imageFolder.setHasLoad(true);
		Singleton.getInstance().setmImageFolder(imageFolder);
	}
	
	private List<File> fileList;
	private void adaptImgSelector(final String parentFolderPath){
		new Thread(){
			@Override
			public void run() {
				fileList = FileUtil.fileList(parentFolderPath, new String[]{".jpg",".jpeg",".png"},true);
				mHanHandler.sendEmptyMessage(Constant.EMPTY_MSG_1);
			}
		}.start();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_select_sure:
			forResult();
			finish();
			break;
		case R.id.bt_all_image:
			if(selectMorePopWin == null){
				createPopWindow();
			}
			showPopWindow();
			break;
		default:
			break;
		}
		
	}
	
	private void forResult(){
		Intent intent = new Intent();
		intent.putStringArrayListExtra(Constant.SELECTED_IMG_LIST, selectedImgList);
		setResult(RESULT_OK, intent);
	}
	
	private void createPopWindow(){
		View view = LayoutInflater.from(this).inflate(R.layout.picture_selector_popwindow_more, null);
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		ListView listView = (ListView) view.findViewById(R.id.lv_picture_select_more);
		PictureSelectorMoreAdapter adapter = new PictureSelectorMoreAdapter(
				Singleton.getInstance().getmImageFolder().getFolderPaths(), this);
		adapter.setListener(this);
		listView.setAdapter(adapter);
		selectMorePopWin = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		selectMorePopWin.setTouchable(true);
		selectMorePopWin.setOutsideTouchable(true);
		view.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_BACK){
					selectMorePopWin.dismiss();
					return true;
				}
				return false;
			}
		});
	}
	
	private void showPopWindow(){
		selectMorePopWin.showAtLocation(mainView, Gravity.BOTTOM, 0, 0);
	}

	private ArrayList<String> selectedImgList = new ArrayList<String>();
	@Override
	public void onChecked(String imgFilePath,int selectedNum) {
		// TODO Auto-generated method stub
//		Log.e(TAG, "imgSelectPath:"+imgFilePath);
		selectedImgList.add(imgFilePath);
		sureButton.setText("确定("+selectedNum+")");
	}

	@Override
	public void onCheckedCancle(String imgFilePath,int selectedNum) {
		// TODO Auto-generated method stub
		if(selectedImgList.contains(imgFilePath)){
			selectedImgList.remove(imgFilePath);
			String sure = "确定("+selectedNum+")";
			if(selectedNum == 0){
				sure = "确定";
			}
			sureButton.setText(sure);
		}
	}

	@Override
	public void onSelected(String folderPath) {
		if(selectMorePopWin != null)
			selectMorePopWin.dismiss();
//		app.toast(folderPath);
		adaptImgSelector(folderPath);
	}

}

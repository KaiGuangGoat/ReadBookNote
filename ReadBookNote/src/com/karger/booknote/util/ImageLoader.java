package com.karger.booknote.util;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.karger.booknote.AppApplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;


public class ImageLoader {
	private static class SingletonHolder{
		private static final ImageLoader instance = new ImageLoader();
	}
	
	private ImageLoader(){}
	
	public static ImageLoader getInstance(){
		return SingletonHolder.instance;
	}
	
	private ImageCache imageCache;
	
	private HashMap<Integer, ImageView> taskMap = new HashMap<Integer, ImageView>();
	
	private ExecutorService mExecutorService;
	
	private boolean isLock = false;
	
	private boolean isDefaultWH;//是否使用默认的宽高设置图片
	
	private Context context;
	
	private int defaultImgWidth;
	
	private int defaultImgHeight;
	
	private int imgWidth;
	
	private int imgHeight;
	
	public void init(Context context){
		int threadNum = Runtime.getRuntime().availableProcessors()+1;
		imageCache = new ImageCache(5);
		mExecutorService = Executors.newFixedThreadPool(threadNum);
		isDefaultWH = true;
		this.context = context;
		AppApplication app = (AppApplication) context.getApplicationContext();
		defaultImgHeight = defaultImgWidth = app.getScreenWidth()/3;
		imgWidth = defaultImgWidth;
		imgHeight = defaultImgHeight;
	}
	
	public void unLock(){
		isLock = false;
		doTask();
	}
	
	public void lock(){
		isLock = true;
	}
	
	public void addTask(String url,ImageView imageView,int dstWidth,int dstHeight){
		isDefaultWH = false;
		this.imgWidth = dstWidth;
		this.imgHeight = dstHeight;
		addTask(url, imageView);
	}
	
	public void addTask(String url,ImageView imageView){
		isDefaultWH = true;
		synchronized (taskMap) {
			imageView.setTag(url);
			taskMap.put(imageView.hashCode(), imageView);
		}
		if(!isLock){
			doTask();
		}
	}
	
	public void doTask(){
		synchronized (taskMap) {
			for(ImageView imageView:taskMap.values()){
				if(imageView != null){
					if(imageView.getTag() != null){
						String url = (String)imageView.getTag();
						Bitmap bitmap = imageCache.getBitmap(url);
						if(bitmap != null){
							imageView.setImageBitmap(bitmap);
						}else{
							loadImage(url, imageView);
						}
						
					}
				}
			}
			taskMap.clear();
		}
		
	}
	
	private void loadImage(String url,ImageView imageView){
		mExecutorService.submit(new TaskWithResult(url, imageView));
	}
	
	private class TaskWithResult implements Callable<String>{
		
		private String url;
		private ImageView imageView;
		private TaskHandler handler;
		
		public TaskWithResult(String url,ImageView imageView){
			this.url = url;
			this.imageView = imageView;
			handler = new TaskHandler(imageView,url,context.getMainLooper());
		}

		@Override
		public String call() throws Exception {
			Message msg = new Message();
			Bitmap bitmap = imageCache.getBitmap(url);
			if(bitmap == null){
				int w = defaultImgWidth;
				int h = defaultImgHeight;
				if(!isDefaultWH){
					w = imgWidth;
					h = imgHeight;
				}
				bitmap = 
						BitmapUtil.getBitmap(w,h,url);
				imageCache.putBitmap(url, bitmap);
			}
			msg.obj = bitmap;
			
			Bundle data = new Bundle();
			data.putString("url", url);
			msg.setData(data);
			
			handler.sendMessage(msg);
			return url;
		}
		
	}
	
	private class TaskHandler extends Handler{
		private ImageView imageView;
		public TaskHandler(ImageView imageView,String url,Looper looper){
			super(looper);
			this.imageView = imageView;
		}
		
		@Override
		public void handleMessage(Message msg) {
			String url = msg.getData().getString("url");
			String imageViewUrl = (String) imageView.getTag();
			if(imageViewUrl.equals(url)){
				if(msg.obj != null){
					Bitmap bitmap = (Bitmap) msg.obj;
					imageView.setImageBitmap(bitmap);
				}
			}
			
		}
	}
	
	public class ImageCache {
		LruCache<String, Bitmap> cache ;
		
		@SuppressLint("NewApi")
		public ImageCache(){
			//緩存的大小設置成10M
			cache = new LruCache<String, Bitmap>(10*1024*1024);
		}
		
		@SuppressLint("NewApi")
		public ImageCache(int maxSize){
			cache = new LruCache<String, Bitmap>(maxSize*1024*1024);
		}
		
		@SuppressLint("NewApi")
		public Bitmap getBitmap(String url) {
			return cache.get(url);
		}

		@SuppressLint("NewApi")
		public void putBitmap(String url, Bitmap bitmap) {
			cache.put(url, bitmap);
		}

	}
	
}

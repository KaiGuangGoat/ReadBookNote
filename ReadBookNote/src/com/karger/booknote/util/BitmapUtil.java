package com.karger.booknote.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;

public class BitmapUtil {
	public static boolean saveBitmap(String filePath,Bitmap bitmap){
		FileUtil.createFile(filePath);
		try{
			OutputStream os = new FileOutputStream(new File(filePath));
			bitmap.compress(CompressFormat.JPEG, 100, os);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static Bitmap getBitmap(String filePath) {
		return getBitmapOptions(0, 0, filePath);
	}
	
	public static Bitmap getBitmap(File file){
		return getBitmapOptions(0, 0, file.getAbsolutePath());
	}
	
	public static Bitmap getBitmap(int dstWidth,int dstHeight,String filePath){
		return getBitmapOptions(dstWidth,dstHeight,filePath);
	}
	public static Bitmap getBitmap(int dstWidth,int dstHeight,File file){
		return getBitmapOptions(dstWidth, dstHeight, file.getAbsolutePath());
	}
	
	private static Bitmap getBitmapOptions(int dstWidth,int dstHeight,String path){
		Options opts = new Options();
		opts.inSampleSize = getOptScale(dstWidth, dstHeight, path);
		Bitmap bitmap =  BitmapFactory.decodeFile(path, opts);
		if(dstWidth == 0 || dstHeight == 0){
			return bitmap;
		}
		return Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
	}
	
	private static int getOptScale(int dstWidth,int dstHeight,String path){
		Options opts = new Options();
		opts.inJustDecodeBounds = true;//只获取Bitmap高和宽的大小
		int scale = 1;
		BitmapFactory.decodeFile(path, opts);
		if(dstWidth == 0 || dstHeight == 0){
			return 1;
		}
		while(dstWidth*scale <= opts.outWidth
				&& dstHeight*scale <= opts.outHeight){
			scale++;
		}
		return scale;
	}
	
}

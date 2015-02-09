package com.karger.booknote.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
	
	public static boolean pathIsExist(String path){
		File file = new File(path);
		return file.exists();
	}
	
	public static boolean createFile(String path){
		File file = new File(path);
		if(file.exists()){
			return true;
		}
		if(file.isDirectory()){
			file.mkdirs();
			return true;
		}
		
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static List<File> fileList(String parentPath,final String[] filterString,final boolean isEndWith){
		File file = new File(parentPath);
		if(!file.exists()){
			return null;
		}
		if(filterString == null || "".equals(filterString)){
			return Arrays.asList(file.listFiles());
		}
		File[] files = file.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String filename) {
				if(isEndWith)
					for(String filter:filterString){
						if(filename.endsWith(filter)){
							return true;
						}
					}
				else
					for(String filter:filterString){
						if(filename.contains(filter)){
							return true;
						}
					}
				return false;
			}
		});
		return Arrays.asList(files);
	}
}
package com.karger.booknote.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageFolder {
	private List<FolderPath> folderPaths = new ArrayList<FolderPath>();
	private int totalImgs = 0;
	private int maxOfFolderImgsCount = 0;
	private String maxOfImgFolderPath;//包含做多图片的文件夹
	private boolean hasLoad = false;
	
	public List<FolderPath> getFolderPaths() {
		return folderPaths;
	}
	public void setFolderPaths(List<FolderPath> folderPaths) {
		this.folderPaths = folderPaths;
	}
	public int getTotalImgs() {
		return totalImgs;
	}
	public void setTotalImgs(int totalImgs) {
		this.totalImgs = totalImgs;
	}
	public int getMaxOfFolderImgsCount() {
		return maxOfFolderImgsCount;
	}
	public void setMaxOfFolderImgsCount(int maxOfFolderImgsCount) {
		this.maxOfFolderImgsCount = maxOfFolderImgsCount;
	}
	public String getMaxOfImgFolderPath() {
		return maxOfImgFolderPath;
	}
	public void setMaxOfImgFolderPath(String maxOfImgFolderPath) {
		this.maxOfImgFolderPath = maxOfImgFolderPath;
	}
	
	public boolean hasLoad(){
		return hasLoad;
	}
	public void setHasLoad(boolean haslLoad){
		this.hasLoad = haslLoad;
	}
}

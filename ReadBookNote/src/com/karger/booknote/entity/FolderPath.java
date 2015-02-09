package com.karger.booknote.entity;

public class FolderPath{
	private String parentPath;
	private String firstPath;
	public FolderPath(String parentPath,String firstPath){
		this.parentPath = parentPath;
		this.firstPath = firstPath;
	}
	public String getFirstPath() {
		return firstPath;
	}
	public void setFirstPath(String firstPath) {
		this.firstPath = firstPath;
	}
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	
}
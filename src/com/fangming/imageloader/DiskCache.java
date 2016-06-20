package com.fangming.imageloader;

import android.graphics.Bitmap;

public class DiskCache {
	private FileUtil mFileUtil;
	
	public DiskCache() {
		mFileUtil=new FileUtil();
	}

	public Bitmap get(String url){
		return mFileUtil.getBitmap(mFileUtil.SDPATH+"", url);
	}
	
	public void put(String url,Bitmap bitmap){
		mFileUtil.saveBitmap(mFileUtil.SDPATH+"", bitmap, url);
	}

}

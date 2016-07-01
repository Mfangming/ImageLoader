package com.fm.cache;

import com.fm.utils.FileUtil;

import android.graphics.Bitmap;

/**
 * @describe 图片文件缓存
 * @author fangming
 * @CreateTime 2016年6月21日上午9:28:40
 * @version 1.0.0
 */
public class DiskCache implements ImageCache{
	private FileUtil mFileUtil;
	
	public DiskCache() {
		mFileUtil=new FileUtil();
	}

	@Override
	public Bitmap get(String url){
		return mFileUtil.getBitmap(mFileUtil.SDPATH+"", url);
	}
	
	@Override
	public void put(String url,Bitmap bitmap){
		mFileUtil.saveBitmap(mFileUtil.SDPATH+"", bitmap, url);
	}

}

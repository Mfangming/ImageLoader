package com.fm.cache;

import android.graphics.Bitmap;

/**
 * @describe 内存和文件双缓存
 * @author fangming
 * @CreateTime 2016年6月21日上午10:05:21
 * @version 1.0.0
 */
public class DoubleCache implements ImageCache{
	ImageCache mMemoryCache=new MemoryCache();
	ImageCache mDiskCache=new DiskCache();
	

	@Override
	public void put(String url, Bitmap bitmap) {
		mMemoryCache.put(url, bitmap);
		mDiskCache.put(url, bitmap);
	}

	@Override
	public Bitmap get(String url) {
		Bitmap bitmap=null;
		bitmap=mMemoryCache.get(url);
		if(bitmap==null){
			bitmap=mDiskCache.get(url);
		}
		return bitmap;
	}

}

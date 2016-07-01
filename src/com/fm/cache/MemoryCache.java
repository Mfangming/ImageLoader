package com.fm.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @describe 图片内存缓存
 * @author fangming
 * @CreateTime 2016年6月21日上午9:28:15
 * @version 1.0.0
 */
public class MemoryCache implements ImageCache{
	LruCache<String, Bitmap> iMemoryCache;
	
	public MemoryCache() {
		initImageCache();
	}

	/**
	 * @describe:图片缓存
	 */
	public void initImageCache(){
		//计算可使用的最大内存
		int maxMemory=(int) ((Runtime.getRuntime().maxMemory())/1024);
		//取四分之一的可用内存作为缓存
		int cacheSize=maxMemory/4;
		iMemoryCache=new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight() /1024;
			}
		};
		
	}

	@Override
	public void put(String url, Bitmap bitmap) {
		iMemoryCache.put(url, bitmap);
	}

	@Override
	public Bitmap get(String url) {
		return iMemoryCache.get(url);
	}


}

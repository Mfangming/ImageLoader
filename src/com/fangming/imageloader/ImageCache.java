package com.fangming.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache {
	LruCache<String, Bitmap> iImageCache;
	
	public ImageCache() {
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
		iImageCache=new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight() /1024;
			}
		};
		
	}

	
	public void put(String url,Bitmap bitmap){
		iImageCache.put(url, bitmap);
	}
	
	public Bitmap get(String url){
		return iImageCache.get(url);
	}
}

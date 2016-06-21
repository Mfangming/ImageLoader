package com.fangming.imageloader;

import android.graphics.Bitmap;

public interface ImageCache {
	
	/**
	 * @describe:存图片
	 * @param url
	 * @param bitmap
	 */
	public void put(String url,Bitmap bitmap);
	
	/**
	 * @describe:取图片
	 * @param url
	 * @return
	 */
	public Bitmap get(String url);

}

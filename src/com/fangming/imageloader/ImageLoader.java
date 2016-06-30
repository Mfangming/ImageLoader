package com.fangming.imageloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

/**
 * @describe 图片加载类（使用了3级缓存）
 * @author fangming
 * @CreateTime 2016年6月17日上午9:55:06
 * @version 1.0.0
 */
public class ImageLoader {
	private String Tag = ImageLoader.class.getName();
	// 图片缓存
	ImageCache mImageCache = new DoubleCache();

	/**
	 * 线程池
	 */
	ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	
	private ImageLoader(){
		
	} 
	
	public static ImageLoader getInstance(){
		return ImageLoaderHolder.instance;
	}
	
	private static class ImageLoaderHolder{
		private static final ImageLoader instance=new ImageLoader();
	}
	
	
	/**
	 * @describe:注入缓存实现
	 * @param mImageCache
	 */
	public void setmImageCache(ImageCache mImageCache) {
		this.mImageCache = mImageCache;
	}

	/**
	 * @describe:down image
	 * @param imageUrl
	 */
	public Bitmap downLoadImage(String imageUrl) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(imageUrl);
			Log.i(Tag, imageUrl);
			HttpURLConnection mUrlConnection = (HttpURLConnection) url.openConnection();
			mUrlConnection.connect();
			bitmap = BitmapFactory.decodeStream(mUrlConnection.getInputStream());
			mUrlConnection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * @describe:显示图片
	 * @param imageUrl图片地址
	 * @param imageView控件
	 */
	public void displayImage(final String imageUrl, final ImageView imageView, final Handler handler) {
		// 1.先从缓存中拿
		Bitmap bitmap = mImageCache.get(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			return;
		}
		// 2.缓存中没有时，从服务器上拿
		submitLoadRequest(imageUrl, imageView, handler);
	}

	/**
	 * @describe:http提交图片请求
	 * @param imageUrl图片地址
	 * @param imageView控件
	 */
	public void submitLoadRequest(final String imageUrl, final ImageView imageView, final Handler handler) {
		imageView.setTag(imageUrl);
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				Bitmap bitmap = downLoadImage(imageUrl);
				if (bitmap == null) {
					return;
				}
				mImageCache.put(imageUrl, bitmap);
				if (imageView.getTag().equals(imageUrl)) {
					Message msg = new Message();
					msg.obj = bitmap;
					handler.sendMessage(msg);
				}
			}
		});
	}
}

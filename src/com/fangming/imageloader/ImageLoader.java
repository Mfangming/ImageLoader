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
	private String Tag=ImageLoader.class.getName();
	public static ImageLoader _instance;
	//内存缓存
	ImageCache mImageCache=new ImageCache();
	//文件缓存
	DiskCache mDiskCache=new DiskCache();
	
	Boolean useSdCache=false;

	/**
	 * 线程池
	 */
	ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public static ImageLoader getInstance(){
		if(_instance==null){
			_instance=new ImageLoader();
		}
		return _instance;
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
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * @describe:显示图片
	 * @param imageUrl图片地址
	 * @param imageView控件
	 */
	public void displayImage(final String imageUrl, final ImageView imageView,final Handler handler) {
		imageView.setTag(imageUrl);
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				//1.先从缓存中拿
				Bitmap bitmap=mImageCache.get(imageUrl);
				//2.缓存中没有时，从sd卡中取
				if(bitmap==null && useSdCache){
					try {
						bitmap=mDiskCache.get(imageUrl);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(bitmap!=null){
						mImageCache.put(imageUrl, bitmap);	
					}
				}
				//3.缓存中没有时，从服务器上拿
				if (bitmap == null) {
					bitmap = downLoadImage(imageUrl);
					mImageCache.put(imageUrl, bitmap);
					if(useSdCache){
						mDiskCache.put(imageUrl, bitmap);
					}
				}
				if (bitmap == null) {
					return;
				}
				if (imageView.getTag().equals(imageUrl)) {
					Message msg = new Message(); 
					msg.obj = bitmap;
					handler.sendMessage(msg);
				}
			}
		});

	}

	public void setIsUseSdCache(Boolean isuse){
		useSdCache=isuse;
	}
}

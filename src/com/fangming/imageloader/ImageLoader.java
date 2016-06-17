package com.fangming.imageloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

/**
 * @describe 图片加载类
 * @author fangming
 * @CreateTime 2016年6月17日上午9:55:06
 * @version 1.0.0
 */
public class ImageLoader {
	private String Tag=ImageLoader.class.getName();
	public static ImageLoader _instance;

	/**
	 * 线程池
	 */
	ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	LruCache<String, Bitmap> iImageCache;
	
	public static ImageLoader getInstance(){
		if(_instance==null){
			_instance=new ImageLoader();
		}
		return _instance;
	}

	public ImageLoader() {
		initImageCache();
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
				Bitmap bitmap = downLoadImage(imageUrl);
				if (bitmap == null) {
					return;
				}
				if (imageView.getTag().equals(imageUrl)) {
					Message msg = new Message(); 
					msg.obj = bitmap;
					handler.sendMessage(msg);
				}
				iImageCache.put(imageUrl, bitmap);
			}
		});

	}

}

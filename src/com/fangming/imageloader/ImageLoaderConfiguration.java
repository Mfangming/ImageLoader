package com.fangming.imageloader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @describe 类似build模式，使用户可以链式调用
 * @author fangming
 * @CreateTime 2016年7月1日下午2:38:02
 * @version 1.0.0
 */
public class ImageLoaderConfiguration {
	// 图片缓存
	public ImageCache mCache = new MemoryCache();
	/**
	 * 线程数量
	 */
	public int threadCount = Runtime.getRuntime().availableProcessors() + 1;

	/**
	 * 线程池
	 */
	public ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

	/**
	 * 加载中显示图片的对象，oop类单一职责
	 */
	public DisplayConfig dConfig = new DisplayConfig();

	/**
	 * @describe:设置缓存
	 * @param cache
	 * @return
	 */
	public ImageLoaderConfiguration setCache(ImageCache cache) {
		//依赖注入的方式
		mCache = cache;
		return this;
	}

	/**
	 * @describe:设置线程数量
	 * @param count
	 * @return
	 */
	public ImageLoaderConfiguration setThreadCount(int count) {
		threadCount = count;
		return this;
	}

	/**
	 * @describe:设置加载中显示的图片
	 * @param resid
	 * @return
	 */
	public ImageLoaderConfiguration setLoadingImageid(int resid) {
		dConfig.lodingid = resid;
		return this;
	}

}

package com.fangming.test;

import com.fangming.imageloader.DoubleCache;
import com.fangming.imageloader.ImageLoader;
import com.fangming.imageloader.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private String Tag = MainActivity.class.getName();
	private ImageLoader mImageLoader;
	private ImageView imageview;
	private Button btn_load;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImageLoader = new ImageLoader();
		mImageLoader.setmImageCache(new DoubleCache());
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(Tag, "onResume");
	}

	private void initView() {
		imageview = (ImageView) findViewById(R.id.imageview);
		imageview.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		btn_load = (Button) findViewById(R.id.btn_load);
		btn_load.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 mImageLoader.displayImage("http://n.sinaimg.cn/translate/20160616/Tqwm-fxthapu9141733.jpg",
				 imageview,
				 handler);
			}
		});
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			try {
				imageview.setImageBitmap((Bitmap) msg.obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	};
}

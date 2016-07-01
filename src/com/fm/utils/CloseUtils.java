package com.fm.utils;

import java.io.Closeable;
import java.io.IOException;

public final class CloseUtils {

	private CloseUtils() {//final类占有一块独立的内存，使用private修饰，不让new，new就新开辟了内存。
	}

	/**
	 * @describe:关闭closeable对象
	 * @param closeable
	 */
	public static void closeQuietly(Closeable closeable) {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

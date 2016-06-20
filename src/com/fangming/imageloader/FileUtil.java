package com.fangming.imageloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/Photo_LJ/";
	public static int SEARCH_FILES = 0;


	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	/**
	 * @describe:保存图片
	 * @param fileName 文件目录
	 * @param bitmap 图片
	 * @param picName 文件名
	 */
	public void saveBitmap(String fileDir,Bitmap bitmap,String picName){
		String picna=picName.replace("/", "");
		try {
			 File file = new File(fileDir);
		        if (!file.exists()) {  
		            try {  
		                file.mkdirs();  
		            } catch (Exception e) {  
		                e.printStackTrace();
		            }  
		        } 
			File f = new File(SDPATH, picna);
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(SDPATH+picna);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Bitmap getBitmap(String fileDir,String picName){
		return BitmapFactory.decodeFile(fileDir+picName.replace("/", ""));
	}
	
	/**
	 * �����ļ���
	 * 
	 * @param dirName
	 * @return
	 * @throws IOException
	 */
	public void createFiles(String dirName) throws IOException {
		if (!hasSdcard()) {
			return;
		}
		File dumpFolder = new File(dirName);
		if (!dumpFolder.exists()) {
			dumpFolder.mkdirs();
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param dirName
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String dirName) throws IOException {
		if (hasSdcard()) {
			File dumpFolder = new File(dirName);
			if (!dumpFolder.exists()) {
				dumpFolder.createNewFile();
			}
			return dumpFolder;
		} else {
			return null;
		}

	}

	/**
	 * ������Դ�ļ���Ŀ¼
	 * 
	 * @param pContext
	 * @param pAssetFilePath
	 *            ��Դ�ļ�Ŀ¼
	 * @param pDestDirPath
	 */
	public static void copyAssets(Context context, String pAssetFilePath,
			String pDestDirPath) {
		AssetManager assetManager = context.getAssets();
		InputStream in = null;
		OutputStream out = null;
		try {
			in = assetManager.open(pAssetFilePath);
			File outFile = new File(pDestDirPath, pAssetFilePath);
			out = new FileOutputStream(outFile);
			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (IOException e) {
			Log.e("tag", "Failed to copy asset file: " + pAssetFilePath, e);
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	private static void copyFile(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024 * 16];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	/**
	 * 1���ж�SD���Ƿ����
	 */
	public static boolean hasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * @param path ·��
	 * @param buffer д������
	 * @param isreset true׷��д�룬false����
	 * @throws IOException
	 */
	public static void writeFileSdcardFile(String path, byte[] buffer,Boolean isreset)
			throws IOException {
		try {
			FileOutputStream fout = new FileOutputStream(path,isreset);
			fout.write(buffer);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

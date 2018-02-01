package com.softvan.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

/**
 * IO工具类。
 * */
public final class IOUtil {
	private static final int BUFFER_SIZE = 16 * 1024;
	/**
	 * 加载文件流。
	 * @param fileName
	 * 	文件名称。
	 * @return 文件流。
	 * */
	public synchronized static InputStream loadFileStream(String fileName) throws IOException{
		if(fileName == null || fileName.isEmpty()) return null;
		Resource rs = new ClassPathResource(fileName, ClassUtils.getDefaultClassLoader());
		return rs.getInputStream();
	}
	/**
	 * 获取文件扩展名。
	 * @param fileName
	 * 	文件名或全路径。
	 * @return 扩展名。
	 * */
	public synchronized static String getExtension(String fileName){
		if(fileName == null || fileName.trim().isEmpty()) return null;
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
	/**
	 * 复制文件。
	 * @param src
	 * 	复制源文件。
	 * @param dest
	 * 	复制目标文件。
	 * @param isDelSrc
	 * 	是否删除源文件。
	 * @return 是否复制文件成功。
	 * */
	public synchronized static boolean copyFile(File src, File dest, boolean isDelSrc){
		if(src == null || dest == null) return false;
		InputStream in = null;
		OutputStream out =  null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE);
		
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while((len = in.read(buffer, 0, buffer.length)) > 0){
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(in != null) in.close();
				if(out != null) out.close();
				if(isDelSrc) src.delete();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
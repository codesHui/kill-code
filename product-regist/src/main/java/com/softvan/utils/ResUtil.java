package com.softvan.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

/**
 *	资源配置文件工具类。
 * */
public final class ResUtil {
	/**
	 * 获取资源配置文件配置项。
	 * @param resourceCfgFileName
	 * 	资源配置文件名称(properties文件名)。
	 * @param key
	 * 	资源配置文件中配置项名称。
	 * @return
	 * 	配置项内容。
	 * */
	public synchronized static String LoadResourceItemValue(String resourceCfgFileName,String key){
		if(resourceCfgFileName == null || resourceCfgFileName.isEmpty()) return null;
		if(key == null || key.isEmpty()) return null;
		
		ResourceBundle bundle = ResourceBundle.getBundle(resourceCfgFileName);
		if(bundle == null)return null;
		return bundle.getString(key);
	}
	/**
	 * 加载资源配置文件流。
	 * @param resourceCfgFileName
	 * 	资源配置文件名称。
	 * @param key
	 * 	文件流配置键名。
	 * @return
	 * 	配置键所表示的文件流。
	 * @throws IOException 
	 * 	文件流加载异常。
	 * */
	public synchronized static InputStream loadResourceStream(String resourceCfgFileName, String key) throws IOException{
		String fileName = LoadResourceItemValue(resourceCfgFileName, key);
		if(fileName == null || fileName.isEmpty()) return null;
		return IOUtil.loadFileStream(fileName);		
	}
}
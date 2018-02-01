package com.softvan.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.util.StringUtils;

/**
 * 属性工具类。
 */
public class PropertiesUtil {
	private String propertiesName = "";
	/**
	 * 构造函数。
	 * @param fileName
	 * 属性文件。
	 */
	public PropertiesUtil(String fileName){
		this.propertiesName = fileName;
	}
	/**
	 * 根据键名获取键值。
	 * @param key
	 * @return
	 */
	public String readProperty(String key){
		if(StringUtils.isEmpty(key)) return null;
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(this.propertiesName);
			Properties p = this.loadProperties(is);
			is.close();
			return p.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 写入属性文件。
	 * @param key
	 * 键名。
	 * @param value
	 * 值。 
	 */
	public void writeProperty(String key,String value){
		if(StringUtils.isEmpty(key)) return;
		try {
			InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(this.propertiesName);
			Properties p = this.loadProperties(is);
			OutputStream os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(this.propertiesName).getFile());
			p.setProperty(key, value);
			p.store(os, key);
			os.flush();
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 加载属性文件流。
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private Properties loadProperties(InputStream is) throws IOException{
		if(is == null) return null;
		Properties p = new Properties();
		p.load(is);
		return p;
	}
}
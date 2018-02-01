package com.softvan.configuration;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 模块数据解析类。
 * */
public final class ModuleParse {
	private static final Logger logger = Logger.getLogger(ModuleParse.class);
	private static final Map<String, ModuleSystem> cache_systems = new HashMap<String, ModuleSystem>();
	/**
	 * 将配置对象转换为XML。
	 * @param ipower
	 * 配置对象。
	 * @return
	 * XML
	 */
	public static String parseToXml(IPowerSystem ipower){
		if(logger.isDebugEnabled()) logger.debug("将配置对象转换为XML...");
		if(ipower == null) return null;
		XStream stream = new XStream();
		stream.processAnnotations(ipower.getClass());
		return stream.toXML(ipower);
	}
	/**
	 * 将XML转换为模块系统集合。
	 * @param inputStream
	 * 配置文件。
	 * @return
	 * 模块系统集合。
	 */
	public static ModuleSystemCollection parse(InputStream inputStream){
		if(logger.isDebugEnabled()) logger.debug("将XML转换为模块系统集合...");
		XStream stream = new XStream(new DomDriver("UTF-8"));
		stream.processAnnotations(IPowerSystem.class);
		IPowerSystem data = (IPowerSystem)stream.fromXML(inputStream);
		if(data != null){
			return data.getSystems();
		}
		return null;
	}
	/**
	 * 加载模块系统。
	 * @param inputStream
	 * 配置文件流。
	 * @param moduleSystemId
	 * 模块系统ID。
	 * @return
	 * 模块系统。
	 */
	public static synchronized ModuleSystem loadModuleSystem(InputStream inputStream,String moduleSystemId){
		if(logger.isDebugEnabled()) logger.debug(String.format(" 加载模块［%s］系统...", moduleSystemId));
		if(inputStream == null || StringUtils.isEmpty(moduleSystemId)) return null;
		ModuleSystem moduleSystem = cache_systems.get(moduleSystemId);
		if(moduleSystem == null){
			ModuleSystemCollection collection = parse(inputStream);
			if(collection == null || collection.size() == 0) return null;
			moduleSystem = collection.item(moduleSystemId);
			if(moduleSystem != null){
				cache_systems.put(moduleSystemId, moduleSystem);
			}
		}
		return moduleSystem;
	}
}
package com.imi.support.freemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.imi.entity.setting.Dictionary;
import com.imi.service.review.impl.ReviewsServiceImpl;
import com.imi.service.setting.DictionaryService;

/**
 * 模拟数据字典
 */
@Component
public class DictContext {
	private static final Logger logger = Logger.getLogger(ReviewsServiceImpl.class);

	private static Map<String, List<String[]>> map = new HashMap<String, List<String[]>>();

	private static DictContext dict;
	@Resource
	private DictionaryService dictionaryService;
	@Resource  
    private  SessionFactory sessionFactory;  
	@PostConstruct
	public void init() {
		logger.debug("Open single Hibernate Session in AdminInitializer");

		dict = this;

		dict.dictionaryService = this.dictionaryService;
		

	}

	public static String getDictKV(String key, String value) {
/*		System.out.println("key:"+key+"+++++++++value:"+value);*/

		String dic = getDict(key, value);
		if (dic == null) {
			return value;
		}
		return dic;
	}

	private static List<String[]> getChildDicLst(String key) {
 
		List<String[]> dictList = new ArrayList<String[]>();
	    
		  if(dict!=null){
			 try {
			 List<Dictionary> dictionarys = dict.getDictionaryService()
				.findChildDicLst(key);
			 for (Dictionary item : dictionarys) {
			dictList.add(new String[] { item.getDicKey(), item.getDicValue() });
			 	}
			 map.put(key, dictList); // 性别
			 } finally {
		     
		  
		    }
		
		}
		return dictList;
	}

	/**
	 * 根据key获得数据字典集 <br/>
	 * 每个数据集
	 * 
	 * @param key
	 *            数据字典标识 egg xb01, xl01
	 * @return 数据字典集合List
	 */
	public static List<String[]> getDict(String key) {
//		if (!map.containsKey(key))
//			return getChildDicLst(key);
//		return map.get(key);
		return getChildDicLst(key);
	}

	/**
	 * 根据key和value获得对应的文本
	 * 
	 * @param key
	 *            数据字典标识 egg xb01, xl01
	 * @param value
	 *            数据字典值 egg key:xb01 value:1
	 * @return 数据字典对应的文本值
	 */
	public static String getDict(String key, String value) {

		List<String[]> list = getDict(key);
		if (list == null)
			return null;
		for (String[] s : list) {
			if (StringUtils.isBlank(s[0]))
				continue;
			if (s[0] != null && s[0].equals(value))
				return s[1];
		}
		return null;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

}
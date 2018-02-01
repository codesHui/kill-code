package com.imi.service.setting.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.imi.dao.setting.ISensitiveWordDAO;
import com.imi.entity.setting.SensitiveWord;
import com.imi.service.setting.SensitiveWordService;
import com.softvan.model.DataGrid;

public class SensitiveWordServiceImpl implements SensitiveWordService {
	private static final Logger logger = Logger
			.getLogger(SensitiveWordServiceImpl.class);
	private ISensitiveWordDAO sensitiveWordDAO;

	private Map<String, SensitiveWord> sensitiveWordMap;

	private static Object LOCK = new Object();

	/**
	 * 初始化缓存数据
	 */
	@Override
	public void initSensitiveWordCacheMap(boolean create) {
		synchronized (LOCK) {
			if (create) {
				List<SensitiveWord> list = sensitiveWordDAO.selectAll();
				sensitiveWordMap = new LinkedHashMap<String, SensitiveWord>();
				for (SensitiveWord sw : list) {
					sensitiveWordMap.put(sw.getContent(), sw);
				}

			}

		}
	}

	@Override
	public List<SensitiveWord> getAllSensitiveWords() {

		List<SensitiveWord> list = new ArrayList<SensitiveWord>();
		Iterator<SensitiveWord> it = sensitiveWordMap.values().iterator();
		while (it.hasNext()) {
			SensitiveWord sw = it.next();
			list.add(sw);
		}
		return list;
	}

	@Override
	public boolean checkSensitiveWords(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}

		Iterator<String> it = sensitiveWordMap.keySet().iterator();
		while (it.hasNext()) {
			String word = it.next();
			// 忽略大小写匹配
			Pattern pat = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
			Matcher mat = pat.matcher(value);
			if (mat.find()) {
				logger.info("checkSensitiveWords...有敏感词[" + word + "]");
				return true;
			}
		}
		logger.info("checkSensitiveWords...没敏感词");
		return false;
	}

	@Override
	public String returnSensitiveWords(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}

		StringBuffer msg = new StringBuffer();
		Iterator<String> it = sensitiveWordMap.keySet().iterator();
		while (it.hasNext()) {
			String word = it.next();
			// 忽略大小写匹配
			Pattern pat = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
			Matcher mat = pat.matcher(value);
			if (mat.find()) {
				msg.append("'").append(word).append("'、");
			}
		}
		if (msg.length() > 0) {
			msg.setLength(msg.length() - 1);
			logger.info("returnSensitiveWords...有敏感词[" + msg.toString() + "]");
			return msg.toString();
		}
		logger.info("returnSensitiveWords...没敏感词");
		return null;
	}

	@Override
	public DataGrid<SensitiveWord> datagrid(SensitiveWord info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SensitiveWord update(SensitiveWord info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub

	}
}
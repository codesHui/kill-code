package com.imi.service.setting;

import java.util.List;

import com.imi.base.service.IBaseDataService;
import com.imi.entity.setting.SensitiveWord;

public interface SensitiveWordService extends IBaseDataService<SensitiveWord> {

	/**
	 * 校验是否有敏感词，有则返回true，否则返回false * * {@value} 需要判断的值
	 * */
	public boolean checkSensitiveWords(String value);

	/**
	 * 校验敏感词，返回包含的敏感词内容，没有则为null * * {@value} 需要判断的值
	 * */
	public String returnSensitiveWords(String value);

	public List<SensitiveWord> getAllSensitiveWords();

	public void initSensitiveWordCacheMap(boolean create);

}
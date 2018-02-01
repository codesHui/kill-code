package com.imi.dao.setting;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.entity.setting.SensitiveWord;

public interface ISensitiveWordDAO extends IBaseDao<SensitiveWord> {

	public List<SensitiveWord> selectAll();

}

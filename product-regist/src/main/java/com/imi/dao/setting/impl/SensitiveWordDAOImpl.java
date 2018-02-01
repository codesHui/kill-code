package com.imi.dao.setting.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.setting.ISensitiveWordDAO;
import com.imi.entity.setting.SensitiveWord;
@Repository("sensitiveWordDAO")
public class SensitiveWordDAOImpl extends BaseDaoImpl<SensitiveWord> implements ISensitiveWordDAO {

	@Override
	public List<SensitiveWord> selectAll() {
		final String hql = "from SensitiveWord m where 1=1";
		return this.find(hql, null, null, null);
	}

}

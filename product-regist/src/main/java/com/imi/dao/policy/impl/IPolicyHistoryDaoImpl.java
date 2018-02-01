package com.imi.dao.policy.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.policy.IPolicyHistoryDao;
import com.imi.entity.reportPolicy.ReportPolicyHistory;

@Repository
public class IPolicyHistoryDaoImpl extends BaseDaoImpl<ReportPolicyHistory> implements IPolicyHistoryDao {
	
	@Override
	public Long insertPolicyFileHistory(ReportPolicyHistory policyHistory) {
		if(policyHistory == null) {
			return null;
		}
		return (Long)this.save(policyHistory);
	}

	@Override
	public List<ReportPolicyHistory> queryList(Long userId) {
		List<ReportPolicyHistory> listCount = this.find(sql_content(userId));
		return listCount;
	}
	
	private String sql_content(Long userId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p from ReportPolicyHistory p where userId = " + userId );
		sql.append(" order by p.reportTime desc");
		return sql.toString();
	}
	
}

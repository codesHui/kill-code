package com.imi.service.policy.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.dao.policy.IPolicyInformationDao;
import com.imi.dao.setting.IMemberUserDao;
import com.imi.entity.reportPolicy.ReportPolicy;
import com.imi.entity.setting.MemberUser;
import com.imi.model.policy.PolicyInformation;
import com.imi.service.policy.IPolicyInformationService;

@Service
public class IPolicyInformationServiceImpl extends BaseDataServiceImpl<ReportPolicy, PolicyInformation> implements IPolicyInformationService {
	
	@Resource
	private  IPolicyInformationDao policyInformationDao;
	
	@Resource
	private IMemberUserDao memberUserDao;
	/**
	 * 查询保单数据
	 */
	@Override
	public void queryList(PolicyInformation policyInformation) {
		
 		policyInformationDao.queryList(policyInformation);
		//通过userid查询menmberUser
		//List<MemberUser> list = memberUserDao.getAll();
		
		//policyInformation.setListMemberUser(list);
		
	}

	@Override
	protected List<ReportPolicy> find(PolicyInformation info) {
		return null;
	}

	@Override
	protected PolicyInformation changeModel(ReportPolicy data) {
		return null;
	}


	@Override
	public ReportPolicy queryDetails(Long policyId) {
		return policyInformationDao.queryDetails(policyId);
		
	}

	@Override
	protected Long total(PolicyInformation info) {
		return null;
	}

	@Override
	public PolicyInformation update(PolicyInformation info) {
		return null;
	}

	@Override
	public void delete(String[] ids) {
		
	}

	@Override
	public ReportPolicy qryPolicyByPolicyNo(String policyNo) {
		 ReportPolicy policy = null;
		try{
			 policy = this.policyInformationDao.qryInfoByPolicyNo(policyNo);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return policy;
	}

	@Override
	public List<MemberUser> getAllCompany(Long currentId) {
		if(null == currentId){
			return this.memberUserDao.findAllUser();
		}else{
			return this.memberUserDao.findUsersByUserId(currentId);
		}
	}

	@Override
	public void savePolicyInfoBatch(List<ReportPolicy> policyLst,List<String> policyNoLst) {
		if(CollectionUtils.isNotEmpty(policyLst)){
			this.policyInformationDao.insertPolicyBatch(policyLst);
		}
	}	
}

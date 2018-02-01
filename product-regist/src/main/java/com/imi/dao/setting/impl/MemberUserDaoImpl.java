package com.imi.dao.setting.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.setting.IMemberUserDao;
import com.imi.entity.reportPolicy.ReportPolicyHistory;
import com.imi.entity.security.User;
import com.imi.entity.setting.MemberUser;
import com.imi.model.member.MemberUserInfo;
/**
 * 用户数据接口实现类。
 */
@Repository
public class MemberUserDaoImpl extends BaseDaoImpl<MemberUser> implements IMemberUserDao {
	private static final Logger logger = Logger.getLogger(MemberUserDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.softvan.netplatform.dao.admin.IUserDao#findUsers(com.softvan.netplatform.model.admin.MemberUserInfo)
	 */
	@Override
	public List<MemberUser> findUsers(MemberUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "select m from MemberUser m,User u where m.user.id=u.id  and u.type = '2' "; 
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/**
	 * 用户管理 用户列表
	 * @param info
	 * @return
	 */
	@Override
	public void findUsersList(MemberUserInfo info,String roleCode) {
		List<Map<String, Object>> list = null;
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		int total = 0;
		StringBuffer sql = new StringBuffer();
		sql.append (" SELECT    ");
		sql.append (" 	u.account,    ");
		sql.append (" 	m.companyName as companyName,    ");
		sql.append (" 	m.legalPerson as legalPerson,    ");
		sql.append (" 	m.specialMail as specialMail,    ");
		sql.append (" 	m.registeredCode as registeredCode,    ");
		sql.append (" 	u.nickName as nickName,    ");
		sql.append (" 	u.id as userId,    ");
		sql.append (" 	u.status as userStatus    ");
		sql.append (" FROM    ");
		sql.append (" 	biz_member_user m    ");
		sql.append (" LEFT JOIN sys_users u ON m.user_id = u.id    ");
		sql.append (" WHERE  1=1   ");
		//在原基础上修改bjs角色和系统管理员角色才能查询bjs信息、登记会员和系统管理员才能查询登记会员的信息begin
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(!"bjsAdmin".equals(roleCode) && !"sysAdmin".equals(roleCode)){
			sql.append (" AND	EXISTS (    ");
			sql.append (" 		SELECT    ");
			sql.append (" 			1    ");
			sql.append (" 		FROM    ");
			sql.append (" 			sys_user_roles r    ");
			sql.append(" LEFT JOIN sys_roles sr on r.role_id = sr.id ");
			sql.append (" 		WHERE    ");
			sql.append (" 			r.user_id = u.id    ");
			sql.append (" 		AND sr.code != :roleCode2    ");
			sql.append (" 	)    ");
			parameters.put("roleCode2", "bjsAdmin");
		}
		//除了系统管理员以外其他角色不能看到登记会员信息
		if(!"sysAdmin".equals(roleCode)){
			sql.append (" AND	EXISTS (    ");
			sql.append (" 		SELECT    ");
			sql.append (" 			1    ");
			sql.append (" 		FROM    ");
			sql.append (" 			sys_user_roles r    ");
			sql.append(" LEFT JOIN sys_roles sr on r.role_id = sr.id ");
			sql.append (" 		WHERE    ");
			sql.append (" 			r.user_id = u.id    ");
			sql.append (" 		AND sr.code != :roleCode3    ");
			sql.append (" 	)    ");
			parameters.put("roleCode3", "icrm");
		}
		
		
		//end
		if(!StringUtils.isEmpty(info.getRoleCode())){
			sql.append (" AND	EXISTS (    ");
			sql.append (" 		SELECT    ");
			sql.append (" 			1    ");
			sql.append (" 		FROM    ");
			sql.append (" 			sys_user_roles r    ");
			sql.append(" LEFT JOIN sys_roles sr on r.role_id = sr.id ");
			sql.append (" 		WHERE    ");
			sql.append (" 			r.user_id = u.id    ");
			sql.append (" 		AND sr.code = :roleCode    ");
			sql.append (" 	)    ");
			parameters.put("roleCode", info.getRoleCode());
		}
		
		if(StringUtils.isNotBlank(info.getAccount())){
			sql.append(" and u.account like :account ");
			parameters.put("account", "%"+info.getAccount()+"%");
		}
		
		if(StringUtils.isNotBlank(info.getRegisteredCode())){
			sql.append(" and m.registeredCode like :registeredCode ");
			parameters.put("registeredCode", "%"+info.getRegisteredCode()+"%");
		}
		
		if(StringUtils.isNotBlank(info.getCompanyName())){
			sql.append(" and m.companyName like :companyName ");
			parameters.put("companyName", "%"+info.getCompanyName()+"%");
		}
		
		if(info.getUserStatus() != null){
			sql.append(" and u.status = :status ");
			parameters.put("status", info.getUserStatus());
		}
		
		
		Query query = this.getSession().createSQLQuery(sql.toString());
		 addParameters(query, parameters,false);
        total = query.list().size();
        Integer page = info.getPage();
        Integer rows = info.getRows();
        if(page == null && rows == null){
        	list = query.list();
        	info.setMemberList(convertList(list));
        	return;
        }
		if(page == null || page < 1) page = 1;
		//2015.02.03 rows默认10,修改为5
		if(rows == null || rows < 5) rows = 5;
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
       
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        info.setMemberList(convertList(list));
        info.setTotal(total);
	}
	
	public String convertString(Object o){
		if(o == null){return null;}
		return o.toString();
	}
	
	public Long convertLong(Object o){
		if(o == null){return null;}
		return Long.parseLong(o.toString());
	}
	public Integer convertInteger(Object o){
		if(o == null){return null;}
		return Integer.parseInt(o.toString());
	}
	
	
	public List<MemberUser> convertList(List<Map<String, Object>> list ){
		 List<MemberUser> list2 = new ArrayList<MemberUser>();
		 MemberUser mu = null;
		if(list != null && list.size() >0){
        	for (int i = 0; i < list.size(); i++) {
        		Map<String, Object> map = list.get(i);
        		if(map != null && map.size() >0){
        			mu = new MemberUser();
        			mu.setCompanyName(convertString(map.get("companyName")));
        			mu.setLegalPerson(convertString(map.get("legalPerson")));
        			mu.setSpecialMail(convertString(map.get("specialMail")));
        			mu.setRegisteredCode(convertString(map.get("registeredCode")));
        			User u = new User();
        			u.setId(convertLong(map.get("userId")));
        			u.setAccount(convertString(map.get("account")));
        			u.setStatus(convertInteger(map.get("userStatus")));
        			mu.setSpecialMail(convertString(map.get("specialMail")));
        			mu.setUser(u);
        			list2.add(mu);
        		}
			}
        }
		return list2;
	}
	
	
	/*
	 * 查询数据统计。
	 * @see com.softvan.netplatform.dao.admin.IUserDao#total(com.softvan.netplatform.model.admin.MemberUserInfo)
	 */
	@Override
	public Long total(MemberUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from MemberUser m where 1=1";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(MemberUserInfo info, String hql, Map<String, Object> parameters){
		//用户注册代码	
		if(!StringUtils.isEmpty(info.getRegisteredCode())){
			hql += " and m.registeredCode = :registeredCode ";
			parameters.put("registeredCode", info.getRegisteredCode());
		}
		//用户ID 
		if(info.getUser() != null){
			if(info.getUser().getId() != null){
				hql += " and m.user.id = :userId ";
				parameters.put("userId", info.getUser().getId());
			}
		}
		
	
		return hql;
	}
	/*
	 * 根据账号查找用户。
	 * @see com.softvan.netplatform.dao.admin.IUserDao#findByAccount(java.lang.String)
	 */
	@Override
	public MemberUser findByMemberUserinfo(MemberUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug(String.format("根据账号查询用户：%s", info.getRegisteredCode()));
		String hql = "select m from MemberUser m where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
	
		hql = this.addWhere(info, hql, parameters);
		List<MemberUser> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	/*
	 * 重载删除数据。
	 * @see com.softvan.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(MemberUser data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
		if(data == null) return;
		int count = 0;
		super.delete(data);
	}
	/*
	 * 更新数据。
	 * @see com.softvan.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void update(MemberUser data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
		if(data == null) return;
		int count = 0;
		super.update(data);
	}
	
	@Override
	public List<MemberUser> findUsersByUserId(Long userId) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "select m from MemberUser m where m.user.id=" +userId; 
		List<MemberUser> lst = this.find(hql);
		return lst;
	}
	
	@Override
	public List<MemberUser> findAllUser() {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "select m from MemberUser m,User u where m.user.id=u.id  and u.type = '5'"; 
		List<MemberUser> lst = this.find(hql);
		return lst;
	}
}
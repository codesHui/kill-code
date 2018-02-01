package com.imi.dao.products.impl;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.products.IAuditDao;
import com.imi.entity.products.Audit;

import com.imi.model.product.AuditInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuditDaoImpl extends BaseDaoImpl<Audit> implements IAuditDao{
    private static final Logger logger = Logger.getLogger(ProductDaoImpl.class);

  /*  @Override
    public List<Audit> findAudit(AuditInfo info){
        if(logger.isDebugEnabled()) logger.debug("查询数据...");
        String hql = "select a from Audit a where 1=1";
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("review","0");
        hql = this.addWhere(hql,parameters);
        List<Audit> auditList = this.find(hql,parameters,info.getPage(),info.getRows());
        return auditList;

    }

    @Override
    public Long insert(Audit data) {
        if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
        if(data == null) {
            return null;
        }
        return (Long)this.save(data);
    }
    @Override
    public Long total(){
        if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
        String hql = "select count(*) from Audit a where 1=1";
        return this.count(hql,null);
    }

    public String addWhere(String hql, Map<String, Object> parameters){

        hql+=" and a.review = :review order by a.createTime desc";
        return hql;
    }*/

    @Override
    public List<Audit> findAudit(AuditInfo info){
        if(logger.isDebugEnabled()) logger.debug("查询数据...");
        String hql = "select a from Audit a where review = 0 order by a.createTime desc";
        return this.find(hql,null,info.getPage(),info.getRows());
    }

    @Override
    public Long insert(Audit data) {
        if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
        if(data == null) {
            return null;
        }
        return (Long)this.save(data);
    }
    @Override
    public Long total(){
        if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
        String hql = "select count(*) from Audit a where 1=1";
        return this.count(hql,null);
    }
}

package com.imi.service.products.impl;

import com.imi.dao.products.IAuditDao;
import com.imi.entity.products.Audit;
import com.imi.model.product.AuditInfo;
import com.imi.service.products.AuditService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService{
    @Resource
    private IAuditDao auditDao;

    @Override
   public List<Audit> findAudit(AuditInfo info){
       return auditDao.findAudit(info);
   }
    @Override
   public Long insert(Audit audit){
       return auditDao.insert(audit);
   }
    @Override
   public Long total(){
       return auditDao.total();
   }

   public Audit findAuditByProductNo(String productNo){
        final String hql = " from Audit a where a.productNO = ? ";
        return auditDao.findUnique(hql,productNo);
   }
}


package com.imi.dao.products;

import com.imi.base.IBaseDao;
import com.imi.entity.products.Audit;
import com.imi.entity.products.Product;
import com.imi.model.product.AuditInfo;

import java.util.List;

public interface IAuditDao extends IBaseDao<Audit> {

    List<Audit> findAudit(AuditInfo info);

    Long insert(Audit audit);

    Long total();

}

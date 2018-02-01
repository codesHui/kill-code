package com.imi.service.products;

import com.imi.entity.products.Audit;
import com.imi.model.product.AuditInfo;

import java.util.List;

public interface AuditService {

    List<Audit> findAudit(AuditInfo info);

    Long insert(Audit audit);

    Long total();

    Audit findAuditByProductNo(String productNo);
}

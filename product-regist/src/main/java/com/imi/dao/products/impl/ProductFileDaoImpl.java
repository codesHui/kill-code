package com.imi.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.products.IProductFileDao;
import com.imi.entity.products.ProductFile;
import com.imi.entity.setting.Attachment;
import com.imi.model.product.ProductFileInfo;

@Repository
public class ProductFileDaoImpl extends BaseDaoImpl<ProductFile> implements
		IProductFileDao {
	private static final Logger logger = Logger
			.getLogger(ProductFileDaoImpl.class);

	@Override
	public List<ProductFile> findAllbyProductNOAndType(String productNO,
			int type) {
		ProductFileInfo info = new ProductFileInfo();
		info.setProductNO(productNO);
		info.setType(type);
		return findAllByProductInfo(info);
	}

	@Override
	public List<ProductFile> findAllByProductNO(String productNO) {
		ProductFileInfo info = new ProductFileInfo();
		info.setProductNO(productNO);
		return findAllByProductInfo(info);
	}

	@Override
	public List<ProductFile> findAllbyProductNOAndType(String productNO,
			int type, String extension) {
		ProductFileInfo info = new ProductFileInfo();
		info.setProductNO(productNO);
		info.setType(type);
		info.setExtension(extension);
		return null;
	}

	@Override
	public List<ProductFile> findAllByProductInfo(ProductFileInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("查询数据...");
		String hql = "select p from ProductFile p where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(info.getProductNO())) {
			parameters.put("productNO", info.getProductNO());
			hql += " and p.productNO = :productNO ";
		}
		if (!StringUtils.isEmpty(info.getType())) {
			parameters.put("type", info.getType());
			hql += " and p.type = :type ";
		}

		if (!StringUtils.isEmpty(info.getExtension())) {
			parameters.put("type", info.getType());
			hql += " and p.attachment.extension = :extension ";
		}

		return query(hql, parameters, null, null);
	}

	@Override
	public void updateBySql(List<Attachment> attList, String productNO) {
		for (int i = 0; i < attList.size(); i++) {
			String hql = " UPDATE ProductFile p SET productNO = :productNO WHERE p.attachment.id = :id ";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("productNO", productNO);
			map.put("id", attList.get(i).getId());
			super.executeUpdate(hql, map);
		}
	}

}

package com.imi.service.report;

import java.util.List;
import java.util.Map;

import com.imi.entity.report.ProductLogoutQuarter;
import com.imi.entity.report.ProductQuarter;
import com.imi.entity.report.ProductRegistQuarter;
import com.imi.entity.report.RegistNoQuarter;
import com.imi.model.product.ProductInfo;
import com.imi.model.report.ReportVo;

public interface ReportService {

	public Map<String,ReportVo> getReportRisk();
	
	
	public Map<String,ReportVo> getReportRisk(Map condition);


	public List<ReportVo>  getReportCompany(Map model);


	public List<ProductQuarter> getReprotByYear();
	
	public List<ProductRegistQuarter> getReprotByProduct(ProductInfo product);
	public List<RegistNoQuarter> registNoQuarterList();
	public List<ProductLogoutQuarter> productLogoutList(ProductInfo p);
}

package com.imi.model.excel;

import com.imi.base.annotate.ReportColumn;
import com.imi.entity.products.Product;
import com.imi.support.freemarker.DictContext;

public class Demo {

	@ReportColumn(columnName = "测试", columnOrder = 1)
	private String id;

	@ReportColumn(columnName = "险种", converter = true, columnOrder = 2)
	private String riskType;

	@ReportColumn(columnName = "险类", converter = true, columnOrder = 3)
	private String riskName;

	@ReportColumn(columnName = "子对象ID", columnOrder = 4)
	private String user_id;

	public String convertRiskType(Object entitie, String v) {
		try{
		if (v != null) {
			System.out.println(v);
			return DictContext.getDictKV("RISK", v);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return v;
	}

	public String convertRiskName(Object entitie, String v) {
		  Product pro= (Product)entitie;
		  try{
		if (v != null) {
			return DictContext.getDictKV(pro.getRiskType(), v);
		}	
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		return v;
		
	
	}

}

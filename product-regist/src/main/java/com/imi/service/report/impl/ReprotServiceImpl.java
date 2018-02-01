package com.imi.service.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.imi.entity.products.Product;
import com.imi.entity.report.ProductLogoutQuarter;
import com.imi.entity.report.ProductQuarter;
import com.imi.entity.report.ProductRegistQuarter;
import com.imi.entity.report.RegistNoQuarter;
import com.imi.model.product.ProductInfo;
import com.imi.model.report.ReportVo;
import com.imi.service.products.impl.ProductServiceImpl;
import com.imi.service.report.ReportService;
import com.imi.support.ResourceUtil;
@Service
public class ReprotServiceImpl implements ReportService {

	private static final Logger logger = Logger
			.getLogger(ProductServiceImpl.class);
	
	@Resource
	private JdbcTemplate  jdbcTemplate;

	public Map<String,ReportVo> getReportRisk() {
		
		return getReportRisk(Maps.newHashMap());
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
		public Map<String,ReportVo> getReportRisk(Map condition){
			Map<String,ReportVo> map = Maps.newHashMap(); 
			
			List param = new ArrayList();
		 StringBuffer sbf = new StringBuffer();
		 StringBuffer commonsql = commonsql();
		 
		 if(condition.containsKey("registeredCode")&&StringUtils.isNotEmpty((String) condition.get("registeredCode"))  ){
			 commonsql.append("and t.registeredCode= '").append(condition.get("registeredCode")).append(" ' ") ;
		/*	 param.add((String) condition.get("registeredCode"));*/
		 }
		 
		 if(condition.containsKey("registTimeStart")&&StringUtils.isNotEmpty((String) condition.get("registTimeStart"))  ){
			 commonsql.append("and t.registTime>'").append((String) condition.get("registTimeStart")).append(" ' ");
		
		 }
		 if(condition.containsKey("registTimeEnd")&&StringUtils.isNotEmpty((String) condition.get("registTimeEnd"))  ){
			 commonsql.append("and t.registTime<= '").append((String) condition.get("registTimeEnd")).append(" ' ");
		
		 }
		 
		 
		 sbf.append("(SELECT t.productType,t.riskType,t.riskName");
		sbf.append(commonsql);
		 sbf.append(" GROUP BY  t.productType, t.riskType,t.riskName");
		 sbf.append(" ORDER BY  t.productType, t.riskName)");
		 
		 sbf.append("UNION ALL (SELECT t.productType,t.riskType,'小计' AS riskName");
		 sbf.append(commonsql).append("and t.productType = 1");
		 sbf.append(" GROUP BY  t.productType,t.riskType");
		 sbf.append(" ORDER BY  t.productType, t.riskType)");
		 
		 
		 sbf.append("UNION ALL (SELECT t.productType,null as riskType,'合计' AS riskName");
		 sbf.append(commonsql);
		 sbf.append(" GROUP BY t.productType");
		 sbf.append(" ORDER BY t.productType)");
		 
		 sbf.append("UNION ALL (SELECT null as productType,null as riskType,'总计' AS riskName");
		 sbf.append(commonsql).append(")");	
		
/*		 System.out.println(sbf.toString());*/
		 

			List<ReportVo> list= querySqlByJdbcTpl(sbf.toString(), param, new RowMapper() {

			public ReportVo mapRow(ResultSet rs, int arg1) throws SQLException {
				ReportVo model=new ReportVo();
				model.setProductType(rs.getString("productType"));
				model.setRiskType(rs.getString("riskType"));
				model.setRiskName(rs.getString("riskName"));
				model.setRegistNum(rs.getInt("registNum"));
				model.setCancelNum(rs.getInt("cancelNum"));
				model.setVerifyNum(rs.getInt("verifyNum"));
				model.setProblemNum(rs.getInt("problemNum"));
				model.setChineseNum(rs.getInt("chineseNum"));
				model.setEnglishNum(rs.getInt("englishNum"));
				model.setOtherNum(rs.getInt("otherNum"));
				
			
				return model;
			}
			 
		 });
			for (int i = 0; i < list.size(); i++) {  
				ReportVo model=list.get(i);
				String key= StringUtils.defaultString(model.getProductType())	
				+StringUtils.defaultString(model.getRiskType())
				+StringUtils.defaultString(model.getRiskName());
		
				
				map.put(key, model); 
			}  
			
	
		return map;
	}
	
	
	private StringBuffer commonsql(){
		 StringBuffer sbf = new StringBuffer();
		 
		 sbf.append(",count(t.productNO) AS registNum");// 注册情况
		 sbf.append(",count(v.productNO) AS verifyNum");// 审核情况
		 sbf.append(",sum(CASE WHEN t.status = 4 THEN 1 ELSE 0 END) AS cancelNum");// 注销情况
		 sbf.append(",sum(CASE WHEN t.productFlag = 1 THEN 1 ELSE 0 END) AS problemNum");//问题产品情况
		 sbf.append(",sum(CASE WHEN t.productLanguage = '中文' THEN 1 ELSE 0 END) AS chineseNum");//中文
		 sbf.append(",sum(CASE WHEN t.productLanguage = '英文' THEN 1 ELSE 0 END) AS englishNum");//英文
		 sbf.append(",sum(CASE WHEN t.productLanguage = '英文' THEN  0  WHEN t.productLanguage = '中文' THEN 0 ELSE 1 END) AS otherNum");//"其他语言"
		 sbf.append(" FROM biz_product t LEFT JOIN biz_verifys v ON t.productNO = v.productNO ");
		 sbf.append("WHERE 1=1 ");
		 

		 

		 
	return sbf;	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	 public List querySqlByJdbcTpl(String sql, List<Object> params,
	            RowMapper rowMapper) {
	        List list = new ArrayList();
	        if (rowMapper != null) {
	            logger.info("sql is :" + sql);
	            list = jdbcTemplate.query(
	                    sql.toLowerCase(),
	                    params != null ? params.toArray() : null, rowMapper);
	        }
	        else {
	            sql = sql.replace("NVL", "IFNULL");
	            logger.info("sql is :" + sql);
	            list = jdbcTemplate.queryForList(
	                    sql.toLowerCase(), params.toArray());
	        }
	        return list;

	    }

	@Override
	public  List<ReportVo> getReportCompany(Map condition) {
		Map<String,ReportVo> map = Maps.newHashMap(); 
		
		List param = new ArrayList();
	 StringBuffer sbf = new StringBuffer("SELECT  ");
	 StringBuffer commonsql = commonsql();
	 if(condition.containsKey("registTimeStart")&&StringUtils.isNotEmpty((String) condition.get("registTimeStart"))  ){
		 commonsql.append("and t.registTime>'").append((String) condition.get("registTimeStart")).append(" ' ");
	
	 }
	 if(condition.containsKey("registTimeEnd")&&StringUtils.isNotEmpty((String) condition.get("registTimeEnd"))  ){
		 commonsql.append("and t.registTime<= '").append((String) condition.get("registTimeEnd")).append(" ' ");
	
	 }
	 
	 if(condition.containsKey("productType")&&StringUtils.isNotEmpty((String) condition.get("productType"))  ){
		 commonsql.append("and t.productType= ").append((String) condition.get("productType")).append(" ");
	
	 }
	 sbf.append(" (SELECT b.companyName FROM  biz_member_user  b where b.registeredCode=t.registeredCode) AS companyName ");
	 sbf.append(commonsql);
	 sbf.append("GROUP BY   t.registeredCode "); 
	 sbf.append("UNION ALL (SELECT '合计'  AS companyName ");
	 sbf.append(commonsql).append(")");
		
		return querySqlByJdbcTpl(sbf.toString(), param, new RowMapper() {

			public ReportVo mapRow(ResultSet rs, int arg1) throws SQLException {
				ReportVo model=new ReportVo();
				model.setCompanyName(rs.getNString("companyName"));
				model.setRegistNum(rs.getInt("registNum"));
				model.setCancelNum(rs.getInt("cancelNum"));
				model.setVerifyNum(rs.getInt("verifyNum"));
				model.setProblemNum(rs.getInt("problemNum"));
				model.setChineseNum(rs.getInt("chineseNum"));
				model.setEnglishNum(rs.getInt("englishNum"));
				model.setOtherNum(rs.getInt("otherNum"));
				
			
				return model;
			}
			 
		 });
		
		
	
	}
	
	
	
	public String getReportByYearSql(){
		
		String r_01 = ResourceUtil.getConfigByName("REPROT_01");
		String r_01_01 = ResourceUtil.getConfigByName("REPROT_01_01");
		String r_01_02 = ResourceUtil.getConfigByName("REPROT_01_02");
		String r_01_03 = ResourceUtil.getConfigByName("REPROT_01_03");
		String r_01_04 = ResourceUtil.getConfigByName("REPROT_01_04");
		
		String r_02 = ResourceUtil.getConfigByName("REPROT_02");
		String r_02_01 = ResourceUtil.getConfigByName("REPROT_02_01");
		String r_02_02 = ResourceUtil.getConfigByName("REPROT_02_02");
		String r_02_03 = ResourceUtil.getConfigByName("REPROT_02_03");
		String r_02_04 = ResourceUtil.getConfigByName("REPROT_02_04");
		
		String r_03 = ResourceUtil.getConfigByName("REPROT_03");
		String r_03_01 = ResourceUtil.getConfigByName("REPROT_03_01");
		String r_03_02 = ResourceUtil.getConfigByName("REPROT_03_02");
		String r_03_03 = ResourceUtil.getConfigByName("REPROT_03_03");
		String r_03_04 = ResourceUtil.getConfigByName("REPROT_03_04");
		
		StringBuffer sql = new StringBuffer();
		// 主险
		sql.append(" SELECT ");
		sql.append(" T.productType as productType,  ");
		sql.append(" T.riskName as riskName,        ");
		sql.append(" T.riskType as riskType,  ");
		
		sql.append(" 	T.total as total,   ");
		String[] date = r_01_01.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0101, ");
		date = r_01_02.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0102, ");
		date = r_01_03.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0103, ");
		date = r_01_04.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0104, ");
		
		date = r_01.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p01total, ");
		
		
		date = r_02_01.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0201, ");
		date = r_02_02.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0202, ");
		date = r_02_03.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0203, ");
		date = r_02_04.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0204, ");
		date = r_02.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p02total, ");
		
		
		date = r_03_01.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0301, ");
		date = r_03_02.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0302, ");
		date = r_03_03.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0303, ");
		date = r_03_04.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p0304, ");
		date = r_03.split("/");
		sql.append("("+getSql(date[0], date[1])).append(" ) as p03total ");
		
		
		sql.append(" FROM                                                                                            ");
		sql.append(" 	(                                                                                            ");
		sql.append(" 		SELECT                                                                                   ");
		sql.append(" 			b.productType,b.riskName,riskType, count(b.productNO) AS total               ");
		sql.append(" 		FROM biz_product b                                                                       ");
		sql.append(" 		WHERE 1=1 GROUP BY b.productType,b.riskName,b.riskType                        ");
		sql.append(" 	) T                                                                                          ");
		
		// 附加险
		sql.append(" UNION ALL  (                                                                              ");
		sql.append(" SELECT ");
		sql.append(" T.productType as productType,  ");
		sql.append(" T.riskName as riskName,        ");
		sql.append(" T.riskType as riskType,  ");
		
		sql.append(" 	T.total as total,   ");
		date = r_01_01.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0101, ");
		date = r_01_02.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0102, ");
		date = r_01_03.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0103, ");
		date = r_01_04.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0104, ");
		
		date = r_01.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p01total, ");
		
		
		date = r_02_01.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0201, ");
		date = r_02_02.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0202, ");
		date = r_02_03.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0203, ");
		date = r_02_04.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0204, ");
		date = r_02.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p02total, ");
		
		
		date = r_03_01.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0301, ");
		date = r_03_02.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0302, ");
		date = r_03_03.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0303, ");
		date = r_03_04.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p0304, ");
		date = r_03.split("/");
		sql.append("("+getSql3(date[0], date[1])).append(" ) as p03total ");
		
		
		sql.append(" FROM                                                                                            ");
		sql.append(" 	(                                                                                            ");
		sql.append(" 		SELECT                                                                                   ");
		sql.append(" 			b.productType,b.riskName,riskType, count(b.productNO) AS total               ");
		sql.append(" 		FROM biz_product b  where  b.productType =2                                    ");
		sql.append(" 	    GROUP BY b.productType,b.riskType                        ");
		sql.append(" 	) T    )    ");
		
		
		
		
		sql.append(" UNION ALL                                                                                       ");
		sql.append(" 	(                                                                                            ");
		sql.append(" 		SELECT                                                                                   ");
		sql.append(" 			'total','total','total',                                       ");
		sql.append(" 			COUNT(*),                                                                            ");
		
		date = r_01_01.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0101, ");
		date = r_01_02.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0102, ");
		date = r_01_03.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0103, ");
		date = r_01_04.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0104, ");
		date = r_01.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p01total, ");
		
		date = r_02_01.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0201, ");
		date = r_02_02.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0202, ");
		date = r_02_03.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0203, ");
		date = r_02_04.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0204, ");
		date = r_02.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p02total, ");
		
		
		date = r_03_01.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0301, ");
		date = r_03_02.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0302, ");
		date = r_03_03.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0303, ");
		date = r_03_04.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p0304, ");
		date = r_03.split("/");
		sql.append("("+getSql2(date[0], date[1])).append(" ) as p03total ");
		
		sql.append(" 		FROM             ");
		sql.append(" 			biz_product ) ");
		System.err.println(sql);
		return sql.toString();
		
	}
	
	
	// 主险
	private String getSql(String left,String right){
		StringBuffer sql = new StringBuffer();
		sql.append(" 		SELECT count(*) FROM biz_product P WHERE                                                 ");
		sql.append(" 			UNIX_TIMESTAMP(P.registTime) >= UNIX_TIMESTAMP('"+left+" 00:00:00')                   ");
		sql.append(" 		AND UNIX_TIMESTAMP(P.registTime) < UNIX_TIMESTAMP('"+right+" 23:59:59')                   ");
		sql.append(" 		AND P.riskType = T.riskType and p.productType=t.productType and p.riskName = t.riskName      ");
		return sql.toString();
	}
	// 附加险
	private String getSql3(String left,String right){
		StringBuffer sql = new StringBuffer();
		sql.append(" 		SELECT count(*) FROM biz_product P WHERE                                                 ");
		sql.append(" 			UNIX_TIMESTAMP(P.registTime) >= UNIX_TIMESTAMP('"+left+" 00:00:00')                   ");
		sql.append(" 		AND UNIX_TIMESTAMP(P.registTime) < UNIX_TIMESTAMP('"+right+" 23:59:59')                   ");
		sql.append(" 		AND P.riskType = T.riskType and p.productType=t.productType     ");
		return sql.toString();
	}
	
	private String getSql2(String left,String right){
		StringBuffer sql = new StringBuffer();
		sql.append(" 		SELECT count(*) FROM biz_product P WHERE                                                 ");
		sql.append(" 			UNIX_TIMESTAMP(P.registTime) >= UNIX_TIMESTAMP('"+left+" 00:00:00')                   ");
		sql.append(" 		AND UNIX_TIMESTAMP(P.registTime) < UNIX_TIMESTAMP('"+right+" 23:59:59')                   ");
		return sql.toString();
	}
	
	/**
	 * 注册数量情况变化表
	 */
	@Override
	public List<ProductQuarter> getReprotByYear() {
		// TODO Auto-generated method stub
		
		List param = new ArrayList();
		String sql = getReportByYearSql();
	 
		return querySqlByJdbcTpl(sql.toString(), param, new RowMapper() {

			public ProductQuarter mapRow(ResultSet rs, int arg1) throws SQLException {
				ProductQuarter model=new ProductQuarter();
				
				model.setProductType(rs.getNString("productType"));
				model.setRiskType(rs.getNString("riskType"));
				model.setRiskName(StringUtils.isBlank(rs.getNString("riskName"))?"xugang_"+UUID.randomUUID().toString():rs.getNString("riskName"));
				
				model.setQuarter0101(rs.getInt("p0101"));
				model.setQuarter0102(rs.getInt("p0102"));
				model.setQuarter0103(rs.getInt("p0103"));
				model.setQuarter0104(rs.getInt("p0104"));
				model.setQuarter01total(rs.getInt("p01total"));
				
				model.setQuarter0201(rs.getInt("p0201"));
				model.setQuarter0202(rs.getInt("p0202"));
				model.setQuarter0203(rs.getInt("p0203"));
				model.setQuarter0204(rs.getInt("p0204"));
				model.setQuarter02total(rs.getInt("p02total"));
				
				model.setQuarter0301(rs.getInt("p0301"));
				model.setQuarter0302(rs.getInt("p0302"));
				model.setQuarter0303(rs.getInt("p0303"));
				model.setQuarter0304(rs.getInt("p0304"));
				model.setQuarter03total(rs.getInt("p03total"));
				
				return model;
			}
			 
		 });
		
		
	}
	
	/**
	 * 核查启动事由情况表
	 */
	@Override
	public List<ProductRegistQuarter> getReprotByProduct(ProductInfo product){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT     ");
		sql.append(" 	m.registeredCode AS registeredCode,     ");
		sql.append(" 	m.companyName AS companyName,     ");
		sql.append(" 	(     ");
		sql.append(" 		SELECT     ");
		sql.append(" 			count(*)     ");
		sql.append(" 		FROM     ");
		sql.append(" 			biz_verifys v     ");
		sql.append(" 		LEFT JOIN biz_product p ON p.productNO = v.productNO     ");
		sql.append(" 		WHERE     ");
		sql.append(" 			m.registeredCode = p.registeredCode     ");
		sql.append( getReprotByProduct_model2(product));
		sql.append(" 		AND v.source = '1'     ");
		sql.append(" 	) AS fk,     ");
		sql.append(" 	(     ");
		sql.append(" 		SELECT     ");
		sql.append(" 			count(*)     ");
		sql.append(" 		FROM     ");
		sql.append(" 			biz_verifys v     ");
		sql.append(" 		LEFT JOIN biz_product p ON p.productNO = v.productNO     ");
		sql.append(" 		WHERE     ");
		sql.append(" 			m.registeredCode = p.registeredCode     ");
		sql.append( getReprotByProduct_model2(product));
		sql.append(" 		AND v.source = '2'     ");
		sql.append(" 	) AS ts,     ");
		sql.append(" 	(     ");
		sql.append(" 		SELECT     ");
		sql.append(" 			count(*)     ");
		sql.append(" 		FROM     ");
		sql.append(" 			biz_verifys v     ");
		sql.append(" 		LEFT JOIN biz_product p ON p.productNO = v.productNO     ");
		sql.append(" 		WHERE     ");
		sql.append(" 			m.registeredCode = p.registeredCode     ");
		sql.append( getReprotByProduct_model2(product));
		sql.append(" 		AND v.reviewNum = '1'     ");
		sql.append(" 	) AS fy,     ");
		sql.append(" 	(     ");
		sql.append(" 		SELECT     ");
		sql.append(" 			count(*)     ");
		sql.append(" 		FROM     ");
		sql.append(" 			biz_product p     ");
		sql.append(" 		LEFT JOIN BIZ_VERIFYS v ON v.productno = p.productno     ");
		sql.append(" 		WHERE     ");
		sql.append(" 			p.registeredCode = m.registeredCode     ");
		sql.append( getReprotByProduct_model2(product));
		sql.append(" 		AND p.logoutType = '1'     ");
		sql.append(" 	) AS zx     ");
		sql.append(" FROM     ");
		sql.append(" 	biz_member_user m     ");
		sql.append("  left join sys_users uu on uu.id = m.user_id ");
		sql.append(" LEFT JOIN biz_product p ON p.registeredCode = m.registeredCode     ");
		sql.append(" LEFT JOIN BIZ_VERIFYS v ON v.productno = p.productno     ");
		sql.append(" WHERE     ");
		sql.append(" 	1 = 1  and uu.type = 2    ");
		if(StringUtils.isNotBlank(product.getRegisteredCode())){
			sql.append("  and m.registeredCode = '").append(product.getRegisteredCode()).append("'");
		}
		sql.append(" GROUP BY     ");
		sql.append(" 	m.registeredCode,     ");
		sql.append(" 	m.companyName     ");
		sql.append(" UNION ALL     ");
		sql.append(" 	SELECT     ");
		sql.append(" 		'total',     ");
		sql.append(" 		'total',     ");
		sql.append(" 		(     ");
		sql.append(" 			SELECT     ");
		sql.append(" 				count(*)     ");
		sql.append(" 			FROM     ");
		sql.append(" 				biz_verifys v     ");
		sql.append(" 			LEFT JOIN biz_product m ON v.productNo = m.productNo     ");
		sql.append(" 			WHERE     ");
		sql.append(" 				1 = 1     ");
		sql.append( getReprotByProduct_model(product));
		sql.append(" 			AND v.source = '1'     ");
		sql.append(" 		) AS fk,     ");
		sql.append(" 		(     ");
		sql.append(" 			SELECT     ");
		sql.append(" 				count(*)     ");
		sql.append(" 			FROM     ");
		sql.append(" 				biz_verifys v     ");
		sql.append(" 			LEFT JOIN biz_product m ON v.productNo = m.productNo     ");
		sql.append(" 			WHERE     ");
		sql.append(" 				1 = 1     ");
		sql.append( getReprotByProduct_model(product));
		sql.append(" 			AND v.source = '2'     ");
		sql.append(" 		) AS ts,     ");
		sql.append(" 		(     ");
		sql.append(" 			SELECT     ");
		sql.append(" 				count(*)     ");
		sql.append(" 			FROM     ");
		sql.append(" 				biz_verifys v     ");
		sql.append(" 			LEFT JOIN biz_product m ON v.productNo = m.productNo     ");
		sql.append(" 			WHERE     ");
		sql.append(" 				1 = 1     ");
		sql.append( getReprotByProduct_model(product));
		sql.append(" 			AND v.reviewNum = '1'     ");
		sql.append(" 		) AS fy,     ");
		sql.append(" 		(     ");
		sql.append(" 			SELECT     ");
		sql.append(" 				count(*)     ");
		sql.append(" 			FROM     ");
		sql.append(" 				biz_product m     ");
		sql.append(" 			LEFT JOIN biz_verifys v ON v.productno = m.productno     ");
		sql.append(" 			WHERE     ");
		sql.append(" 				1 = 1     ");
		sql.append( getReprotByProduct_model(product));
		sql.append(" 			AND m.logoutType = '1'     ");
		sql.append(" 		) AS zx     ");
		sql.append(" 	FROM     ");
		sql.append(" 		DUAL     ");
		System.out.println(sql);
		List param = new ArrayList();
		
		
		
		return querySqlByJdbcTpl(sql.toString(), param, new RowMapper() {

			public ProductRegistQuarter mapRow(ResultSet rs, int arg1) throws SQLException {
				
				ProductRegistQuarter model = new ProductRegistQuarter();
				model.setCompanyName(rs.getNString("companyName"));
				model.setRegisteredCode(rs.getNString("registeredCode"));
				model.setFk(rs.getInt("fk"));
				model.setFy(rs.getInt("fy"));
				model.setTs(rs.getInt("ts"));
				model.setZx(rs.getInt("zx"));
				
				return model;
			}
			 
		 });
		
		
		
		
	}
	
	
	private String getReprotByProduct_model(ProductInfo product){
		if(product == null){return "";}
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(product.getRegistTime_left())){
			sql.append(" AND UNIX_TIMESTAMP(v.create_time) >= UNIX_TIMESTAMP('").append(product.getRegistTime_left()).append("') ");
		}
		if(StringUtils.isNotBlank(product.getRegistTime_right())){
			sql.append(" AND UNIX_TIMESTAMP(v.create_time) < UNIX_TIMESTAMP('").append(product.getRegistTime_right()).append("') ");
		}
		
		if(StringUtils.isNotBlank(product.getRegisteredCode())){
			sql.append("  and m.registeredCode = '").append(product.getRegisteredCode()).append("'");
		}
		return sql.toString();
	}
	
	private String getReprotByProduct_model2(ProductInfo product){
		if(product == null){return "";}
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(product.getRegistTime_left())){
			sql.append(" AND UNIX_TIMESTAMP(v.create_time) >= UNIX_TIMESTAMP('").append(product.getRegistTime_left()).append("') ");
		}
		if(StringUtils.isNotBlank(product.getRegistTime_right())){
			sql.append(" AND UNIX_TIMESTAMP(v.create_time) < UNIX_TIMESTAMP('").append(product.getRegistTime_right()).append("') ");
		}
		return sql.toString();
	}
	
	
	/**
	 * 产品主持人数量情况表		
	 * @return
	 */
	@Override
	public List<RegistNoQuarter> registNoQuarterList(){
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT   ");
		sql.append(" 	p.registeredCode,   ");
		sql.append(" 	p.companyName,   ");
		sql.append(" 	(   ");
		sql.append(" 		SELECT   ");
		sql.append(" 			count(*)   ");
		sql.append(" 		FROM   ");
		sql.append(" 			biz_product b   ");
		sql.append(" 		WHERE   ");
		sql.append(" 			b.registeredCode = p.registeredCode   ");
		sql.append(" 		AND UNIX_TIMESTAMP(b.registTime) >= UNIX_TIMESTAMP('2015-01-01')   ");
		sql.append(" 		AND UNIX_TIMESTAMP(b.registTime) < UNIX_TIMESTAMP('2015-12-31')   ");
		sql.append(" 	) AS 01total,   ");
		sql.append(" 	(   ");
		sql.append(" 		SELECT   ");
		sql.append(" 			count(*)   ");
		sql.append(" 		FROM   ");
		sql.append(" 			biz_product b   ");
		sql.append(" 		WHERE   ");
		sql.append(" 			b.registeredCode = p.registeredCode   ");
		sql.append(" 		AND UNIX_TIMESTAMP(b.registTime) >= UNIX_TIMESTAMP('2016-01-01')   ");
		sql.append(" 		AND UNIX_TIMESTAMP(b.registTime) < UNIX_TIMESTAMP('2016-12-31')   ");
		sql.append(" 	) AS 02total,   ");
		sql.append(" 	(   ");
		sql.append(" 		SELECT   ");
		sql.append(" 			count(*)   ");
		sql.append(" 		FROM   ");
		sql.append(" 			biz_product b   ");
		sql.append(" 		WHERE   ");
		sql.append(" 			b.registeredCode = p.registeredCode   ");
		sql.append(" 		AND UNIX_TIMESTAMP(b.registTime) >= UNIX_TIMESTAMP('2017-01-01')   ");
		sql.append(" 		AND UNIX_TIMESTAMP(b.registTime) < UNIX_TIMESTAMP('2017-12-31')   ");
		sql.append(" 	) AS 03total   ");
		sql.append(" FROM   ");
		sql.append(" 	(   ");
		sql.append(" 		SELECT   ");
		sql.append(" 			mm.registeredCode,   ");
		sql.append(" 			mm.companyName,   ");
		sql.append(" 			count(*)   ");
		sql.append(" 		FROM   ");
		sql.append(" 			(   ");
		sql.append(" 				SELECT   ");
		sql.append(" 					u.registeredCode,   ");
		sql.append(" 					u.companyName   ");
		sql.append(" 				FROM   ");
		sql.append(" 					biz_member_user u   ");
		sql.append(" 				LEFT JOIN biz_product p ON u.registeredCode = p.registeredCode   ");
		sql.append(" 			) mm   ");
		sql.append(" 		GROUP BY   ");
		sql.append(" 			mm.registeredCode,   ");
		sql.append(" 			mm.companyName   ");
		sql.append(" 	) p   ");
		sql.append(" UNION ALL   ");
		sql.append(" 	SELECT   ");
		sql.append(" 		'total',   ");
		sql.append(" 		'total',   ");
		sql.append(" 		(   ");
		sql.append(" 			SELECT   ");
		sql.append(" 				count(*)   ");
		sql.append(" 			FROM   ");
		sql.append(" 				biz_product p01   ");
		sql.append(" 			WHERE   ");
		sql.append(" 				1 = 1   ");
		sql.append(" 			AND UNIX_TIMESTAMP(p01.registTime) >= UNIX_TIMESTAMP('2015-01-01')   ");
		sql.append(" 			AND UNIX_TIMESTAMP(p01.registTime) < UNIX_TIMESTAMP('2015-12-31')   ");
		sql.append(" 		),   ");
		sql.append(" 		(   ");
		sql.append(" 			SELECT   ");
		sql.append(" 				count(*)   ");
		sql.append(" 			FROM   ");
		sql.append(" 				biz_product p01   ");
		sql.append(" 			WHERE   ");
		sql.append(" 				1 = 1   ");
		sql.append(" 			AND UNIX_TIMESTAMP(p01.registTime) >= UNIX_TIMESTAMP('2016-01-01')   ");
		sql.append(" 			AND UNIX_TIMESTAMP(p01.registTime) < UNIX_TIMESTAMP('2016-12-31')   ");
		sql.append(" 		),   ");
		sql.append(" 		(   ");
		sql.append(" 			SELECT   ");
		sql.append(" 				count(*)   ");
		sql.append(" 			FROM   ");
		sql.append(" 				biz_product p01   ");
		sql.append(" 			WHERE   ");
		sql.append(" 				1 = 1   ");
		sql.append(" 			AND UNIX_TIMESTAMP(p01.registTime) >= UNIX_TIMESTAMP('2017-01-01')   ");
		sql.append(" 			AND UNIX_TIMESTAMP(p01.registTime) < UNIX_TIMESTAMP('2017-12-31')   ");
		sql.append(" 		)   ");
		sql.append(" 	FROM   ");
		sql.append(" 		DUAL   ");
		
		
		System.out.println(sql);
		List param = new ArrayList();
		
		
		
		return querySqlByJdbcTpl(sql.toString(), param, new RowMapper() {

			public RegistNoQuarter mapRow(ResultSet rs, int arg1) throws SQLException {
				
				RegistNoQuarter model = new RegistNoQuarter();
				model.setCompanyName(rs.getNString("companyName"));
				model.setRegisteredCode(rs.getNString("registeredCode"));
				model.setQuarter01(rs.getInt("01total"));
				model.setQuarter02(rs.getInt("02total"));
				model.setQuarter03(rs.getInt("03total"));
				return model;
			}
			 
		 });
		
		
		
		
	}
	
	private String logout_model(ProductInfo product){
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(product.getRegistTime_left())){
			sql.append(" AND UNIX_TIMESTAMP(P.registTime) >= UNIX_TIMESTAMP('").append(product.getRegistTime_left()).append("') ");
		}
		if(StringUtils.isNotBlank(product.getRegistTime_right())){
			sql.append(" AND UNIX_TIMESTAMP(P.registTime) < UNIX_TIMESTAMP('").append(product.getRegistTime_right()).append("') ");
		}
		
		if(StringUtils.isNotBlank(product.getRegisteredCode())){
			sql.append("  and p.registeredCode = '").append(product.getRegisteredCode()).append("'");
		}
		return sql.toString();
	}
	
	/**
	 * 产品注销事由统计
	 * @param p
	 * @return
	 */
	@Override
	public List<ProductLogoutQuarter> productLogoutList(ProductInfo p){
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT       ");
		sql.append(" 	m.registeredCode,       ");
		sql.append(" 	m.companyName,       ");
		sql.append(" 	(       ");
		sql.append(" 		SELECT       ");
		sql.append(" 			count(*)       ");
		sql.append(" 		FROM       ");
		sql.append(" 			biz_product p       ");
		sql.append(" 		WHERE       ");
		sql.append(" 			p.registeredCode = m.registeredCode       ");
		sql.append(" 		AND p. STATUS = '4'       ");
		sql.append(" 		AND p.offreasonStatus = '1'       ");
		sql.append( logout_model(p) );
		sql.append(" 	) AS c1,       ");
		sql.append(" 	(       ");
		sql.append(" 		SELECT       ");
		sql.append(" 			count(*)       ");
		sql.append(" 		FROM       ");
		sql.append(" 			biz_product p       ");
		sql.append(" 		WHERE       ");
		sql.append(" 			p.registeredCode = m.registeredCode       ");
		sql.append(" 		AND p. STATUS = '4'       ");
		sql.append(" 		AND p.offreasonStatus = '2'       ");
		sql.append( logout_model(p) );
		sql.append(" 	) AS c2,       ");
		sql.append(" 	(       ");
		sql.append(" 		SELECT       ");
		sql.append(" 			count(*)       ");
		sql.append(" 		FROM       ");
		sql.append(" 			biz_product p       ");
		sql.append(" 		WHERE       ");
		sql.append(" 			p.registeredCode = m.registeredCode       ");
		sql.append(" 		AND p. STATUS = '4'       ");
		sql.append(" 		AND p.offreasonStatus = '3'       ");
		sql.append( logout_model(p) );
		sql.append(" 	) AS c3,       ");
		sql.append(" 	(       ");
		sql.append(" 		SELECT       ");
		sql.append(" 			count(*)       ");
		sql.append(" 		FROM       ");
		sql.append(" 			biz_product p       ");
		sql.append(" 		WHERE       ");
		sql.append(" 			p.registeredCode = m.registeredCode       ");
		sql.append(" 		AND p. STATUS = '4'       ");
		sql.append(" 		AND p.offreasonStatus = '4'       ");
		sql.append( logout_model(p) );
		sql.append(" 	) AS c4,       ");
		sql.append(" 	(       ");
		sql.append(" 		SELECT       ");
		sql.append(" 			count(*)       ");
		sql.append(" 		FROM       ");
		sql.append(" 			biz_product p       ");
		sql.append(" 		WHERE       ");
		sql.append(" 			p.registeredCode = m.registeredCode       ");
		sql.append(" 		AND p. STATUS = '4'       ");
		sql.append(" 		AND p.offreasonStatus = '5'       ");
		sql.append( logout_model(p) );
		sql.append(" 	) AS c5,       ");
		sql.append(" 	(       ");
		sql.append(" 		SELECT       ");
		sql.append(" 			count(*)       ");
		sql.append(" 		FROM       ");
		sql.append(" 			biz_product p       ");
		sql.append(" 		WHERE       ");
		sql.append(" 			p.registeredCode = m.registeredCode       ");
		sql.append(" 		AND p. STATUS = '4'       ");
		sql.append(" 		AND p.offreasonStatus = '6'       ");
		sql.append( logout_model(p) );
		sql.append(" 	) AS c6,       ");
		sql.append(" 	(       ");
		sql.append(" 		SELECT       ");
		sql.append(" 			count(*)       ");
		sql.append(" 		FROM       ");
		sql.append(" 			biz_product p       ");
		sql.append(" 		WHERE       ");
		sql.append(" 			p.registeredCode = m.registeredCode       ");
		sql.append(" 		AND p. STATUS = '4'       ");
		sql.append(" 		AND p.offreasonStatus = '7'       ");
		sql.append( logout_model(p) );
		sql.append(" 	) AS c7,      ");
		sql.append(" 	(	SELECT       ");
		sql.append(" 			count(*)       ");
		sql.append(" 		FROM       ");
		sql.append(" 			biz_product p       ");
		sql.append(" 		WHERE       ");
		sql.append(" 			p.registeredCode = m.registeredCode       ");
		sql.append(" 		AND p. STATUS = '4'       ");
		sql.append(" 		AND p.logoutType = '1'       ");
		sql.append( logout_model(p) );
		sql.append(" 	) AS c8       ");
		sql.append(" FROM       ");
		sql.append(" 	(       ");
		sql.append(" 		SELECT       ");
		sql.append(" 			u.registeredCode,       ");
		sql.append(" 			u.companyName       ");
		sql.append(" 		FROM       ");
		sql.append(" 			biz_member_user u       ");
		sql.append("        left join sys_users uu on uu.id = u.user_id ");
		sql.append(" 		LEFT JOIN biz_product p ON u.registeredCode = p.registeredCode       ");
		sql.append(" 		WHERE       ");
		sql.append(" 			1 = 1  and uu.type = 2      ");
		sql.append( logout_model(p) );
		sql.append(" 		GROUP BY       ");
		sql.append(" 			u.registeredCode,       ");
		sql.append(" 			u.companyName       ");
		sql.append(" 	) AS m       ");
		sql.append(" UNION ALL       ");
		sql.append(" 	SELECT       ");
		sql.append(" 		'total',       ");
		sql.append(" 		'total',       ");
		sql.append(" 		(       ");
		sql.append(" 			SELECT       ");
		sql.append(" 				count(*)       ");
		sql.append(" 			FROM       ");
		sql.append(" 				biz_product p       ");
		sql.append(" 			WHERE       ");
		sql.append(" 				1 = 1       ");
		sql.append(" 			AND p. STATUS = '4'       ");
		sql.append(" 			AND p.offreasonStatus = '1'       ");
		sql.append( logout_model(p) );
		sql.append(" 		) AS c1,       ");
		sql.append(" 		(       ");
		sql.append(" 			SELECT       ");
		sql.append(" 				count(*)       ");
		sql.append(" 			FROM       ");
		sql.append(" 				biz_product p       ");
		sql.append(" 			WHERE       ");
		sql.append(" 				1 = 1       ");
		sql.append(" 			AND p. STATUS = '4'       ");
		sql.append(" 			AND p.offreasonStatus = '2'       ");
		sql.append( logout_model(p) );
		sql.append(" 		) AS c2,       ");
		sql.append(" 		(       ");
		sql.append(" 			SELECT       ");
		sql.append(" 				count(*)       ");
		sql.append(" 			FROM       ");
		sql.append(" 				biz_product p       ");
		sql.append(" 			WHERE       ");
		sql.append(" 				1 = 1       ");
		sql.append(" 			AND p. STATUS = '4'       ");
		sql.append(" 			AND p.offreasonStatus = '3'       ");
		sql.append( logout_model(p) );
		sql.append(" 		) AS c3,       ");
		sql.append(" 		(       ");
		sql.append(" 			SELECT       ");
		sql.append(" 				count(*)       ");
		sql.append(" 			FROM       ");
		sql.append(" 				biz_product p       ");
		sql.append(" 			WHERE       ");
		sql.append(" 				1 = 1       ");
		sql.append(" 			AND p. STATUS = '4'       ");
		sql.append(" 			AND p.offreasonStatus = '4'       ");
		sql.append( logout_model(p) );
		sql.append(" 		) AS c4,       ");
		sql.append(" 		(       ");
		sql.append(" 			SELECT       ");
		sql.append(" 				count(*)       ");
		sql.append(" 			FROM       ");
		sql.append(" 				biz_product p       ");
		sql.append(" 			WHERE       ");
		sql.append(" 				1 = 1       ");
		sql.append(" 			AND p. STATUS = '4'       ");
		sql.append(" 			AND p.offreasonStatus = '5'       ");
		sql.append( logout_model(p) );
		sql.append(" 		) AS c5,       ");
		sql.append(" 		(       ");
		sql.append(" 			SELECT       ");
		sql.append(" 				count(*)       ");
		sql.append(" 			FROM       ");
		sql.append(" 				biz_product p      ");
		sql.append(" 			WHERE       ");
		sql.append(" 				1 = 1       ");
		sql.append(" 			AND p. STATUS = '4'       ");
		sql.append(" 			AND p.offreasonStatus = '6'       ");
		sql.append( logout_model(p) );
		sql.append(" 		) AS c6,       ");
		sql.append(" 		(       ");
		sql.append(" 			SELECT       ");
		sql.append(" 				count(*)       ");
		sql.append(" 			FROM       ");
		sql.append(" 				biz_product p       ");
		sql.append(" 			WHERE       ");
		sql.append(" 				1 = 1       ");
		sql.append(" 			AND p. STATUS = '4'       ");
		sql.append(" 			AND p.offreasonStatus = '7'       ");
		sql.append( logout_model(p) );
		sql.append(" 		) AS c7,       ");
		sql.append(" 		(       ");
		sql.append(" 			SELECT       ");
		sql.append(" 				count(*)       ");
		sql.append(" 			FROM       ");
		sql.append(" 				biz_product p       ");
		sql.append(" 			WHERE       ");
		sql.append(" 				1 = 1       ");
		sql.append(" 			AND p. STATUS = '4'       ");
		sql.append(" 			AND p.logoutType = '1'       ");
		sql.append( logout_model(p) );
		sql.append(" 		) AS c8      ");
		sql.append(" 	FROM       ");
		sql.append(" 		DUAL       ");
		
		
		
		

		System.out.println(sql);
		List param = new ArrayList();
		
		
		
		return querySqlByJdbcTpl(sql.toString(), param, new RowMapper() {

			public ProductLogoutQuarter mapRow(ResultSet rs, int arg1) throws SQLException {
				
				ProductLogoutQuarter model = new ProductLogoutQuarter();
				model.setCompanyName(rs.getNString("companyName"));
				model.setRegisteredCode(rs.getNString("registeredCode"));
				model.setC1(rs.getInt("c1"));
				model.setC2(rs.getInt("c2"));
				model.setC3(rs.getInt("c3"));
				model.setC4(rs.getInt("c4"));
				model.setC5(rs.getInt("c5"));
				model.setC6(rs.getInt("c6"));
				model.setC7(rs.getInt("c7"));
				model.setC8(rs.getInt("c8"));
				return model;
			}
			 
		 });
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

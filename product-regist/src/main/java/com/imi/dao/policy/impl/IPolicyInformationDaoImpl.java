package com.imi.dao.policy.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.controllers.icrm.policy.PolicyController;
import com.imi.dao.policy.IPolicyInformationDao;
import com.imi.entity.reportPolicy.ReportPolicy;
import com.imi.model.policy.PolicyInformation;
import com.softvan.utils.CollectionUtil;
@Repository
public class IPolicyInformationDaoImpl extends BaseDaoImpl<ReportPolicy> implements IPolicyInformationDao{
	
	private static final Logger logger = Logger.getLogger(IPolicyInformationDaoImpl.class);
	
	@Override
	public void queryList(PolicyInformation info) {
		try{		
		String qryCountPre = "select count(*) ";
		
		StringBuffer baseSql = new StringBuffer();
		baseSql.append("from ReportPolicy where 1=1 ");
		
		String orderBy = " order by id desc";
		//保单号
		if(StringUtils.isNotBlank(info.getPolicyNo())){
			baseSql.append(" and policyNo  = '").append(StringUtils.trim(info.getPolicyNo())).append("' ");
		}
		//上报单位
		if(StringUtils.isNotBlank(info.getPolicyUploadUnit())){
			baseSql.append(" and policyUploadUnit = '").append(StringUtils.trim(info.getPolicyUploadUnit())).append("' ");
		}
		
		String policyStartTime = info.getPolicyStartTime();
		
		//截取起始时间
		if(StringUtils.isNotBlank(policyStartTime)){
			policyStartTime = policyStartTime.substring(0, 4) + 
			policyStartTime.substring(5,7) + policyStartTime.substring(8, 10);
		}
		
		String policyEndTime = info.getPolicyEndTime();
		//截取终止时间
		if(StringUtils.isNotBlank(policyEndTime)){
			policyEndTime = policyEndTime.substring(0, 4) + 
					policyEndTime.substring(5,7) + policyEndTime.substring(8, 10);
		}
		
		//险种空
		if(StringUtils.isBlank(info.getRiskType())){
			
			if(StringUtils.isNotBlank(policyStartTime) && StringUtils.isNotBlank(policyEndTime)){
				//险种空，查询时间都不是空，将两种拼起来
				//先查四个险种
				List<ReportPolicy> newList = new ArrayList<ReportPolicy>();
				StringBuffer sql1 = new StringBuffer();
				sql1.append(" and riskType in ('08OV','05PI','08CT','05OP')");
				sql1.append(" and policyStartTime >= ").append(policyStartTime);
				sql1.append(" and policyStartTime <= ").append(policyEndTime);
				
				String requestSqlList = baseSql.toString() + sql1.toString() + orderBy;
				List<ReportPolicy> cList1 = this.query(requestSqlList,null, info.getPage(), info.getRows());
				
				String requestSqlCount = qryCountPre + baseSql.toString() + sql1.toString() + orderBy;
				Query sqlQuery = this.getCurrentSession().createQuery(requestSqlCount);
				Long count=(Long) sqlQuery.iterate().next();
				
				//查询09IE
				StringBuffer sql2 = new StringBuffer();
				sql2.append(" and riskType in ('09IE')");
				sql2.append(" and sailingDate >= ").append(policyStartTime);
				sql2.append(" and sailingDate <= ").append(policyEndTime);
				
 
				String requestSqlList2 = baseSql.toString() + sql2.toString() + orderBy;
				List<ReportPolicy> cList2 = this.query(requestSqlList2,null, info.getPage(), info.getRows());
 	
				String requestSqlCount2 = qryCountPre + baseSql.toString() + sql2.toString() + orderBy;
				Query sqlQuery2 = this.getCurrentSession().createQuery(requestSqlCount2);
				Long count2=(Long) sqlQuery2.iterate().next();
				//计算总数
				
				//遍历cList2然后给cList1
				if(CollectionUtil.isNotNull(cList1)){
					newList.addAll(cList1);
				}
				if(CollectionUtil.isNotNull(cList2)){
					newList.addAll(cList2);
				}
				info.setListPolicy(newList);
				info.setTotal(String.valueOf(count+count2));
				return;
			}
			if(StringUtils.isNotBlank(policyStartTime) && StringUtils.isBlank(policyEndTime)){
				List<ReportPolicy> newList = new ArrayList<ReportPolicy>();
				StringBuffer sql1 = new StringBuffer();
				sql1.append(" and riskType in ('08OV','05PI','08CT','05OP')");
				sql1.append(" and policyStartTime >= ").append(policyStartTime);
				
				String requestSqlList = baseSql.toString() + sql1.toString() + orderBy;
				List<ReportPolicy> cList1 = this.query(requestSqlList,null, info.getPage(), info.getRows());
				
				String requestSqlCount = qryCountPre + baseSql.toString() + sql1.toString() + orderBy;
				Query sqlQuery = this.getCurrentSession().createQuery(requestSqlCount);
				Long count=(Long) sqlQuery.iterate().next();
				 
				//查询09IE
				StringBuffer sql2 = new StringBuffer();
				sql2.append(" and riskType in ('09IE')");
				sql2.append(" and sailingDate >= ").append(policyStartTime);
				
				String requestSqlList2 = baseSql.toString() + sql2.toString() + orderBy;
				List<ReportPolicy> cList2 = this.query(requestSqlList2,null, info.getPage(), info.getRows());
 	
				String requestSqlCount2 = qryCountPre + baseSql.toString() + sql2.toString() + orderBy;
				Query sqlQuery2 = this.getCurrentSession().createQuery(requestSqlCount2);
				Long count2=(Long) sqlQuery2.iterate().next();
				//计算总数
				
				//遍历cList2然后给cList1
				if(CollectionUtil.isNotNull(cList1)){
					newList.addAll(cList1);
				}
				if(CollectionUtil.isNotNull(cList2)){
					newList.addAll(cList2);
				}
				info.setListPolicy(newList);
				info.setTotal(String.valueOf(count+count2));
				return;
			}
			if(StringUtils.isBlank(policyStartTime) && StringUtils.isNotBlank(policyEndTime)){
				List<ReportPolicy> newList = new ArrayList<ReportPolicy>();
				List<ReportPolicy> newLst = new ArrayList<ReportPolicy>();
				StringBuffer sql1 = new StringBuffer();
				sql1.append(" and riskType in ('08OV','05PI','08CT','05OP')");
				sql1.append(" and policyStartTime <= ").append(policyEndTime);
				
				String requestSqlList = baseSql.toString() + sql1.toString() + orderBy;
				List<ReportPolicy> cList1 = this.query(requestSqlList,null, info.getPage(), info.getRows());
				
				String requestSqlCount = qryCountPre + baseSql.toString() + sql1.toString() + orderBy;
				Query sqlQuery = this.getCurrentSession().createQuery(requestSqlCount);
				Long count=(Long) sqlQuery.iterate().next();
				
				//查询09IE
				StringBuffer sql2 = new StringBuffer();
				sql2.append(" and riskType in ('09IE')");
				sql2.append(" and sailingDate <= ").append(policyEndTime);
				
				String requestSqlList2 = baseSql.toString() + sql2.toString() + orderBy;
				List<ReportPolicy> cList2 = this.query(requestSqlList2,null, info.getPage(), info.getRows());
 	
				String requestSqlCount2 = qryCountPre + baseSql.toString() + sql2.toString() + orderBy;
				Query sqlQuery2 = this.getCurrentSession().createQuery(requestSqlCount2);
				Long count2=(Long) sqlQuery2.iterate().next();
				//计算总数
				
				if(CollectionUtil.isNotNull(cList1)){
					newList.addAll(cList1);
				}
				if(CollectionUtil.isNotNull(cList2)){
					newList.addAll(cList2);
				}
				info.setListPolicy(newList);
				info.setTotal(String.valueOf(count+count2));
				return;
			}else{
				//险种空，时间空喽
				String requestSqlList = baseSql.toString()  + orderBy;
 				List<ReportPolicy> cList = this.find(requestSqlList, null, info.getPage(), info.getRows());
 				String requestSqlCount = qryCountPre + baseSql.toString() + orderBy;
				Query sqlQuery = this.getCurrentSession().createQuery(requestSqlCount);
				Long count=(Long) sqlQuery.iterate().next();
				info.setTotal(String.valueOf(count));
				info.setListPolicy(cList);
				return;
			}
		}else{
			//险种是09IE，只查saillingDate
			if(("09IE").equals(info.getRiskType())){
				//保单启始时间
				if(StringUtils.isNotBlank(policyStartTime)){
					baseSql.append(" and sailingDate >= ").append(policyStartTime);
				}
				//保单结束时间
				if(StringUtils.isNotBlank(policyEndTime)){
					baseSql.append(" and sailingDate <= ").append(policyEndTime);
				}
				baseSql.append(" and riskType = '09IE' ");
			}else{
				if(StringUtils.isNotBlank(policyStartTime)){
					baseSql.append(" and policyStartTime >= ").append(policyStartTime);
				}
				//保单结束时间
				if(StringUtils.isNotBlank(policyEndTime)){
					baseSql.append(" and policyStartTime <= ").append(policyEndTime);
				}
				baseSql.append(" and riskType = '").append(info.getRiskType()).append("' ");
			}
			String requestSqlList = baseSql.toString()  + orderBy;
			List<ReportPolicy> cList = this.find(requestSqlList, null, info.getPage(), info.getRows());
			String requestSqlCount = qryCountPre + baseSql.toString() + orderBy;
			Query sqlQuery = this.getCurrentSession().createQuery(requestSqlCount);
			Long count=(Long) sqlQuery.iterate().next();
			info.setTotal(String.valueOf(count));
			info.setListPolicy(cList);
			return;
		}}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 查询biz_policy表内容
	 * @return
	 */
	private String sql_content(){
		StringBuffer sql = new StringBuffer();
		sql.append("select p from ReportPolicy p where 1=1 ");
		return sql.toString();
	}
	
	/**
	 * 查询biz_risk表详情
	 * @return
	 */
	private String sql_content(Long policyId){
		StringBuffer sql = new StringBuffer();
		sql.append("select p from ReportPolicy p  where p.id = "+policyId);
		return sql.toString();
	}

	/**
	 * 查询SQL条件
	 * @param info
	 * @return
	 */
	private String sql_condition(PolicyInformation info){
		
		StringBuffer sql = new StringBuffer();
		
		//保单号
		System.out.println(info.getPolicyNo()+"");
		if(!StringUtils.isBlank(info.getPolicyNo())){
			sql.append(" and p.policyNo  = '").append(StringUtils.trim(info.getPolicyNo())).append("' ");
		}
		//保单启始时间
		if(!StringUtils.isBlank(info.getPolicyStartTime())){
			String policyStartTime = info.getPolicyStartTime().substring(0, 4) + 
									 info.getPolicyStartTime().substring(5, 7) + 
									 info.getPolicyStartTime().substring(8, 10);
			sql.append(" and p.policyStartTime >= ").append(policyStartTime);
		}
		//保单结束时间
		if(!StringUtils.isBlank(info.getPolicyEndTime())){
			String policyStartTime = info.getPolicyEndTime().substring(0, 4) + 
					 				 info.getPolicyEndTime().substring(5, 7) + 
					                 info.getPolicyEndTime().substring(8, 10);
			sql.append(" and p.policyStartTime <= ").append(policyStartTime);
		}
		//险种名称
		if(!StringUtils.isBlank(info.getRiskType()) ){
			sql.append(" and p.riskType = '").append(info.getRiskType()).append("' ");
		}
		//保险公司
		if(!StringUtils.isBlank(info.getPolicyUploadUnit())){
			sql.append(" and p.policyUploadUnit = '").append(StringUtils.trim(info.getPolicyUploadUnit())).append("' ");
		}
		
		sql.append(" order by p.createdTime desc ");
		
		return sql.toString();
	}
 
	@Override
	public ReportPolicy queryDetails(Long policyId) {
		ReportPolicy reportPolicy = new ReportPolicy();
		String hql_policy = this.sql_content(policyId);
		/*String hql = this.sql_content();*/
		reportPolicy = (ReportPolicy)this.uniqueResult(hql_policy, null);
		return reportPolicy;
	}
	
	/**
	 *  根据保单号查询保单信息
	 */
	@Override
	public ReportPolicy qryInfoByPolicyNo(String policyNo) {
		ReportPolicy reportPolicy = new ReportPolicy();
		PolicyInformation info = new PolicyInformation();
		info.setPolicyNo(policyNo);
		String hql_policy = this.sql_content() + this.sql_condition(info);
		reportPolicy = (ReportPolicy)this.uniqueResult(hql_policy, null);
		return reportPolicy;
	}
	
	@Override
	public void insertPolicyBatch(List<ReportPolicy> policyLst) {
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "";
		String user = "";
		String password = "";
		
		Properties prop =  new  Properties();    
		String path = IPolicyInformationDaoImpl.class.getResource("/config.properties").getPath();
		try  { 

			InputStream in = new FileInputStream( path );    
           
            prop.load(in);    
            url = prop.getProperty( "jdbc_url" ).trim();    
            user = prop.getProperty( "jdbc_username" ).trim(); 
            password = prop.getProperty( "jdbc_password" ).trim();    
        }  catch  (Exception e) {    
            e.printStackTrace();    
        }    
		
		
		  //1、新建驱动
	    Driver driverInstance;
	    Connection conn = null;
	    PreparedStatement ps = null;
		try {
			driverInstance = (Driver) Class.forName(driver).newInstance();
			DriverManager.registerDriver(driverInstance);
			conn = DriverManager.getConnection(url, user, password);
					
		conn.setAutoCommit(false);
		String sql = "insert into biz_policy(accident_limit, accident_limit_05pi, add_prem, add_risk_name, add_term, amount, appnt_name, " +
        		"bill_no, claim_place, contract_no, created_time, currency, end_place, franchise, franchise_05op, goods_name, goods_quantity, " +
        		"insured_mark, insured_mark_quantity, insured_name, invoice_amount, is_offshore_vessels, judicial_control, loading_name, " +
        		"main_term, modified_time, pay_type, policy_end_time, policy_no, policy_rate, policy_rate_08ct, policy_start_time, policy_upload_unit, " +
        		"policy_value, prem, produce_time, producer, risk_name, risk_type, sailing_date, shipping_type, special_agreement, start_place, " +
        		"total_accident_limit, total_accident_limit_08ct, total_tonnage, trading_limit, underwrite_area, use_limit, vessels_age, vessels_amount, " +
        		"vessels_level, vessels_name, vessels_no, vessels_port, vessels_rate, vessels_type, vessels_use) " +
        		"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " +
        		"ON DUPLICATE KEY UPDATE accident_limit=?, accident_limit_05pi=?, add_prem=?, add_risk_name=?, add_term=?, amount=?, appnt_name=?," +
        		"bill_no=?, claim_place=?, contract_no=?, currency=?, end_place=?, franchise=?, franchise_05op=?, goods_name=?, goods_quantity=?," +
        		"insured_mark=?, insured_mark_quantity=?, insured_name=?, invoice_amount=?, is_offshore_vessels=?, judicial_control=?, loading_name=?," +
        		"main_term=?, modified_time=?, pay_type=?, policy_end_time=?, policy_rate=?, policy_rate_08ct=?, policy_start_time=?, policy_upload_unit=?," +
        		"policy_value=?, prem=?, produce_time=?, producer=?, risk_name=?, risk_type=?, sailing_date=?, shipping_type=?, special_agreement=?, start_place=?," +
        		"total_accident_limit=?, total_accident_limit_08ct=?, total_tonnage=?, trading_limit=?, underwrite_area=?, use_limit=?, vessels_age=?, vessels_amount=?," + 
        		"vessels_level=?, vessels_name=?, vessels_no=?, vessels_port=?, vessels_rate=?, vessels_type=?, vessels_use=?"; 
		 ps = conn.prepareStatement(sql); 
		 for(int i=0;i<policyLst.size();i++){ 
     		ReportPolicy policy = policyLst.get(i);
			ps.setBigDecimal(1,policy.getAccidentLimit()); 
            ps.setString(2 , policy.getAccidentLimit_05PI());
            ps.setBigDecimal(3 , policy.getAddPrem());
            ps.setString(4 , policy.getAddRiskName());
            ps.setString(5 , policy.getAddTerm());
            ps.setBigDecimal(6 , policy.getAmount());
            ps.setString(7 , policy.getAppntName());
            ps.setString(8 , policy.getBillNo());
            ps.setString(9 , policy.getClaimPlace());
            ps.setString(10, policy.getContractNo());
            ps.setTimestamp(11, new Timestamp(policy.getCreatedTime().getTime()));   
            ps.setString(12, policy.getCurrency());
            ps.setString(13, policy.getEndPlace());
            ps.setString(14, policy.getFranchise());
            ps.setBigDecimal(15, policy.getFranchise_05OP());
            ps.setString(16, policy.getGoodsName());
            ps.setString(17, policy.getGoodsQuantity());
            ps.setString(18, policy.getInsuredMark());
            ps.setBigDecimal(19, policy.getInsuredMarkQuantity());
            ps.setString(20, policy.getInsuredName());
            ps.setString(21, policy.getInvoiceAmount());
            ps.setString(22, policy.getIsOffshoreVessels());
            ps.setString(23, policy.getJudicialControl());
            ps.setString(24, policy.getLoadingName());
            ps.setString(25, policy.getMainTerm());
            ps.setTimestamp(26, new Timestamp(policy.getModifiedTime().getTime()));   
            ps.setString(27, policy.getPayType());
            ps.setString(28, policy.getPolicyEndTime());
            ps.setString(29, policy.getPolicyNo());
            ps.setBigDecimal(30, policy.getPolicyRate());
            ps.setString(31, policy.getPolicyRate_08CT());
            ps.setString(32, policy.getPolicyStartTime());
            ps.setString(33, policy.getPolicyUploadUnit());
            ps.setString(34, policy.getPolicyValue());
            ps.setBigDecimal(35, policy.getPrem());
            ps.setString(36, policy.getProduceTime());
            ps.setString(37, policy.getProducer());
            ps.setString(38, policy.getRiskName());
            ps.setString(39, policy.getRiskType());
            ps.setString(40, policy.getSailingDate());
            ps.setString(41, policy.getShippingType());
            ps.setString(42, policy.getSpecialAgreement());
            ps.setString(43, policy.getStartPlace());
            ps.setBigDecimal(44, policy.getTotalAccidentLimit());
            ps.setString(45, policy.getTotalAccidentLimit_05OP());
            ps.setString(46, policy.getTotalTonnage());
            ps.setString(47, policy.getTradingLimit());
            ps.setString(48, policy.getUnderwriteArea());
            ps.setString(49, policy.getUseLimit());
            ps.setString(50, policy.getVesselsAge());
            ps.setString(51, policy.getVesselsAmount());
            ps.setString(52, policy.getVesselsLevel());
            ps.setString(53, policy.getVesselsName());
            ps.setString(54, policy.getVesselsNo());
            ps.setString(55, policy.getVesselsPort());
            ps.setString(56, policy.getVesselsRate());
            ps.setString(57, policy.getVesselsType());
            ps.setString(58, policy.getVesselsUse());
            
            ps.setBigDecimal(59,policy.getAccidentLimit()); 
            ps.setString(60 , policy.getAccidentLimit_05PI());
            ps.setBigDecimal(61 , policy.getAddPrem());
            ps.setString(62 , policy.getAddRiskName());
            ps.setString(63 , policy.getAddTerm());
            ps.setBigDecimal(64 , policy.getAmount());
            ps.setString(65 , policy.getAppntName());
            ps.setString(66 , policy.getBillNo());
            ps.setString(67, policy.getClaimPlace());
            ps.setString(68, policy.getContractNo());
            ps.setString(69, policy.getCurrency());
            ps.setString(70, policy.getEndPlace());
            ps.setString(71, policy.getFranchise());
            ps.setBigDecimal(72, policy.getFranchise_05OP());
            ps.setString(73, policy.getGoodsName());
            ps.setString(74, policy.getGoodsQuantity());
            ps.setString(75, policy.getInsuredMark());
            ps.setBigDecimal(76, policy.getInsuredMarkQuantity());
            ps.setString(77, policy.getInsuredName());
            ps.setString(78, policy.getInvoiceAmount());
            ps.setString(79, policy.getIsOffshoreVessels());
            ps.setString(80, policy.getJudicialControl());
            ps.setString(81, policy.getLoadingName());
            ps.setString(82, policy.getMainTerm());
            ps.setTimestamp(83, new Timestamp(policy.getModifiedTime().getTime()));   
            ps.setString(84, policy.getPayType());
            ps.setString(85, policy.getPolicyEndTime());
            ps.setBigDecimal(86, policy.getPolicyRate());
            ps.setString(87, policy.getPolicyRate_08CT());
            ps.setString(88, policy.getPolicyStartTime());
            ps.setString(89, policy.getPolicyUploadUnit());
            ps.setString(90, policy.getPolicyValue());
            ps.setBigDecimal(91, policy.getPrem());
            ps.setString(92, policy.getProduceTime());
            ps.setString(93, policy.getProducer());
            ps.setString(94, policy.getRiskName());
            ps.setString(95, policy.getRiskType());
            ps.setString(96, policy.getSailingDate());
            ps.setString(97, policy.getShippingType());
            ps.setString(98, policy.getSpecialAgreement());
            ps.setString(99, policy.getStartPlace());
            ps.setBigDecimal(100, policy.getTotalAccidentLimit());
            ps.setString(101, policy.getTotalAccidentLimit_05OP());
            ps.setString(102, policy.getTotalTonnage());
            ps.setString(103, policy.getTradingLimit());
            ps.setString(104, policy.getUnderwriteArea());
            ps.setString(105, policy.getUseLimit());
            ps.setString(106, policy.getVesselsAge());
            ps.setString(107, policy.getVesselsAmount());
            ps.setString(108, policy.getVesselsLevel());
            ps.setString(109, policy.getVesselsName());
            ps.setString(110, policy.getVesselsNo());
            ps.setString(111, policy.getVesselsPort());
            ps.setString(112, policy.getVesselsRate());
            ps.setString(113, policy.getVesselsType());
            ps.setString(114, policy.getVesselsUse());
			 ps.addBatch();
			 if(i % 3000 == 0 && i != 0){
					ps.executeBatch();
					conn.commit();
					ps.clearBatch();
			 }
		 }
		 ps.executeBatch();
		 conn.commit();
		}catch(Exception ex){
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ex.printStackTrace();
			logger.info("异常信息:" + ex.getMessage());
		}finally{
			if(null!=ps){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void updatePolicy(ReportPolicy data) {
		super.update(data);
	}
}

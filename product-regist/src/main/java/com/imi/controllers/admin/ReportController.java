package com.imi.controllers.admin;

import java.text.CollationKey;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imi.controllers.admin.verifys.VerifysController;
import com.imi.entity.report.ProductLogoutQuarter;
import com.imi.entity.report.ProductQuarter;
import com.imi.entity.report.ProductRegistQuarter;
import com.imi.entity.report.RegistNoQuarter;
import com.imi.entity.setting.Dictionary;
import com.imi.entity.setting.MemberUser;
import com.imi.model.member.MemberUserInfo;
import com.imi.model.product.ProductInfo;
import com.imi.service.report.ReportService;
import com.imi.service.security.IMemberUserService;
import com.imi.service.setting.DictionaryService;
import com.imi.support.ResourceUtil;

@Controller(value="adminReportController")
@RequestMapping(value={"/admin/report"})
public class ReportController {
	
	private static final Logger logger = Logger.getLogger(VerifysController.class);
	@Resource
	private ReportService reportService;
	//registTimeStart=&memberUser=&registTimeEnd=
	@Resource
	private IMemberUserService memberUserService;
	@Resource
	private DictionaryService dictionaryServiceImpl;
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
public String test3(ModelMap model,String registTimeStart,String registTimeEnd,String registeredCode){
		if(logger.isDebugEnabled()) logger.debug("加载登录页面...");
	

		model.addAttribute("registTimeStart",registTimeStart);
		model.addAttribute("registTimeEnd",registTimeEnd);
		model.addAttribute("registeredCode",registeredCode);
		
		model.addAttribute("reportMap", reportService.getReportRisk(model));
		MemberUserInfo muser = new MemberUserInfo();
		List<MemberUser> musers = memberUserService.findMemberUsers(muser);
		model.addAttribute("musers",musers);
		
		return "report/test3";
	}
	
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
public String test2(ModelMap model,String registTimeStart,String registTimeEnd,String productType){
		if(logger.isDebugEnabled()) logger.debug("加载登录页面...");
	
		
		model.addAttribute("registTimeStart",registTimeStart);
		model.addAttribute("registTimeEnd",registTimeEnd);
		model.addAttribute("productType",productType);
		model.addAttribute("reportList", reportService.getReportCompany(model));



		return "report/test2";
	}
	
	@RequestMapping(value = "/test4", method = RequestMethod.GET)
	public String test4(ModelMap model,HttpServletRequest request){
		String productType = request.getParameter("productType");
		List<ProductQuarter> list = reportService.getReprotByYear();
		
		ProductQuarter pq = null;
		
		
		List<Dictionary> dicList = dictionaryServiceImpl.findChildDicLst("RISK");
		if(dicList != null && dicList.size()>0){
			
			for(int i=0;i<dicList.size();i++){
				
				pq = new ProductQuarter();
				Dictionary d = dicList.get(i);
				if(d != null){
					
					String riskType = d.getDicKey();
					String xz_key = d.getDicDesc();
					if(StringUtils.isNotBlank(productType)){
						if(!productType.equals(riskType)){
							continue;
						}
					}
					pq.setProductType("1");
					pq.setRiskType(riskType);
					pq.setRiskName("小计");
					for (int j = 0; j < list.size(); j++) {
						ProductQuarter p = list.get(j);
						if(p != null){
							if(d.getDicKey().equals(p.getRiskType()) && "1".equals(p.getProductType())){
								pq.setQuarter0101(pq.getQuarter0101() + p.getQuarter0101());
								pq.setQuarter0102(pq.getQuarter0102() + p.getQuarter0102());
								pq.setQuarter0103(pq.getQuarter0103() + p.getQuarter0103());
								pq.setQuarter0104(pq.getQuarter0104() + p.getQuarter0104());
								pq.setQuarter01total(pq.getQuarter01total() + p.getQuarter01total());
								
								pq.setQuarter0201(pq.getQuarter0201() + p.getQuarter0201());
								pq.setQuarter0202(pq.getQuarter0202() + p.getQuarter0202());
								pq.setQuarter0203(pq.getQuarter0203() + p.getQuarter0203());
								pq.setQuarter0204(pq.getQuarter0204() + p.getQuarter0204());
								pq.setQuarter02total(pq.getQuarter02total() + p.getQuarter02total());
								
								pq.setQuarter0301(pq.getQuarter0301() + p.getQuarter0301());
								pq.setQuarter0302(pq.getQuarter0302() + p.getQuarter0302());
								pq.setQuarter0303(pq.getQuarter0303() + p.getQuarter0303());
								pq.setQuarter0304(pq.getQuarter0304() + p.getQuarter0304());
								pq.setQuarter03total(pq.getQuarter03total() + p.getQuarter03total());
								
								pq.setQuarterTotal(pq.getQuarterTotal() + p.getQuarterTotal());
								pq.setProductType(p.getProductType());
								pq.setRiskType(p.getRiskType());
								pq.setRiskName("小计");
							}
						}
					}
					
					list.add(pq);	
					
					pq = new ProductQuarter();
					for (int j = 0; j < list.size(); j++) {
						ProductQuarter p = list.get(j);
						if(p != null){
							if(d.getDicKey().equals(p.getRiskType()) && "2".equals(p.getProductType())){
								pq.setQuarter0101(pq.getQuarter0101() + p.getQuarter0101());
								pq.setQuarter0102(pq.getQuarter0102() + p.getQuarter0102());
								pq.setQuarter0103(pq.getQuarter0103() + p.getQuarter0103());
								pq.setQuarter0104(pq.getQuarter0104() + p.getQuarter0104());
								pq.setQuarter01total(pq.getQuarter01total() + p.getQuarter01total());
								
								pq.setQuarter0201(pq.getQuarter0201() + p.getQuarter0201());
								pq.setQuarter0202(pq.getQuarter0202() + p.getQuarter0202());
								pq.setQuarter0203(pq.getQuarter0203() + p.getQuarter0203());
								pq.setQuarter0204(pq.getQuarter0204() + p.getQuarter0204());
								pq.setQuarter02total(pq.getQuarter02total() + p.getQuarter02total());
								
								pq.setQuarter0301(pq.getQuarter0301() + p.getQuarter0301());
								pq.setQuarter0302(pq.getQuarter0302() + p.getQuarter0302());
								pq.setQuarter0303(pq.getQuarter0303() + p.getQuarter0303());
								pq.setQuarter0304(pq.getQuarter0304() + p.getQuarter0304());
								pq.setQuarter03total(pq.getQuarter03total() + p.getQuarter03total());
								
								pq.setQuarterTotal(pq.getQuarterTotal() + p.getQuarterTotal());
								pq.setProductType(p.getProductType());
								pq.setRiskType(p.getRiskType());
								pq.setRiskName("小计");
							}
						}
					}
					if(pq.getRiskType() != null){
						list.add(pq);
					}
						
				}
			}
			
			
		}
		
		
		
		Map<String,Map<String,ProductQuarter>> map_xz_zx = new LinkedHashMap<String,Map<String,ProductQuarter>>();
		Map<String,Map<String,ProductQuarter>> map_xz_fjx = new LinkedHashMap<String,Map<String,ProductQuarter>>();
		/*Map<String,Map<String,Map<String,ProductQuarter>>> map_map = new TreeMap<String, Map<String,Map<String,ProductQuarter>>>(new Comparator(){
			Collator collator = Collator.getInstance();
			public int compare(Object element1, Object element2) {
			    CollationKey key1 = collator.getCollationKey(element1.toString());
			    CollationKey key2 = collator.getCollationKey(element2.toString());
			    if(key1.getSourceString().equals("合计")){
			    	return -1;
			    }
			    return 1;
			}
		});*/
		Map<String,Map<String,Map<String,ProductQuarter>>> map_map = new LinkedHashMap<String, Map<String,Map<String,ProductQuarter>>>();
		Map<String,ProductQuarter> map_pro_zx = null;
		Map<String,ProductQuarter> map_pro_fjx = null;

		
		
		
		
		
		
		if(dicList != null && dicList.size()>0){
			String type_key = "-1";
			for (int i = 0; i < dicList.size(); i++) {
				map_pro_zx = new LinkedHashMap<String,ProductQuarter>();
				map_pro_fjx = new LinkedHashMap<String,ProductQuarter>();
				Dictionary d = dicList.get(i);
				if(d != null){
					String riskType = d.getDicKey();
					String xz_key = d.getDicDesc();
					if(StringUtils.isNotBlank(productType)){
						if(!productType.equals(riskType)){
							continue;
						}
					}
					/*for (String key : map_pro.keySet()) {
						ProductQuarter p = map_pro.get(key);
						if(p==null){continue;}
						if(p.getRiskType().equals(riskType)){
							map_xz.put(xz_key, value);
						}
					}*/
					
					// 主险
					for(int j=0;j<list.size();j++){
						pq = list.get(j);
						if(pq != null){
							if(pq.getRiskType().equals(riskType) && pq.getProductType().equals("1")){
								map_pro_zx.put(pq.getRiskName(), pq);
							}
						}
					}
					
					// 附加险
					for(int j=0;j<list.size();j++){
						pq = list.get(j);
						if(pq != null){
							if(pq.getRiskType().equals(riskType) && pq.getProductType().equals("2")){
								map_pro_fjx.put(pq.getRiskName(), pq);
							}
						}
					}
					
					
					
					
					
					if(map_pro_zx.size() == 0){
						map_pro_zx.put(xz_key, new ProductQuarter(xz_key));
						map_xz_zx.put(xz_key, map_pro_zx);
					}else{
						map_xz_zx.put(xz_key, map_pro_zx);
					}
					
					if(map_pro_fjx.size() > 0){
						map_xz_fjx.put(xz_key, map_pro_fjx);
					}
					
					
				}
				
				
			}
			
		}
		
		map_map.put("主险", map_xz_zx);
		if(map_xz_fjx.size() >0){
			map_map.put("附加险", map_xz_fjx);
		}
		
		
		
		map_xz_zx = new LinkedHashMap<String,Map<String,ProductQuarter>>();
		Map<String,ProductQuarter> map_total = new LinkedHashMap<String,ProductQuarter>();
		if(list != null || list.size()>0){
			for(int i=0;i<list.size();i++){
				pq = list.get(i);
				if(pq != null && pq.getRiskName().equals("total")){
					map_total.put("合计", pq);
					break;
				}
			}
		}
		
		if(StringUtils.isBlank(productType)){
			map_xz_zx.put("合计", map_total);
			map_map.put("合计", map_xz_zx);
		}
		
		
		model.addAttribute("map_map",test4_model(map_map));
		model.addAttribute("productType", productType);
		return "report/test4";
		
	}
	
	
	
	private  String test4_model(Map<String,Map<String,Map<String,ProductQuarter>>> map_map){
		/*Map<String,Map<String,Map<String,ProductQuarter>>> map_map = new HashMap<String, Map<String,Map<String,ProductQuarter>>>();
		
		Map<String,ProductQuarter> map_31 = new HashMap<String, ProductQuarter>();
		map_31.put("xz1", new ProductQuarter("t1"));
		map_31.put("xz2", new ProductQuarter("t2"));
		map_31.put("xz3", new ProductQuarter("t3"));
		map_31.put("xz4", new ProductQuarter("t4"));
		
		Map<String,ProductQuarter> map_32 = new HashMap<String, ProductQuarter>();
		map_32.put("xz5", new ProductQuarter("t5"));
		map_32.put("xz6", new ProductQuarter("t6"));
		map_32.put("xz7", new ProductQuarter("t7"));
		map_32.put("xz8", new ProductQuarter("t8"));
		
		Map<String,ProductQuarter> map_33 = new HashMap<String, ProductQuarter>();
		map_33.put("xz9", new ProductQuarter("t9"));
		map_33.put("xz10", new ProductQuarter("t10"));
		map_33.put("xz11", new ProductQuarter("t11"));
		map_33.put("xz12", new ProductQuarter("t12"));
		
		Map<String,ProductQuarter> map_34 = new HashMap<String, ProductQuarter>();
		map_34.put("xz13", new ProductQuarter("t13"));
		map_34.put("xz14", new ProductQuarter("t14"));
		
		
		Map<String,Map<String,ProductQuarter>> map_2 = new HashMap<String, Map<String,ProductQuarter>>();
		
		map_2.put("xl1", map_31);
		map_2.put("xl2", map_32);
		map_2.put("xl3", map_33);
		map_2.put("xl4", map_34);
		
		map_map.put("zl1", map_2);
		
		Map<String,ProductQuarter> map_35 = new HashMap<String, ProductQuarter>();
		map_35.put("xz15", new ProductQuarter("t15"));
		map_35.put("xz16", new ProductQuarter("t16"));
		map_35.put("xz17", new ProductQuarter("t17"));
		
		Map<String,Map<String,ProductQuarter>> map_3 = new HashMap<String, Map<String,ProductQuarter>>();
		
		map_3.put("xl5", map_35);
		map_map.put("zl2", map_3);
		
		
		
		
		
		
		
		
		
		*/
		
		
		
		
		
		Map<String,Integer> map_no = new LinkedHashMap<String, Integer>();
		 
		for (String key : map_map.keySet()) {
			String new_key ="";
			if(key.equals("主险")){
				new_key = "1";
			}else{
				new_key = "2";
			}
			Map<String,Map<String,ProductQuarter>> map2 = map_map.get(key);
			int num = 0;
			for (String key2 : map2.keySet()) {
				num = num+map2.get(key2).size();
				map_no.put(key2+"_"+new_key, map2.get(key2).size());
			}
			map_no.put(key+"_"+new_key, num);
		  }
		
		StringBuffer html = new StringBuffer();
		for (String key : map_map.keySet()) {
			String new_key ="";
			if(key.equals("主险")){
				new_key = "1";
			}else{
				new_key = "2";
			}
			html.append("<tr>");
			Map<String,Map<String,ProductQuarter>> map2 = map_map.get(key);
			int num_1 = map_no.get(key+"_"+new_key);
			
			html.append("<td ").append(" rowspan = '").append(num_1).append("'>").append(key).append("</td>");
			
			for (String key2 : map2.keySet()) {
				int num_2 = map_no.get(key2+"_"+new_key);
				Map<String,ProductQuarter> map3 = map2.get(key2);
				html.append("<td ").append(" rowspan = '").append(num_2).append("'>").append(key2).append("</td>");
				for (String key3 : map3.keySet()) {
					if(key3.startsWith("xugang")){
						html.append("<td style='text-align:left;'").append(" rowspan = '1'></td>");
					}else{
						Dictionary dic = null;
						try {
							dic = dictionaryServiceImpl.getValueByType(key3,key3.substring(0,2));
							
						} catch (Exception e) {
							
						}
						if(dic != null){
							html.append("<td style='text-align:left;'").append(" rowspan = '1'>").append(dic.getDicValue()).append("</td>");
						}else{
							html.append("<td style='text-align:left;'").append(" rowspan = '1'>").append(key3).append("</td>");
						}
						
					}
					
					ProductQuarter p =  map3.get(key3);
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0101()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0102()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0103()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0104()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter01total()).append("</td>");
					
					
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0201()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0202()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0203()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0204()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter02total()).append("</td>");
					
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0301()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0302()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0303()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter0304()).append("</td>");
					html.append("<td ").append(" rowspan = '1'>").append(p.getQuarter03total()).append("</td>");
					
					html.append("</tr>");
				}
			}
		  }
		
		System.err.println(html);
		
		return html.toString();
		
	}
	
	
	@RequestMapping(value = "/test5", method = RequestMethod.GET)
	public String test5(ModelMap model,@ModelAttribute(value="productInfo")ProductInfo productInfo){
		model.addAttribute("productInfo",productInfo);
		List<ProductRegistQuarter> list = reportService.getReprotByProduct(productInfo);
		Map<String,List<ProductRegistQuarter>> map_list = new LinkedHashMap<String,List<ProductRegistQuarter>>();
		
		List<ProductRegistQuarter> list_01 = new LinkedList<ProductRegistQuarter>();
		ProductRegistQuarter total_01 = new ProductRegistQuarter();
		total_01.setCompanyName("小计");
		List<ProductRegistQuarter> list_02 = new LinkedList<ProductRegistQuarter>();
		ProductRegistQuarter total_02 = new ProductRegistQuarter();
		total_02.setCompanyName("小计");
		
		List<ProductRegistQuarter> list_total = new LinkedList<ProductRegistQuarter>();
		
		
		if(list != null && list.size() > 0){
			for (int j = 0; j < list.size(); j++) {
				ProductRegistQuarter p = list.get(j);
				if(p == null){continue;}
				String registeredCode = p.getRegisteredCode();
				if(registeredCode != null && registeredCode.startsWith("70")){
					list_02.add(p);
					total_02.setFk(total_02.getFk() + p.getFk());
					total_02.setFy(total_02.getFy() + p.getFy());
					total_02.setTs(total_02.getTs() + p.getTs());
					total_02.setZx(total_02.getZx() + p.getZx());
				}else{
					if(p.getCompanyName().equals("total")){
						p.setCompanyName("总计");
						list_total.add(p);
						continue;
					}
					list_01.add(p);
					total_01.setFk(total_01.getFk() + p.getFk());
					total_01.setFy(total_01.getFy() + p.getFy());
					total_01.setTs(total_01.getTs() + p.getTs());
					total_01.setZx(total_01.getZx() + p.getZx());
				}
			}
		}
		
		
		if(list_01.size() >0){
			list_01.add(total_01);
			map_list.put("保险公司", list_01);
		}
		
		if(list_02.size() >0){
			list_02.add(total_02);
			map_list.put("保险经纪公司", list_02);
		}
		map_list.put("总计", list_total);
		
		
		
		
		model.addAttribute("map_map",test5_model(map_list));
		MemberUserInfo muser = new MemberUserInfo();
		List<MemberUser> musers = memberUserService.findMemberUsers(muser);
		model.addAttribute("musers",musers);
		return "report/test5";
		
	}
	
	
	
	
	private  String test5_model(Map<String,List<ProductRegistQuarter>> map_map){
		/*Map<String,List<ProductRegistQuarter>> map_map = new HashMap<String,List<ProductRegistQuarter>>();
		
		List<ProductRegistQuarter> list_01 = new ArrayList<ProductRegistQuarter>();
		list_01.add(new ProductRegistQuarter("t1"));
		list_01.add(new ProductRegistQuarter("t2"));
		list_01.add(new ProductRegistQuarter("t3"));
		list_01.add(new ProductRegistQuarter("t4"));
		
		Map<String,ProductQuarter> map_33 = new HashMap<String, ProductQuarter>();
		map_33.put("xz9", new ProductQuarter("t9"));
		map_33.put("xz10", new ProductQuarter("t10"));
		map_33.put("xz11", new ProductQuarter("t11"));
		map_33.put("xz12", new ProductQuarter("t12"));
		
		Map<String,ProductQuarter> map_34 = new HashMap<String, ProductQuarter>();
		map_34.put("xz13", new ProductQuarter("t13"));
		map_34.put("xz14", new ProductQuarter("t14"));
		map_map.put("zl1", list_01);
		*/
		

		
		
		Map<String,Integer> map_no = new LinkedHashMap<String, Integer>();
		 
		for (String key : map_map.keySet()) {
			List<ProductRegistQuarter> list = map_map.get(key);
			map_no.put(key, list.size());
		  }
		
		StringBuffer html = new StringBuffer();
		for (String key : map_map.keySet()) {
			
			
			List<ProductRegistQuarter> list_02 = map_map.get(key);
			
			for (int i=0;i<list_02.size();i++) {
				html.append("<tr>");
				if(i==0){
					
					int num_1 = map_no.get(key);
					
					html.append("<td width='20%' ").append(" rowspan = '").append(num_1).append("'>").append(key).append("</td>");
					
				}
				
				ProductRegistQuarter p = list_02.get(i);
				if(p != null){
					html.append("<td width='80%'").append(" rowspan = '1'>").append(p.getCompanyName()).append("</td>");
					html.append("<td").append(" rowspan = '1'>").append(p.getFk()).append("</td>");
					html.append("<td").append(" rowspan = '1'>").append(p.getZx()).append("</td>");
					html.append("<td").append(" rowspan = '1'>").append(p.getFy()).append("</td>");
					html.append("<td").append(" rowspan = '1'>").append(p.getZx()).append("</td>");
					
				}
				html.append("</tr>");
			}
			
		}
		  
		
		System.err.println(html);
		
		return html.toString();
		
	}
	
	
	@RequestMapping(value = "/test7", method = RequestMethod.GET)
	public String test7(ModelMap model){
		
		List<RegistNoQuarter> list = reportService.registNoQuarterList();
		List<RegistNoQuarter> list_total = new LinkedList<RegistNoQuarter>();
		
		RegistNoQuarter regist01 = new RegistNoQuarter();
		
		regist01.setCompanyName("保险公司");
		RegistNoQuarter regist02 = new RegistNoQuarter();
		regist02.setCompanyName("保险经纪公司");
		RegistNoQuarter registTotal = new RegistNoQuarter();
		registTotal.setCompanyName("合计");
		
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				RegistNoQuarter p = list.get(i);
				if(p != null){
					if(p.getRegisteredCode() != null){
						if(p.getRegisteredCode().startsWith("70")){
							regist02.setQuarter01(regist02.getQuarter01()+ p.getQuarter01());
							regist02.setQuarter02(regist02.getQuarter02()+ p.getQuarter02());
							regist02.setQuarter03(regist02.getQuarter03()+ p.getQuarter03());
						}else{
							if(p.getRegisteredCode().equals("total")){
								registTotal = p;
								registTotal.setCompanyName("合计");
								continue;
							}
							regist01.setQuarter01(regist01.getQuarter01()+ p.getQuarter01());
							regist01.setQuarter02(regist01.getQuarter02()+ p.getQuarter02());
							regist01.setQuarter03(regist01.getQuarter03()+ p.getQuarter03());
						}
					}
				}
			}
			
			list_total.add(regist01);
			list_total.add(regist02);
			list_total.add(registTotal);
			
			model.addAttribute("list_total",test7_model(list_total));
		}
		
		
		return "report/test7";
	
	
	}
	
	private  String test7_model(List<RegistNoQuarter> list){
		StringBuffer html = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			html.append("<tr>");
			RegistNoQuarter p = list.get(i);
			html.append("<td>").append(p.getCompanyName()).append("</td>");
			html.append("<td>").append(p.getQuarter01()).append("</td>");
			html.append("<td>").append(p.getQuarter02()).append("</td>");
			html.append("<td>").append(p.getQuarter03()).append("</td>");
			html.append("</tr>");
		}
		
		
		return html.toString();
		
	}
	
	
	public void setObjectValue(ProductLogoutQuarter p01,ProductLogoutQuarter p){
		p01.setC1(p01.getC1() + p.getC1());
		p01.setC2(p01.getC2() + p.getC2());
		p01.setC3(p01.getC3() + p.getC3());
		p01.setC4(p01.getC4() + p.getC4());
		p01.setC5(p01.getC5() + p.getC5());
		p01.setC6(p01.getC6() + p.getC6());
		p01.setC7(p01.getC7() + p.getC7());
		p01.setC8(p01.getC8() + p.getC8());
	}
	
	
	@RequestMapping(value = "/test6", method = RequestMethod.GET)
	public String test6(ModelMap model,@ModelAttribute(value="productInfo")ProductInfo productInfo){
		model.addAttribute("productInfo",productInfo);
		List<ProductLogoutQuarter> list = reportService.productLogoutList(productInfo);
		List<ProductLogoutQuarter> list_01 = new LinkedList<ProductLogoutQuarter>();
		List<ProductLogoutQuarter> list_02 = new LinkedList<ProductLogoutQuarter>();
		List<ProductLogoutQuarter> list_total = new LinkedList<ProductLogoutQuarter>();
		
		ProductLogoutQuarter p01 = new ProductLogoutQuarter();
		p01.setRegisteredCode(UUID.randomUUID().toString());
		p01.setCompanyName("小计");
		ProductLogoutQuarter p02 = new ProductLogoutQuarter();
		p02.setRegisteredCode("70"+UUID.randomUUID().toString());
		p02.setCompanyName("小计");
		
		
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				ProductLogoutQuarter p = list.get(i);
				if(p != null){
					if(p.getRegisteredCode() != null){
						if(p.getRegisteredCode().startsWith("70")){
							list_02.add(p);
							setObjectValue(p02,p);
						}else if(p.getRegisteredCode().equals("total")){
							p.setCompanyName("合计");
							list_total.add(p);
						}else{
							list_01.add(p);
							setObjectValue(p01,p);
						}
					}
				}
			}
			
			Map<String,List<ProductLogoutQuarter>> map = new LinkedHashMap<String, List<ProductLogoutQuarter>>();
			if(list_01.size() > 0){
				list_01.add(p01);
				map.put("保险公司", list_01);
			}
			if(list_02.size() > 0){
				list_02.add(p02);
				map.put("保险经纪公司", list_02);
			}
			map.put("合计", list_total);
			
			
			
			model.addAttribute("map_list",test6_model(map));
			MemberUserInfo muser = new MemberUserInfo();
			List<MemberUser> musers = memberUserService.findMemberUsers(muser);
			model.addAttribute("musers",musers);
		}
		
		
		
		
		
		return "report/test6";
	}
	
	private  String test6_model(Map<String,List<ProductLogoutQuarter>> map_list){
		StringBuffer html = new StringBuffer();
		
		for(String key:map_list.keySet()){
			
			List<ProductLogoutQuarter> list = map_list.get(key);
			
			for(int i=0;i<list.size();i++){
				html.append("<tr>");
				ProductLogoutQuarter p = list.get(i);
				if(i==0){
					html.append("<td ").append(" rowspan = '").append(list.size()).append("'>").append(key).append("</td>");
				}
				html.append("<td>").append(p.getCompanyName()).append("</td>");
				html.append("<td>").append(p.getC1()).append("</td>");
				html.append("<td>").append(p.getC2()).append("</td>");
				html.append("<td>").append(p.getC3()).append("</td>");
				html.append("<td>").append(p.getC4()).append("</td>");
				html.append("<td>").append(p.getC5()).append("</td>");
				html.append("<td>").append(p.getC6()).append("</td>");
				html.append("<td>").append(p.getC7()).append("</td>");
				html.append("<td>").append(p.getC8()).append("</td>");
				
				html.append("</tr>");
			}
			
			
			
		}
		
		
		
		return html.toString();
		
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		//test5_model();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

package com.imi.controllers.user;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imi.entity.products.Audit;
import com.imi.service.products.AuditService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.controllers.admin.security.ModuleConstant;
import com.imi.entity.products.Product;
import com.imi.entity.security.Right;
import com.imi.entity.security.User;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.Dictionary;
import com.imi.entity.setting.MemberUser;
import com.imi.model.excel.UProductExcel;
import com.imi.model.member.MemberUserInfo;
import com.imi.model.product.ProductInfo;
import com.imi.service.attachment.IAttachmentService;
import com.imi.service.news.INewsService;
import com.imi.service.products.IProductService;
import com.imi.service.security.IMemberUserService;
import com.imi.service.setting.DictionaryService;
import com.imi.service.setting.ReceiverService;
import com.imi.support.Constants;
import com.imi.support.DFAUtil;
import com.imi.support.ImportExport;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;
/**
 * 主险种管理控制器。
 * @author wanmei.
 * @since 2014-06-02.
 */
@Controller
@RequestMapping(value = "/user")
public class UProductController  implements IUserAware  {
	private static final Logger logger = Logger.getLogger(UProductController.class);
	//注入产品服务接口。
	@Resource
	private IProductService productService;
	@Resource
	private IMemberUserService memberUserService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private IAttachmentService attachmentService;
	@Resource
	private ReceiverService receiverService;
	
	@Resource
	private INewsService newService;

	@Resource
	private AuditService auditService;
	
	private Long current_user_id;
	private String userNickName;

	/**
	 * 获取列表页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/list","/","/index"})
	public String list(@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
	
		List<Product> products = null;
		MemberUser muser = new MemberUser();
		MemberUserInfo memberUserInfo = new MemberUserInfo();
	    User user=  new User();
	/*	if(StringUtils.isEmpty(current_user_id)){
			return "redirect:/front/index "; 
		}*/
		
		user.setId(current_user_id);
		memberUserInfo.setUser(user);
		muser = memberUserService.findMemberUser(memberUserInfo);
		productInfo.setUser(muser);
		if(productInfo.getPage()==null){
			productInfo.setPage(1);
		}
		long total = productService.totalNums(productInfo);

		productInfo.setRows(10);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		
		products = productService.findProducts(productInfo,null,null,null);
		
		request.setAttribute("userNickName",userNickName);
		request.setAttribute("companyName",muser.getCompanyName());
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("productInfo",productInfo);
		request.setAttribute("total",total);
		request.setAttribute("products",products);
		
		return "uproduct_list";
	}
	
	/**
	 * 获取详情页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/detail"})
	public String detail(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载详情页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		String productNO = request.getParameter("productNO");
/*		ProductInfo productInfo = new ProductInfo();
		List<Product> products = null;
		productInfo.setProductNO(productNO);
		products = productService.findProducts(productInfo);*/
		Product product = productService.findProductByProductNO(productNO);
		// 查询pdf附件供预览
	/*	List<Attachment> att = new ArrayList<Attachment>();*/
		try {
		/*	int type = Constants.PRODUCT_SIGN;
			att = attachmentServiceImpl.findAllFile(productNO, Constants.PRODUCT_SIGN);
			if(null != att && att.size() >0){
				for (int i = 0; i < att.size(); i++) {
					 String doc=att.get(i).getName().split("\\.")[1];
					 if(doc.equals("pdf")){
						 request.setAttribute("filePath", att.get(i).getFilePath());
					 }
				}
			}*/
			List<Attachment> atts = attachmentService.findAllSIGNFile(productNO);
			if(atts!=null){
				request.setAttribute("attachments",atts);
			}
			Attachment att = attachmentService.findSIGNPDF(productNO);
				
				if(att!=null){
				 request.setAttribute("filePath", att.getFilePath());
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
/*		request.setAttribute("attachments",att);*/
		request.setAttribute("userNickName",userNickName);
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("product",product);
		
		return "uproduct_detail";
	}
	
	/**
	 * 获取编辑页面。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.UPDATE})
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String reg(@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response,String regErr){
	MemberUser muser = new MemberUser();
	MemberUserInfo memberUserInfo = new MemberUserInfo();
    User user=  new User();
/*	if(StringUtils.isEmpty(current_user_id)){
		return "redirect:/front/index "; 
	}*/
	
	user.setId(current_user_id);
	memberUserInfo.setUser(user);
	muser = memberUserService.findMemberUser(memberUserInfo);
		productInfo.setUser(muser);
		request.setAttribute("userNickName",userNickName);
		request.setAttribute("productInfo", productInfo);
		if(regErr!=null&&!"".equals(regErr)){
			request.setAttribute("regErr", regErr);
	}
		return "uproduct_reg";
	}
	/**
	 * 查询数据。
	 * @return
	 *//*
	@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ProductInfo> datagrid(ProductInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.productService.find(info);
	}*/
	/**
	 * 更新数据。regErr
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.UPDATE})
	@RequestMapping(value="/insert", method = RequestMethod.POST)
	public String insert(@ModelAttribute(value="ycPolicyEntity")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			String attr_ext_list =request.getParameter("attr_ext_list");

		
			if(attr_ext_list==null|| !StringUtils.contains(attr_ext_list.toLowerCase(), "pdf")){
				request.setAttribute("regErr", 1);
			 return "redirect:/user/reg?regErr=1"; 
			}
			
			
			String attIdList = request.getParameter("attr_id_list");
			List<Attachment> attList = new ArrayList<Attachment>();
			if(StringUtils.isNotBlank(attIdList)){
				String[] arr = attIdList.split(",");
				for (int i = 0; i < arr.length; i++) {
					Attachment att = new Attachment();
					att.setId(Long.parseLong(arr[i]));
					attList.add(att);
				}
			}
		/*	 if(null!=productInfo&&productInfo.getProductType()==2){//附加险
				 productInfo.setRiskName(null);						//清空险类
				 清空险类 入registProduct方法中
			 }*/
			
			MemberUser	muser = memberUserService.findMemberUserbyUserId(current_user_id);
			productInfo.setUser(muser);
			request.setAttribute("userNickName",userNickName);
			productInfo.setCurrentUserId(current_user_id);
			productService.registProduct(productInfo,muser,attList);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新用户数据发生异常", e);
		}
		return "redirect:/user/list";
	}
	@RequestMapping(value="/checkKeyWords", method = RequestMethod.POST)
	@ResponseBody
	public Json checkKeyWords(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		String str = request.getParameter("str");
		Json result = new Json();
		List<String> censerWords;
		String msg = "1";
		try {
			censerWords = DFAUtil.searchWord(str);
			if (censerWords != null && censerWords.size() > 0) {
				msg = "发现关键字";
				for (String b : censerWords) {
					msg = " "+b;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		result.setSuccess(true);
		result.setMsg(msg);
		return result;
	}

	/**
	 * 获取列表页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/riskName"})
	@ResponseBody
	public Json getRiskName(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("获取对应险种数据...");
		String key  = request.getParameter("key");
		String type=request.getParameter("productType");
		Json result = new Json();
		//获取险类
		List<Dictionary> dictionarys = null;
		Dictionary dictionary1 = new Dictionary();
		dictionary1.setDicValue("测试一");
		dictionarys = dictionaryService.findChildDicLst(key);
	/*	dictionarys.add(dictionary1);*/
		
		 List a= new ArrayList();
	        int index = 0;
	        //如果选择其他选项则只显示全球定制保单险种
	        if(type!=null&&type.equals("3")&&key.equals("89")){
	            a.add(dictionarys.get(1).getDicKey()+"|"+dictionarys.get(1).getDicValue());

	        }else {
				for (Dictionary item : dictionarys) {
					a.add(item.getDicKey() + "|" + item.getDicValue());
				}
			}
			/* //如果不是其他选项则不显示全球定制保单险种
	        else if(type!=null&&!type.equals("3")&&key.equals("89")){
				a.add(dictionarys.get(0).getDicKey()+"|"+dictionarys.get(0).getDicValue());
			}*/
	       
		result.setSuccess(true);
		result.setData(a);
		return result;
	}
	
	/**
	 * 产品注销列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/uproduct_cancel_list"})
	public String product_cancel_list(Model model,@ModelAttribute(value="productInfo")ProductInfo productInfo)
	{
		List<Product> products = null;
		MemberUser muser = new MemberUser();
		MemberUserInfo memberUserInfo = new MemberUserInfo();
	    User user=  new User();
	/*	if(StringUtils.isEmpty(current_user_id)){
			return "redirect:/front/index "; 
		}*/
		
		user.setId(current_user_id);
		memberUserInfo.setUser(user);
		muser = memberUserService.findMemberUser(memberUserInfo);
		productInfo.setTitle("1");
		productInfo.setUser(muser);
		productInfo.setStatus(4);
		productInfo.setRows(10);
		if(productInfo.getPage()==null){
			productInfo.setPage(1);	
		}
		long total = productService.totalNums(productInfo);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		
		products = productService.findProducts(productInfo,null,null,null);
		
		model.addAttribute("productInfo",productInfo);
		model.addAttribute("userNickName",userNickName);
		model.addAttribute("total",total);
		model.addAttribute("products",products);
		return "uproduct_cancel_list";
	}
	/**
	 * 获取详情页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/uproduct_cancel_detail"})
	public String product_cancel_detail(HttpServletRequest request){
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		String productNO = request.getParameter("productNO");
		ProductInfo productInfo = new ProductInfo();
/*		List<Product> products = null;
		productInfo.setProductNO(productNO);
		
		products = productService.findProducts(productInfo);*/
		Product	product =	productService.findProductByProductNO(productNO);
		
		if(product != null){
			Attachment att = null;
			try {
				att = attachmentService.findSIGNPDF(productNO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(att != null){
				request.setAttribute("filePath", att.getFilePath());
			}
		}
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("product",product);
		
		return "uproduct_cancel_detail";
	}
	
	/**
	 * 注销产品。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/cancelProduct"}, method = RequestMethod.POST)
	public String cancelProduct(@ModelAttribute(value="productInfo")ProductInfo productInfo){
		
		Product product=productService.findProductByProductNO(productInfo.getProductNO());
		product.setOffreason(productInfo.getOffreason());
		if(product!=null){
			product.setStatus(4);//注销
            product.setProtectionType(2);//撤销保护期
			product.setModifiedTime(new Date());//设置注销日期
			productService.saveProduct(product);
		
			String subtitle=String.format("%s%s号产品自行注销",product.getUser().getCompanyName(),product.getProductNO());
			newService.insertProductNews(2, "产品注销", subtitle, current_user_id);	
			//站内信
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String title = "产品注销";
				String text = "贵公司的 "+product.getProductNO()+" 产品于"+format.format(new Date())+"完成注销。";
				String title1 = "产品注销";
				String text1 = productInfo.getRegistPerson()+"公司的 "+product.getProductNO()+" 产品于"+format.format(new Date())+"已自行注销。";
				receiverService.insertMess(title, text, current_user_id,current_user_id);
				receiverService.insertMess(title1, text1, current_user_id,Long.valueOf(1));
				receiverService.insertMess(title1, text1, current_user_id, Long.valueOf(2));
				receiverService.insertMess(title1, text1, current_user_id, Long.valueOf(3));
				receiverService.insertMess(title1, text1, current_user_id, Long.valueOf(4));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return "redirect:/user/uproduct_cancel_list";
	}
	
	/**
	 * 导出表格。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/import_product"})
	public void import_product(@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response){ 
		MemberUser muser = new MemberUser();
		MemberUserInfo memberUserInfo = new MemberUserInfo();
	    User user=  new User();
		user.setId(current_user_id);
		memberUserInfo.setUser(user);
		muser = memberUserService.findMemberUser(memberUserInfo);
		productInfo.setUser(muser);
		productInfo.setPage(1);
		productInfo.setRows(100000);
		List<Product> products = productService.findProducts(productInfo,null,null,null);
		UProductExcel upe = new UProductExcel();
		Workbook workbook = ImportExport.exportExcel(upe.getClass(),"产品详情表", products);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = "产品详情表"+format.format(new Date());
		try {
			 String userAgent = request.getHeader("user-agent").toLowerCase();  
			 if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
			         // win10 ie edge 浏览器 和其他系统的ie  
				 fileName = URLEncoder.encode(fileName, "UTF-8");  
			 } else {  
			         // fe  
				 fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");  
			 }
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".xlsx");
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void setUserName(String userName) {
	
		System.out.println(userName);
	}

	@Override
	public void setUserNickName(String userNickName) {
		// TODO Auto-generated method stub
		this.userNickName=userNickName;
	}

	@Override
	public void setUserId(Long userId) {
		current_user_id = userId;
		
	}


	public void setRoleCode(String roleCodes) {

		// TODO Auto-generated method stub
		
	}


	@RequestMapping(value={"/test_insert"})
	public String test_insert(@ModelAttribute(value="ycPolicyEntity")ProductInfo info,HttpServletRequest request,HttpServletResponse response){

		MemberUser	muser = memberUserService.findMemberUserbyUserId(current_user_id);

		// 得到年份
		Calendar a = Calendar.getInstance();
		String year = String.valueOf(a.get(Calendar.YEAR));
		// 4位流水号码
		String random4 = "";
		if (muser.getSwiftNum() == null) {
			random4 = String.format("%04d", 1);
			muser.setSwiftNum(new Integer(2));
		} else {
			random4 = String.format("%04d", muser.getSwiftNum());
			muser.setSwiftNum(new Integer(muser.getSwiftNum() + 1));
		}

		memberUserService.updateUser(muser);

		if (info.getProductType() == 1) {
			info.setProductNO(info.getRiskName() + year
					+ muser.getRegisteredCode() + random4);
		}
		if (info.getProductType() == 2) {
			info.setProductNO(info.getRiskType() + "AD" + year
					+ muser.getRegisteredCode() + random4);
			info.setRiskName(null);//清空险类
		}
		if (info.getProductType() == 3) {
			info.setProductNO(info.getRiskName()+ year
					+ muser.getRegisteredCode() + random4);
		}
		info.setRegistPerson(muser.getCompanyName());


		Audit audit = new Audit();
		audit.setOrderNo(1);
		audit.setReview(0);
		audit.setProductNO(info.getProductNO());
		audit.setProductLanguage(info.getProductLanguage());
		audit.setProRegReason(info.getProRegReason());//外文条款注册原因
		audit.setProductName(info.getProductName());
		audit.setProductType(info.getProductType());
		audit.setProtectionType(info.getProtectionType());
		audit.setChineseName(info.getChineseName());
		audit.setRiskName(info.getRiskName());
		audit.setRiskType(info.getRiskType());
		audit.setOffreason(info.getOffreason());
		audit.setStatus(1);
		audit.setUser(muser);
		audit.setDescription(info.getDescription());
		audit.setCreateTime(new Date());
		audit.setContactPersonName(info.getContactPersonName());
		audit.setContactPersonPhone(info.getContactPersonPhone());
		audit.setContactPersonEmail(info.getContactPersonEmail());
		audit.setAccreditationCompany(info.getAccreditationCompany());
		audit.setRegistPerson(info.getRegistPerson());
		audit.setRegistTime(new Date());
		audit.setMainSafe(info.getMainSafe());
		audit.setAddSafe(info.getAddSafe());


		auditService.insert(audit);

		return "redirect:/user/list";
	}

}
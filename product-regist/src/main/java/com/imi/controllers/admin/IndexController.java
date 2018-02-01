package com.imi.controllers.admin;
 

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imi.dao.products.IAuditDao;
import com.imi.dao.products.IProductDao;
import com.imi.entity.products.Audit;
import com.imi.model.product.AuditInfo;
import com.imi.service.products.AuditService;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.base.model.security.UserInfo;
import com.imi.controllers.admin.security.ModuleConstant;
import com.imi.entity.products.Product;
import com.imi.entity.security.Right;
import com.imi.entity.security.Role;
import com.imi.entity.security.User;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.Dictionary;
import com.imi.entity.setting.MemberUser;
import com.imi.model.excel.AProductExcel;
import com.imi.model.member.MemberUserInfo;
import com.imi.model.product.ProductInfo;
import com.imi.service.attachment.IAttachmentService;
import com.imi.service.products.IProductService;
import com.imi.service.security.IMemberUserService;
import com.imi.service.security.IUserService;
import com.imi.service.setting.DictionaryService;
import com.imi.service.setting.ReceiverService;
import com.imi.support.Constants;
import com.imi.support.ImportExport;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;

/**
 * 管理后台首页。
 * @author josh.
 * @since 2014-04-25.
 */
@Controller
@RequestMapping(value={"/admin"})
public class IndexController implements IUserAware {
	private static final Logger logger = Logger.getLogger(IndexController.class);
	private Long current_user_id;
	private String userName,userNickName,roleCode;
	
	@Resource
	private IProductService productService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private IAttachmentService attachmentService;
	
	//注入产品服务接口。
	@Resource
	private IMemberUserService memberUserService;
	@Resource
	private IUserService userService;
	@Resource
	private ReceiverService receiverService;
	@Resource
	private IAuditDao auditDao;

	@Resource
	private AuditService auditService;

	@Resource
	private IProductDao productDao;
	/*
	 * 设置用户ID.
	 * @see com.softvan.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(Long userId) {
		this.current_user_id = userId;
	}
	/*
	 * 设置用户名称。
	 * @see com.softvan.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*
	 * 设置用户昵称。
	 * @see com.softvan.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	

	@Override
	public void setRoleCode(String roleCode) {
		
		this.roleCode=roleCode;
	}
	/**
	 * 获取首页。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"","index","/"}, method = RequestMethod.GET)
	public String index(Model model){ 
		
				model.addAttribute("userNickName",userNickName);
		return "admin_index";
	}
	
	/**
	 * 产品查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/list"})
	public String product_list(Model model,@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletResponse response)
	{
		
		
		productInfo.setRows(10);
		if(productInfo.getPage()==null){
			productInfo.setPage(1);	
		}
		/**
		 * 相关权限数据总条数
		 */
		long total = productService.totalNums(productInfo);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		/**
		 * 所有相关信息
		 */
		List<Product>	products = productService.findProducts(productInfo,null,null,null);
		
		model.addAttribute("productInfo",productInfo);
		model.addAttribute("userNickName",userNickName);
		model.addAttribute("total",String.valueOf(total));
		model.addAttribute("products",products);
		return "admin_product_list";
	}
	
	/**
	 *系统管理员- 获取产品详情页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/product_detail"})
	public String product_detail(HttpServletRequest request){
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		String productNO = request.getParameter("productNO");
/*		ProductInfo productInfo = new ProductInfo();
		List<Product> products = null;
		productInfo.setProductNO(productNO);*/
		
		//products = productService.findProducts(productInfo);
		Product  product=  productService.findProductByProductNO(productNO);
		
		// 查询pdf附件供预览
	//	List<Attachment> att = new ArrayList<Attachment>();
		try {
		/*	int type = Constants.PRODUCT_SIGN;
			att = attachmentService.findAllFile(productNO, Constants.PRODUCT_SIGN);
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
			// request.setAttribute("filePath", att.getFilePath());
			if(att != null){
				 request.setAttribute("filePath",att.getFilePath());
				}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	/*	request.setAttribute("attachments",att);*/
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("product",product);
		
		return "admin_product_detail";
	}
	
	/**
	 * 产品注销列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/product_cancel_list"})
	public String product_cancel_list(Model model,@ModelAttribute(value="productInfo")ProductInfo productInfo)
	{
		productInfo.setTitle("1");
		productInfo.setStatus(4);
		productInfo.setRows(10);
		if(productInfo.getPage()==null){
			productInfo.setPage(1);	
		}
		long total = productService.totalNums(productInfo);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		
		List<Product>	products = productService.findProducts(productInfo,null,null,null);
		
		model.addAttribute("productInfo",productInfo);
		model.addAttribute("userNickName",userNickName);
		model.addAttribute("total",String.valueOf(total));
		model.addAttribute("products",products);
		return "product_cancel_list";
	}
	/**
	 * 获取详情页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/product_cancel_detail"})
	public String product_cancel_detail(HttpServletRequest request){
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		String productNO = request.getParameter("productNO");
/*		ProductInfo productInfo = new ProductInfo();
		Product products = null;
		productInfo.setProductNO(productNO);*/
		Product	product =	productService.findProductByProductNO(productNO);
	//products = productService.findProducts(productInfo);
		if(product != null){
			Attachment att = null;
			try {
				//att = attachmentServiceImpl.findFile(products.get(0),Constants.PRODUCT_SIGN);
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
		
		return "product_cancel_detail";
	}
	
	/**
	 * 获取险种列表。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/product_riskName"})
	@ResponseBody
	public Json product_riskName(HttpServletRequest request){
		String key  = request.getParameter("key");
		Json result = new Json();
		//获取险类
		List<Dictionary> dictionarys = null;
		dictionarys = dictionaryService.findChildDicLst(key);
		 List a= new ArrayList();
	        int index = 0;
	        for (Dictionary item : dictionarys) {
	        	
	        	a.add(item.getDicKey()+"|"+item.getDicValue());
	            
	        }
	       
		result.setSuccess(true);
		result.setData(a);
		return result;
	}
	
	/**
	 * 修改密码。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/modifyPassword"}, method = RequestMethod.POST)
	@ResponseBody
	public Json modifyPassword(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		Json result = new Json();
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		try {
			userService.modifyPassword(current_user_id, oldPassword, newPassword);
			result.setMsg("1");
		} catch (Exception e) {
			result.setMsg("0");
		}
		result.setSuccess(true);
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("userNickName",userNickName);
		return result;
	}
	/**
	 * 获取修改密码页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/modifyPassword_init"})
	public String modifyPassword_init(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		
		User user = new User();
		user = userService.findById(current_user_id);
		request.setAttribute("userNickName",userNickName);
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("oldPassword", user.getPassword());
		
		return "admin_modifyPassword";
	}

	/**
	 * 用户查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/muser_list"})
	public String muser_list(Model model,HttpServletRequest request,@ModelAttribute(value="userInfo")MemberUserInfo muser)
	{
		String page = request.getParameter("page");
		if(page != null){
			muser.setPage(Integer.parseInt((page)));
		}
	
		if(muser.getPage()==null || muser.getPage() == 0){
			muser.setPage(1);
		}
		long total = memberUserService.totalNums(muser);

		muser.setRows(10);
		muser.setCurrentRowNo((muser.getPage()-1)*muser.getRows()+1);
		//通过角色限制查询列表结果
		String roleCode = (String)request.getSession().getAttribute("roleCode");
		memberUserService.findMemberUsersList(muser,roleCode);
		
		model.addAttribute("musers",muser.getMemberList());
		model.addAttribute("userNickName",userNickName);
		model.addAttribute("muser",muser);
		model.addAttribute("total",muser.getTotal());
		return "muser_list";
	}
	
	/**
	 * 获取详情页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/muser_modify"})
	public String muser_modify(HttpServletRequest request){
		String userId = request.getParameter("muserId");
		MemberUser muser = new MemberUser();
		MemberUserInfo memberUserInfo = new MemberUserInfo();
	    User user=  new User();
		user.setId(Long.parseLong(userId));
		
		memberUserInfo.setUser(user);
		muser = memberUserService.findMemberUser(memberUserInfo);
		User newuser = muser.getUser();
		Set<Role> rose = newuser.getRoles();
		
		if(rose!=null&&!rose.isEmpty()){
			Role role=(Role) rose.toArray()[0];
			request.setAttribute("role",role);
		}
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("muser",muser);
		request.setAttribute("user",newuser);
		return "muser_modify";
	}
	
	/**
	 * 新增或保存。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/muser_addInit"})
	public String muser_addInit(HttpServletRequest request){
		
		MemberUserInfo memberUserInfo = new MemberUserInfo();
		
		/*String roleCode = request.getParameter("roleCode");
		if(!"sysAdmin".equals(roleCode)){
			
		}*/
		User user=  new User();
	    request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("muser",memberUserInfo);
		request.setAttribute("user",user);
		return "muser_add";
	}
	
	/**
	 * 新增或保存。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/muser_saveOrUpdate"})
	public String muser_saveOrUpdate(HttpServletRequest request,@ModelAttribute(value="muser")MemberUserInfo muser,@ModelAttribute(value="user")User user){
		String roleName = request.getParameter("roleName");
		if("user".equals(roleName) || "buser".equals(roleName) ){
			user.setType(2);
		}
		else if("cirsAdmin".equals(roleName)){
			user.setType(3);
		}
		else if("bjsAdmin".equals(roleName)){
			user.setType(4);
		}
		//保险公司登记会员
		else if("icrm".equals(roleName)){
			user.setType(5);
		}
		else{
			user.setType(1);
		}
		try {
			muser.setUser(user);
			muser.setCurrentUserId(current_user_id);
			memberUserService.saveOrUpdate(muser,roleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("current_user_id",current_user_id);
		
		return "redirect:/admin/muser_list";
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
		product.setOffreasonStatus(productInfo.getOffreasonStatus());
		if(product!=null){
			try {
			    product.setStatus(4);//注销
			    product.setProtectionType(2);//撤销保护期
				product.setModifiedTime(new Date());//设置注销时间
				productService.saveProduct(product);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String title = "产品注销";
				String text = "贵公司的 "+product.getProductNO()+" 产品于"+format.format(new Date())+"强制注销。";
				String title1 = "产品注销";
				String text1 = productInfo.getRegistPerson()+"公司的 "+product.getProductNO()+" 产品于"+format.format(new Date())+"强制注销。";
				receiverService.insertMess(title, text, current_user_id,product.getUser().getId());
				receiverService.insertMess(title1, text1, current_user_id,current_user_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/admin/product_cancel_list";
	}
	
	/**
	 * 导出表格。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/import_product"})
	public void import_product(@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response) throws ParseException {
		 productInfo.setPage(1);
		 productInfo.setRows(10000);
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 //获取开始时间 结束时间
        String startTime=request.getParameter("start");
		 String endTime=request.getParameter("end");
		 String findDate=request.getParameter("find");
        Date start=null;
        Date end=null;
        Date find=null;
		 if(!StringUtils.isEmpty(startTime)) {
              start = format.parse(startTime);
         }else{

		 }
        if(!StringUtils.isEmpty(endTime)) {
             end = format.parse(endTime);
        }
        if(!StringUtils.isEmpty(findDate)) {
             find = format.parse(findDate);
        }

		 List<Product> products = productService.findProducts(productInfo,start,end,find);
		 AProductExcel ape = new AProductExcel();
		 Workbook workbook = ImportExport.exportExcel(ape.getClass(),"产品详情表", products);

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

	/**
	 * 测试
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/test"})
	public String product_test(Model model, @ModelAttribute(value="auditInfo")AuditInfo auditInfo, HttpServletResponse response)
	{


		auditInfo.setRows(10);
		if(auditInfo.getPage()==null){
			auditInfo.setPage(1);
		}
		/**
		 * 相关权限数据总条数
		 */
		long total = auditService.total();
		auditInfo.setCurrentRowNo((auditInfo.getPage()-1)*auditInfo.getRows()+1);
		/**
		 * 所有相关信息
		 */
		List<Audit> auditList = auditService.findAudit(auditInfo);
		model.addAttribute("auditInfo",auditInfo);
		model.addAttribute("userNickName",userNickName);
		model.addAttribute("total",String.valueOf(total));
		model.addAttribute("auditList",auditList);
		return "admin_product_test";
	}

	/*
	产品审核
	*/
	@RequestMapping(value={"/test_cancel_detail"})
	public String test_cancel_detail(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("产品审核...");
		String productNo = request.getParameter("productNO");
		String review = request.getParameter("review");
		Audit audit = auditService.findAuditByProductNo(productNo);
		audit.setReview(Integer.parseInt(review));
		auditService.insert(audit);


		if ("1".equals(review)){
			Product product = new Product();
			product.setProductNO(audit.getProductNO());
			product.setProductLanguage(audit.getProductLanguage());
			product.setProRegReason(audit.getProRegReason());//外文条款注册原因
			product.setProductName(audit.getProductName());
			product.setProductType(audit.getProductType());
			product.setProtectionType(audit.getProtectionType());
			product.setChineseName(audit.getChineseName());
			product.setRiskName(audit.getRiskName());
			product.setRiskType(audit.getRiskType());
			product.setOffreason(audit.getOffreason());
			product.setStatus(1);
			product.setUser(audit.getUser());
			product.setDescription(audit.getDescription());
			product.setCreateTime(new Date());
			product.setContactPersonName(audit.getContactPersonName());
			product.setContactPersonPhone(audit.getContactPersonPhone());
			product.setContactPersonEmail(audit.getContactPersonEmail());
			product.setAccreditationCompany(audit.getAccreditationCompany());
			product.setRegistPerson(audit.getRegistPerson());
			product.setRegistTime(new Date());
			product.setMainSafe(audit.getMainSafe());
			product.setAddSafe(audit.getAddSafe());
			productDao.insert(product);
		}


		return "redirect:/admin/test";
	}


}
package com.imi.controllers.cirs;
 

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.base.model.news.NewsInfo;
import com.imi.base.model.security.ComplaintsInfo;
import com.imi.base.model.security.VerifysInfo;
import com.imi.controllers.admin.security.ModuleConstant;
import com.imi.entity.products.Complaints;
import com.imi.entity.products.News;
import com.imi.entity.products.Product;
import com.imi.entity.products.Verifys;
import com.imi.entity.security.Right;
import com.imi.entity.security.Role;
import com.imi.entity.security.User;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.Dictionary;
import com.imi.entity.setting.MemberUser;
import com.imi.model.member.MemberUserInfo;
import com.imi.model.product.ProductInfo;
import com.imi.service.attachment.IAttachmentService;
import com.imi.service.complaints.IComplaintsService;
import com.imi.service.news.INewsService;
import com.imi.service.products.IProductService;
import com.imi.service.security.IMemberUserService;
import com.imi.service.security.IUserService;
import com.imi.service.setting.DictionaryService;
import com.imi.service.verifys.IVerifysService;
import com.imi.support.Constants;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;

/**
 * 管理后台首页。
 * @author josh.
 * @since 2014-04-25.
 */
@Controller
@RequestMapping(value={"/cirsAdmin"})
public class CirsController implements IUserAware {
	private static final Logger logger = Logger.getLogger(CirsController.class);
	private Long current_user_id;
	private String userName,userNickName,roleCode;
	@Resource
	private IUserService userService;
	@Resource
	private IComplaintsService complaintsService;
	@Resource
	private IProductService productService;
	
	@Resource
	private IAttachmentService attachmentService;
	
	@Resource
	private DictionaryService dictionaryService;
	
	@Resource
	private IVerifysService verifyService;
	@Resource
	private IAttachmentService attachmentServiceImpl;
	@Resource
	private IComplaintsService complaintsServiceImpl;
	@Resource
	private INewsService newsService;
	
	//注入产品服务接口。
	@Resource
	private IMemberUserService memberUserService;
	
	@Override
	public void setUserId(Long userId) {
		current_user_id=userId;
		
	}
	@Override
	public void setUserName(String userName) {
		this.userName=userName;
		
	}
	@Override
	public void setUserNickName(String userNickName) {
		this.userNickName=userNickName;
		
	}
	@Override
	public void setRoleCode(String roleCodes) {
		this.roleCode=roleCodes;
		
	}
	
	/**
	 * 获取修改密码页面。
	 * @return
	 */

	@RequestMapping(value={"/modifyPassword_init"})
	public String modifyPassword_init(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		
		User user = new User();
		user = userService.findById(current_user_id);
		request.setAttribute("userNickName",userNickName);
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("oldPassword", user.getPassword());
		
		return "cirsAdmin_modifyPassword";
	}
	

	/**
	 * 修改密码。
	 * @return
	 */

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
		long total = productService.totalNums(productInfo);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		
		List<Product>	products = productService.findProducts(productInfo,null,null,null);
		model.addAttribute("productInfo",productInfo);
		model.addAttribute("userNickName",userNickName);
		model.addAttribute("total",String.valueOf(total));
		model.addAttribute("products",products);
		return "product_cirs/product_list";
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
	 * 产品获取详情页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/product_detail"})
	public String product_detail(HttpServletRequest request){
		String productNO = request.getParameter("productNO");
/*		ProductInfo productInfo = new ProductInfo();
		List<Product> products = null;
		productInfo.setProductNO(productNO);
		
		products = productService.findProducts(productInfo);*/
		Product  product=  productService.findProductByProductNO(productNO);
		// 查询pdf附件供预览
/*		List<Attachment> att = new ArrayList<Attachment>();*/
		try {
			/*int type = Constants.PRODUCT_SIGN;
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
	/*	request.setAttribute("attachments",att);*/
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("product",product);
		
		return "product_cirs/product_detail";
	}
	
	/**
	 * 用户查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/muser_list"})
	public String muser_list(Model model,HttpServletRequest request)
	{
		String page = request.getParameter("page");
		MemberUserInfo muser = new MemberUserInfo();
		if(page != null){
			muser.setPage(Integer.parseInt((page)));
		}
	
		if(muser.getPage()==null || muser.getPage() == 0){
			muser.setPage(1);
		}
		long total = memberUserService.totalNums(muser);

		muser.setRows(10);
		muser.setCurrentRowNo((muser.getPage()-1)*muser.getRows()+1);
		
		List<MemberUser> musers = memberUserService.findMemberUsers(muser);
		
		model.addAttribute("musers",musers);
		model.addAttribute("userNickName",userNickName);
		model.addAttribute("muser",muser);
		model.addAttribute("total",total);
		return "muser_cirs/muser_list";
	}
	
	/**
	 * 用户获取详情页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/muser_detail"})
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
		return "muser_cirs/muser_detail";
	}
	
	
	
	
	/**
	 * 查询所有投诉数据列表
	 * @param productInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/complaints_list"})
	public String list(@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		
		MemberUser user = new MemberUser();
		user.setId(current_user_id);
		productInfo.setUser(user);
		if(productInfo.getPage()==null){
			productInfo.setPage(1);
		}

		productInfo.setRows(10);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		try {
			complaintsService.findComplaints(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("productInfo",productInfo);
		return "complaints_cirs/complaints_list";
	}
	
	/**
	 * 查询投诉详情数据
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/cirsAdmin_complain_detail"}, method = RequestMethod.GET)
	public String showVerifysData(Model model,HttpServletRequest request)
	{
		//投诉数据ID
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)){
			request.setAttribute("msg", "获取投诉数据出错...");
			return "500";
		}
		String productNO = request.getParameter("productNO");
		ComplaintsInfo info = new ComplaintsInfo();
		info.setProductNO(productNO);
		Attachment att = null;
		// 获取预览PDF文档
		try {
/*			Product product = new Product();
			product.setProductNO(productNO);*/
			att = attachmentService.findSIGNPDF(productNO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(att != null){
			request.setAttribute("filePath", att.getFilePath());
		}
		// 查询该投诉记录对应的产品
		ProductInfo pInfo = null;
		try {
			// 产品信息
			pInfo = complaintsService.findProduct(productNO);
			// 投诉信息
			Complaints com = complaintsService.findComplaintsById(id);
			Product p = pInfo.getProduct();
			model.addAttribute("p", p);
			model.addAttribute("m", p.getUser());
			model.addAttribute("c", com);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "complaints_cirs/complaints_detail";
	}
	
	
	
	
	
	@RequestMapping(value={"/complaints_update"},method = RequestMethod.POST)
	public String update(@ModelAttribute(value="Complaints")Complaints complaints,HttpServletRequest request,HttpServletResponse response){
		String token = request.getParameter("token");
		if(token(token, request)){
			return "redirect:/cirsAdmin/complaints/list ";
		}
		String productNO = request.getParameter("productNO");
		// 无理0   有理1
		String result = request.getParameter("result");
		String complainsResult = request.getParameter("complainsResult");
		if("0".equals(result)){
			complaints.setStatus(Constants.COMPLAINS_STATUS_NO_NEED);
		}
		if(complaints.getId() == null || StringUtils.isBlank(productNO)){
			request.setAttribute("msg", "发起投诉失败...");
			return "500";
		}
		ComplaintsInfo info = new ComplaintsInfo();
		info.setComplaints(complaints);
		try {
			info.setCurrent_user_id(current_user_id);
			info.setProductNO(productNO);
			complaintsServiceImpl.updateComplain(info);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			return "500";
		}
		
		// 添加产品详情
		productService.insertProductLog(
				Constants.PRODUCT_COMPLAINS, 
				null, 
				complainsResult,
				"1", 
				current_user_id, 
				productNO, 
				complaints.getId(), 
				complaints.getResultReason(),
				Constants.ATTACHMENT_YES, 
				null);
		
		
		
		return "redirect:/cirsAdmin/complaints_list ";
	}
	
	public boolean token(String token,HttpServletRequest request){
		Object session_token = request.getSession().getAttribute("token");
		if(session_token == null){
			request.getSession().setAttribute("token", token);
			return false;
		}else{
			if(token.equals(session_token.toString())){
				return true;
			}else{
				request.getSession().setAttribute("token", token);
				return false;
			}
		}
	}

	/**
	 * 查询所有已核查数据
	 * @param productInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/verifys_list"})
	public String veritys_list(@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response){
		
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
	
		MemberUser user = new MemberUser();
		user.setId(current_user_id);
		productInfo.setUser(user);
		if(productInfo.getPage()==null){
			productInfo.setPage(1);
		}
		productInfo.setRows(10);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		
		try {
			verifyService.findProductsCirs(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("productInfos",productInfo);
		
		
		
		return "verifys_cirs/verifys_list";
	}
	
	
	/**
	 * 显示核查数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/cirsAdmin_verifys_detail"}, method = RequestMethod.GET)
	public String verifys_detail(Model model,HttpServletRequest request)
	{
		String productNO = request.getParameter("productNO");
		String v_id = request.getParameter("v_id");
		VerifysInfo verifysInfo = new VerifysInfo();
		verifysInfo.setProductNO(productNO);
		Verifys v = new Verifys();
		v.setId(Long.parseLong(v_id));
		verifysInfo.setVerifys(v);
		List<Verifys> list = verifyService.find(verifysInfo);
		// 查询pdf附件供预览
		Attachment att = null;
		try {
/*			Product product = new Product();
			product.setProductNO(productNO);*/
			att = attachmentService.findSIGNPDF(productNO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(att != null){
			request.setAttribute("filePath", att.getFilePath());
		}
		if(list != null && list.size() > 0){
			Product p = list.get(0).getProduct();
			model.addAttribute("p", p);
			model.addAttribute("m", p.getUser());
			model.addAttribute("v", list.get(0));
		}
		return "verifys_cirs/verifys_detail";
	}
	
	
	/**
	 * 发布消息查看
	 * @param model
	 * @param info
	 * @param pagenumber
	 * @param status
	 * @param checkAll
	 * @return
	 */
	@RequestMapping(value={"/news_list"},method={RequestMethod.GET, RequestMethod.POST})
	public String list(Model model,NewsInfo info,String pagenumber,String status,String checkAll){
		if(logger.isDebugEnabled()) logger.debug("加载消息列表页面...");
		if(checkAll!=null && !"".equals(checkAll)){
			model.addAttribute("checkAll", checkAll);
		}else{
			model.addAttribute("checkAll", 0);
		}
		if(status!=null && !"".equals(status)){
			if(status.length()==1){
				Integer[] state=new Integer[1];
				state[0]=Integer.valueOf(status);
				info.setAllState(state);
				model.addAttribute("states", status);
			}else{
				String [] stateStr=status.split(",");
				Integer[] allState=new Integer[10];
				String stateStr1="";
				for(int i=0;i<stateStr.length;i++){
					int j = Integer.valueOf(stateStr[i]).intValue();
					allState[i]=j;
					stateStr1+=j;
					System.out.println("state-----"+stateStr[i]+"/"+allState[i]);
				}
				info.setAllState(allState);
				model.addAttribute("states", stateStr1);
			}
		}
		if(!StringUtils.isEmpty(pagenumber)){
			info.setPage(Integer.valueOf(pagenumber));
		}else{
			pagenumber=1+"";
		} 
		info.setRows(10);
		info.setWhereFrom("后台");
		System.out.println("页码："+pagenumber);
		model.addAttribute("newslist", this.newsService.findAllNews(info));
		model.addAttribute("total",this.newsService.total(info));
		model.addAttribute("page",Integer.valueOf(pagenumber));
		
		return "news_cirs/news_list";
	}
	
	/**
	 * 发布消息获取详情页面。
	 * @return
	 */
	@RequestMapping(value={"/news_detail"})
	public String detail(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载详情页面...");
		String newsId = request.getParameter("newsId");
		News news = new News();
		
		news = newsService.findById(Long.parseLong(newsId));
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("news",news);
		
		return "news_cirs/news_detail";
	}
	
	
	

}
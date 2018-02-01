package com.imi.controllers.admin.verifys;

import java.util.ArrayList;
import java.util.List;

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

import com.imi.base.model.security.VerifysInfo;
import com.imi.controllers.admin.security.ModuleConstant;
import com.imi.entity.products.Product;
import com.imi.entity.products.Verifys;
import com.imi.entity.security.Right;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.Dictionary;
import com.imi.entity.setting.MemberUser;
import com.imi.model.product.ProductInfo;
import com.imi.service.attachment.IAttachmentService;
import com.imi.service.news.INewsService;
import com.imi.service.products.IProductService;
import com.imi.service.setting.DictionaryService;
import com.imi.service.verifys.IVerifysService;
import com.imi.support.Constants;
import com.softvan.aware.IUserAware;

/**
 * 核查
 * @author xugang
 *
 */
@Controller
@RequestMapping(value = "/admin/verifys")
public class VerifysController implements IUserAware {
	
	private static final Logger logger = Logger.getLogger(VerifysController.class);
	@Resource
	private IVerifysService verifyServiceImpl;
	@Resource
	private IProductService productService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private IAttachmentService attachmentService;
	@Resource
	private INewsService newsService;
	// 当前用户ID
	private Long current_user_id;
	private String roleCode;
	private String userNickName;
	
	@RequestMapping(value={"/list"})
	public String list(@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response){
		// 执行跑批 将 核查状态 待举证 修改为 待定论 （条件为 待举证状态下12小时内，产品注册人未操作）
		verifyServiceImpl.updateVerifysStatus();
		// 执行跑批 讲 核查状态 待复议 修改为 已审核  （）
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
//			products = productServiceImpl.findProducts(productInfo);
			verifyServiceImpl.findProducts(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("productInfos",productInfo);
		
		
		
		return "verifys/verifys_list";
	}
	
	/**
	 * 根据险种获取险类信息
	 * @return
	 */
	public List<Dictionary> getRiskInfo(String riskType){
		List<Dictionary> dictionarys = null;
		dictionarys = dictionaryService.findChildDicLst(riskType);
		return dictionarys;
	}
	
	
	
	/**
	 * 显示核查数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
	public String showVerifysData(Model model,HttpServletRequest request)
	{
		String productNO = request.getParameter("productNO");
		String v_id = request.getParameter("v_id");
		VerifysInfo verifysInfo = new VerifysInfo();
		verifysInfo.setProductNO(productNO);
		Verifys v = new Verifys();
		v.setId(Long.parseLong(v_id));
		verifysInfo.setVerifys(v);
		List<Verifys> list = verifyServiceImpl.find(verifysInfo);
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
		return "verifys/verifys_detail";
	}
	
	
	
	/**
	 * 管理员发起核查
	 * @param verifys
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/updateStatus1"},method = RequestMethod.POST)
	public String updateStatus1(@ModelAttribute(value="Verifys")VerifysInfo verifysInfo,HttpServletRequest request,HttpServletResponse response){
		
		String token = request.getParameter("token");
		if(token(token, request)){
			return "redirect:/admin/verifys/list ";
		}
		if(verifysInfo.getId() == null){
			request.setAttribute("msg", "修改投诉信息失败...");
			return "500";
		}
		
		verifysInfo.setCurrent_user_id(current_user_id);
		
		verifysInfo.setCurrent_user_id(current_user_id);
		Verifys v = new Verifys();
		v.setId(Long.parseLong(verifysInfo.getId()));
		verifysInfo.setVerifys(v);
		try {
			verifyServiceImpl.updateComplain(verifysInfo);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			return "500";
		}
		List<Long> attachmentIds = null;
		if(StringUtils.isNotBlank(verifysInfo.getFileIdJoin())){
			attachmentIds = new ArrayList<Long>();
			String[] ids = verifysInfo.getFileIdJoin().split(",");
			for (int i = 0; i < ids.length; i++) {
				attachmentIds.add(Long.parseLong(ids[i]));
			}
		}
		productService.insertProductLog(
				Constants.PRODUCT_VERIFYS, 
				null, 
				verifysInfo.getStatusString(),
				"1", 
				current_user_id, 
				verifysInfo.getProductNO(), 
				Long.parseLong(verifysInfo.getId()), 
				verifysInfo.getRemark(), 
				Constants.ATTACHMENT_YES, 
				attachmentIds);
		
		return "redirect:/admin/verifys/list ";
	}
	
	
	/**
	 * 管理员提交核查结论     待复议
	 * @param verifys
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/updateStatus4"},method = RequestMethod.POST)
	public String updateStatus3(@ModelAttribute(value="Verifys")VerifysInfo verifysInfo,HttpServletRequest request,HttpServletResponse response){
		
		String token = request.getParameter("token");
		if(token(token, request)){
			return "redirect:/admin/verifys/list";
		}
		if(verifysInfo.getId() == null){
			request.setAttribute("msg", "修改投诉信息失败...");
			return "500";
		}
		verifysInfo.setCurrent_user_id(current_user_id);
		
		
		Verifys v = new Verifys();
		v.setId(Long.parseLong(verifysInfo.getId()));
		verifysInfo.setVerifys(v);
		try {
			verifyServiceImpl.updateComplain4(verifysInfo);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			return "500";
		}
		
		List<Long> attachmentIds = null;
		if(StringUtils.isNotBlank(verifysInfo.getFileIdJoin())){
			attachmentIds = new ArrayList<Long>();
			String[] ids = verifysInfo.getFileIdJoin().split(",");
			for (int i = 0; i < ids.length; i++) {
				attachmentIds.add(Long.parseLong(ids[i]));
			}
		}
		// 记录核查操作日志
		productService.insertProductLog(
				Constants.PRODUCT_VERIFYS, 
				null, 
				verifysInfo.getStatusString(),
				"1", 
				current_user_id, 
				verifysInfo.getProductNO(), 
				Long.parseLong(verifysInfo.getId()), 
				verifysInfo.getRemark(), 
				Constants.ATTACHMENT_YES, 
				attachmentIds);
		
		return "redirect:/admin/verifys/list ";
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
	
	@Override
	public void setUserName(String userName) {
	
		System.out.println(userName);
	}

	@Override
	public void setUserNickName(String userNickName) {
		// TODO Auto-generated method stub
		System.out.println(userNickName);
		this.userNickName=userNickName;
	}
	@Override
	public void setUserId(Long userId) {
		current_user_id = userId;
	}
	public void setRoleCode(String roleCodes) {
		System.out.println(roleCodes);
		roleCode  = roleCodes;
	}
	
	
	
	
	
	

}

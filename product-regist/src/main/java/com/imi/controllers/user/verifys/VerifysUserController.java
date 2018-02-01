package com.imi.controllers.user.verifys;

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
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.imi.service.products.IProductService;
import com.imi.service.setting.DictionaryService;
import com.imi.service.verifys.IVerifysService;
import com.imi.support.Constants;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;

/**
 * 核查
 * @author xugang
 *
 */
@Controller
@RequestMapping(value = "/user/verifys")
public class VerifysUserController implements IUserAware {
	
	private static final Logger logger = Logger.getLogger(VerifysUserController.class);
	@Resource
	private IVerifysService verifyServiceImpl;
	@Resource
	private IProductService productService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private IAttachmentService attachmentServiceImpl;
	// 当前用户ID

	private Long current_user_id;
	private String roleCode;
	private String userNickName;
	
	@RequestMapping(value={"/list"})
	public String list(@ModelAttribute(value="productInfo")ProductInfo productInfo,HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isBlank(current_user_id+"")){
			request.setAttribute("msg", "获取用户信息失败...");
			return "500";
		}
		productInfo.setCurrentUserId(current_user_id);
		// 注册人查询 管理员发起核查的数据
		
		
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
	
		MemberUser user = new MemberUser();
		user.setId((long) 1);
		productInfo.setUser(user);
		if(productInfo.getPage()==null){
			productInfo.setPage(1);
		}
		productInfo.setRows(10);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		
		try {
//			products = productServiceImpl.findProducts(productInfo);
			verifyServiceImpl.findUserProducts(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("productInfo",productInfo);
		
		
		
		return "verifys/verifys_user_list";
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
		String v_id = request.getParameter("v_id");
		String productNO = request.getParameter("productNO");
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
			att = attachmentServiceImpl.findSIGNPDF(productNO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		
		return "verifys/verifys_user_detail";
	}
	
	
	
	/**
	 * 注册人对核查信息发起自述
	 * 1.填写自述原因
	 * 2.上传附件
	 * 3.核查状态 修改为待定论
	 * @param verifys
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/updateComByUser2"},method = RequestMethod.POST)
	public String updateComByUser2(@ModelAttribute(value="Verifys")VerifysInfo verifysInfo,HttpServletRequest request,HttpServletResponse response){
		
		String token = request.getParameter("token");
		if(token(token, request)){
			return "redirect:/user/verifys/list ";
		}
		verifysInfo.setCurrent_user_id(current_user_id);
		if(verifysInfo.getId() == null){
			request.setAttribute("msg", "修改投诉信息失败...");
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
				"核查材料上传",
				"1", 
				current_user_id, 
				verifysInfo.getProductNO(), 
				Long.parseLong(verifysInfo.getId()), 
				verifysInfo.getRemark(), 
				Constants.ATTACHMENT_YES, 
				attachmentIds);
		
		Verifys verifys = new Verifys();
		verifys.setId(Long.parseLong(verifysInfo.getId()));
		verifysInfo.setVerifys(verifys);
		try {
			verifyServiceImpl.updateComplainByUser3(verifysInfo);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			return "500";
		}
		
		return "redirect:/user/verifys/list ";
	}
	
	/**
	 * 申请复议
	 * @param verifys
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/updateComByUser4"},method = RequestMethod.POST)
	@ResponseBody
	public Json updateComByUser4(@ModelAttribute(value="Verifys")VerifysInfo verifysInfo,HttpServletRequest request,HttpServletResponse response){

		Json j = new Json();
		j.setSuccess(true);
		String msg = "";
		if(verifysInfo.getId() == null){
			j.setSuccess(false);
			j.setMsg("获取投诉信息失败...");
			return j;
		}
		verifysInfo.setCurrent_user_id(current_user_id);
		Verifys verifys = new Verifys();
		verifys.setId(Long.parseLong(verifysInfo.getId()));
		verifysInfo.setVerifys(verifys);
		try {
			msg = verifyServiceImpl.updateComplainByUser4(verifysInfo);
			if(!"".equals(msg)){
				j.setSuccess(false);
				j.setMsg(msg);
				return j;
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
					verifysInfo.getVerifyStatus(), 
					verifysInfo.getStatusString(),
					"1", 
					current_user_id, 
					verifysInfo.getProductNO(), 
					Long.parseLong(verifysInfo.getId()), 
					verifysInfo.getRemark(), 
					Constants.ATTACHMENT_YES, 
					attachmentIds);
			
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("处理投诉信息失败...");
			return j;
		}
		j.setMsg(msg);
		return j;
	}
	
	
	public boolean token(String token,HttpServletRequest request){
		Object session_token = request.getSession().getAttribute("token");
		if(session_token == null){
			request.getSession().setAttribute("token", token);
			return false;
		}else{
			if(session_token.toString().equals(token)){
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

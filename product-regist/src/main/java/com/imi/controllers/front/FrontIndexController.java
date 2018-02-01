package com.imi.controllers.front;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.base.model.news.NewsInfo;
import com.imi.controllers.admin.HelperController;
import com.imi.entity.products.News;
import com.imi.entity.products.Product;
import com.imi.entity.security.Role;
import com.imi.entity.security.User;
import com.imi.model.product.ProductInfo;
import com.imi.service.news.INewsService;
import com.imi.service.products.IProductService;
import com.imi.service.security.IUserAuthentication;
import com.imi.service.security.IUserAuthorization;
import com.softvan.mail.DefaultMailService;
import com.softvan.model.Json;

/**
 * 前台控制器
 * 
 * @author fengwei.
 * @since 2015年1月19日 下午1:36:44.
 */
@Controller
@RequestMapping(value = { "/front" })
public class FrontIndexController extends FrontBaseController {
	private static final Logger logger = Logger.getLogger(FrontIndexController.class);
	private static final String SESSION_KEY_VERIFYCODE = "__verifyCode__";
	private static final String SESSION_KEY_NICkNAME = "nickName";
	private static final String SESSION_KEY_ACCOUNT = "account";
	private static final Map<String,String>  sessionMap=new HashMap<String,String>();
	
	//注入用户验证服务接口。
		@Resource
		private IUserAuthentication userAuthentication;
		
		@Resource
		private IProductService productService;
	
		@Resource
		IUserAuthorization  userAuthorization;
		
		@Resource
		private INewsService newsService;
		
/*		@Resource
		private MockMailService mockMailService;*/
		@Resource
		private  DefaultMailService defaultMailService;

	/**
	 * 登陆页面
	 * @return
	 */
	@RequestMapping(value = {"/index","/","","login"}, method = RequestMethod.GET)
	public String loginPage(Model model,HttpServletRequest request)
	{
/*		String code="";
		Object sessioncode = request.getSession().getAttribute(SESSION_KEY_VERIFYCODE);
		if(sessioncode!=null){
			code=(String) sessioncode;
		
		}
		
		if(null==code||"".equals(code)){
			code ="111111"; this.userAuthentication.createVerifyCode();	
			request.getSession().setAttribute(SESSION_KEY_VERIFYCODE, code);
		}
		*/
		//初始化session

		
		request.setAttribute("news",this.newsService.findLatestNewsJson());
	/*	System.out.println(code);*/
	//将验证码放到HttpSession里面。
		System.out.println("全部进到了这里");
/*	if(logger.isDebugEnabled())logger.debug(String.format("本次生成验证码为[%s],已存放到Http Session中...", code));*/
		return "index";
	}

	/**
	 * 转到发布页面
	 * 
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
			@Deprecated
			@RequestMapping(value={"/newslist","/news"},method={RequestMethod.GET, RequestMethod.POST})
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
				System.out.println("页码："+pagenumber);
				model.addAttribute("newslist", this.newsService.findAllNews(info));
				model.addAttribute("total",String.valueOf(this.newsService.total(info)));
				model.addAttribute("page",Integer.valueOf(pagenumber));
				
				return "newslist";
			}
	
	/**
	 * 推送消息至首页
	 * @return
	 */
//	@RequiresPermissions(ModuleConstant.SECURITY_USER + ":" +Right.UPDATE)
	@RequestMapping(value="/pushnewstoindex", method = RequestMethod.GET)
	@ResponseBody
	public Json pushnewstoindex(HttpServletRequest request){
		if(logger.isDebugEnabled())logger.debug(String.format("首页接受推送消息"));
		Json result = new Json();
		try {
			Integer news =Integer.valueOf(request.getParameter("news"));
			Integer table=Integer.valueOf(request.getParameter("table"));
			System.out.println("news/table==="+news+"/"+table);
			//分别取最新消息和消息公示栏
			List<News>  tablelist=this.newsService.findLatestNews(0,10,new Integer[]{table});
			List<News>  newslist=this.newsService.findLatestNews(0,3,new Integer[]{news});
			Map<String, List<News>> map=new HashMap<String, List<News>>();
			map.put("table", tablelist);
			map.put("news", newslist);
			result.setData(map);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("推送消息发生异常", e);
		}
		return result;
	}


	/**
	 * 前台提取消息详情
	 * @return
	 */
//	@RequiresPermissions(ModuleConstant.SECURITY_USER + ":" +Right.UPDATE)
	@RequestMapping(value="/newsinfo", method = RequestMethod.GET)
	@ResponseBody
	public Json newsinfo(HttpServletRequest request,NewsInfo newsinfo){
		if(logger.isDebugEnabled())logger.debug(String.format("前台提取消息详情"));
		Json result = new Json();
		try {
			Long newsId =Long.valueOf(request.getParameter("newsId"));
			News news=this.newsService.findById(newsId);
			newsinfo.setClickCount(news.getClickCount());
			newsinfo.setContent(news.getContent());
			newsinfo.setDescr(news.getDescr());
			newsinfo.setEditors(news.getEditors());
			newsinfo.setKeywords(news.getKeywords());
			newsinfo.setState(news.getState());
			newsinfo.setSubtitle(news.getSubtitle());
			newsinfo.setTitle(news.getTitle());
			result.setData(newsinfo);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("提取消息详情发生异常", e);
		}
		return result;
	}
	
	
	
	/**
	 * 验证用户。
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Json authentication(HttpServletRequest request,HttpServletResponse response) {

		Json result = new Json();
		String addr="";
		try {
			String account = WebUtils.getCleanParam(request, "account"), 
				   password = WebUtils.getCleanParam(request, "password"), 
				   submitCode = WebUtils.getCleanParam(request, "verifycode");
			String browser = request.getHeader("User-Agent");
			if (!StringUtils.isEmpty(browser) && browser.length() > 255) {
				browser = browser.substring(0, 254);
			}
			String code="";
			//获取HttpSession中的验证码。
		/*	Object sessioncode = request.getSession().getAttribute(SESSION_KEY_VERIFYCODE);
			if(sessioncode!=null){
				code=(String) sessioncode;
			
			}*/
			
/*			if(null==code||"".equals(code)){
				code ="111111"; this.userAuthentication.createVerifyCode();
				request.getSession().setAttribute(SESSION_KEY_VERIFYCODE, code);
						System.out.println(code);
			}*/
			User user=userAuthorization.loadUserByAccount(account);
			if(user==null){
				
				throw new RuntimeException("用户不存在！");
			}
			//通过userId查询role
			Set<String> role = userAuthorization.findRolesByAccount(account);
			Iterator<String> it = role.iterator(); 
			String roleCode ="";
			while(it.hasNext()){
				roleCode= it.next();  
				System.out.println("roleCode"+roleCode);
			}
			 //添加到session域中
			request.getSession().setAttribute("roleCode",roleCode);
			code=sessionMap.get(account);
			
			//if(code==null||"".equals(code)){
				 /*this.userAuthentication.createVerifyCode();*/	
				
				if(user.getType()!=2){
					code ="111111";
				}//else{
				//	code=	this.userAuthentication.createVerifyCode();
			//	}

			//}

			System.out.println("验证码"+submitCode);
			System.out.println("验证码"+code);
			if(logger.isDebugEnabled())logger.debug(String.format("用户[%1$s,%2$s]登录时输入的验证码为[%3$s],HttpSession中的验证码为[%4$s]",account,password,submitCode,code));
			if(StringUtils.isEmpty(submitCode) ||  !submitCode.equalsIgnoreCase(code)){
				if(logger.isDebugEnabled()) logger.debug(String.format("验证码不正确！［%1$s => %2$s］", submitCode,code));
				throw new RuntimeException("验证码不正确！");
			}
			
			this.userAuthentication.authentication(account, password,
					HelperController.getRemoteAddr(request), browser);
	
			//根据用户类型判断角色。
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute(SESSION_KEY_NICkNAME, user.getNickName());
			request.getSession().setAttribute(SESSION_KEY_ACCOUNT, user.getAccount());
			result.setSuccess(true);
			result.setMsg("验证通过！");
			
			if(user.getType()==3){
				addr="cirsAdmin";
			}else if(user.getType()==2){
				addr="user";
			}
			else if(user.getType()==4){
				addr="bjsAdmin";
			}else if(user.getType()==5){
				addr="icrm";
			}
			else{
				addr="admin";	
			}
			sessionMap.remove(account);
			
			
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		//判断权限返回页面拼装权限地址
		result.setData(String.format("%s/list",addr));
		
		return result;
	}
	

	
	/**
	 * 获取验证码
	 */
	@RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
	@ResponseBody
	public Json  getVerifyCode(HttpServletRequest request,ModelMap model){
		Json result = new Json();
		String account = WebUtils.getCleanParam(request, "account");
		
		try {
			System.out.println(account);
			
			
			User user=userAuthorization.loadUserByAccount(account);
			
			
			
			if(user!=null){
			//生成验证码。
				String code="";
				Object sessioncode = request.getSession().getAttribute(SESSION_KEY_VERIFYCODE);
				if(sessioncode!=null){
					code=(String) sessioncode;
				}

				
				if(code==null||"".equals(code)){
					 /*this.userAuthentication.createVerifyCode();*/	
					
					if(user!=null&&user.getType()==2){
						code=	this.userAuthentication.createVerifyCode();
					}else{
						code ="111111";
					}
					
					
				}
				
			
			SimpleMailMessage msg=new SimpleMailMessage();
			msg.setFrom("mitr@sh-imi.org");
			msg.setTo(user.getEmail());
			msg.setSubject("航运保险协会注册平台验证码");
			msg.setText("发送成功");
			model.addAttribute("userName",user.getName());
			model.addAttribute("emailCode", code);
			request.getSession().setAttribute(SESSION_KEY_VERIFYCODE, code);
			sessionMap.put(account, code);
			System.out.println("session验证码"+request.getSession().getAttribute(SESSION_KEY_VERIFYCODE));
			System.out.println("发出验证码"+code);
			defaultMailService.send(msg,"mailTemplate.html", model);
			result.setData("验证码已经邮件发送到您指定邮箱中，有效期为30分钟，请注意查收；如果未收到请稍候再重试");
			result.setSuccess(true);
			}else{
				result.setData("用户名不正确");
				result.setSuccess(false);
			}

		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("验证码发送失败");
			
		
			logger.error("推送消息发生异常", e);
		}
	
		return result ;	
	}

	
	
	/**
	 * 相关新闻
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/about"}, method = RequestMethod.GET)
	public String about(Model model)
	{

		return "about";
	}
	
	/**
	 * 产品查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/product"}, method = RequestMethod.POST)
	public String list(Model model,@ModelAttribute(value="productInfo")ProductInfo productInfo)
	{
		List<Product> products = null;
		if(productInfo.getPage()==null){
			productInfo.setPage(1);
		}
		long total = productService.totalNums(productInfo);

		productInfo.setRows(5);
		productInfo.setCurrentRowNo((productInfo.getPage()-1)*productInfo.getRows()+1);
		
		products = productService.findProducts(productInfo,null,null,null);
		
		model.addAttribute("productInfo",productInfo);
		model.addAttribute("total",total);
		model.addAttribute("products",products);
		return "product_list";
	}
	
	@RequestMapping(value = {"/unauth"}, method = RequestMethod.GET)
	public String unauth(){
		 
		 return "unauth";
	 }
	
	
	//添加定时任务
	@RequestMapping(value={"/timedinteraction"},method = RequestMethod.POST)
	@ResponseBody
	public Json product_riskName(HttpServletRequest request){
		Json result = new Json();
		result.setSuccess(true);
		return result;
	}
}

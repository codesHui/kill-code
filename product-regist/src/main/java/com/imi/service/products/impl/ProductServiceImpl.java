package com.imi.service.products.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.dao.products.IProductDao;
import com.imi.dao.products.IProductFileDao;
import com.imi.dao.products.IProductHistroyDao;
import com.imi.dao.security.IUserDao;
import com.imi.dao.setting.IAttachmentDao;
import com.imi.entity.products.Product;
import com.imi.entity.products.ProductHistroy;
import com.imi.entity.security.User;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.MemberUser;
import com.imi.model.product.ProductInfo;
import com.imi.service.news.INewsService;
import com.imi.service.products.IProductService;
import com.imi.service.review.IReviewsService;
import com.imi.service.security.IMemberUserService;
import com.imi.service.setting.ReceiverService;

/**
 * 产品服务接口实现。
 */
@Service
public class ProductServiceImpl extends
		BaseDataServiceImpl<Product, ProductInfo> implements IProductService {
	private static final Logger logger = Logger
			.getLogger(ProductServiceImpl.class);
	@Resource
	private IProductDao productDao;
	@Resource
	private IProductFileDao productFileDaoImpl;
	@Resource
	private IMemberUserService memberUserService;


	@Resource
	private IUserDao userDao;
	@Resource
	private IProductHistroyDao productHistroyDao;

	@Resource
	private IAttachmentDao attachmentDao;

	@Resource
	private IReviewsService reviewsService;
	@Resource
	private ReceiverService receiverService;
	@Resource
	private INewsService newService;
	
	

	@Override
	public String insert(ProductInfo info, List<Attachment> attList){
		if (logger.isDebugEnabled())
			logger.debug("查询数据...");
		List<Product> products = null;
		ProductInfo info1 = new ProductInfo();
		info1.setProductNO(info.getProductNO());
		products = this.productDao.findProducts(info1,null,null,null);
		if (products.size() > 0) {
			return "该产品已经存在！";
		}
		Product product = new Product();
		product.setProductNO(info.getProductNO());
		product.setProductLanguage(info.getProductLanguage());
		product.setProRegReason(info.getProRegReason());//外文条款注册原因
		product.setProductName(info.getProductName());
		product.setProductType(info.getProductType());
		product.setProtectionType(info.getProtectionType());
		product.setChineseName(info.getChineseName());
		product.setRiskName(info.getRiskName());
		product.setRiskType(info.getRiskType());
		product.setOffreason(info.getOffreason());
		product.setStatus(1);
		product.setUser(info.getUser());
		product.setDescription(info.getDescription());
		product.setCreateTime(new Date());
		product.setContactPersonName(info.getContactPersonName());
		product.setContactPersonPhone(info.getContactPersonPhone());
		product.setContactPersonEmail(info.getContactPersonEmail());
		product.setAccreditationCompany(info.getAccreditationCompany());
		product.setRegistPerson(info.getRegistPerson());
		product.setRegistTime(new Date());
		product.setMainSafe(info.getMainSafe());
		product.setAddSafe(info.getAddSafe());
		Long id = this.productDao.insert(product);
		if (id != null) {
			productFileDaoImpl.updateBySql(attList, product.getProductNO());
		}

		if(product.getProductType() == 1){
			reviewsService.firstReviewMain(product);
		}
		if(product.getProductType() == 2){
			reviewsService.firstReviewExt(product);
		}
		if(product.getProductType() == 3){
			reviewsService.firstReviewOth(product);
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String title = "产品注册";
			String text = "贵公司的 "+product.getProductNO()+" 产品于"+format.format(new Date())+"完成注册。";
			receiverService.insertMess(title, text, info.getCurrentUserId(), info.getUser().getUser().getId());
			String title1 = "产品注册";
			String text1 = info.getUser().getCompanyName()+"公司的"+info.getProductNO()+"产品于"+format.format(new Date())+"完成注册。";
			receiverService.insertMess(title1, text1, info.getCurrentUserId(), Long.valueOf(1));
			receiverService.insertMess(title1, text1, info.getCurrentUserId(), Long.valueOf(2));
			receiverService.insertMess(title1, text1, info.getCurrentUserId(), Long.valueOf(3));
			receiverService.insertMess(title1, text1, info.getCurrentUserId(), Long.valueOf(4));
			String subtitle=String.format("%s%s号产品注册成功",info.getUser().getCompanyName(),product.getProductNO());
			newService.insertProductNews(1, "产品注册", subtitle, info.getCurrentUserId());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	protected List<Product> find(ProductInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("查询数据...");
		return this.productDao.findProducts(info,null,null,null);
	}

	@Override
	protected ProductInfo changeModel(Product data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Long total(ProductInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductInfo update(ProductInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Product> findProducts(ProductInfo info,Date start,Date end,Date find) {
		if (logger.isDebugEnabled())
			logger.debug("查询数据...");
		return this.productDao.findProducts(info,start,end,find);
	}

	@Override
	public Long totalNums(ProductInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("统计数据...");
		return this.productDao.total(info);
	}

	@Override
	public void deleteProduct(String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Product findProductById(Long id) {
		// TODO Auto-generated method stub
		return this.productDao.findByAccount(String.valueOf(id));
	}

	@Override
	public void registProduct(ProductInfo productInfo, MemberUser muser,
			List<Attachment> attList) {
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

		if (productInfo.getProductType() == 1) {
			productInfo.setProductNO(productInfo.getRiskName() + year
					+ muser.getRegisteredCode() + random4);
		}
		if (productInfo.getProductType() == 2) {
			productInfo.setProductNO(productInfo.getRiskType() + "AD" + year
					+ muser.getRegisteredCode() + random4);
			productInfo.setRiskName(null);//清空险类
		}
        if (productInfo.getProductType() == 3) {
            productInfo.setProductNO(productInfo.getRiskName()+ year
                    + muser.getRegisteredCode() + random4);
        }
		productInfo.setRegistPerson(muser.getCompanyName());
		this.insert(productInfo, attList);
	}

	@Override
	public Product findProductByProductNO(String productNO) {
		final String hql = " from Product p where p.productNO = ? ";
		return this.productDao.findUnique(hql, productNO);
	}

	@Override
	public void saveProduct(Product product) {
		this.productDao.saveOrUpdate(product);

	}

	

	@Override
	public void insertProductLog(ProductHistroy productHistory) {
		productHistroyDao.save(productHistory);

	}

	@Override
	public List<ProductHistroy> findallProductLog(ProductHistroy productHistory) {

		DetachedCriteria criteria = DetachedCriteria
				.forClass(ProductHistroy.class);
		if (productHistory.getType() != null) {
			criteria.add(Restrictions.eq("type", productHistory.getType()));
		}

		if (productHistory.getActionCode() != null) {

			criteria.add(Restrictions.eq("actionCode",
					productHistory.getActionCode()));
		}

		
			criteria.add(Restrictions.eq("productNO",
					productHistory.getProductNO()));
		
		// TODO Auto-generated method stub
		return productHistroyDao.find(criteria);
	}

	@Override
	public void insertProductLog(int type, String roleCode, String actionName,
			String actionCode, Long userId, String productNO, Long fkId,
			String content, int status, Collection<Long> attachmentIds) {
		if (userId != null) {
			ProductHistroy his = new ProductHistroy();
			
			his.setType(type);
			his.setStatus(status);
			his.setRoleCode(roleCode);
			his.setActionName(actionName);
			his.setActionCode(actionCode);
			User user = userDao.findById(userId);
			his.setOperPerson(user.getNickName());			
			his.setCreator(user);
			his.setCreateTime(new Date());
			his.setProductNO(productNO);
			his.setStatus(status);
			his.setFkId(fkId);
			his.setContent(content);			
			if(attachmentIds !=null&&attachmentIds.size()>0){
			List<Attachment> attachmentlist = attachmentDao.get(attachmentIds);
			his.setAttachment(attachmentlist);
			}
			insertProductLog(his);
		}
		
	}

}
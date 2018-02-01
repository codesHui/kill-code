package com.imi.service.complaints.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.imi.base.model.security.ComplaintsInfo;
import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.dao.complaints.IComplaintsDao;
import com.imi.dao.products.IProductDao;
import com.imi.dao.products.impl.ProductDaoImpl;
import com.imi.entity.products.Complaints;
import com.imi.entity.products.Product;
import com.imi.entity.products.Verifys;
import com.imi.entity.security.User;
import com.imi.model.product.ProductInfo;
import com.imi.service.complaints.IComplaintsService;
import com.imi.service.setting.ReceiverService;
import com.imi.service.verifys.IVerifysService;
import com.imi.service.verifys.impl.VerifysServiceImpl;
import com.imi.support.Constants;

@Service
public class ComplaintsServiceImpl extends BaseDataServiceImpl<Complaints, ComplaintsInfo> implements IComplaintsService {
	
	@Resource
	private IProductDao productDaoImpl;
	@Resource
	private IComplaintsDao complaintsDaoImpl;
	@Resource
	private IVerifysService verifysServiceImpl;
	
	@Resource
	private ReceiverService receiverService;
	
	
	@Override
	public void findComplaints(ProductInfo productInfo) {
		
		complaintsDaoImpl.findComplaints(productInfo);
	}
	
	@Override
	public void findDisposeComplaints(ProductInfo productInfo) {
		
		complaintsDaoImpl.findDisposeComplaints(productInfo);
	}
	

	/*@Override
	public List<Complaints> find(ComplaintsInfo info) {
		// TODO Auto-generated method stub
		return complaintsDaoImpl.find(info);
	}*/

	@Override
	protected ComplaintsInfo changeModel(Complaints data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Long total(ComplaintsInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComplaintsInfo update(ComplaintsInfo info){
		
		return null;
	}

	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 根据产品ID 查询产品信息，并跳转到产品投诉页面
	 * @param id
	 * @return
	 */
	public ComplaintsInfo getProductSkipComplaints(Long id){
		ComplaintsInfo info = null;
		try {
			info = new ComplaintsInfo();
			Product product = productDaoImpl.findById(id);
			info.setProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	@Override
	public void updateComplain(ComplaintsInfo info) throws Exception {
		// 更新投诉数据
		complaintsDaoImpl.update(info);
		if(info.getComplaints().getStatus() == Constants.COMPLAINS_STATUS_NO_NEED){
			return;
		}
		// 生成核查数据
		Product p = null;
		Criteria  c = productDaoImpl.getSession().createCriteria(Product.class);
		c.add(Restrictions.eq("productNO", info.getProductNO()));
		List<Product> list = c.list();
		if(list == null || list.size() == 0){
			return;
		}
		p = list.get(0);
		Verifys v = new Verifys();
		v.setStatus(Constants.VERIFYS_STATUS_1);
		v.setSource(Constants.VERIFYS_SOURCE_TOUSU);
		v.setCreateTime(new Date());
		v.setModifiedTime(new Date());
		User u = new User();
		u.setId(info.getCurrent_user_id());
		v.setVerifyMan(u);
		v.setVerifyDate(new Date());
		v.setProduct(p);
		v.setVerifyReason(info.getComplaints().getResultReason());
		verifysServiceImpl.save(v);
		
		
		/**
		 * 发送站内信信息
		 * 产品被投诉、举报
		 * 注册申请人收到的内容
		 * 1.贵公司的…（此处填写注册号）产品于…（此处填写时间，具体到分钟）受到投诉/举报。[查看详情]
		 * 系统管理员收到的内容
		 * …公司的…（此处填写注册号）产品于…（此处填写时间，具体到分钟）受到投诉/举报。请于5个工作日内组建工作组、启动核查程序[查看详情]
		 */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = "产品被投诉、举报";
		String text = "贵公司的 "+p.getProductNO()+" 产品于"+format.format(new Date())+"受到投诉举报。";
		receiverService.insertMess(title, text, info.getCurrent_user_id(), p.getUser().getUser().getId());
		
	}

	@Override
	public ProductInfo findProduct(String productNO) throws Exception {
		// TODO Auto-generated method stub
		return complaintsDaoImpl.findProduct(productNO);
	}

	@Override
	public Complaints findComplaintsById(String id) {
		return complaintsDaoImpl.findComplaintsById(id);
	}

	@Override
	public void save(ComplaintsInfo info) throws Exception {
		Complaints com = info.getComplaints();
		com.setCreateTime(new Date());
		com.setStatus(Constants.COMPLAINS_STATUS_NO);
//		com.setResultMan(resultMan); 创建人游客
		complaintsDaoImpl.save(com);
	}

	@Override
	protected List<Complaints> find(ComplaintsInfo info) {
		// TODO Auto-generated method stub
		return null;
	}


}

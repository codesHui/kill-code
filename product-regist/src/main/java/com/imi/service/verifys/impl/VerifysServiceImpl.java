package com.imi.service.verifys.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.imi.base.model.security.VerifysInfo;
import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.dao.products.IProductDao;
import com.imi.dao.setting.IAttachmentDao;
import com.imi.dao.verifys.IVerifysDao;
import com.imi.entity.products.Product;
import com.imi.entity.products.Verifys;
import com.imi.entity.security.User;
import com.imi.entity.setting.Attachment;
import com.imi.model.product.ProductInfo;
import com.imi.service.news.INewsService;
import com.imi.service.products.IProductService;
import com.imi.service.setting.ReceiverService;
import com.imi.service.verifys.IVerifysService;
import com.imi.support.Constants;
@Service
public class VerifysServiceImpl extends BaseDataServiceImpl<Verifys, VerifysInfo> implements IVerifysService {
	
	@Resource
	private IVerifysDao verifysDaoImpl;
	@Resource
	private IAttachmentDao attachmentDaoImpl;
	@Resource
	private ReceiverService receiverService;
	@Resource
	private IProductDao productDao;
	@Resource
	private IProductService productService;
	@Resource
	private INewsService newsService;

	@Override
	public void findProducts(ProductInfo productInfo) {
		verifysDaoImpl.findProducts(productInfo);
	}
	
	@Override
	public void findProductsCirs(ProductInfo productInfo) {
		verifysDaoImpl.findProductsCirs(productInfo);
	}
	
	@Override
	public void findUserProducts(ProductInfo productInfo) {
		
		verifysDaoImpl.findUserProducts(productInfo);
	}

	@Override
	public List<Verifys> find(VerifysInfo info) {
		
		return verifysDaoImpl.find(info);
	}

	@Override
	protected VerifysInfo changeModel(Verifys data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Long total(VerifysInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VerifysInfo update(VerifysInfo info) {
		return null;
	}

	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Verifys get(Long id) {
		Verifys v = verifysDaoImpl.load(Verifys.class, id);
		return v;
	}
	
	@Override
	public void save(Verifys vf) {
		// TODO Auto-generated method stub
		this.verifysDaoImpl.save(vf);
	}

	@Override
	public void update(Verifys v,Attachment attachment) throws Exception {
		try {
			// 保存附件
			attachmentDaoImpl.save(attachment);
			this.verifysDaoImpl.update(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateComplain(VerifysInfo info) throws Exception {
		Criteria  c = verifysDaoImpl.getSession().createCriteria(Verifys.class);
		c.add(Restrictions.eq("id", info.getVerifys().getId()));
		List<Verifys> list = c.list();
		Verifys com = null;
		if(c.list() != null && c.list().size()>0){
			com = list.get(0);
			//com.setResultReason(info.getVerifys().getResultReason());
			//com.setResult(info.getVerifys().getResult());
			com.setResultDate(new Date());
			com.setCreateTime(new Date());
			if((Constants.VERIFYS_STATUS_0+"").equals(info.getVerifyStatus())){
				com.setStatus(Constants.VERIFYS_STATUS_5);
			}else{
				com.setStatus(Constants.VERIFYS_STATUS_2);
			}
			User resultMan = new User();
			resultMan.setId(info.getCurrent_user_id());
			com.setResultMan(resultMan);
			com.setModifiedTime(new Date());
			verifysDaoImpl.update(com);
		}
		Product p = null;
		Criteria  crit = productDao.getSession().createCriteria(Product.class);
		crit.add(Restrictions.eq("productNO", info.getProductNO()));
		List<Product> pList = crit.list();
		if(list == null || list.size() == 0){
			return;
		}
		p = pList.get(0);
		/**
		 * 贵公司的…（此处填写注册号）产品于…（此处填写时间，具体到分钟）被启动核查程序。
		 * 	请于10个工作日内提交被核查产品的说明材料，包括申辩理由、支持文件以及产品注册人认为有必要提交的其他材料。                      
		 * 核查工作组成员为…,…,…,产品注册人认为工作组成员有利害关系可能影响其公正性、须回避的，必须在收到本通知后2
		 * 		个工作日内提出回避申请并说明理由，否则视为不申请回避。
		 */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!(Constants.VERIFYS_STATUS_0+"").equals(info.getVerifyStatus())){
			String title = "发起产品核查";
			String text = "贵公司的%s产品于%s被启动核查程序。请于10个工作日内提交被核查产品的说明材料，包括申辩理由、支持文件以"
					+ "及产品注册人认为有必要提交的其他材料。核查工作组成员为%s产品注册人认为工作组成员有利害关系可能影响其公正性、须回避的，"
					+ "必须在收到本通知后2个工作日内提出回避申请并说明理由，否则视为不申请回避。";
			text = String.format(text,p.getProductNO(),format.format(new Date()),"xxxxx");
			// 发送站内信
			receiverService.insertMess(title, text, info.getCurrent_user_id(), p.getUser().getUser().getId());
			
		}
		
	}
	
	@Override
	public void updateComplain4(VerifysInfo info) throws Exception {
		Criteria  c = verifysDaoImpl.getSession().createCriteria(Verifys.class);
		c.add(Restrictions.eq("id", info.getVerifys().getId()));
		List<Verifys> list = c.list();
		Verifys com = null;
		Product p = productService.findProductByProductNO(info.getProductNO());
		if(c.list() != null && c.list().size()>0){
			com = list.get(0);
			com.setModifiedTime(new Date());
			// 0 4 3 
			if("0,4,3".contains(info.getVerifyStatus())){
				com.setStatus(Constants.VERIFYS_STATUS_5);
				if("3".contains(info.getVerifyStatus())){
					// 添加问题产品标示
					p.setProductFlag(1);
				}
			}else{
				// 判断该流程是否为复议流程，如果是，则注销产品，修改状态为已核查；否则将状态修改为 待复议
				if(com.getReviewNum() > 0){
					com.setStatus(Constants.VERIFYS_STATUS_5);
					p.setLogoutType("1");
					p.setStatus(4);
				}else{
					com.setStatus(Constants.VERIFYS_STATUS_4);
				}
			}
			com.setResult(Integer.parseInt(info.getVerifyStatus()));
			com.setResultDate(new Date());
			verifysDaoImpl.update(com);
		}
		productService.saveProduct(p);
		
		/**
		 * 贵公司的…（此处填写注册号）产品的核查结果如下：（由协会填写）
		 */
		String title = "核查结果通知";
		String text = "贵公司的%s产品的核查结果如下:%s";
		text = String.format(text,p.getProductNO(),info.getStatusString());
		// 发送站内信
		receiverService.insertMess(title, text, info.getCurrent_user_id(),p.getUser().getUser().getId());
		
		/*消息接口
		  state:    对应Constants 里的【消息表单初始状态】
		  createId: 当前用户ID
		  title:    栏目名称或标题
		  subtitle: 子标题
		  content:  内容
		*/
		String subtitle= "%s产品为%s有问题！";
		subtitle = String.format(subtitle, p.getProductNO(),p.getProductName()); 
		newsService.saveNews(
				Integer.parseInt(info.getVerifyStatus()), 
				info.getCurrent_user_id(), 
				subtitle, 
				info.getRemark());
	}

	@Override
	public void updateComplainByUser3(VerifysInfo info) throws Exception {
		Criteria  c = verifysDaoImpl.getSession().createCriteria(Verifys.class);
		c.add(Restrictions.eq("id", info.getVerifys().getId()));
		List<Verifys> list = c.list();
		Verifys com = null;
		if(c.list() != null && c.list().size()>0){
			com = list.get(0);
			com.setModifiedTime(new Date());
			com.setStatus(Constants.VERIFYS_STATUS_3);
			verifysDaoImpl.update(com);
		}
		
		Product p = null;
		Criteria  crit = productDao.getSession().createCriteria(Product.class);
		crit.add(Restrictions.eq("productNO", info.getProductNO()));
		List<Product> pList = crit.list();
		if(list == null || list.size() == 0){
			return;
		}
		p = pList.get(0);
		/**
		 * …公司的书面核查说明材料于…（此处填写时间，具体到分钟）已上传[查看详情]
		 */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = "核查材料上传";
		String text = "%s公司的书面核查说明材料于%s已上传";
		text = String.format(text,"xxxxxxx",format.format(new Date()));
		// 发送站内信
		receiverService.insertMess(title, text, info.getCurrent_user_id(),1l);
	}
	
	@Override
	public String updateComplainByUser4(VerifysInfo info) throws Exception {
		String msg ="";
		Criteria  c = verifysDaoImpl.getSession().createCriteria(Verifys.class);
		c.add(Restrictions.eq("id", info.getVerifys().getId()));
		List<Verifys> list = c.list();
		Verifys com = null;
		if(c.list() != null && c.list().size()>0){
			com = list.get(0);
			if(com.getReviewNum() > 0){
				msg = "发起复查失败，一次核查只能发起一起复查...";
				return msg;
			}
			com.setCreateTime(new Date());
			// 1/开启复议  0/无需复议
			if(info.getVerifyStatus().equals("1")){
				// 将核查状态有 待复查 修改为 待核查
				com.setStatus(Constants.VERIFYS_STATUS_1);
				com.setReviewNum(com.getReviewNum()+1);
			}else{
				com.setStatus(Constants.VERIFYS_STATUS_5);
			}
			com.setModifiedTime(new Date());
			verifysDaoImpl.update(com);
		}
		
		Product p = null;
		Criteria  crit = productDao.getSession().createCriteria(Product.class);
		crit.add(Restrictions.eq("productNO", info.getProductNO()));
		List<Product> pList = crit.list();
		if(list != null && list.size() > 0){
			p = pList.get(0);
			/**
			 * …公司于…（此处填写时间，具体到分钟）提出核查复议申请[查看详情]
			 */
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String title = "核查复议";
			String text = "%s公司于%s提出核查复议申请";
			text = String.format(text,p.getUser().getCompanyName(),format.format(new Date()));
			// 发送站内信
			receiverService.insertMess(title, text, info.getCurrent_user_id(),1l);
		}
		return msg;
	}
	/**
	 * 核查状态跑批（没有测试）
	 */
	@Override
	public void updateVerifysStatus() {
		StringBuffer sql = new StringBuffer();
		// 1、执行跑批 将 核查状态 待举证 修改为 待定论 （条件为 待举证状态下12小时内，产品注册人未操作）
		sql.append(" UPDATE biz_verifys v ");
		sql.append(" SET v. STATUS = '3' ");
		sql.append(" WHERE ");
		sql.append(" 	v. STATUS = '2' ");
		sql.append(" AND UNIX_TIMESTAMP(SYSDATE()) * 1000 - UNIX_TIMESTAMP(v.modified_time) * 1000 > 12 * 60 * 60 * 1000 ");
		verifysDaoImpl.getSession().createSQLQuery(sql.toString()).executeUpdate();
		// 执行跑批 讲 核查状态 待复议 修改为 已审核 
		// 2、第一次待复议状态，判断在12小时后产品注册人未操作申请复议，直接注销该产品，并将核查状态修改为已核查
		sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" 	v.id, ");
		sql.append(" 	v.productNo ");
		sql.append(" FROM ");
		sql.append(" 	biz_verifys v ");
		sql.append(" WHERE ");
		sql.append(" 	v. STATUS = '4' ");
		sql.append(" AND UNIX_TIMESTAMP(SYSDATE()) * 1000 - UNIX_TIMESTAMP(v.modified_time) * 1000 > 12 * 60 * 60 * 1000 ");
		sql.append(" AND v.reviewNum = 0 ");
		List<Object[]> list = verifysDaoImpl.getSession().createSQLQuery(sql.toString()).list();
		if(list != null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				String id = ((BigInteger) list.get(i)[0]).toString();
				String productNO = (String) list.get(i)[1];
				// 修改产品状态为 注销产品
				sql = new StringBuffer();
				sql.append(" update biz_product p set p.status = '4' where p.productNO = '").append(productNO).append("' ");
				verifysDaoImpl.getSession().createSQLQuery(sql.toString()).executeUpdate();
				// 修改核查状态为 核查结束
				sql = new StringBuffer();
				sql.append(" update biz_verifys v set v.status = '5' where id = ").append(id);
				verifysDaoImpl.getSession().createSQLQuery(sql.toString()).executeUpdate();
			}
		}
		// 没有第二次复议的跑批（待定论状态提交后，判断第一次还是第二次，如果为第二次就直接“注销产品”或“其他正常情况”，此时核查状态修改为已核查，就不让产品注册人操作后面的了）
		
		// 3、产品核查结果为“风险提示注册人”、“通报问题产品”时，15日后如果注册人自己没有注销产品，则强制注销产品，并发送站内信
		sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" 	v.id, ");
		sql.append("   v.productNO ");
		sql.append(" FROM ");
		sql.append(" 	biz_verifys v ");
		sql.append(" WHERE ");
		sql.append(" 	v. STATUS = 5 ");
		sql.append(" AND v.result IN (3, 4) ");
		sql.append(" AND UNIX_TIMESTAMP(SYSDATE()) * 1000 - UNIX_TIMESTAMP(v.modified_time) * 1000 > 15 * 24 * 60 * 60 * 1000 ");
		sql.append(" and v.logoutType <> 1 ");
		list = verifysDaoImpl.getSession().createSQLQuery(sql.toString()).list();
		if(list != null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				String id = ((BigInteger) list.get(i)[0]).toString();
				String productNO = (String) list.get(i)[1];
				// 修改产品状态为 注销产品
				sql = new StringBuffer();
				sql.append(" update biz_product p set p.status = '4' where p.productNO = '").append(productNO).append("' ");
				verifysDaoImpl.getSession().createSQLQuery(sql.toString()).executeUpdate();
				
				// 发送站内信
				// 查询该产品注册人ID
				sql = new StringBuffer();
				sql.append(" SELECT ");
				sql.append(" 	u.id ");
				sql.append(" FROM ");
				sql.append(" 	biz_product p ");
				sql.append(" LEFT JOIN biz_member_user m ON m.registeredCode = p.registeredCode ");
				sql.append(" LEFT JOIN sys_users u ON m.user_id = u.id ");
				sql.append(" WHERE ");
				sql.append(" 	p.productNO = '").append(productNO).append("' ");
				List list_new = verifysDaoImpl.getSession().createSQLQuery(sql.toString()).list();
				if(null != list && list.size()>0){
					String id_new = (list_new.get(0)).toString();
					/**
					 * 贵公司的…（此处填写注册号）产品的核查结果如下：（由协会填写）
					 */
					String title = "核查结果通知";
					String text = "贵公司的%s产品的核查结果如下:%s";
					text = String.format(text,productNO,"注销产品");
					// 发送站内信
					try {
						receiverService.insertMess(title, text, 1l,Long.parseLong(id_new));
						// 修改核查发送站内信状态 为已发送，避免跑批重复发送
						sql = new StringBuffer();
						sql.append(" update biz_verifys v set v.logoutType = 1 where v.id = ").append(id);
						verifysDaoImpl.getSession().createSQLQuery(sql.toString()).executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
	}
	


}

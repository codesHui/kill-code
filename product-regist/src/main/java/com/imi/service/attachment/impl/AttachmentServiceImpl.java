package com.imi.service.attachment.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.dao.products.IProductFileDao;
import com.imi.dao.setting.IAttachmentDao;
import com.imi.entity.products.ProductFile;
import com.imi.entity.setting.Attachment;
import com.imi.model.product.AttachmentInfo;
import com.imi.service.attachment.IAttachmentService;
import com.imi.support.Constants;

/**
 * The Class AttachmentServiceImpl.
 */
@Service
public class AttachmentServiceImpl extends BaseDataServiceImpl<Attachment, AttachmentInfo> implements IAttachmentService {
	
	/**
	 * The Constant logger.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(AttachmentServiceImpl.class);
	
	/**
	 * The attachment dao impl.
	 */
	@Resource
	private IAttachmentDao attachmentDaoImpl;
	
	/**
	 * The product file dao impl.
	 */
	@Resource
	private IProductFileDao productFileDaoImpl;
	
	
	/* (non-Javadoc)
	 * @see com.imi.base.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Attachment> find(AttachmentInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.imi.base.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AttachmentInfo changeModel(Attachment data) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.imi.base.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(AttachmentInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.imi.base.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AttachmentInfo update(AttachmentInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.imi.base.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		
	}
	
	

	/**
	 * 逻辑删除
	 */
	@Override
	public void delete(Long id) throws Exception {
		Attachment attachment = new Attachment();
		attachment.setId(id);
		String hql = " UPDATE ProductFile p SET p.status = :status  WHERE p.attachment.id = :id ";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", Constants.ATTACHMENT_NO);
		map.put("id", id);
		productFileDaoImpl.executeUpdate(hql, map);
		
	}

	/* (non-Javadoc)
	 * @see com.imi.service.attachment.IAttachmentService#save(com.imi.entity.setting.Attachment)
	 */
	@Override
	public Long save(Attachment attachment) throws Exception {
		ProductFile p = new ProductFile();
		p.setAttachment(attachment);
		p.setCreateTime(new Date());
		p.setStatus(Constants.ATTACHMENT_YES);
		p.setType(Constants.PRODUCT_SIGN);
//		p.setCreator();
		productFileDaoImpl.save(p);
		return (Long)attachmentDaoImpl.save(attachment);
	}

	/* (non-Javadoc)
	 * @see com.imi.service.attachment.IAttachmentService#saveReal(com.imi.entity.setting.Attachment, java.lang.String, java.lang.String)
	 */
	@Override
	public Long saveReal(Attachment attachment, String productNO,String type)
			throws Exception {
		ProductFile p = new ProductFile();
		p.setAttachment(attachment);
		p.setProductNO(productNO);
		p.setCreateTime(new Date());
		p.setStatus(Constants.ATTACHMENT_YES);
		p.setType(Integer.parseInt(type));
//		p.setCreator();
		productFileDaoImpl.save(p);
		return (Long)attachmentDaoImpl.save(attachment);
	}
	
	/**
	 * 
	 */
	public Attachment findSIGNPDF(String  productNO) throws Exception{
		return findFile(productNO, Constants.PRODUCT_SIGN,Constants.ATTACHMENT_FILETYPE_PDF);  
		}
	
	
	/**
	 * @param productNO 
	 * @param type  1 :产品注册 ，2:产品复查，3:产品核查,4:产品投诉 
	 *  @param fileType  1:注册条款(PDF)    0:其他
	 * @return
	 */
	@Override
	public Attachment findFile(String  productNO,int type,String fileType) {
		String hql = " select att from ProductFile as  p    join  p.attachment  as  att where p.productNO = ? and p.type = ?  and  att.filetype= ? ";
	List<Attachment> list = productFileDaoImpl.find(hql,productNO,type,fileType);
		if(list == null || list.size() == 0){
			return null;
		}
		/*for (int i = 0; i < list.size(); i++) {
			if(String.valueOf(Constants.PRODUCT_SIGN).equals(list.get(i).getAttachment().getFiletype())){
				return list.get(i).getAttachment();
			}
		}*/

		return list.get(0);
		
	}

	/**
	 * @param productNO 
	 * @return
	 */
	public List<Attachment> findAllSIGNFile(String productNO) {
		
		return findAllFile( productNO,Constants.PRODUCT_SIGN) ;
	}
	/**
	 * @param productNO 
	 * @param type  1 :产品注册 ，2:产品复查，3:产品核查,4:产品投诉 
	 * @return
	 */
	@Override
	public List<Attachment> findAllFile(String productNO,int type) {
	final 	String hql = " select att from ProductFile p    join  p.attachment att  where p.productNO = ? and p.type = ?  and   att.type= ?";
		return productFileDaoImpl.find(hql, productNO,type,Constants.ATTACHMENT_UPLOAD_TYPE_SUCC);
		/*	List<Attachment> listAttachment =new ArrayList<Attachment>();
		if(list == null || list.size() == 0){
			return null;
		}else{
			for (int i = 0; i < list.size(); i++) {
				if(String.valueOf(Constants.PRODUCT_SIGN).equals(list.get(i).getAttachment().getType())){
					listAttachment.add(list.get(i).getAttachment()) ;
				}
			}
			return listAttachment;
		}
		
	}*/
	}


	 
}

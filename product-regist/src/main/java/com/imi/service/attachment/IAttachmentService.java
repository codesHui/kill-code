/**
*@Project: product-regist
*@Author: josh
*@Date: 2015-7-20
*@Copyright: 2015 www.xxx.com Inc. All rights reserved.
*/
package com.imi.service.attachment;

import java.util.List;

import com.imi.entity.setting.Attachment;

/**
 * The Interface IAttachmentService.
 */
public interface IAttachmentService {
	
	/**
	 * Delete.
	 *
	 * @param id the id
	 * @throws Exception the exception
	 */
	void delete(Long id) throws Exception;

	/**
	 * Save.
	 *
	 * @param attachment the attachment
	 * @return the long
	 * @throws Exception the exception
	 */
	Long save(Attachment attachment)throws Exception;

	/**
	 * Save real.
	 *
	 * @param attachment the attachment
	 * @param productNo the product no
	 * @param type the type
	 * @return the long
	 * @throws Exception the exception
	 */
	Long saveReal(Attachment attachment, String productNo,String type)throws Exception;
	
	/**
	 * Find file.
	 *
	 * @param product the product
	 * @param type the type
	 * @param fileType the file type
	 * @return the attachment
	 * @throws Exception the exception
	 */
	public Attachment findFile(String  productNO,int type,String fileType) throws Exception;

	/**
	 * 查找注册时PDF文件
	 *
	 * @param product the product
	 * @return the attachment
	 * @throws Exception the exception
	 */
	public Attachment findSIGNPDF(String  productNO) throws Exception;
	
	
	/**
	 * Find all sign file.
	 *
	 * @param productNO the product no
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<Attachment> findAllSIGNFile(String  productNO) throws Exception;
	/**
	 * Find all file.
	 *
	 * @param productId the product id
	 * @param type the type
	 * @return the list
	 */
	List<Attachment> findAllFile(String productId,int type);

}

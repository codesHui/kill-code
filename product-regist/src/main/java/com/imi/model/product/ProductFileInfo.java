package com.imi.model.product;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.imi.entity.setting.Attachment;
import com.softvan.model.Paging;
/**
 * 产品信息。
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ProductFileInfo extends Paging {
	private static final long serialVersionUID = 1L;

	private String productNO;// 产品编号

	private int type; // 1 :产品注册 ，2:产品复查，3:产品核查,4:产品投诉

	private int status;// 状态 1 是生效，状态2 是 未生效
	
	private String extension;//文件类型 

	private Attachment attachment; // 附件数据

	public String getProductNO() {
		return productNO;
	}

	public void setProductNO(String productNO) {
		this.productNO = productNO;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
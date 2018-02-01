package com.imi.support.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.imi.entity.products.ProductHistroy;
import com.imi.entity.setting.Attachment;
import com.imi.service.products.IProductService;
import com.imi.support.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("productHistoryJson")
public class ProductHistoryJsonDirective implements TemplateDirectiveModel {
	public static final String FORMAT_YMDHMS = "yyyy-MM-dd";
	
	  private static final String PARAM_TYPE = "type"; 
	  private static final String PARAM_PRODUCT_NO = "productNO"; 
	  private static final String PARAM_PRODUCT_File = "showFile";
	 
	@Resource
	private IProductService productService;
	private SimpleDateFormat sf = new SimpleDateFormat(FORMAT_YMDHMS);
	private static Map<Integer, String> typeMap = new HashMap<Integer, String>();
	static {
		typeMap.put(1, "注册记录");
		typeMap.put(2, "复查记录");
		typeMap.put(3, "核查记录");
		typeMap.put(4, "投诉记录");
		typeMap.put(5, "复议记录");
	}

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		 String productNO = DirectiveUtils.getString(PARAM_PRODUCT_NO, params);
		 Integer type = DirectiveUtils.getInt(PARAM_TYPE, params); 
		 Boolean showfile = DirectiveUtils.getBool(PARAM_PRODUCT_File,
				  params);
			
	/*		System.out.println("productNO"+productNO);
		
			System.out.println("type"+type);*/

		

			ProductHistroy productHistory = new ProductHistroy();
			if(productNO!=null){
				productHistory.setProductNO(productNO);	
			}

		if(type!=null){
				productHistory.setType(type);
			}
			
		if(showfile==null){
			
			showfile=false;
		}
			
	
		List<ProductHistroy> list = productService
				.findallProductLog(productHistory);

		Writer out = env.getOut();

		JsonGenerator g = null;
		StringWriter stringWriter = new StringWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		g = objectMapper.getJsonFactory().createJsonGenerator(stringWriter);
		// g.writeObject(list);
		g.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
		g.writeStartObject();
		g.writeArrayFieldStart("data");
		for (ProductHistroy ph : list) {
			g.writeStartObject();

			g.writeNumberField("id", ph.getId());
			g.writeStringField("type", typeMap.get(ph.getType()));
			g.writeStringField("actionName", ph.getActionName());
			g.writeStringField("operPerson", ph.getOperPerson());
			g.writeStringField("actionCode", ph.getActionCode());
			g.writeStringField("content", ph.getContent());
			g.writeStringField("createTime", sf.format(ph.getCreateTime()));
		if(showfile&&ph.getAttachment()!=null){
				if(ph.getAttachment().size()>0){
			Attachment att=ph.getAttachment().get(0);
			g.writeStringField("filePath",att.getFilePath());	
			g.writeStringField("fileName",att.getName());	
				}
			}
			
			
			
			
			g.writeEndObject();
		}
		g.writeEndArray();
		g.writeEndObject();

		g.close();
		out.write(stringWriter.toString());
		

	}

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public static void main(String[] args) throws IOException {
		JsonGenerator g = null;
		StringWriter stringWriter = new StringWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		g = objectMapper.getJsonFactory().createJsonGenerator(stringWriter);
		// g.writeObject(list);
		g.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
		g.writeStartObject();
		g.writeArrayFieldStart("data");
		// for( ProductHistroy ph:list){
		g.writeStartObject();
	//	System.out.println("ddddddddddddddddddddddddd" + "333333333333");
		g.writeNumberField("f_id", 1111);
		g.writeStringField("f_abbr", "222222");
		g.writeEndObject();
		// }
		g.writeEndArray();
		g.writeEndObject();

		g.close();
	//	System.out.println(stringWriter.toString());
	}

}

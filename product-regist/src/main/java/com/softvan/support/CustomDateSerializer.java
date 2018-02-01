package com.softvan.support;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
/**
 * 自定义日期格式JSON化。
 */
public  class CustomDateSerializer extends JsonSerializer<Date> {
	//时间格式
	private static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss",SHORT_DATE_FORMAT  = "yyyy-MM-dd";
	//长日期格式（yyyy-MM-dd HH:mm:ss） 
	private final static LongDate Long = new LongDate();
	/*
	 * 格式转化。
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
	 */
	@Override
	public void serialize(Date value, JsonGenerator jgen,SerializerProvider provider) throws IOException,JsonProcessingException {
		if(value != null){
			Long.serialize(value, jgen, provider);
		}
	}
	/**
	 * 格式化日期数据。
	 * @param value
	 * @param jgen
	 * @param format
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	protected void serialize(Date value, JsonGenerator jgen, String format) throws JsonGenerationException, IOException{
		if(value != null){
			SimpleDateFormat formatter = new SimpleDateFormat(format); 
			jgen.writeString(formatter.format(value));
		}
	}
	/**
	 * 长时间格式。
	 * @author josh。
	 * @since 2014-08-06。
	 */
	public static class LongDate extends CustomDateSerializer{
		/*
		 * 时间格式序列化。
		 * @see com.softvan.support.CustomDateSerializer#serialize(java.util.Date, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
		 */
		@Override
		public void serialize(Date value, JsonGenerator jgen,SerializerProvider provider) throws IOException,JsonProcessingException {
			if(value != null){
				this.serialize(value, jgen, LONG_DATE_FORMAT);
			}
		}
	}
	/**
	 * 短时间格式。
	 * @author josh。
	 * @since 2014-08-06。
	 */
	public static class ShortDate extends CustomDateSerializer{
		/*
		 * 时间格式序列化。
		 * @see com.softvan.support.CustomDateSerializer#serialize(java.util.Date, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
		 */
		@Override
		public void serialize(Date value, JsonGenerator jgen,SerializerProvider provider) throws IOException,JsonProcessingException {
			if(value != null){
				this.serialize(value, jgen, SHORT_DATE_FORMAT);
			}
		}
	}
}
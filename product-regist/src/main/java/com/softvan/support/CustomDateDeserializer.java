package com.softvan.support;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * 
 */
public class CustomDateDeserializer extends JsonDeserializer<Date> {
	//时间格式
	private static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss",SHORT_DATE_FORMAT  = "yyyy-MM-dd";
	//长日期格式（yyyy-MM-dd HH:mm:ss） 
	private final static LongDate Long = new LongDate();
	/*
	 * 格式转化。
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
	 */
	public Date deserialize(JsonParser arg0, DeserializationContext arg1,String format)
			throws IOException, JsonProcessingException, ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format); 
		return formatter.parse(arg0.getText());
	}
	/**
	 * 长时间格式。
	 * @author josh。
	 * @since 2014-08-06。
	 */
	public static class LongDate extends CustomDateDeserializer{
		/*
		 * 时间格式序列化。
		 * @see com.softvan.support.CustomDateSerializer#serialize(java.util.Date, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
		 */
		@Override
		public Date deserialize(JsonParser arg0, DeserializationContext arg1)
				throws IOException, JsonProcessingException {
			try {
				return this.deserialize(arg0, arg1, LONG_DATE_FORMAT);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	/**
	 * 短时间格式。
	 * @author josh。
	 * @since 2014-08-06。
	 */
	public static class ShortDate extends CustomDateDeserializer{
		/*
		 * 时间格式序列化。
		 * @see com.softvan.support.CustomDateSerializer#serialize(java.util.Date, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
		 */
		@Override
		public Date deserialize(JsonParser arg0, DeserializationContext arg1)
				throws IOException, JsonProcessingException {
			try {
				return this.deserialize(arg0, arg1, SHORT_DATE_FORMAT);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	@Override
	public Date deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		try {
			return Long.deserialize(arg0, arg1, LONG_DATE_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
